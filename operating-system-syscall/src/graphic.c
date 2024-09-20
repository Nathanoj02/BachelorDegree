/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "playground.h"
#include "graphic.h"

/**
 * GLOBAL VAR
*/
char *ENUM_TEXT[] = {
    WAITING_TEXT,
    WIN_TEXT,
    LOSS_TEXT,
    DRAW_TEXT,
    TIMEOUT_WIN_TEXT,
    TIMEOUT_LOSS_TEXT,
    FORFEIT_WIN_TEXT,
    SERVER_LOSS_TEXT
};

char* getColoredText(char *text, char *color) {
    char *ptr = (char *) malloc((strlen(text) + strlen(color) + strlen(RESET)) * sizeof (char));

    sprintf(ptr, "%s%s%s", color, text, RESET);

    return ptr;
}

void put(char *buffer, int *offset, char *str) {
    int length = strlen(str);

    memcpy(&buffer[*offset], str, length);

    *offset += length;
}

void putChar(char *buffer, int *offset, char ch) {
    buffer[*offset] = ch;

    (*offset)++;
}

void putSpaces(char *buffer, int *offset, int numSpaces) {
    /*int rest = numSpaces % 8;

    for (int i = 0; i < (numSpaces - rest) / 8; i++) {
        put(buffer, offset, "\t");
    }*/

    for (int i = 0; i < numSpaces; i++) {
        put(buffer, offset, " ");
    }
}

void putColoredText(char *buffer, int *offset, char *text, char *color) {
    char *coloredText = getColoredText(text, color);

    put(buffer, offset, coloredText);

    free(coloredText);
}

int putMultiLineCenterText(char *buffer, int *offset, int width, char *text, char *color) {
    char ch;
    int i = 0, startLine = 0, lineCount = 0;

    while ((ch = text[i]) != '\0')  {
        if (ch == '\n') {
            text[i] = '\0';
            
            putSpaces(buffer, offset, (width / 2) - (strlen(&text[startLine]) / 2));
            putColoredText(buffer, offset, &text[startLine], color);
            putChar(buffer, offset, '\n');

            lineCount++;
            startLine = i + 1;
        }

        i++;
    }

    putSpaces(buffer, offset, (width / 2) - (strlen(&text[startLine]) / 2));
    putColoredText(buffer, offset, &text[startLine], color);

    return ++lineCount;
}

void putHeadLine(char *buffer, int *offset, int width) {
    int centerX = width / 2; // + (width % 2 == 0 ?  0 : 1);

    int titleSize = strlen(TITLE);

    char gamekeyBuffer[30];
    int gamekeySize = sprintf(gamekeyBuffer, GAMEKEY, gameKey);

    // Calcola lo spazio disponibile per il testo per il bot 
    int spaceBotText = centerX - (titleSize / 2) - (OFFSET_BOT_TEXT * 2);

    // Calcola lo spazio disponibile per la game key
    int spaceGameKey = centerX - (titleSize / 2) - (OFFSET_GAMEKEY * 2);

    // Applica il colore alla linea
    put(buffer, offset, BLACK_WHITE);

    for (int i = 0; i < width;) {
        if (ptr->isBot[graphicData->playerId] && spaceBotText > strlen(BOT_TEXT) && i == OFFSET_BOT_TEXT) {
            // Aggiunge il testo del bot se attivo

            putColoredText(buffer, offset, BOT_TEXT, BOT_COLOR);
            i += strlen(BOT_TEXT);

            // Riapplica il colore alla linea
            put(buffer, offset, BLACK_WHITE);
        }else if (i == centerX - (titleSize / 2)) {
            // Aggiunge il titolo al buffer

            putColoredText(buffer, offset, TITLE, BOLD_RED);
            i += titleSize;

            // Riapplica il colore alla linea
            put(buffer, offset, BLACK_WHITE);
        } else if (spaceGameKey > gamekeySize && i == width - gamekeySize - OFFSET_GAMEKEY) {
            // Aggiunge la game key al buffer

            putColoredText(buffer, offset, gamekeyBuffer, GRAY);
            i += gamekeySize;

            // Riapplica il colore alla linea
            put(buffer, offset, BLACK_WHITE);
        }else {
            // Altrimenti riempie di spazi

            putChar(buffer, offset, ' ');
            i++;
        }
    }
    
    // Resetta lo stile
    put(buffer, offset, RESET);

    // New Line
    putChar(buffer, offset, '\n');
}

