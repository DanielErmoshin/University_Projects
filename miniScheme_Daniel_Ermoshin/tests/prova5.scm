; (foldl f acc llista) aplica f acumulant els valors de la llista
(define (foldl f acc llista)
  (if (null? llista)
        acc
        (foldl f (f acc (car llista)) (cdr llista))))

; (zipWith func l1 l2) combina dues llistes amb la funció donada
(define (zipWith func l1 l2)
  (if (or (null? l1) (null? l2))
        '()
        (cons (func (car l1) (car l2)) (zipWith func (cdr l1) (cdr l2)))))

; (takeWhile predicat llista) retorna els elements des del principi mentre compleixen el predicat
(define (takeWhile predicat llista)
    (if (or (null? llista) (not (predicat (car llista))))
        '()
        (cons (car llista) (takeWhile predicat (cdr llista)))))

; (dropWhile predicat llista) elimina els elements al principi de la llista mentre compleixin el predicat
(define (dropWhile predicat llista)
    (if (or (null? llista) (not (predicat (car llista))))
        llista
        (dropWhile predicat (cdr llista))))

; Funció que suma dos nombres
(define (suma x y) (+ x y))

; Funció que multiplica dos nombres
(define (multiplica x y) (* x y))

; Retorna #t si el valor és positiu
(define (positiu? x) (> x 0))

; Funció principal
(define (main)
    (display "==== JOC DE PROVES: FOLDL I ZIPWITH ====")
    (newline)

    ; Prova 1: Utilitzar foldl per sumar una llista
    (display "-- Prova 1: foldl per sumar una llista --")
    (newline)
    (display "(foldl + 0 '(1 2 3 4 5)) => ")
    (display (foldl suma 0 '(1 2 3 4 5)))  ; Esperem 15
    (newline)

    ; Prova 2: Utilitzar foldl per multiplicar una llista
    (display "-- Prova 2: foldl per multiplicar una llista --")
    (newline)
    (display "(foldl * 1 '(1 2 3 4 5)) => ")
    (display (foldl multiplica 1 '(1 2 3 4 5)))  ; Esperem 120
    (newline)

    ; Prova 3: zipWith per sumar dues llistes
    (display "-- Prova 3: zipWith per sumar dues llistes --")
    (newline)
    (display "(zipWith + '(1 2 3) '(4 5 6)) => ")
    (display (zipWith suma '(1 2 3) '(4 5 6)))  ; Esperem (5 7 9)
    (newline)

    ; Prova 4: zipWith per multiplicar dues llistes
    (display "-- Prova 4: zipWith per multiplicar dues llistes --")
    (newline)
    (display "(zipWith * '(1 2 3) '(4 5 6)) => ")
    (display (zipWith multiplica '(1 2 3) '(4 5 6)))  ; Esperem (4 10 18)
    (newline)

    ; Prova 5: zipWith amb llistes de longitud diferent
    (display "-- Prova 5: zipWith amb llistes de longitud diferent --")
    (newline)
    (display "(zipWith + '(1 2) '(3 4 5)) => ")
    (display (zipWith suma '(1 2) '(3 4 5)))  ; Esperem (4 6)
    (newline)
  
    ; Prova 6: takeWhile de numeros positius
    (display "-- Prova 5: takeWhile amb valors positius --")
    (newline)
    (display "(takeWhile positiu? '(1 2 -3 4 -5)) => ")
    (display (takeWhile positiu? '(1 2 -3 4 -5)))  ; Esperem (1 2)
    (newline)

    ; Prova 7: dropWhile de numeros positius
    (display "-- Prova 6: dropWhile amb valors positius --")
    (newline)
    (display "(dropWhile positiu? '(1 2 -3 4 -5)) => ")
    (display (dropWhile positiu? '(1 2 -3 4 -5)))  ; Esperem (-3 4 -5)
    (newline)

    (display "==== FI DEL JOC DE PROVES ====")
    (newline)
)
