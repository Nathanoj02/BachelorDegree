/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _GRAPHIC_H
#define _GRAPHIC_H

#include "shared_memory.h"

#define TICK 50

/** 
 * TEXT
 */
#define BOT_TEXT "BOT: ON"
#define TITLE "Forza 4" 
#define GAMEKEY "GAME KEY: %d"
#define SIZE_WARNING "⚠  IL TERMINALE È TROPPO PICCOLO  ⚠"
#define YOUR_TURN "È il tuo turno, fai la tua mossa"
#define OTHER_TURN "È il turno di %s, aspetta che faccia la sua mossa"
#define WAITING_TEXT "La partita sta per cominciare\n   In attesa di un altro giocatore%c%c%c"
#define WIN_TEXT "Hai Vinto"
#define LOSS_TEXT "Hai Perso"
#define DRAW_TEXT "La partita è finita in pareggio"
#define TIMEOUT_WIN_TEXT "Hai vinto per tempo"
#define TIMEOUT_LOSS_TEXT "Hai perso per tempo"
#define FORFEIT_WIN_TEXT "Hai vinto, il tuo avversario si è disconnesso"
#define SERVER_LOSS_TEXT "Connessione persa"
#define TIMER_TEXT "%.1fs"
#define BUTTONS_EXTENDED_TEXT "[<-] Sinistra [->] Destra [Enter] Fine Turno"
#define BUTTONS_TEXT "[<-] [->] [Enter]"

/**
 * OFFSET
 */
#define OFFSET_BOT_TEXT 2        // Offset dal bordo sinistro 
#define OFFSET_GAMEKEY 2         // Offset dal bordo destro della game key
#define OFFSET_INPUT 1           // Spazi tra il campo da gioco e la barra di input
#define OFFSET_STATUS_TEXT 2     // Offset del testo di stato sotto il campo da gioco (min: 2)
#define OFFSET_WAITING_MESSAGE 1 // Indica la distanza dal centro dei due messaggi (min: 1)
#define OFFSET_TIMER 2           // Offset dal bordo sinistro del timer
#define OFFSET_BUTTONS 2         // Offset dal bordo destro relativo ai tasti

/**
 * DELAY
 */
#define DELAY_UPDATE_DOT 0.5    // Indica in secondi ogni quanto aggiunge o resetta i dot nel WAITING_MESSAGE_L2

/** 
 * COLORS 
 */
#define RESET "\033[0m"
#define BOT_COLOR "\x1b[38;2;66;135;245m"       // Blue
#define WIN_COLOR "\x1b[38;2;138;226;52m"       // Bright Green
#define LOSE_COLOR "\x1b[38;2;239;41;41m"       // Bright Red
#define BOLD_RED "\033[1;91m"
#define GRAY "\033[90m"
#define YELLOW "\033[33m"
#define BLACK_WHITE "\033[47;30m"
#define BLACK_GREEN "\033[42;30m"
#define BLACK_YELLOW "\033[43;30m"
#define BLACK_RED "\033[41;30m"

/**
 * STRUCT
 */
typedef struct {
    int playerId;

    bool inputEnabled;
    int currentColumn;
} GraphicData;

/**
 * GLOBAL VARS
 */
extern GraphicData *graphicData;
extern int gameKey;

/**
 * ! Private function
 * @brief Ritorna il puntatore ad una stringa con il colore indicato
 * 
 * @param text Testo della stringa
 * @param color Colore del testo
 * @return Puntatore alla stringa colorata
 */
char* getColoredText(char *text, char *color);

/**
 * @brief Data una stringa, la concatena con il buffer e sposta l'offset
 * 
 * @param buffer Buffer, stringa di destinazione
 * @param offset Offset
 * @param str Stringa da concatenare
 */
void put(char *buffer, int *offset, char *str);

/**
 * @brief Aggiunge tabulazioni e spazi al buffer per raggiungere il valore di spazi desiderati
 * 
 * @param buffer Buffer, stringa risultante
 * @param offset Offset
 * @param numSpaces Numero di spazi
 */
void putSpaces(char *buffer, int *offset, int numSpaces);

/**
 * @brief Dato un testo e un colore, crea il testo colorato, lo inserisce nel buffer
 *        e cancella il testo (colorato) appena creato
 * 
 * @param buffer Buffer
 * @param offset Offset
 * @param text Testo
 * @param color Colore
 */
void putColoredText(char *buffer, int *offset, char *text, char *color);


/**
 * @brief Mette nel buffer la riga di intestazione
 * 
 * @param buffer Buffer
 * @param offset Offset
 * @param width Colonna
 */
void putHeadLine(char *buffer, int *offset, int width);

/**
 * @brief Inserisce nel buffer la riga di input
 * 
 * @param buffer Buffer
 * @param offset Offset
 */
void putInputLine(char *buffer, int *offset);

/**
 * @brief Mette nel buffer la riga del campo da gioco inserita
 * 
 * @param buffer Buffer
 * @param offset Offset
 * @param row Riga
 */
void putPlaygroundRow(char *buffer, int *offset, int row);

/**
 * @brief Data la dimensione del terminale, genera nel buffer la schermata di gioco
 * 
 * @param canvas Stringa con schermata del gioco (buffer)
 * @param row n° righe
 * @param column n° colonne
 * @param tick count dei tick
 */
void _draw(char *canvas, int row, int column, int tick);

/*
    UTILS
*/

/**
 * @brief Ritorna se un valore è all'interno di un range
 * 
 * @param value Il valore da controllare
 * @param start Il valore di inizio del range
 * @param width Il valore di spostamento dall'inizio
*/
bool isInRange(int value, int start, int width);

#endif