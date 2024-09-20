# Verifica se 2 stringhe sono uguali

/* Parametri:
 *  2 indirizzi di stringhe in ESI e EDI
 * Return:
 *	AH = 1 se le stringhe sono uguali, altrimenti AH = 0
*/

/* Registri modificati:
 * AH, BH, BL (BX), ECX
 * ESI, EDI
*/

.section .text
.global cmpstr

.type cmpstr, @function

cmpstr:

movb $1, %ah
xorl %ecx, %ecx     # Per lo spiazzamento

ciclo:
    // FORSE bisogna mettere (%esi, %ecx) in %bl e l'altro in %bh
    movb (%esi, %ecx), %bl
    movb (%edi, %ecx), %bh
    cmpb %bl, %bh
    jne sbagliato   # Se sono diverse salta subito a "sbagliato"
    # Se sono qui significa che i char sono uguali
    cmpb $0, %bl   # Verifico la fine di una stringa (e quindi la fine anche dell'altra)
    je fine
    incl %ecx

jmp ciclo

sbagliato:
    movb $0, %ah

fine:

ret
