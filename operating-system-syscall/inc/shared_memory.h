/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _SHARED_MEMORY_HH
#define _SHARED_MEMORY_HH

#include <stdlib.h>
#include <stdbool.h>

#include "playground.h"

#define MAX_NAME 20
#define MAX_TIMER 20

typedef enum Status {Playing = -1, Waiting = 0, Win = 1, Lose = 2, Draw = 3, TimeoutWin = 4, TimeoutLose = 5, 
                ForfeitWin = 6, ServerDisconnected = 7} Status;

/**
 * @brief Rappresentazione della zona di memoria condivisa
 */
typedef struct Data {
    int playerCount;    // Numero giocatori online
    int playerNum;      // Numero assegnato ad un giocatore (0 o 1)
    int turn;           // Numero del turno (0 o 1)

    pid_t serverPid;                        // PID del Server
    pid_t playersPid[2];                    // PID dei Client
    char playersName[2][MAX_NAME + 1];      // Nomi dei Client
    char playersSymbol[2];                  // Simbolo del gettone dei giocatori

    pid_t playerQuitNum;    // Pid del Client che ha lasciato la partita durante il gioco

    bool isBot[2];      // Indica se il giocatore ha attivato la funzione Bot
    bool botLog[2];     // Indica se il bot deve stampare i log nel Server

    int mossa;      // Numero della colonna dove viene messa la mossa

    float timer;    // Timer per il timeout della mossa

    enum Status status[2];  // stato del giocatore

    key_t playgroundKey;                // Key della memoria condivisa del campo
    winningcells_t winningCells;        // Struct che indica le celle vincenti

    key_t semConnKey;                   // Key del set di semafori per la connessione Client - Server
    int semConnSize;

    key_t semPlayKey;                   // Key del set di semafori per gestire la sincronizzazione dei turni di gioco
    int semPlaySize;

    key_t semCloseKey;                  // Key del set di semafori per gestire la sincronizzazione sulla chiusura dei Client e Server
    int semCloseSize;
} Data;

extern Data *ptr;

/**
 * @brief Crea, se non esiste già, una segmento di memoria condivisa
 * 
 * @param shmKey Key della zona di memoria
 * @param size Dimensione della zona di memoria
 * @return Id della zona di memoria se in caso di successo, altrimenti termina il processo chiamante
 */
int alloc_shared_memory(key_t shmKey, size_t size);

/**
 * @brief Crea, se non esiste già, una segmento di memoria condivisa. Se esiste ritorna un errore
 * 
 * @param shmKey Key della zona di memoria
 * @param size Dimensione della zona di memoria
 * @return Id della zona di memoria se in caso di successo, altrimenti -1
 */
int alloc_new_shared_memory(key_t shmKey, size_t size);

/**
 * @brief Crea un segmento di memoria condivisa, cercando la prima Key libera
 * 
 * @param shmKey Puntatore alla Key di partenza
 * @param size Dimensione della zona di memoria
 * @return Id della zona di memoria
 */
int alloc_new_shared_memory_findKey(key_t *shmKey, size_t size);

/**
 * @brief Allega un segmento di memoria condivisa nello spazio di indirizzamento logico del processo chiamante
 * 
 * @param shmid Id della zona di memoria
 * @param shmflg Flag
 * @return Puntatore alla zona di memoria, se fallisce termina il processo chiamante
 */
void *get_shared_memory(int shmid, int shmflg);

/**
 * @brief Stacca il segmento di memoria condivisa dallo spazio di indirizzamento logico del processo chiamante
 * 
 * @param ptr_sh Puntatore alla zona di memoria
 * 
 * Se fallisce termina il processo chiamante
 */
void free_shared_memory(void *ptr_sh);

/**
 * @brief Rimuove un segmento di memoria condivisa
 * 
 * @param shmid Id della zona di memoria
 * 
 * Se fallisce, termina il processo chiamante
 */
void remove_shared_memory(int shmid);

#endif