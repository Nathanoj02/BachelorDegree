# Stringa a Numero

/* Parametri:
 *	L'indirizzo della stringa deve essere in ESI
 * Return:
 *	Il numero si trova in EAX
 * 	BL = 1 --> operazione andata a buon fine
 *	BL = 0 --> erano presenti caratteri non numerici
*/

/* Registri modificati:
 * EAX, EBX, ECX, DX
 * ESI
*/

.section .text
.global atoi

.type atoi, @function

atoi:

movl $0, %ecx  # azzero il contatore
movl $0, %ebx  # azzero il registro EBX
movl $0, %eax	# azzero EAX

ripeti_a:
	movb (%esi, %ecx), %bl

	cmpb $0, %bl    # vedo se e' stato letto il carattere '\0'
	je fine_ripeti_a

	subb $48, %bl   # converte il codice ASCII della cifra nel numero corrisp.

	# Controllo che 0 <= BL <= 9
	cmpb $0, %bl
	jl errore	# Se BL < 0
	cmpb $9, %bl
	jg errore	# Se BL > 9

	pushw %bx	# Mi salvo BL (non posso salvare solo 1 byte, minimo 2)

	movw $10, %bx
	
	# Per la moltiplicazione mi servono 32 bit per avere tutti i numeri a 5 cifre
	mulw %bx    # DX:AX = AX * 10
	
	# Metto tutto in EAX
	pushw %ax # mi salvo AX
	movw %dx, %ax
	salw $16, %ax	# shift a sinistra
	popw %ax	# riprendo il valore 

	popw %bx	# Riprendo il vlaore
	xorb %bh, %bh

	addl %ebx, %eax

	inc %ecx
	jmp ripeti_a

fine_ripeti_a:
	# Operazione andata a buon fine, il risultato si trova in EAX
	movb $1, %bl
	jmp dopo_errore

errore:
	movb $0, %bl

dopo_errore:

ret
