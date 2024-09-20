/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>
#include <fcntl.h>
#include <sys/prctl.h>  // Per far terminare il figlio quando muore il padre
#include <stdbool.h>

#include "errExit.h"
#include "shared_memory.h"
#include "semaphore.h"
#include "terminal.h"
#include "playground.h"
#include "graphic.h"

/**
 * DEFINE
 */
#define clear() write(STDOUT_FILENO, "\033c", strlen("\033c"))  // Clear terminal        

/**
 * GLOBAL VARS
 */
int semWaitController;
int semWaitMove;
int shmGraphicDataId;
key_t gameKey;
GraphicData *graphicData;

// Graphic 
int tickCount = 0, prevInputStatus = 0;


/**
 * @brief Handler che viene eseguito quanto il processo chiama exit().
 *        Invia un segnale al processo Server e termina
 */
void exitHandler();

/**
 * @brief Handler del segnale SIGUSR1 (arriva dal Server).
 *        Stampa il messaggio di Vittoria / Sconfitta / Pareggio e termina il gioco.
 */
void SIGUSR1_handler();

/**
 * @brief Handler dei segnali (non quelli definiti dall'utente).
 *        Notifica il Server e termina il processo
 */
void sigQuitHandler();

/**
 * @brief Handler del segnale SIGUSR2 (arriva dal Server).
 *        Termina il processo
 */
void SIGUSR2_handler();

/**
 * @brief Handler dei segnali per il processo figlio.
 *        Termina il processo
 */
void sigHandlerChild();

/**
 * @brief Funzione di supporto per bloccare il segnale SIGUSR2.
 *        Serve per non generare errori nella terminazione del Client
 *        (invia un segnale al Server ed il Server gliene manda un altro)
 */
void blockSIGUSR2();

/**
 * @brief Imposta un file descriptor in modo che la richiesta di input non sia bloccante
 * 
 * @param fd File descriptor
 */
void setupNonBlockRead(int fd);

/**
 * @brief Rimuove tutto il buffer di un file descriptor
 * 
 * @param fd File descriptor
 */
void clearFd(int fd);

/**
 * @brief Stampa il campo da gioco
 */
void draw();


