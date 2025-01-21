; Definim una llista global
(define llista '(1 2 3 4 5))

; Funció per sumar tots els elements d'una llista (recursivament)
(define (mult-llista l)
    (if (null? l)
        1
        (* (car l) (mult-llista (cdr l)))))

; Funció que ens demostra l'ús de car, cdr, cons, etc.
(define (prova-llistes)
    (display "La llista original és: ")
    (display llista)
    (newline)

    (display "És la llista buida? ")
    (display (null? llista))
    (newline)

    (display "El primer element (car) és: ")
    (display (car llista))
    (newline)

    (display "La llista sense el primer element (cdr) és: ")
    (display (cdr llista))
    (newline)

    ; Retorna una nova llista a partir de la original amb 0 al principi. Realment no s'aplica ningún canvi.
    (display "Afegim un 0 al principi (cons 0 llista): ")
    (display (cons 0 llista))
    (newline)
    
    (display "La llista que seguim tenint a la pila: ")
    (display llista)
    (newline)

    (display "Afegim realment l'element 0 a llista mitjançant la redefinició d'aquesta: ")
    (define llista (cons 0 llista))
    (display llista)
    (newline)

    (display "Treiem el primer element de la llistamitjançant la redefinició d'aquesta: ")
    (define llista (cdr llista))
    (display llista)
    (newline)

    (display "La multiplicació de tots els elements de la llista és: ")
    (display (mult-llista llista))
    (newline))

; Funció principal
(define (main)
    (display "==== JOC DE PROVES: LLISTES ====")
    (newline)
    
    (prova-llistes)

    (newline)
    (display "==== FI DEL JOC DE PROVES ====")
    (newline)
)
