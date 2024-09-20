# Scrive nella stringa di output i dati di ogni misurazione

/* Parametri:
 *  Valori nello Stack (puntatori a stringhe o valori interi):
 *   |                   |
 *   | PC                | <-- (ESP)
 *   | vel_sum           |
 *   | vel_max           |
 *   | vel_str *         |
 *   | temperatura_max   |
 *   | temperatura_str * |
 *   | rpm_max           |
 *   | rpm_str *         |
 *   | tempo_str *       |
 *   |___________________|
 * Return:
 *	Modifica la stringa di output
 *  Valori nuovi delle variabili nello Stack 
 *   negli stessi posti descritti sopra
 *	CL = 0 --> errore (erano presenti caratteri non numerici nella conversione atoi)
*/

/* Registri modificati:
 * EAX, EBX, ECX, EDX
 * ESI
*/

.section .text
.global scrivi_output

.type scrivi_output, @function

scrivi_output:
// Devo copiare il tempo nel file di output
movl 32(%esp), %esi
xorl %ecx, %ecx

copia_tempo:
    movb (%esi, %ecx), %dl
    cmpb $0, %dl
    je dopo_copia_tempo
    movb %dl, (%edi, %ebx)

    incl %ecx
    incl %ebx
jmp copia_tempo
dopo_copia_tempo:

// Aggiungere una ,
movb $44, (%edi, %ebx)
incl %ebx

// RPM (convertire in numero, guardare se è max
// e vedere livello (LOW, MEDIUM, HIGH))
movl 28(%esp), %esi
pushl %ebx
call atoi
# Verifico se la conversione è avvenuta correttamente
cmpb $0, %bl
je errore

popl %ebx
# Adesso in EAX ho il valore dei rpm
cmpl 24(%esp), %eax         # guardo se è massimo
jle dopo_rpm_max
movl %eax, 24(%esp)         # se è > sovrascrivo rpm_max
dopo_rpm_max:

movl $5000, %ecx
movl $10000, %edx
call output_level

// Aggiungere una ,
movb $44, (%edi, %ebx)
incl %ebx

// Temperatura (stessa cosa)
movl 20(%esp), %esi
pushl %ebx
call atoi

cmpb $0, %bl
je errore

popl %ebx

cmpl 16(%esp), %eax
jle dopo_temperatura_max
movl %eax, 16(%esp)
dopo_temperatura_max:

movl $90, %ecx
movl $110, %edx
call output_level

// Aggiungere una ,
movb $44, (%edi, %ebx)
incl %ebx

// Velocità (stessa cosa + sommarla alla somma delle velocità)
movl 12(%esp), %esi
pushl %ebx
call atoi

cmpb $0, %bl
je errore

popl %ebx

cmpl 8(%esp), %eax
jle dopo_vel_max
movl %eax, 8(%esp)
dopo_vel_max:

movl $100, %ecx
movl $250, %edx
call output_level

addl %eax, 4(%esp)  # vel_sum += vel

// Aggiungere \n
movb $10, (%edi, %ebx)
incl %ebx

jmp non_errore

errore:
    movb $0, %cl
    jmp fine

non_errore:
    movb $1, %cl    # Dava errori per EBX = 2304 (BL = 0)

fine:

ret
