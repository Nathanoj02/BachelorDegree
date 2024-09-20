# Numero a stringa

/* Parametri:
 *	Il numero deve essere in EAX
 * Return:
 *	L'indirizzo della stringa si trova in ESI
 * 	La lunghezza della stringa si trova in DH
 *  La lunghezza effettiva del numero si trova in DL
*/

/* Registri modificati:
 * AX, BX, ECX, DX
 * ESI
*/

.section .data

num_str:
    .ascii "00000"
num_str_len:
    .byte . - num_str


.section .text
.global itoa

.type itoa, @function

itoa:
# Preparo il registro ECX (= lunghezza del numero)
xorl %ecx, %ecx
movb num_str_len, %cl
leal num_str, %esi  # prendo l'indirizzo in memoria della stringa
xorw %dx, %dx

conversione:
    movw $10, %bx   # Prendo il resto quindi la cifra a destra
    divw %bx        # DX:AX / BX --> Q in AX, R in DX
    addb $48, %dl   # Aggiungo 48 per ASCII

    decl %ecx    # decremento ECX per lo spiazzamento
    movb %dl, (%esi, %ecx)   # indiretto a ESI con spiazzamento di ECX
    incl %ecx    # reincremento ECX

    xorw %dx, %dx   # azzero il registro del resto

loop conversione    # = ECX-- e se ECX > 0 ritorna a "conversione"

# Devo passare l'indirizzo e la lunghezza
# Indirizzo si trova già in ESI e lunghezza in EDX (gia' pronti per la stampa)
movb num_str_len, %dh
movb %dh, %dl
xorl %ecx, %ecx

// Trovo la lunghezza effettiva del numero (conto il numero di 0 iniziali)
ciclo:
    cmpb $48, (%esi, %ecx)
    jne dopo_ciclo
    decb %dl
    incl %ecx
jmp ciclo

dopo_ciclo:

ret
