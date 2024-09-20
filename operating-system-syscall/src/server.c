/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>
#include <sys/sem.h>
#include <string.h>
#include <sys/wait.h>

#include "errExit.h"
#include "shared_memory.h"
#include "semaphore.h"
#include "terminal.h"
#include "playground.h"

/**
 * DEFINE
 */
#define CTRLC_TIMER 3   // Dopo * secondi resetta il contatore del Ctrl + C

/**
 * GLOBAL VARS
 */
int shmId;  // Id della zona di memoria condivisa principale
int ctrlCounter = 0;    // Contatore per il Ctrl + C
int semConnId, semPlayId, semCloseId;   // Id dei set di semafori
int playgroundId;   // Id del playground

/**
 * @brief Notifica i Client che il gioco è finito (Il risultato si vede nello Status).
 *        Manda un segnale SIGUSR1 a ciascun processo Client
 */
void notifyEndToClient();

/**
 * @brief Handler che viene eseguito quando il processo chiama exit().
 *        Invia un segnale ai processi Client per farli terminare e rimuove tutti gli IPCs usati
 */
void exitHandler();

/**
 * @brief Stampa il risultato della partita (chi ha vinto o pareggiato)
 */
void printResults();

/**
 * @brief Data una path ad un eseguibile, ritorna la path della directory relativa
 * 
 * @param fullPath Path completa di un eseguibile
 * @param relativePath Path della directory
 */
void getRelativePath(char *fullPath, char *relativePath);

/**
 * @brief Handler che viene eseguito se il Client si disconnette.
 *        Termina il processo se il gioco era iniziato.
 */
void SIGUSR2_handler();

/**
 * @brief Handler che viene eseguito quando il processo riceve un segnale SIGINT (Ctrl + C).
 *        Termina il processo
 */
void SIGINT_handler();

/**
 * @brief Handler che viene eseguito quando il processo riceve un segnale SIGALRM.
 *        Resetta il contatore del Ctrl + C
 */
void SIGALRM_handler();

/**
 * @brief Handler per tutti i segnali tranne SIGUSR, SIGINT e SIGALRM.
 *        Termina il processo correttamente, eseguendo gli exit handler
 */
void sigHandler();