void putInputLine(char *buffer, int *offset) {
    put(buffer, offset, " ");
    for (int i = 0; i < ptr_pg->sizeX; i++) {
        if (graphicData->currentColumn - 1 == i)
            put(buffer, offset, " ←  ");
        else if (graphicData->currentColumn == i)
            *offset += sprintf(&buffer[*offset], " %c  ", ptr->playersSymbol[graphicData->playerId]);
        else if (graphicData->currentColumn + 1 == i)
            put(buffer, offset, " →  ");
        else 
            put(buffer, offset, "    ");
    }
}

void putPlaygroundRow(char *buffer, int *offset, int row) {
    if (row == ptr_pg->sizeY) {
        // Se è l'ultima riga
        for (int i = 0; i <= ptr_pg->sizeX; i++) {
            if (i == 0) 
                put(buffer, offset, "└───");
            else if (i == ptr_pg->sizeX)
                put(buffer, offset, "┘");
            else 
                put(buffer, offset, "┴───");
        }
    }else {
        // Tutte le altre righe

        char cellBuffer[20];
        for (int i = -1; i < ptr_pg->sizeX; i++) {
            if (i == -1) 
                put(buffer, offset, "│");
            else {
                int cellValue = getPlaygroundCell(row, i);
                sprintf(cellBuffer, " %c %s│", cellValue >= 0 ? ptr->playersSymbol[cellValue] : ' ', RESET);

                if ((ptr->status[graphicData->playerId] == Win ||  ptr->status[graphicData->playerId] == Lose) && isWinningCell(i, row, ptr->winningCells))
                    putColoredText(buffer, offset, cellBuffer, ptr->status[graphicData->playerId] == Win ? WIN_COLOR : LOSE_COLOR);
                else
                    put(buffer, offset, cellBuffer);
            }
        }
    }
}

void putFooterLine(char *buffer, int *offset, int width, float timer) {
    // Aggiunge il background bianco o verde al testo
    if (graphicData->inputEnabled) {
        int percentage = (1 - (timer / MAX_TIMER)) * 100;

        // Aggiunge il colore della progress bar in base alla percentuale del timer
        if (percentage > 50) put(buffer, offset, BLACK_GREEN);
        else if (percentage > 20) put(buffer, offset, BLACK_YELLOW);
        else put(buffer, offset, BLACK_RED);

    }else put(buffer, offset, BLACK_WHITE);

    int endProgressbar = 0;

    char timerBuffer[20];
    sprintf(timerBuffer, TIMER_TEXT, MAX_TIMER - timer < 0 ? 0 : MAX_TIMER - timer);

    // Calcola lo spazio disponibilie per i bottoni
    int spaceControlButtons = (width - 1) - (graphicData->inputEnabled ? (strlen(timerBuffer) + OFFSET_TIMER) : 0) - (OFFSET_BUTTONS * 2);

    if(!ptr->isBot[graphicData->playerId]) {
        for (int i = 0; i < width; i++) {
            if (graphicData->inputEnabled && !endProgressbar && (float) i / width >= 1 - (timer / MAX_TIMER)) {
                // Cambia il colore di background
                
                put(buffer, offset, BLACK_WHITE);
                endProgressbar = 1;
            }

            if (graphicData->inputEnabled && isInRange(i, OFFSET_TIMER, strlen(timerBuffer)))
                // Timer
                putChar(buffer, offset, timerBuffer[i - OFFSET_TIMER]);
            else if (spaceControlButtons >= strlen(BUTTONS_EXTENDED_TEXT) && isInRange(i, (width - 1) - strlen(BUTTONS_EXTENDED_TEXT) - OFFSET_BUTTONS, strlen(BUTTONS_EXTENDED_TEXT)))
                // Extended Control Buttons
                putChar(buffer, offset, BUTTONS_EXTENDED_TEXT[i - ((width - 1) - strlen(BUTTONS_EXTENDED_TEXT) - OFFSET_BUTTONS)]);
            else if (spaceControlButtons >= strlen(BUTTONS_TEXT) && isInRange(i, (width - 1) - strlen(BUTTONS_TEXT) - OFFSET_BUTTONS, strlen(BUTTONS_TEXT)))
                // Control Buttons
                putChar(buffer, offset, BUTTONS_TEXT[i - ((width - 1) - strlen(BUTTONS_TEXT) - OFFSET_BUTTONS)]);
            else
                // Spaces
                put(buffer, offset, " ");
        }
    }
    else
        // Spaces
        putSpaces(buffer, offset, width);

    // Resetta lo style
    put(buffer, offset, RESET);
}

