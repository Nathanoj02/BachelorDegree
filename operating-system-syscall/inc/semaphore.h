/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _SEMAPHORE_HH
#define _SEMAPHORE_HH

// Definizione dell'union semun
union semun {
    int val;
    struct semid_ds * buf;
    unsigned short * array;
};

/**
 * @brief Crea un set di semafori, se non esiste già
 * 
 * @param semKey Key usata per creare il set di semafori
 * @param nSem Numero di semafori da creare
 * @return Id del set di semafori, se fallisce termina il processo chiamante
 */
int get_semaphore(key_t semKey, size_t nSem);

/**
 * @brief Crea un set di semafori, se non esiste già. Se esiste ritorna un errore
 * 
 * @param semKey Key usata per creare il set di semafori
 * @param nSem Numero di semafori da creare
 * @return Id del set di semafori, se fallisce ritorna -1
 */
int get_new_semaphore(key_t semKey, size_t nSem);

/**
 * @brief Crea un set di semafori, cercando la prima Key libera
 * 
 * @param semKey Puntatore alla Key di partenza
 * @param size Numero di semafori da creare
 * @return Id del set di semafori
 */
int get_new_semaphore_findKey(key_t *semKey, size_t size);

/**
 * @brief Inizializza un semaforo in un set ad un valore specificato
 * 
 * @param semId Id del set di semafori
 * @param semNum Numero del semaforo nel set
 * @param val Valore da dare al semaforo
 * 
 * Se fallisce termina il processo chiamante
 */
void initialize_semaphore(int semId, int semNum, int val);

/**
 * @brief Funzione di supporto per manipolare il valore di un semaforo in un set
 * 
 * @param semid Id del set
 * @param sem_num Numero del semaforo nel set
 * @param sem_op Operazione da fare sul semaforo
 * 
 * Se fallisce termina il processo chiamante
 */
void semOp(int semid, unsigned short sem_num, short sem_op);

/**
 * @brief Rimuove un set di semafori
 * 
 * @param semId Id del set
 * 
 * Se fallisce termina il processo chiamante
 */
void remove_semaphore(int semId);

#endif
