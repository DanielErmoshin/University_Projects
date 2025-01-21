// Grammar for simple expressions
grammar scheme;
root : expr+             // The label is already root
    ;

expr : '(' 'read' ')'                            # Read
    | '(' 'display' expr ')'                     # Display
    | '(' 'newline' ')'                          # NewLine

    | '(' op expr* ')'                           # DefaultOp
    | '(' 'define' VAR expr ')'                  # ConstAssig
    | '(' 'define' '(' VAR VAR* ')' expr* ')'    # DefineFunc
    | '(' 'let' '(' myLet+ ')' expr* ')'         # LetClasue

    | '(' 'cond' cond+ ')'                       # Conditional
    | '(' 'if' expr expr expr ')'                # If
    | '(' 'and' expr+ ')'                        # and
    | '(' 'or' expr+ ')'                         # or
    | '(' 'not' expr ')'                         # not

    | '\'' '(' (expr)* ')'                       # list
    | '(' 'car' expr ')'                         # car
    | '(' 'cdr' expr ')'                         # cdr
    | '(' 'cons' expr expr ')'                   # cons
    | '(' 'null?' expr ')'                       # null

    | NUM                                        # numero
    | VAR                                        # variable
    | BOOL                                       # boolean
    | STRING                                     # string
    ;

cond : '(' 'else' expr ')'
    | '(' expr expr ')'
    ;

myLet  : '(' VAR  expr ')' ;

op   : ARITH
    | REL
    | VAR
    ;

ARITH : '+' | '-' | '*' | '/' ;  
REL : '>' | '<' | '>=' | '<=' | '=' | '<>' ;

VAR : [a-zA-Z][a-zA-Z0-9\-?]* ;
NUM : '-'? [0-9]+ ;
BOOL : '#t' | '#f' ;
STRING : '"' (~["\r\n])* '"' ;

COMENTARIO : ';' ~[\r\n]* -> skip ;
WS : [ \t\r\n]+ -> skip ;
