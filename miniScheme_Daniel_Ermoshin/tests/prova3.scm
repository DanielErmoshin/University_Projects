; Definim la funció map
(define (map func llista)
    (cond
        ((null? llista) '())
        (else (cons (func (car llista)) (map func (cdr llista))))))

; Definim la funció filter
(define (filter predicat llista)
    (cond
        ((null? llista) '())
        ((predicat (car llista))
        (cons (car llista) (filter predicat (cdr llista))))
        (#t (filter predicat (cdr llista)))))

; Funció auxiliar per doblar nombres
(define (triplica x) (* x 3))

; Funció auxiliar que retorna #t si un nombre és imparell
(define (imparell? x) (not (= (mod x 2) 0)))

; Funció auxiliar que retorna #t si un nombre és parell
(define (parell? x) (= (mod x 2) 0))

; Funció principal
(define (main)
    (display "==== JOC DE PROVES: MAP I FILTER ====")
    (newline)

    (display "-- Prova 1: map amb la funció triplica --")
    (newline)
    (display "(map triplica '(1 2 3 4)) => ")
    (display (map triplica '(1 2 3 4)))  ; Esperat: (3 4 9 12)
    (newline)

    (display "(map triplica '()) => ")
    (display (map triplica '()))  ; Esperat: ()
    (newline)

    (display "-- Prova 2: filter amb la funció parell? i imparell? --")
    (newline)
    (display "(filter imparell? '(1 2 3 4)) => ")
    (display (filter imparell? '(1 2 3 4))) ; Esperat: (1 3)
    (newline)

    (display "(filter parell? '(1 2 3 4)) => ")
    (display (filter parell? '(1 2 3 4))) ; Esperat: (2 4)
    (newline)

    (display "-- Prova 3: composar map i filter --")
    (newline)
    (display "(filter parell? (map triplica '(1 2 3 4))) => ")
    (display (filter parell? (map triplica '(1 2 3 4)))) ; Esperat: (6 12)
    (newline)

    (display "==== FI DEL JOC DE PROVES ====")
    (newline)
)
