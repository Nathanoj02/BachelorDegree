/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <sys/shm.h>

#include "shared_memory.h"
#include "playground.h"

Playground *ptr_pg;
int *playground, *freeColumn;
int pg_playgroundId, pg_freeColumnId;

int buildPlayground(int sizeX, int sizeY, key_t *shmKey) {
    int shmId = alloc_new_shared_memory_findKey(shmKey, sizeof(Playground));
    ptr->playgroundKey = *shmKey;
    
    ptr_pg = (Playground *) get_shared_memory(shmId, 0);

    pg_playgroundId = alloc_new_shared_memory_findKey(shmKey, sizeof(int) * sizeX * sizeY);
    ptr_pg->playgroundKey = *shmKey;
    pg_freeColumnId = alloc_new_shared_memory_findKey(shmKey, sizeof(int) * sizeX);
    ptr_pg->freeColumnKey = *shmKey;

    ptr_pg->sizeX = sizeX;
    ptr_pg->sizeY = sizeY;

    playground = (int *) get_shared_memory(pg_playgroundId, 0);
    freeColumn = (int *) get_shared_memory(pg_freeColumnId, 0);

    initPlayground();

    return shmId;
}

void getPlayground(key_t shmKey) {
    int shmId = alloc_shared_memory(shmKey, sizeof(Playground));
    ptr_pg = (Playground *) get_shared_memory(shmId, 0);

    pg_playgroundId = alloc_shared_memory(ptr_pg->playgroundKey, sizeof(int) * ptr_pg->sizeX * ptr_pg->sizeY);
    playground = (int *) get_shared_memory(pg_playgroundId, 0);

    pg_freeColumnId = alloc_shared_memory(ptr_pg->freeColumnKey, sizeof(int) * ptr_pg->sizeX);
    freeColumn = (int *) get_shared_memory(pg_freeColumnId, 0);
}

void freePlayground() {
    free_shared_memory(playground);
    free_shared_memory(freeColumn);

    free_shared_memory(ptr_pg);
}

void deletePlayground(int shmId) {
    remove_shared_memory(pg_playgroundId);
    remove_shared_memory(pg_freeColumnId);

    remove_shared_memory(shmId);
}

void initPlayground() {
    // Init Playground
    for (int i = 0; i < ptr_pg->sizeX * ptr_pg->sizeY; i++) {
        playground[i] = -1;
    }

    // Init Free column
    for (int i = 0; i < ptr_pg->sizeX; i++) {
        freeColumn[i] = ptr_pg->sizeY - 1;
    }
}

int columnIsFull(int column) {
    return !(freeColumn[column] >= 0);
}

int nextFreeColumn(int column) {
    for (int i = 1; i <= ptr_pg->sizeX; i++) {
        int nextColumn = (column + i) % ptr_pg->sizeX;

        if (freeColumn[nextColumn] >= 0) return nextColumn;
    }

    return -1;
}

int prevFreeColumn(int column) {
    for (int i = 1; i <= ptr_pg->sizeX ; i++) {
        int prevColumn = (column - i) % ptr_pg->sizeX;
        if (prevColumn < 0) prevColumn += ptr_pg->sizeX;

        if (freeColumn[prevColumn] >= 0) return prevColumn;
    }
    
    return -1;
}

int playgroundIsFull() {
    for (int i = 0; i < ptr_pg->sizeX; i++) {
        // Se c'è una colonna libera ritorna 0
        if (freeColumn[i] >= 0) return 0;
    }

    return 1;
}

int makeMove(int column, int player) {

    if (freeColumn[column] >= 0) {
        int pos = column + freeColumn[column] * ptr_pg->sizeX;
        playground[pos] = player;

        freeColumn[column]--;

        return freeColumn[column] + 1;
    }

    return -1;
}

int unmakeMove(int column) {
    if(freeColumn[column] < ptr_pg->sizeY - 1) {
        freeColumn[column]++;

        int pos = column + freeColumn[column] * ptr_pg->sizeX;
        playground[pos] = -1;

        return 0;
    }
    
    return -1;
}

