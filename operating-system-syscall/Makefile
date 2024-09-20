# /************************************
# * VR471297,		VR472851
# * Jonathan Fin,	Roberto Trinchini
# * 19/06/2023
# *************************************/

C_FLAGS = -Wall -std=gnu99
INCLUDES = -I./inc

SERVER_SRCS = src/server.c src/errExit.c src/shared_memory.c src/semaphore.c src/playground.c src/terminal.c
SERVER_BIN = bin/F4Server

CLIENT_SRCS = src/client.c src/errExit.c src/shared_memory.c src/semaphore.c src/playground.c src/graphic.c src/terminal.c
CLIENT_BIN = bin/F4Client

BOT_SRCS = src/bot.c src/errExit.c src/shared_memory.c src/playground.c
BOT_BIN = bin/F4Bot

all: server client bot

server:
	@gcc $(SERVER_SRCS) $(C_FLAGS) $(INCLUDES) -o $(SERVER_BIN)
	@echo "Made server executable"

client:
	@gcc $(CLIENT_SRCS) $(C_FLAGS) $(INCLUDES) -o $(CLIENT_BIN)
	@echo "Made client executable"

bot:
	@gcc $(BOT_SRCS) $(C_FLAGS) $(INCLUDES) -o $(BOT_BIN)
	@echo "Made bot executable"

.PHONY: clean

clean:
	@rm -f bin/*
	@echo "Removed executables"
