# Stringa a ID

/* Parametri:
 *  L'indirizzo della stringa da confrontare deve essere in ESI
 * Return:
 *	L'ID si trova in AL (ID = 20 --> Invalid)
*/

/* Registri usati:
 * AL, AH, BX, ECX
 * ESI, EDI
*/

.section .data

pilot_0_str:
    .string "Pierre Gasly\0"
pilot_1_str:
    .string "Charles Leclerc\0"
pilot_2_str:
    .string "Max Verstappen\0"
pilot_3_str:                       
    .string "Lando Norris\0"
pilot_4_str:
    .string "Sebastian Vettel\0"
pilot_5_str:
    .string "Daniel Ricciardo\0"
pilot_6_str: 
    .string "Lance Stroll\0"
pilot_7_str:
    .string "Carlos Sainz\0"
pilot_8_str:
    .string "Antonio Giovinazzi\0"
pilot_9_str:
    .string "Kevin Magnussen\0"
pilot_10_str:
    .string "Alexander Albon\0"
pilot_11_str:
    .string "Nicholas Latifi\0"
pilot_12_str:
    .string "Lewis Hamilton\0"
pilot_13_str:
    .string "Romain Grosjean\0"
pilot_14_str:
    .string "George Russell\0"
pilot_15_str:
    .string "Sergio Perez\0"
pilot_16_str:
    .string "Daniil Kvyat\0"
pilot_17_str:
    .string "Kimi Raikkonen\0"
pilot_18_str:
    .string "Esteban Ocon\0"
pilot_19_str:
    .string "Valtteri Bottas\0"


.section .text
.global stoid

.type stoid, @function

stoid:
	leal pilot_0_str, %edi  # Punto alla stringa del primo pilota
    call cmpstr     # Verifico se le stringhe sono uguali
    movb $0, %al    # Metti l'ID
    cmpb $1, %ah
    je fine     # Se sono uguali vado alla fine

    // Rifaccio per tutti i piloti
    leal pilot_1_str, %edi
    call cmpstr
    movb $1, %al
    cmpb $1, %ah
    je fine

    leal pilot_2_str, %edi
    call cmpstr
    movb $2, %al
    cmpb $1, %ah
    je fine

    leal pilot_3_str, %edi
    call cmpstr
    movb $3, %al
    cmpb $1, %ah
    je fine

    leal pilot_4_str, %edi
    call cmpstr
    movb $4, %al
    cmpb $1, %ah
    je fine

    leal pilot_5_str, %edi
    call cmpstr
    movb $5, %al
    cmpb $1, %ah
    je fine

    leal pilot_6_str, %edi
    call cmpstr
    movb $6, %al
    cmpb $1, %ah
    je fine

    leal pilot_7_str, %edi
    call cmpstr
    movb $7, %al
    cmpb $1, %ah
    je fine

    leal pilot_8_str, %edi
    call cmpstr
    movb $8, %al
    cmpb $1, %ah
    je fine

    leal pilot_9_str, %edi
    call cmpstr
    movb $9, %al
    cmpb $1, %ah
    je fine

    leal pilot_10_str, %edi
    call cmpstr
    movb $10, %al
    cmpb $1, %ah
    je fine

    leal pilot_11_str, %edi
    call cmpstr
    movb $11, %al
    cmpb $1, %ah
    je fine

    leal pilot_12_str, %edi
    call cmpstr
    movb $12, %al
    cmpb $1, %ah
    je fine

    leal pilot_13_str, %edi
    call cmpstr
    movb $13, %al
    cmpb $1, %ah
    je fine

    leal pilot_14_str, %edi
    call cmpstr
    movb $14, %al
    cmpb $1, %ah
    je fine

    leal pilot_15_str, %edi
    call cmpstr
    movb $15, %al
    cmpb $1, %ah
    je fine

    leal pilot_16_str, %edi
    call cmpstr
    movb $16, %al
    cmpb $1, %ah
    je fine

    leal pilot_17_str, %edi
    call cmpstr
    movb $17, %al    
    cmpb $1, %ah
    je fine

    leal pilot_18_str, %edi
    call cmpstr
    movb $18, %al    
    cmpb $1, %ah
    je fine

    leal pilot_19_str, %edi
    call cmpstr
    movb $19, %al    
    cmpb $1, %ah
    je fine

    // Se sono qua significa che nessun pilota è giusto
    movb $20, %al   # Metto l'ID invalido

fine:

ret