int checkVictory(int moveX, int moveY, winningcells_t *winningCells) {
    int player = getPlaygroundCell(moveY, moveX);
    if(player < 0)  return 0;

    // Controllo della riga
    for(int i = 0; i < 4; i++) {
        for(int j = -3; j <= 0; j++) {
            if(getPlaygroundCell(moveY, moveX + i + j) != player)
                break;
            
            if(j == 0)  {
                if (winningCells != NULL) {
                    winningCells->direction = Horizontal;
                    
                    winningCells->startCol = moveX + i - 3;
                    winningCells->startRow = moveY;

                    winningCells->endCol = moveX + i;
                    winningCells->endRow = moveY;
                }

                return 1;
            }
        }
    }

    // Controllo della colonna
    for(int i = 0; i < 4; i++) {
        for(int j = -3; j <= 0; j++) {
            if(getPlaygroundCell(moveY + i + j, moveX) != player)
                break;
            
            if(j == 0)  {
                if (winningCells != NULL) {
                    winningCells->direction = Vertical;
                    
                    winningCells->startCol = moveX;
                    winningCells->startRow = moveY + i - 3;

                    winningCells->endCol = moveX;
                    winningCells->endRow = moveY  + i;
                }

                return 1;
            }
        }
    }

    // Controllo della diagonale principale
    for(int i = 0; i < 4; i++) {
        for(int j = -3; j <= 0; j++) {
            if(getPlaygroundCell(moveY + i + j, moveX + i + j) != player)
                break;
            
            if(j == 0)  {
                if (winningCells != NULL) {
                    winningCells->direction = Primary_Diagonal;
                    
                    winningCells->startCol = moveX + i - 3;
                    winningCells->startRow = moveY + i - 3;

                    winningCells->endCol = moveX + i;
                    winningCells->endRow = moveY + i;
                }

                return 1;
            }
        }
    }

    // Controllo della diagonale secondaria
    for(int i = 0; i < 4; i++) {
        for(int j = -3; j <= 0; j++) {
            if(getPlaygroundCell(moveY - i - j, moveX + i + j) != player)
                break;
            
            if(j == 0)  {
                if (winningCells != NULL) {
                    winningCells->direction = Secondary_Diagonal;
                    
                    winningCells->startCol = moveX + i - 3;
                    winningCells->startRow = moveY - i + 3;

                    winningCells->endCol = moveX + i;
                    winningCells->endRow = moveY - i;
                }

                return 1;
            }
        }
    }

    // Se non ha vinto, controlla se è pieno il campo
    if(playgroundIsFull()) 
        return -1;


    // Altrimenti non cè nessun evento e si continua
    return 0;
}


int preCheckVictory(int column, int player) {
    // Faccio la mossa
    int row = makeMove(column, player);

    // Controllo la mossa
    int ret = checkVictory(column, row, NULL);

    // Toglo la mossa
    unmakeMove(column);

    return ret;
}

int getPlaygroundCell(int row, int column) {
    // Controllo se siamo all'interno del campo
    if(row < 0 || column < 0 || row >= ptr_pg->sizeY || column >= ptr_pg->sizeX)
        return -2;

    // Ritorna il valore nella cella
    return playground[column + row * ptr_pg->sizeX];
}

int isWinningCell(int col, int row, winningcells_t winningCells) {
    switch (winningCells.direction) {
        case Vertical:
            return col == winningCells.startCol && row >= winningCells.startRow && row <= winningCells.endRow;
        case Horizontal:
            return row == winningCells.startRow && col >= winningCells.startCol && col <= winningCells.endCol;
        case Primary_Diagonal:
            return  col >= winningCells.startCol && col <= winningCells.endCol && 
                    row >= winningCells.startRow && row <= winningCells.endRow && 
                    winningCells.startRow - row == winningCells.startCol - col &&
                    winningCells.endRow - row == winningCells.endCol - col;
        case Secondary_Diagonal:
            return  col >= winningCells.startCol && col <= winningCells.endCol && 
                    row <= winningCells.startRow && row >= winningCells.endRow && 
                    winningCells.startRow - row == col - winningCells.startCol &&
                    winningCells.endRow - row == col - winningCells.endCol;
        default:
            return -1;
    }
}