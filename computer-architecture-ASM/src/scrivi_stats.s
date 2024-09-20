# Scrive l'ultima riga del file di output con le specifiche richieste

/* Parametri:
 *  Valori nello Stack (valori interi):
 *   |                   |
 *   | PC                | <-- (ESP)
 *   | num_data          |
 *   | vel_sum           |
 *   | vel_max           |
 *   | temperatura_max   |
 *   | rpm_max           |
 *   |___________________|
 * Return:
 *	Modifica la stringa di output
*/

/* Registri usati:
 * EAX, EBX, ECX, EDX
 * ESI
*/

.section .text
.global scrivi_stats

.type scrivi_stats, @function

scrivi_stats:
// RPM max --> devo convertirlo in stringa e metterlo nel file di output
movl 20(%esp), %eax
call scrivi_stats_sup
// Aggiungo una ,
movl $44, (%edi, %ebx)
incl %ebx
// Temperatura max
movl 16(%esp), %eax
call scrivi_stats_sup
// Aggiungo una ,
movl $44, (%edi, %ebx)
incl %ebx
// Velocità max
movl 12(%esp), %eax
call scrivi_stats_sup
// Aggiungo una ,
movl $44, (%edi, %ebx)
incl %ebx
// Velocità media
movl 8(%esp), %ecx  # = somma delle velocità
xorl %eax, %eax     # Preparo i registri
movw %cx, %ax
sarl $16, %ecx      
xorl %edx, %edx
movw %cx, %dx

movl 4(%esp), %ecx  # anche in CX, perché non supera mai i 16 bit
divw %cx            # DX:AX / CX --> Q in AX, R in DX
call scrivi_stats_sup
// Aggiungo \n e \0
movl $10, (%edi, %ebx)
incl %ebx
movl $0, (%edi, %ebx)

ret
