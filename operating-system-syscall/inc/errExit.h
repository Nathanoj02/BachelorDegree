/************************************
* VR471297,     VR472851
* Jonathan Fin, Roberto Trinchini
* 19/06/2023
*************************************/

#ifndef _ERREXIT_HH
#define _ERREXIT_HH

/**
 * @brief Funzione di supporto per stampare il messaggio d'errore dell'ultima system call che ha fallito
 * 
 * @param msg Messaggio da scrivere prima del messaggio di errore
 * 
 * Termina il processo chiamante
 */
void errExit(const char *msg);

#endif