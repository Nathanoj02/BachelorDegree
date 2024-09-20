# Funzione di supporto per scrivi_stats
# (per non ripetere 4 volte lo stesso codice)

/* Parametri:
 *  Valore da scrivere in output in EAX
*/

.section .text
.global scrivi_stats_sup

.type scrivi_stats_sup, @function

scrivi_stats_sup:
cmpl $0, %eax
je scrivi_zero
// Devo convertirlo in stringa e metterlo nel file di output
pushl %ebx
call itoa
popl %ebx
# Es: esi -> 04035
# DH = 5, DL = 4
xorl %ecx, %ecx
movb %dh, %cl
subb %dl, %cl  # in CL (= ECX) hai lo spiazzamento giusto

ciclo_scrivi:
    movb (%esi, %ecx), %dl
    movb %dl, (%edi, %ebx)
    incb %cl
    incl %ebx
    cmpb %dh, %cl
    je fine
jmp ciclo_scrivi

scrivi_zero:
    movb $48, (%edi, %ebx)
    incl %ebx

fine:

ret
