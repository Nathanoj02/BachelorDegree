/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _PLAYGROUND_H
#define _PLAYGROUND_H

typedef enum Direction {Vertical = 0, Horizontal = 1, Primary_Diagonal = 2, Secondary_Diagonal = 3} Direction;

/**
 * Struct Shared Memory:
 *  +-----------------------+
 *  |   Struct Playground   |
 *  +-----------------------+
 *  |   Array Free Column   |
 *  +-----------------------+
 *  |   Array Playground    |
 *  +-----------------------+
 */
typedef struct {
    key_t playgroundKey, freeColumnKey;
    int sizeX, sizeY;
} Playground;

typedef struct {
    Direction direction;
    int startCol, startRow;
    int endCol, endRow;
} winningcells_t;

/**
 * DEFINE
 */
#define MIN_PG_SIZE 5
#define MAX_PG_SIZE 20

/**
 * GLOBAL VARS
 */
extern Playground *ptr_pg;
extern int *freeColumn, *playground;

/**
 * @brief Crea un campo da gioco e lo inizializza
 * 
 * @param sizeX Dimensione orizzontale (n° colonne)
 * @param sizeY Dimensione verticale (n° righe)
 * @param shmKey Puntatore alla Key di partenza 
 * @return Id della zona di memoria condivisa
 */
int buildPlayground(int sizeX, int sizeY, key_t *shmKey);

/**
 * @brief Dato in input un ID di una zona di memoria condivisa, imposta il puntatore del Playground su di essa
 * 
 * @param shmKey Zona di memoria condivisa
 */
void getPlayground(key_t shmKey);

/**
 * @brief Free sul puntatore del Playground
 */
void freePlayground();

/**
 * @brief Rimuove la zona di memoria condivisa del Playground
 * 
 * @param shmId Id della zona di memoria del Playground
 */
void deletePlayground(int shmId);

/**
 * @brief Inizializza i 2 array Playground e freeColumn
 */
void initPlayground();

/**
 * @brief Ritorna sela colonna passata è piena
 *
 * @param column Colonna
 * @return Ritorna 1 se piena altrimenti 0
 */
int columnIsFull(int column);

/**
 * @brief Ritorna un valore che indica la prossima colonna libera rispetto alla colonna inserita in input se possibile
 *
 * @param column Colonna attuale
 * @return Colonna con valore [0, sizeX - 1] se disponibile, altrimenti se tutte le colonne sono occupate ritorna -1
 */
int nextFreeColumn(int column);

/**
 * @brief Ritorna un valore che indica la prima colonna libera che precede quella inserita in input
 * 
 * @param column Colonna attuale
 * @return Colonna con valore [0, sizeX - 1] se disponibile, altrimenti se tutte le colonne sono occupate ritorna -1
 */
int prevFreeColumn(int column);

/**
 * @brief Indica se il campo da gioco è pieno di gettoni
 * 
 * @return 1 se il campo da gioco è pieno, 0 altrimenti
 */
int playgroundIsFull();

/**
 * @brief Effettua la mossa di un giocatore in una determinata colonna, se libera 
 * 
 * @param column Colonna scelta dal giocatore
 * @param player Giocatore
 * @return Riga dove è stato messo il gettone, oppure -1 in caso di errore
 */
int makeMove(int column, int player);

/**
 * @brief Toglie l'ultimo gettone dalla colonna specificata
 * 
 * @param column Colonna da cui togliere il gettone
 * @return 0 se è andata a buon fine, -1 altrimenti
 */
int unmakeMove(int column);

/**
 * @brief Controlla se un giocatore ha vinto o pareggiato (se il campo è pieno)
 * 
 * @param moveX Colonna del gettone
 * @param moveY Riga del gettone
 * @param winningCells Se qualcuno ha vinto viene riempito con i dati delle celle vincenti [Può essere NULL]
 * @return 1 se il giocatore ha vinto, -1 se ha pareggiato, 0 se non succede niente
 */
int checkVictory(int moveX, int moveY, winningcells_t *winningCells);

/**
 * @brief Funzione (per il bot) che controlla se la mossa nella colonna specificata vince la partita o meno
 * 
 * @param column Colonna
 * @param player Giocatore
 * @return 1 se il giocatore ha vinto, -1 se ha pareggiato, 0 se non succede niente
 */
int preCheckVictory(int column, int player);

/**
 * @brief Ritorna il valore del gettone presente in una cella del campo da gioco
 * 
 * @param row Riga del gettone
 * @param column Colonna del gettone
 * @return -2 se la cella non è all'interno del campo da gioco, -1 se la cella è vuota, 0 o 1 se nella cella è inserito il gettone di un giocatore
 */
int getPlaygroundCell(int row, int column);


/**
 * @brief Ritorna se la cella (col, row) inserita è vincente o meno
 * 
 * @param col Colonna
 * @param row Riga
 * @param winningCells Struct che indica le celle vincenti
 * @return 1 se la cella è vincente, altrimenti 0
 */
int isWinningCell(int col, int row, winningcells_t winningCells);

#endif