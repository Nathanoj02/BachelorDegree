/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "errExit.h"
#include "shared_memory.h"

Data *ptr;

int alloc_shared_memory(key_t shmKey, size_t size) {
    int creatFlag = shmKey == IPC_PRIVATE ? IPC_CREAT : 0;

    // get, or create, a shared memory segment
    int shmid = shmget(shmKey, size, creatFlag | S_IRUSR | S_IWUSR);
    if (shmid == -1) {
        if(shmKey == IPC_PRIVATE)   errExit("Failed to create new shared memory");
        else    errExit("Failed to connect to shared memory");
    }

    return shmid;
}

int alloc_new_shared_memory(key_t shmKey, size_t size) {
    int shmid = shmget(shmKey, size, IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);

    return shmid;
}

int alloc_new_shared_memory_findKey(key_t *shmKey, size_t size) {
    int shmId;

    while((shmId = alloc_new_shared_memory(*shmKey, size)) == -1)
        (*shmKey)++;

    return shmId;
}

void *get_shared_memory(int shmid, int shmflg) {
    // attach the shared memory
    void *ptr_sh = shmat(shmid, NULL, shmflg);
    if (ptr_sh == (void *)-1)
        errExit("Shmat failed");

    return ptr_sh;
}

void free_shared_memory(void *ptr_sh) {
    // detach the shared memory segments
    if (shmdt(ptr_sh) == -1)
        errExit("Shmdt failed");
}

void remove_shared_memory(int shmid) {
    // delete the shared memory segment
    if (shmctl(shmid, IPC_RMID, NULL) == -1)
        errExit("Shmctl failed");
}