int main(int argc, char **argv) {
    // Controllo gli argomenti
    if(argc < 3 || argc > 5) {
        printf("Usage: <exe> <Server Key> <Nome utente> <Bot option (\\*)> <Bot Log>\n");
        exit(EXIT_FAILURE);
    }

    // Mi salvo il nome dell'utente
    char *name = argv[2];

    // Controllo che il nome non sia troppo lungo per la zona di memoria condivisa
    if(strlen(name) > 20) {
        printf("Name too long (max %d characters)\n", MAX_NAME);
        exit(EXIT_FAILURE);
    }

    bool isBot = false;     // Indica se il giocatore è un bot
    bool botLog = false;    // Indica se il bot deve stampare messaggi nel Terminale

    // Controllo sull'opzione Bot
    if(argc >= 4) {
        if(strcmp(argv[3], "*") != 0) {
            printf("Invalid Bot option\tCorrect usage: \\*\n");
            exit(EXIT_FAILURE);
        }
        else
            isBot = true;

        // Se è presente l'opzione del Log del bot
        if(argc == 5) {
            if(strcmp(argv[4], "on") != 0 && strcmp(argv[4], "off") != 0) {
                printf("Invalid Bot Log option: must be either \"on\" or \"off\"\n");
                exit(EXIT_FAILURE);
            }
            botLog = strcmp(argv[4], "on") == 0 ? true : false;
        }
    }

    // Implemento signal handlers per entrambi i processi (padre e figlio)
    if(signal(SIGINT, sigQuitHandler) == SIG_ERR)   errExit("Changing SIGINT handler failed");  // Ctrl + C
    if(signal(SIGHUP, sigQuitHandler) == SIG_ERR)   errExit("Changing SIGHUP handler failed");  // Quando chiude il terminale

    // Converto l'ID della zona di memoria
    gameKey = atoi(argv[1]);
    if(gameKey <= 0) { 
        printf("Game Key must be greater than 0");
        exit(EXIT_FAILURE);
    }

    // Ottengo il puntatore alla zona di memoria
    int gameId = alloc_shared_memory(gameKey, sizeof(Data)); 
    ptr = (Data *) get_shared_memory(gameId, 0);

    // Controllo se la partita è già iniziata
    if(ptr->playerCount == 2) {
        printf("È in corso un'altra partita - Riprovare più tardi\n");
        exit(EXIT_FAILURE);
    }

    // Inizializzo l'area di memoria del campo da gioco
    getPlayground(ptr->playgroundKey);

    // Inizializzo il semaforo che permette al padre di aspettare che il controller effettui la connessione
    semWaitController = get_semaphore(IPC_PRIVATE, 1);  // Wc
    initialize_semaphore(semWaitController, 0, 0);

    // Inizializzo il semaforo che permette al controller di aspettare che venga effettuata la mossa dall'utente
    semWaitMove = get_semaphore(IPC_PRIVATE, 1);    // Wm
    initialize_semaphore(semWaitMove, 0, 0);

    // Creo un area di memoria per gestire l'interazione tra padre e figlio
    shmGraphicDataId = alloc_shared_memory(IPC_PRIVATE, sizeof(GraphicData));

    int pid = fork();

    if(pid == -1)   errExit("Fork failed");

    if (pid == 0) {
        if(signal(SIGINT, sigHandlerChild) == SIG_ERR)   errExit("Changing SIGINT handler failed");
        if(signal(SIGHUP, sigHandlerChild) == SIG_ERR)   errExit("Changing SIGHUP handler failed");
        
        // Imposto che il figlio riceva un segnale SIGTINT quando il padre muore
        if(prctl(PR_SET_PDEATHSIG, SIGINT) == -1)  errExit("Prctl failed");

        // Mi collego alla zona di memoria per l'interazione
        graphicData = (GraphicData *) get_shared_memory(shmGraphicDataId, 0);

        // Mi salvo i set di semafori (per semplicità di scrittura)
        int semConnId = get_semaphore(ptr->semConnKey, ptr->semConnSize);
        int semPlayId = get_semaphore(ptr->semPlayKey, ptr->semPlaySize);

        // -- Connessione al Server --
        semOp(semConnId, 0, 1);     // V(Sc)
        semOp(semConnId, 1, -1);    // P(Cc)
        graphicData->playerId = ptr->playerNum; // mi salvo il mio numero (0 o 1)
        strcpy(ptr->playersName[graphicData->playerId], name); // Carico il nome
        ptr->playersPid[graphicData->playerId] = getppid();     // Carico il PID
        semOp(semConnId, 2, 1);     // V(Fc)
        // -- Fine inizializzazione della connessione --
        
        ptr->isBot[graphicData->playerId] = isBot;
        if(isBot)
            ptr->botLog[graphicData->playerId] = botLog;

        semOp(semWaitController, 0, 1); // V(Wc) -> Sblocca il padre

        // -- Inizio del gioco --
        if(isBot)
            exit(EXIT_SUCCESS);

        do {
            while(ptr->turn != graphicData->playerId);  // aspetta il proprio turno

            semOp(semPlayId, 0, -1);    // P(Sg)

            // Se la colonna è piena ricalcola la prossima colonna
            if (columnIsFull(graphicData->currentColumn))
                graphicData->currentColumn = nextFreeColumn(graphicData->currentColumn);
            
            // Abilita l'input nel padre e aspetta la mossa
            graphicData->inputEnabled = 1;
            semOp(semWaitMove, 0, -1);  // P(Wm)

            // Imposta la mossa effettuata
            ptr->mossa = graphicData->currentColumn;

            semOp(semPlayId, 1, -1);    // P(Fg)
        } while(1);
    
    } else {
        // Padre

        // Implemento exit e signal handlers 
        if(signal(SIGUSR1, SIGUSR1_handler) == SIG_ERR)   errExit("Changing SIGUSR1 handler failed");  // Quando riceve il segnale dal Server
        if(signal(SIGUSR2, SIGUSR2_handler) == SIG_ERR)   errExit("Changing SIGUSR2 handler failed");  // Quando riceve il segnale dal Server
        if(atexit(exitHandler) != 0) errExit("AtExit failed");

        // Setup del terminale
        setupTerminal();
        setupNonBlockRead(STDIN_FILENO);

        // Mi collego alla zona di memoria per l'interazione
        graphicData = (GraphicData *) get_shared_memory(shmGraphicDataId, 0);

        semOp(semWaitController, 0, -1); // P(Wc) -> Aspetta la connessione del controller

        while(1) draw();
    }

    exit(EXIT_SUCCESS);
}

