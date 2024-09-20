/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _TERMINAL_HH
#define _TERMINAL_HH

#include <termios.h>    // Terminal setting
#include <sys/ioctl.h>  // Terminal size

typedef struct termios termios;
typedef struct winsize winsize;

extern termios oldSetting;

/**
 * @brief Cambia le impostazione del terminale e
 *        salva una copia delle vecchie imppostazioni.
 *        Rimuove l'"echo" del standard input
 */
void setupTerminal();

/**
 * @brief Ripristina le impostazioni del terminale
 */
void unsetTerminal();

#endif