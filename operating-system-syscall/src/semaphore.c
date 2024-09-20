/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <sys/sem.h>
#include <sys/stat.h>
#include <errno.h>
#include <stdio.h>

#include "semaphore.h"
#include "errExit.h"

int get_semaphore(key_t semKey, size_t nSem) {
    // Crea, o ritorna, un set di semafori
    int semId = semget(semKey, nSem, IPC_CREAT | S_IRUSR | S_IWUSR);
    if (semId == -1)
        errExit("Semget failed");

    return semId;
}

int get_new_semaphore(key_t semKey, size_t nSem) {
    // Crea, o ritorna, un set di semafori
    int semId = semget(semKey, nSem, IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);

    return semId;
}

int get_new_semaphore_findKey(key_t *semKey, size_t size) {
    int semId;

    while((semId = get_new_semaphore(*semKey, size)) == -1)
        (*semKey)++;

    return semId;
}

void initialize_semaphore(int semId, int semNum, int val) {
    union semun arg;
    arg.val = val;

    if(semctl(semId, semNum, SETVAL, arg) == -1)
        errExit("Semctl failed");
}

void semOp (int semid, unsigned short sem_num, short sem_op) {
    struct sembuf sop = {.sem_num = sem_num, .sem_op = sem_op, .sem_flg = 0};

    // Non dà errore se la system call viene interrotta, ma riprova ad eseguirla
    do {
        errno = 0;  // resetto il valore
        if(semop(semid, &sop, 1) == -1 && errno != EINTR)
            errExit("Semop failed");
    } while(errno == EINTR);
        
}

void remove_semaphore(int semId) {
    if(semctl(semId, 0, IPC_RMID, 0) == -1)
        errExit("Semctl - IPC_RMID failed");
}
