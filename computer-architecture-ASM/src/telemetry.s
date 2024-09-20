.section .data

invalid_pilot_str:	
    .string "Invalid\0"

nome_pilota:
    .string "1234567890123456789\0"


tempo_str:
    .string "12345678901\0"

id_str:
    .string "12\0"
id:
    .byte 0

vel_str:
    .string "123\0"
vel_max:
    .long 0
vel_sum:
    .long 0

num_data:
    .long 0

temperatura_str:
    .string "123\0"
temperatura_max:
    .long 0

rpm_str:
    .string "12345\0"
rpm_max:
    .long 0

frase_errore_numero:
	.ascii "Errore nella conversione da stringa a numero:\n sono stati inseriti caratteri non numerici!\n"
frase_errore_numero_len:
	.long . - frase_errore_numero

spiazzamento_output:
    .long 0


.section .text
.global telemetry

telemetry:

// Mi salvo gli indirizzi delle stringhe dei file
movl 4(%esp), %esi      # Input (da leggere)
movl 8(%esp), %edi      # Output (da scrivere)

// Salvo nello Stack i registri GP
pushl %eax
pushl %ebx
pushl %ecx
pushl %edx

xorl %ecx, %ecx

// Devo trovare l'ID corrispondente al pilota
# Mi salvo l'indirizzo di memoria della stringa nome_pilota (mi serve solo per il primo ciclo)
leal nome_pilota, %ebx

call leggi

# Mi salvo i registri importanti (push)
pushl %ecx
pushl %esi
pushl %edi
# Preparo i registri con i parametri della funzione
leal nome_pilota, %esi
# Chiamo la funzione
call stoid
# Ripristino i valori (pop)
popl %edi
popl %esi
popl %ecx

# Adesso abbiamo l'ID in AL
cmpb $20, %al   # Guardo se è Invalido
je stampa_invalid
movb %al, id    # Salvo in una variabile
    
// Adesso devo leggere le altre righe
incl %ecx      # Vado al primo carattere della nuova riga

ciclo:
    leal tempo_str, %ebx    # Mi salvo il tempo nella variabile tempo_str
    call leggi

    leal id_str, %ebx
    call leggi

    pushl %ecx
    pushl %esi

    leal id_str, %esi
    call atoi
    // Verifico se la conversione è avvenuta correttamente
	cmpb $0, %bl
	je stampa_errore_numero
    # Adesso ho l'ID del file in EAX (in AL)
    popl %esi
    popl %ecx

    movb id, %ah
    cmpb %al, %ah

    jne arriva_riga_successiva

    // Se sono qui significa che ho trovato la riga giusta
    # Devo leggere velocità, rpm e temperatura
    leal vel_str, %ebx
    call leggi

    leal rpm_str, %ebx
    call leggi

    leal temperatura_str, %ebx
    call leggi

    // Qui faccio i calcoli ------------------------
    pushl %ecx
    pushl %esi

    incl num_data                   # Devo incrementare num_data
    movl spiazzamento_output, %ebx  # spiazzamento del file di output

    # Carico i parametri della funzione nello Stack (ne servono tanti)
    leal tempo_str, %esi
    pushl %esi
    leal rpm_str, %esi
    pushl %esi
    pushl rpm_max
    leal temperatura_str, %esi
    pushl %esi
    pushl temperatura_max
    leal vel_str, %esi
    pushl %esi
    pushl vel_max
    pushl vel_sum

    call scrivi_output

    cmpb $0, %cl    # Verifico se ci sono errori
    je stampa_errore_numero

    # Pulisco lo Stack e modifico le variabili
    popl vel_sum
    popl vel_max
    popl %esi
    popl temperatura_max
    popl %esi
    popl rpm_max
    popl %esi
    popl %esi
    
    // ---------------------------------------------

    popl %esi   # Ripristino l'indirizzo di input
    popl %ecx

    movl %ebx, spiazzamento_output

    // Devo controllare se sono arrivato a fine file
    cmpb $0, %dl
    je fine_file

    incl %ecx   # Se non sono arrivato alla fine, devo incrementare ECX (vado alla riga successiva)


jmp ciclo

arriva_riga_successiva:     # Ciclo che incrementa ECX fino all'inizio della riga successiva
    movb (%esi, %ecx), %dl

    incl %ecx

    cmpb $10, %dl           # Guardo se è arrivato a fine riga
    je ciclo

    cmpb $0, %dl            # Guardo se è arrivato a fine file
    je fine_file

jmp arriva_riga_successiva

fine_file:
    movl spiazzamento_output, %ebx

    pushl rpm_max
    pushl temperatura_max
    pushl vel_max
    pushl vel_sum
    pushl num_data

    call scrivi_stats   # Non mi serve salvare registri perché
                        # sono a fine programma (non mi servono più)

    movl $5, %ecx
    svuota_stack:       # Pulisco lo Stack con un loop (non mi interessa salvare i risultati)
        popl %eax
    loop svuota_stack

jmp fine

stampa_errore_numero:   # Stampa a video
    movl $4, %eax
    movl $1, %ebx
    leal frase_errore_numero, %ecx
    movl frase_errore_numero_len, %edx
    int $0x80
jmp fine

stampa_invalid:     # Se il nome del pilota è errato (stampa nel file)
    leal invalid_pilot_str, %esi
    xorl %ecx, %ecx

    ciclo_invalid:
        movb (%esi, %ecx), %dl
        cmpb $0, %dl
        je dopo_ciclo_invalid
        movb %dl, (%edi, %ecx)
        incl %ecx
    jmp ciclo_invalid

    dopo_ciclo_invalid:
        movb $10, (%edi, %ecx)
        incl %ecx
        movb $0, (%edi, %ecx)

fine:
    // Ripristino i registri GP
    popl %edx
    popl %ecx
    popl %ebx
    popl %eax

ret
