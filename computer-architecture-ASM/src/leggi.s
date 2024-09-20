# Legge l'ID dal file di input

/* Parametri:
 *  L'indirizzo della stringa su cui scrivere deve essere in EBX
 * Return:
 *	Stringa a cui EBX punta modificata
 *  ECX incrementato fino al punto dove si è arrivati nel file
*/

/* Registri modificati:
 * EAX, EBX, ECX, DL
*/

.section .text
.global leggi

.type leggi, @function

leggi:

xorl %eax, %eax

ciclo:
    movb (%esi, %ecx), %dl

    cmpb $44, %dl   # Guardo se è arrivato alla ,
    je dopo_ciclo
    cmpb $10, %dl   # oppure a fine riga
    je dopo_ciclo
    cmpb $0, %dl    # oppure a fine file
    je dopo_ciclo

    movb %dl, (%ebx, %eax)
    incl %ecx
    incl %eax
jmp ciclo

dopo_ciclo:
    movb $0, (%ebx, %eax)   # Aggiungo '\0' alla fine della stringa
incl %ecx

ret
