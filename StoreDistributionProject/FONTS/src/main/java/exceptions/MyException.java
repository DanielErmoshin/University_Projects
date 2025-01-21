package exceptions;

/**
 * Aquesta classe representa una excepció personalitzada.
 * Aquesta excepció es llença quan es produeix un error en el programa.
 */
public class MyException extends Exception {
    /**
     * Longitud de la serialització
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructora de la classe MyException
     * @param missatge Missatge de l'excepció
     */
    public MyException(String missatge) {
        super(missatge);
    }
}