int main(int argc, char **argv) {
    // Controllo gli argomenti
    if(argc != 6) {
        printf("Usage: <exe> <n° rows> <n° columns> <player 1 symbol> <player 2 symbol> <Key>\n");
        exit(EXIT_FAILURE);
    }

    // Converto gli argomenti
    int rows = atoi(argv[1]), columns = atoi(argv[2]);
    if((rows == 0 && strcmp(argv[1], "0") != 0) || (columns == 0 && strcmp(argv[2], "0") != 0)) {
        printf("Playground size must be a number\n");
        exit(EXIT_FAILURE);
    }

    // Controllo la dimensione del campo da gioco
    if(rows < MIN_PG_SIZE || columns < MIN_PG_SIZE || rows > MAX_PG_SIZE || columns > MAX_PG_SIZE) {
        printf("Wrong Playground size - Min: %dx%d, Max: %dx%d\n", MIN_PG_SIZE, MIN_PG_SIZE, MAX_PG_SIZE, MAX_PG_SIZE);
        exit(EXIT_FAILURE);
    }

    // Controllo che i simboli siano di 1 carattere
    if(strlen(argv[3]) != 1 || strlen(argv[4]) != 1) {
        printf("Players' symbols must be 1 char long (e.g. O / X)\n");
        exit(EXIT_FAILURE);
    }

    // Controllo sulla Key della zona di memoria principale
    int key = atoi(argv[5]);
    if(key <= 0) {
        printf("Key must be greater than 0\n");
        exit(EXIT_FAILURE);
    }
    if(key > 1000000) {
        printf("Key must be less than 1'000'000\n");
        exit(EXIT_FAILURE);
    }

    // Preparo il terminale
    setupTerminal();

    // Modico l'handler dei segnali 
    if(signal(SIGUSR2, SIGUSR2_handler) == SIG_ERR)  errExit("Change SIGUSR2 handler failed");
    if(signal(SIGINT, SIGINT_handler) == SIG_ERR)  errExit("Change SIGINT handler failed");
    if(signal(SIGALRM, SIGALRM_handler) == SIG_ERR)  errExit("Change SIGALRM handler failed");
    if(signal(SIGHUP, sigHandler) == SIG_ERR)  errExit("Change SIGHUP handler failed");

    // Crea zona di memoria condivisa
    shmId = alloc_new_shared_memory(key, sizeof(Data));
    if(shmId == -1) errExit("Key already used");

    // Ottengo il puntatore alla zona di memoria
    ptr = (Data *) get_shared_memory(shmId, 0);

    // Aggiungo un exit handler
    if(atexit(exitHandler) != 0)    errExit("AtExit failed");

    // Inserisce il suo PID
    ptr->serverPid = getpid();

    // Salvo i simboli dei giocatori
    ptr->playersSymbol[0] = argv[3][0];
    ptr->playersSymbol[1] = argv[4][0];

    // Imposta 1o giocatore a 1 --> quando si connette viene messo a 0
    ptr->playerNum = 1;

    // Crea un set di semafori e mette la Key nella zona di memoria condivisa
    ptr->semConnSize = 3;
    semConnId = get_new_semaphore_findKey(&key, ptr->semConnSize);
    initialize_semaphore(semConnId, 0, 0);  // Sc --> Server aspetta che un Client si connetta
    initialize_semaphore(semConnId, 1, 0);  // Cc --> Server sveglia il Client quando ha finito di elaborare i dati
    initialize_semaphore(semConnId, 2, 0);  // Fc --> Client sveglia il Server quando ha finito di salvarsi i dati
    ptr->semConnKey = key;
    
    // Crea un set di semafori per il gioco e lo mette nella zona di memoria
    ptr->semPlaySize = 2;
    semPlayId = get_new_semaphore_findKey(&key, ptr->semPlaySize);
    initialize_semaphore(semPlayId, 0, 0);  // Sg --> Server dice al Client che può giocare
    initialize_semaphore(semPlayId, 1, 0);  // Fg --> Server dice al Client che ha finito l'elaborazione della mossa
    ptr->semPlayKey = key;

    // Crea un set di semafori per la sincronizzazione sulla chiusura e lo mette nella zona di memoria
    ptr->semCloseSize = 1;
    semCloseId = get_new_semaphore_findKey(&key, ptr->semCloseSize);
    initialize_semaphore(semCloseId, 0, 0);
    ptr->semCloseKey = key;

    // Crea il campo da gioco e lo inserisce nella zona di memoria condivisa
    playgroundId = buildPlayground(rows, columns, &key);

    // Imposta lo stato dei Client a Waiting
    ptr->status[0] = Waiting, ptr->status[1] = Waiting;

    // -- Comunica la Key --
    printf("Use this Key to connect to the Server: %s\n", argv[5]);

    // -- Aspetto i Client --
    do {
        semOp(semConnId, 0, -1);    // P(Sc)
        ptr->playerCount++;     // conteggio dei giocatori
        ptr->playerNum = !ptr->playerNum;   // numero del giocatore appena connesso
        semOp(semConnId, 1, 1);     // V(Cc)

        semOp(semConnId, 2, -1);    // P(F)

        printf("Player %d connected:\tName: %s%s\n", 
                    ptr->playerNum + 1, ptr->playersName[ptr->playerNum], ptr->isBot[ptr->playerNum] ? " (bot)" : "");
        fflush(stdout);
    } while(ptr->playerCount < 2);
    // -- Client collegati --

    ptr->status[0] = Playing, ptr->status[1] = Playing;
    ptr->mossa = -1;

    // -- Inizio del Gioco --
    do {
        // Giocatore normale
        if(!ptr->isBot[ptr->turn]) {
            semOp(semPlayId, 0, 1);     // V(Sg)

            // Timeout
            do {
                usleep(100 * 1000);   // ogni 100 ms aumenta il timer
                ptr->timer += 0.1;
            } while(ptr->timer <= MAX_TIMER && ptr->mossa == -1);

            // Se è in timeout
            if(ptr->timer >= MAX_TIMER) {
                ptr->status[ptr->turn] = TimeoutLose;
                ptr->status[!ptr->turn] = TimeoutWin;
                
                notifyEndToClient();

                exit(EXIT_SUCCESS);
            }
        }
        // Bot
        else {
            pid_t botPid = fork();
            if(botPid == -1)    errExit("Fork failed");

            // Bot
            if(botPid == 0) {
                // Converto gli argomenti necessari in stringa
                char playgroundKeyStr[10];
                sprintf(playgroundKeyStr, "%d", ptr->playgroundKey);
                
                char playerNumStr[] = {ptr->turn + '0', '\0'};
                char botLog[4];
                sprintf(botLog, "%s", ptr->botLog[ptr->turn] ? "on" : "off");

                char *args[] = {"F4Bot", argv[5], playgroundKeyStr, playerNumStr, botLog, (char *) NULL};

                // Trovo il percorso dell'eseguibile del Bot
                char botPath[255];
                getRelativePath(argv[0], botPath);
                strcat(botPath, "F4Bot");
                
                // Chiamo l'eseguibile bot
                if(execvp(botPath, args) == -1) errExit("Execlp failed");
            }
            // Server
            int waitStatus;
            botPid = wait(&waitStatus); // Aspetto il Bot
            if(!WIFEXITED(waitStatus)) {
                printf("Bot failure\n");
                exit(EXIT_FAILURE);
            }
        }

        // Faccio la mossa
        int moveRow = makeMove(ptr->mossa, ptr->turn);

        // Controllo se un giocatore ha Vinto / Pareggiato
        int res = checkVictory(ptr->mossa, moveRow, &(ptr->winningCells));
        if(res == 1 || res == -1) {
            ptr->status[ptr->turn] = res == 1 ? Win : Draw;
            ptr->status[!ptr->turn] = res == 1 ? Lose : Draw;
            notifyEndToClient();

            exit(EXIT_SUCCESS);
        }
        
        // Cambia il turno
        ptr->turn = !ptr->turn;

        // Resetta la mossa e il timer
        ptr->mossa = -1;
        ptr->timer = 0;

        if(!ptr->isBot[!ptr->turn]) 
            semOp(semPlayId, 1, 1);     // V(T)

    } while(1);

    exit(EXIT_SUCCESS);
}

