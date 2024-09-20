/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <unistd.h>

#include "terminal.h"

/**
 * GLOBAL VARS
 */
termios oldSetting;

void setupTerminal() {
    termios setting;

    // Save the current setting of the terminal
    tcgetattr(STDIN_FILENO, &setting);

    // Save old setting
    oldSetting = setting;

    // Remove the echo of the stdin
    setting.c_lflag &= ~(ICANON | ECHO);
    
    // Set the new setting
    tcsetattr(STDIN_FILENO, TCSANOW, &setting);
}

void unsetTerminal() {
    tcsetattr(STDIN_FILENO, TCSANOW, &oldSetting);
}