void _draw(char *canvas, int row, int column, int tick) {
    int offset = 0,
        centerX = column / 2,
        centerY = (row / 2) + 1,
        width_pg = (ptr_pg->sizeX * 4) + 1,
        height_pg = (ptr_pg->sizeY + 1) + (OFFSET_INPUT + 1);

    char statustext[100];
    char messagetext[100];
    
    // Head
    if (row > 3) {
        putHeadLine(canvas, &offset, column);
    }


    // Body
    if (row - 2 < height_pg) {
        // Se il campo è troppo grande per il body viene notificato

        int i = row <= 3 ? 1 : 2;
        for (; i < row; i++) {
            if (i == centerY || row == 2) {
                // Inserisce stringa di avviso quando il terminale è troppo piccolo
                
                putSpaces(canvas, &offset, centerX - (strlen(SIZE_WARNING) / 2));
                putColoredText(canvas, &offset, SIZE_WARNING, YELLOW);

            }
            
            put(canvas, &offset, "\n");
        }

    }else {
        if (ptr->status[graphicData->playerId] == Waiting) {
            // Se la partita non è ancora iniziata

            for (int i = 2; i < row; i++) {
                if (i == centerY) {
                    int periodDot = (1000 / TICK) * DELAY_UPDATE_DOT;   // La durata in tick di un dot prima di aggiornarsi
                    int countDot = tick % (periodDot * 4) / periodDot;  // Il numero di dot che devono essere all'interno della stringa
                    char dot[3];
                    for (int i = 0; i < 3; i++) {
                        if (countDot > i) dot[i] = '.';
                        else dot[i] = ' ';
                    }
                    sprintf(messagetext, WAITING_TEXT, dot[0], dot[1], dot[2]);

                    i += putMultiLineCenterText(canvas, &offset, column, messagetext, GRAY) - 1;
                }

                // Next Line
                put(canvas, &offset, "\n");
            }
        }else if (ptr->status[graphicData->playerId] != Waiting) {
            // Quando la partita è iniziata

            for (int i = 2; i < row; i++) {
                if (graphicData->inputEnabled && graphicData->currentColumn != -1 && i == centerY - (height_pg / 2)) {
                    // Input Line
                    putSpaces(canvas, &offset, centerX - (width_pg / 2));
                    putInputLine(canvas, &offset);
                }
                
                if (isInRange(i, centerY - (height_pg / 2) + (OFFSET_INPUT + 1), height_pg - (OFFSET_INPUT + 1))) {
                    // Playground Rows
                    putSpaces(canvas, &offset, centerX - (width_pg / 2));
                    putPlaygroundRow(canvas, &offset, i - centerY + (height_pg / 2) - (OFFSET_INPUT + 1));
                }

                if (i == centerY + (height_pg / 2) + OFFSET_STATUS_TEXT) {
                    // Status Text
                    if (ptr->status[graphicData->playerId] == Playing) {
                        if (!ptr->isBot[graphicData->playerId]) {
                            if (graphicData->inputEnabled) {
                                // Il tuo turno

                                i += putMultiLineCenterText(canvas, &offset, column, YOUR_TURN, GRAY) - 1;
                            }else {
                                // Il turno dell'avversario
                                sprintf(statustext, OTHER_TURN, ptr->playersName[!graphicData->playerId]);
                                
                                i += putMultiLineCenterText(canvas, &offset, column, statustext, GRAY) - 1;
                            }
                        }
                    }else {
                        Status st = ptr->status[graphicData->playerId];

                        i += putMultiLineCenterText(canvas, &offset, column, ENUM_TEXT[st], GRAY) - 1;
                    }
                }

                // Next Line
                put(canvas, &offset, "\n");
            }
        }
    }
    
    
    // Footer
    if (row > 4) {
        putFooterLine(canvas, &offset, column, ptr->timer);
    }

    // EOF
    canvas[offset] = '\0';
}

/**
 * UTILS
 */
bool isInRange(int value, int start, int width) {
    return value >= start && value < (start + width);
}