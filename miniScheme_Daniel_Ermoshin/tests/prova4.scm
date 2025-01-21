; Funció que aplica una funció sobre un valor inicial només si és més gran que un llindar
(define (aplica-si-gran f x llindar)
    (if (> x llindar)
        (f x)  ; Si x és més gran que el llindar, aplica f(x)
        x))    ; Si no, retorna x sense modificar
    
; Funció que aplica una funció sobre una altra que s'aplica sobre el valor inicial només si és més gran que un llindar
(define (aplica-dos-func-si-gran f g x llindar)
    (if (> x llindar)
        (f (g x)) ; Si x és més gran que el llindar, aplica (f (g x))
        x))    ; Si no, retorna x sense modificar

; Funció que aplica una funció sobre un altra que s'aplica sobre una tercera.
(define (aplica-tres-func-si-gran f g w x llindar)
    (if (> x llindar)
        (f (g (w x))) ; Si x és més gran que el llindar, aplica (f (g (w x)))
        x))    ; Si no, retorna x sense modificar

; Funció que duplica un valor
(define (duplica x) (* x 2))

; Funció que suma cinc a un valor
(define (suma-cinc x) (+ x 5))

; Funció principal
(define (main)
    (display "==== JOC DE PROVES: FUNCIONS D'ORDRE SUPERIOR ====")
    (newline)

    ; Prova 1: Aplica la funció "duplica" només si el valor és més gran que 10
    (display "-- Prova 1: duplica només si > 10 --")
    (newline)
    (display "(aplica-si-gran duplica 15 10) => ")
    (display (aplica-si-gran duplica 15 10))  ; Esperem 30
    (newline)

    (display "(aplica-si-gran duplica 8 10) => ")
    (display (aplica-si-gran duplica 8 10))  ; Esperem 8 (no es duplica)
    (newline)

    ; Prova 2: Combinar amb let
    (display "-- Prova 2: combinar amb let i E/S --")
    (newline)
    (let ((valor-inicial 50)
          (llindar (read)))
      (display (read))
      (display valor-inicial)
      (newline)
      (display "Resultat després d'aplicar duplica només si > 30: ")
      (display (aplica-si-gran duplica valor-inicial llindar))  ; Esperem 100
      (newline))

    ; Prova 3: ús creatiu amb 2 funcions
    (display "-- Prova 3: funció que aplica una funció sobre una altra --")
    (newline)
    (define (resta-deu x)
      (- x 10))

    (display "(aplica-dos-func-si-gran resta-deu duplica 60 50) => ")
    (display (aplica-dos-func-si-gran resta-deu duplica 60 50))  ; Esperem 110
    (newline)

    ; Prova 4: ús creatiu amb tres funcions
    (display "-- Prova 4: funció que aplica 3 funcions consecutivament --")
    (newline)
    (display "(aplica-tres-func-si-gran resta-deu duplica suma-cinc 60 50) => ")
    (display (aplica-tres-func-si-gran resta-deu duplica suma-cinc 60 50))  ; Esperem 120
    (newline)

    (display "==== FI DEL JOC DE PROVES ====")
    (newline)
)
