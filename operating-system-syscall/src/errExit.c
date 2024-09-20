/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>

#include "errExit.h"

void errExit(const char *msg) {
    perror(msg);
    exit(1);
}