void exitHandler() {
    // Draw finale per far vedere la situazione
    draw();

    // Ripristino le impostazioni del terminale
    unsetTerminal();

    // Elimino gli IPC usati
    remove_semaphore(semWaitController);
    remove_semaphore(semWaitMove);
    free_shared_memory(graphicData);
    remove_shared_memory(shmGraphicDataId);

    // Ottengo il semaforo e sveglio il server
    int semCloseId = get_semaphore(ptr->semCloseKey, ptr->semCloseSize);
    semOp(semCloseId, 0, 1);
}

void SIGUSR1_handler() {
    // Blocco il segnale SIGUSR2 e invio un messaggio al Server
    blockSIGUSR2();

    exit(EXIT_SUCCESS);
}

void sigQuitHandler() {
    // Invio un segnale al Server dire che un Client si è disconnesso
    blockSIGUSR2();

    ptr->playerQuitNum = graphicData->playerId;

    kill(ptr->serverPid, SIGUSR2);

    exit(EXIT_FAILURE);
}

void SIGUSR2_handler() {
    exit(EXIT_FAILURE);
}

void sigHandlerChild() {
    exit(EXIT_FAILURE);
}

void blockSIGUSR2() {
    sigset_t blockedS2Set;
    if(sigaddset(&blockedS2Set, SIGUSR2) == -1) errExit("SigAddSet failed");
    if(sigprocmask(SIG_SETMASK, &blockedS2Set, NULL) == -1) errExit("SigProcMask failed");
}

void setupNonBlockRead(int fd) {
    // Get current flag
    int flags = fcntl(fd, F_GETFL, 0);

    // Set the old flag + NONBLOCK flag
    fcntl(fd, F_SETFL, flags | O_NONBLOCK);
}

void clearFd(int fd) {
    char temp[10];
    while (read(fd, temp, 10) != -1);
}

void draw() {
    winsize sz;
    char bufferStdin[3];

    // Salva la grandezza del terminale in sz
    ioctl(0, TIOCGWINSZ, &sz);
    
    /** 
     * Se è appena stato impostato l'input pulisce tutto il buffer del stdin
     * per evitare che esegua comandi digitati mentre non era il proprio turno
    */
    if (prevInputStatus == 0 && graphicData->inputEnabled == 1) 
        clearFd(0);

    int chRead;
    while (graphicData->inputEnabled == 1 && (chRead = read(0, bufferStdin, 3)) != -1) {
        if (chRead == 3 && bufferStdin[0] == '\033' && bufferStdin[1] == '[') {
            // Right arrow
            if (bufferStdin[2] == 'C')
                graphicData->currentColumn = nextFreeColumn(graphicData->currentColumn);

            // Left arrow
            if (bufferStdin[2] == 'D')
                graphicData->currentColumn = prevFreeColumn(graphicData->currentColumn);
        }else {
            while (chRead > 0) {
                // Enter
                if (bufferStdin[chRead - 1] == '\n') {
                    graphicData->inputEnabled = 0;
                    semOp(semWaitMove, 0, 1);   // V(Wm) -> sveglia il controller perchè ha effettuato la mossa
                }
                    
                chRead--;
            }
        }
    }

    prevInputStatus = graphicData->inputEnabled;

    char canvas[sz.ws_row * sz.ws_col * 3];
    _draw(canvas, sz.ws_row, sz.ws_col, tickCount);

    clear();
    write(1, canvas, strlen(canvas));
    fflush(stdout);

    tickCount++;
    usleep(TICK * 1000);
}
