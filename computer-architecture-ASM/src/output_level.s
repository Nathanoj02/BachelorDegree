# Scrive i livelli (LOW, MEDIUM o HIGH)
# sulla stringa di output dei parametri passati

/* Parametri:
 *  Il numero da confrontare deve essere in EAX
 *  Il limite LOW deve essere in ECX
 *  Il limite HIGH deve essere in EDX
 *  Spiazzamento della stringa di output in EBX
*/

.section .text
.global output_level

.type output_level, @function

output_level:

cmpl %ecx, %eax
jg med_high
# Significa che è minore (copio LOW in output)
movb $76, (%edi, %ebx)
incl %ebx
movb $79, (%edi, %ebx)
incl %ebx
movb $87, (%edi, %ebx)
incl %ebx

jmp fine

med_high:
cmpl %edx, %eax
jg high
# Significa che è medio (copio MEDIUM)
movb $77, (%edi, %ebx)
incl %ebx
movb $69, (%edi, %ebx)
incl %ebx
movb $68, (%edi, %ebx)
incl %ebx
movb $73, (%edi, %ebx)
incl %ebx
movb $85, (%edi, %ebx)
incl %ebx
movb $77, (%edi, %ebx)
incl %ebx

jmp fine

high:
# Significa che è alto
movb $72, (%edi, %ebx)
incl %ebx
movb $73, (%edi, %ebx)
incl %ebx
movb $71, (%edi, %ebx)
incl %ebx
movb $72, (%edi, %ebx)
incl %ebx

fine:
ret
