; Definició d'una funció que suma dos nombres
(define (suma x y)
    (+ x y))

; Definició d'una funció que mostra la suma de dos valors llegits per teclat
(define (mostrar-suma-llegida)
    (display "Introdueix dos valors a sumar: ")
    (newline)
    (let ((val1 (read))
            (val2 (read)))
        (display "La suma és: ")
        (display (suma val1 val2))
        (newline)))

; Definició d'una funció per provar cond, else i els números negatius
(define (prova-condicio x)
    (cond
        ((> x -18) "x és més gran que -18")
        ((< x -18) "x és més petit que -18")
        (else     "x es igual a -18")))

; Definició d'una funció per provar if
(define (prova-if x)
    (if (> x 0)
        "x és positiu"
        "x és negatiu o zero"))

; Definició d'una funció per provar and
(define (prova-and x y)
    (if (and (> x 0) (> y 0))
        "Ambdós són positius"
        "Un o més són negatius o zero"))

; Definició d'una funció per provar or
(define (prova-or x y)
    (if (or (> x 0) (> y 0))
        "Almenys un és positiu"
        "Cap ni un és positiu"))

; Funció principal:
(define (main)
    (display "==== JOC DE PROVES ====")
    (newline)

    ; 1. Provar la suma i la lectura
    (display "-- Prova 1: Suma llegida de teclat --")
    (newline)
    (mostrar-suma-llegida)

    ; 2. Provar la condicional
    (display "-- Prova 2: Condicional amb cond, else i els números negatius --")
    (newline)
    (display (prova-condicio -18))  ; Esperem "x es igual a -18"
    (newline)

    ; 3. Provar l'if
    (display "-- Prova 3: Condicional amb if --")
    (newline)
    (display (prova-if 0))        ; Esperem "x és negatiu o zero"
    (newline)

    ; 4. Provar and
    (display "-- Prova 4: Condicional amb and --")
    (newline)
    (display "(prova-and 3 5) => ")
    (display (prova-and 3 5))  ; Esperem "Ambdós són positius"
    (newline)
    (display "(prova-and -1 5) => ")
    (display (prova-and -1 5))  ; Esperem "Un o més són negatius o zero"
    (newline)

    ; 5. Provar or
    (display "-- Prova 5: Condicional amb or --")
    (newline)
    (display "(prova-or -3 -5) => ")
    (display (prova-or -3 -5))  ; Esperem "Cap ni un és positiu"
    (newline)
    (display "(prova-or -3 5) => ")
    (display (prova-or -3 5))  ; Esperem "Almenys un és positiu"
    (newline)

    (display "==== FI DEL JOC DE PROVES ====")
    (newline)
)
