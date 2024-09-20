/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

#include "errExit.h"
#include "playground.h"
#include "shared_memory.h"

#define SLEEP_TIME_MAX 4
#define SLEEP_FRACTION 1 / 10.0

void exitHandler();

int main(int argc, char *argv[]) {
    // Controllo sugli argomenti
    if(argc != 5) {
        printf("Usage: <exe> <Memory Key> <Playground Key> <Player Number> <Bot Log>\n");
        exit(EXIT_FAILURE);
    }

    if(strlen(argv[3]) != 1)    errExit("Invalid Player Number");
    int playerNum = argv[3][0] - '0';

    // Mi salvo le zone di memoria
    int shmKey = atoi(argv[1]);
    if(shmKey <= 0) errExit("Invalid Memory Key");

    int shmPlaygroundKey = atoi(argv[2]);
    if(shmPlaygroundKey <= 0)   errExit("Invalid Playground Key");

    int shmId = alloc_shared_memory(shmKey, sizeof(Data));
    ptr = (Data *) get_shared_memory(shmId, 0);

    int shmPlaygroundId = alloc_shared_memory(shmPlaygroundKey, sizeof(Playground));
    ptr_pg = (Playground *) get_shared_memory(shmPlaygroundId, 0);

    int shmFreeColumnId = alloc_shared_memory(ptr_pg->freeColumnKey, sizeof(int) * ptr_pg->sizeX);
    freeColumn = (int *) get_shared_memory(shmFreeColumnId, 0);

    int shmMiniPlaygroundId = alloc_shared_memory(ptr_pg->playgroundKey, sizeof(int) * ptr_pg->sizeX * ptr_pg->sizeY);
    playground = (int *) get_shared_memory(shmMiniPlaygroundId, 0);

    // Controllo opzione Log
    if(strcmp(argv[4], "on") != 0 && strcmp(argv[4], "off") != 0) {
        printf("Invalid Bot Log option: must be either \"on\" or \"off\"\n");
        exit(EXIT_FAILURE);
    }
    bool botLog = strcmp(argv[4], "on") == 0 ? true : false;

    int freeColumns[MAX_PG_SIZE];   // Array delle colonne libere
    int freeColumnsSize = 0;        // Dimensione

    for(int i = 0; i < ptr_pg->sizeX; i++) {
        // Se la colonna è libera la aggiungo all'array delle colonne libere
        if(freeColumn[i] >= 0) {
            freeColumns[freeColumnsSize] = i;
            freeColumnsSize++;
        }
    }

    // Cambio l'exit handler
    if(atexit(exitHandler) == -1)   errExit("AtExit failed");

    // Se può vincere in una mossa, mette il gettone nella colonna giusta
    for(int i = 0; i < freeColumnsSize; i++) {
        if(preCheckVictory(freeColumns[i], playerNum) == 1) {
            if(botLog) {
                printf("Bot can win in 1 move with column %d\n", freeColumns[i] + 1);
                fflush(stdout);
            }

            ptr->mossa = freeColumns[i];

            exit(EXIT_SUCCESS);
        }
    }
    
    // Se può vincere il giocatore in una mossa, lo blocca
    for(int i = 0; i < freeColumnsSize; i++) {
        if(preCheckVictory(freeColumns[i], !playerNum) == 1) {
            if(botLog) {
                printf("Other Player can win in 1 move with column %d\tStopping the Player...\n", freeColumns[i] + 1);
                fflush(stdout);
            }

            ptr->mossa = freeColumns[i];

            exit(EXIT_SUCCESS);
        }
    }

    // Altrimneti faccio una mossa random sulle colonne libere
    fflush(stdout);
    srand((unsigned) time(NULL));
    ptr->mossa = freeColumns[rand() % freeColumnsSize];
    
    exit(EXIT_SUCCESS);
}

void exitHandler() {
    // Attendo un certo tempo per renderlo più realistico
    float sleepTime = MAX_TIMER * SLEEP_FRACTION;
    usleep(1000 * 1000 * (sleepTime > SLEEP_TIME_MAX ? SLEEP_TIME_MAX : sleepTime));
}