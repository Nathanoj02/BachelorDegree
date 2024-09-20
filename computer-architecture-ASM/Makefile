FLAGS = -m32 -c
FLAGS_PIE = -no-pie

NOME_MAIN = telemetry
FUN1 = stoid
FUN2 = cmpstr
ATOI = atoi
ITOA = itoa

SUP1 = leggi
SUP2 = scrivi_output
SUP3 = output_level
SUP4 = scrivi_stats
SUP5 = scrivi_stats_sup

OBJS =	obj/main.o obj/$(NOME_MAIN).o obj/$(FUN1).o obj/$(FUN2).o \
		obj/$(ATOI).o obj/$(ITOA).o \
		obj/$(SUP1).o obj/$(SUP2).o obj/$(SUP3).o obj/$(SUP4).o obj/$(SUP5).o


all: bin/$(NOME_MAIN)

bin/$(NOME_MAIN): $(OBJS)	 
	gcc -m32 $(FLAGS_PIE) $(OBJS) -o bin/$(NOME_MAIN)

obj/main.o: src/main.c
	gcc $(FLAGS) src/main.c -o obj/main.o

obj/$(NOME_MAIN).o: src/$(NOME_MAIN).s
	gcc $(FLAGS) src/$(NOME_MAIN).s -o obj/$(NOME_MAIN).o

obj/$(FUN1).o: src/$(FUN1).s
	gcc $(FLAGS) src/$(FUN1).s -o obj/$(FUN1).o

obj/$(FUN2).o: src/$(FUN2).s
	gcc $(FLAGS) src/$(FUN2).s -o obj/$(FUN2).o

obj/$(ATOI).o: src/$(ATOI).s
	gcc $(FLAGS) src/$(ATOI).s -o obj/$(ATOI).o

obj/$(ITOA).o: src/$(ITOA).s
	gcc $(FLAGS) src/$(ITOA).s -o obj/$(ITOA).o

obj/$(SUP1).o: src/$(SUP1).s
	gcc $(FLAGS) src/$(SUP1).s -o obj/$(SUP1).o

obj/$(SUP2).o: src/$(SUP2).s
	gcc $(FLAGS) src/$(SUP2).s -o obj/$(SUP2).o

obj/$(SUP3).o: src/$(SUP3).s
	gcc $(FLAGS) src/$(SUP3).s -o obj/$(SUP3).o

obj/$(SUP4).o: src/$(SUP4).s
	gcc $(FLAGS) src/$(SUP4).s -o obj/$(SUP4).o

obj/$(SUP5).o: src/$(SUP5).s
	gcc $(FLAGS) src/$(SUP5).s -o obj/$(SUP5).o

clean:
	rm -rf obj/* bin/$(NOME_MAIN)
