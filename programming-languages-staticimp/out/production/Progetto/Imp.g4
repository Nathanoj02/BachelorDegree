grammar Imp;

// "Generally, each new keyword command in ArnoldC should be written on a new line" - Wiki

prog :  (fun ENDL+)* START_MAIN ENDL+ com ENDL* END_MAIN (ENDL* fun ENDL*)* EOF ;

fun :   START_FUN ID ENDL+
        (FUN_ARG ID ENDL+)* (NON_VOID ENDL+)? com
        (RETURN exp ENDL+)? END_FUN
    ;

com :   PRINT exp ENDL+ com                                             # print
    |   DECLARE ID ENDL+ ASSIGN exp ENDL+ com                           # variable
    |   READ_INT ID ENDL+ com                                           # readInt
    |   START_EXP ID ENDL+
        FIST_OP exp ENDL+
        (op=(PLUS | MINUS | MUL | DIV | MOD | EQ | GT | AND | OR) exp ENDL+)*
        END_EXP ENDL+ com                                               # assign
    |   IF exp ENDL+ com ENDL*
        (ELSE ENDL+ com ENDL*)? END_IF ENDL+ com                        # if
    |   WHILE exp ENDL+ com ENDL* END_WHILE ENDL+ com                   # while
    |   (FUN_ASGN ID ENDL+)? CALL ID (exp)* ENDL+ com                   # call
    |                                                                   # empty
    ;

exp :   INT     # int
    |   MACRO_0 # macro0
    |   MACRO_1 # macro1
    |   STRING  # string
    |   ID      # id
    ;



START_MAIN : 'IT\'S SHOWTIME' ;
END_MAIN   : 'YOU HAVE BEEN TERMINATED';

PRINT     : 'TALK TO THE HAND' ;

DECLARE   : 'HEY CHRISTMAS TREE' ;
ASSIGN    : 'YOU SET US UP' ;
READ_INT  : 'I WANT TO ASK YOU A BUNCH OF QUESTIONS AND I WANT TO HAVE THEM ANSWERED IMMEDIATELY' ;

START_EXP : 'GET TO THE CHOPPER' ;
FIST_OP   : 'HERE IS MY INVITATION' ;
END_EXP   : 'ENOUGH TALK' ;

IF        : 'BECAUSE I\'M GOING TO SAY PLEASE' ;
ELSE      : 'BULLSHIT' ;
END_IF    : 'YOU HAVE NO RESPECT FOR LOGIC' ;

WHILE     : 'STICK AROUND' ;
END_WHILE : 'CHILL' ;

START_FUN : 'LISTEN TO ME VERY CAREFULLY' ;
END_FUN   : 'HASTA LA VISTA, BABY' ;
FUN_ARG   : 'I NEED YOUR CLOTHES YOUR BOOTS AND YOUR MOTORCYCLE' ;
NON_VOID  : 'GIVE THESE PEOPLE AIR' ;
RETURN    : 'I\'LL BE BACK' ;

CALL      : 'DO IT NOW' ;
FUN_ASGN  : 'GET YOUR ASS TO MARS' ;

PLUS  : 'GET UP' ;
MINUS : 'GET DOWN' ;
MUL   : 'YOU\'RE FIRED' ;
DIV   : 'HE HAD TO SPLIT' ;
MOD   : 'I LET HIM GO' ;

EQ  : 'YOU ARE NOT YOU YOU ARE ME';
GT  : 'LET OFF SOME STEAM BENNET' ;
OR  : 'CONSIDER THAT A DIVORCE' ;
AND : 'KNOCK KNOCK';

INT : '0' | [1-9][0-9]* | '-' [1-9][0-9]* ;
MACRO_0 : '@I LIED' ;
MACRO_1 : '@NO PROBLEMO' ;

STRING : '"' STRCHR* '"' ;
fragment STRCHR : ~["\\] | ESC ;
fragment ESC : '\\' [btnfr"'\\] ;

ID : [a-zA-Z][a-zA-Z0-9]* ;

ENDL : '\n' ;

WS : [ \t\r]+ -> skip ;