void notifyEndToClient() {
    for(int i = 0; i < ptr->playerCount; i++)
        if(kill(ptr->playersPid[i], SIGUSR1) == -1) errExit("Kill - SIGUSR1 failed");
}

void exitHandler() {
    // Ripristino le impostazioni del terminale (di default)
    unsetTerminal();

    // Invio un segnale ai Client
    for(int i = 0; i < ptr->playerCount; i++)
        kill(ptr->playersPid[i], SIGUSR2);

    // Aspetto che i client finiscano
    if(ptr->playerCount > 0)
        semOp(semCloseId, 0, -ptr->playerCount);

    // Stampo nella console il risultato della partita
    printResults();
    
    // Rimuove i set di semafori
    remove_semaphore(semCloseId);
    remove_semaphore(semConnId);
    remove_semaphore(semPlayId);

    // Rimuove il Playground
    deletePlayground(playgroundId);
    freePlayground();

    // Rimuove la zona di memoria
    free_shared_memory(ptr);
    remove_shared_memory(shmId);

    printf("Server terminated\n");
    fflush(stdout);
}

void printResults() {
    for(int i = 0; i < 2; i++) {
        switch(ptr->status[i]) {
            case Win:
                printf("%s wins\n", ptr->playersName[i]);
                return;
            case TimeoutWin:
                printf("%s wins on time\n", ptr->playersName[i]);
                return;
            case ForfeitWin:
                printf("%s wins by forfeit\n", ptr->playersName[i]);
                return;
            case Draw:
                printf("The game ended in a draw\n");
                return;
            default:
                break;
        }
    }
}

void getRelativePath(char *fullPath, char *relativePath) {
    int i;
    for(i = strlen(fullPath) - 1; i >= 0; i--)
        if(fullPath[i] == '/') break;

    strncpy(relativePath, fullPath, i != 0 ? i + 1 : strlen(fullPath));
    relativePath[strlen(relativePath)] = '\0';
}

void SIGUSR2_handler() {
    printf("User disconnected\n");

    if(ptr->playerCount == 1) {
        ptr->playerCount--;
        ptr->playerNum = !ptr->playerNum;
    }
    else {
        // Non serve controllare quale Client si è disconnesso, perché non è più online (non riceve messaggi)
        ptr->status[!ptr->playerQuitNum] = ForfeitWin;
        
        // Finisce il gioco
        exit(EXIT_FAILURE);
    }
}

void SIGINT_handler() {
    ctrlCounter++;
    
    // Se è la prima volta che preme Ctrl + C
    if(ctrlCounter == 1) {
        printf("Premere ancora entro %d secondi per terminare l'esecuzione", CTRLC_TIMER);
        fflush(stdout);
        // Resetto il contatore dopo CTRLC_TIMER secondi
        alarm(CTRLC_TIMER);
    }
    // Seconda volta
    else {
        printf("\nCtrl + C pressed\n");

        ptr->status[0] = ServerDisconnected, ptr->status[1] = ServerDisconnected;

        exit(EXIT_FAILURE);
    }
}

void SIGALRM_handler() {
    // Resetta il contatore del Ctrl + C
    ctrlCounter = 0;

    // Elimina l'ultima riga e riporta il cursore ad inizio riga
    printf("\33[2K\r");
    fflush(stdout);
}

void sigHandler() {
    ptr->status[0] = ServerDisconnected;
    ptr->status[1] = ServerDisconnected;

    exit(EXIT_FAILURE);
}
