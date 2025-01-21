package domini.models;

import exceptions.MyException;

/**
 * Aquesta classe representa una prestatgeria.
 * Cada prestatgeria té un identificador i un número de prestatges.
 */
public class Prestatgeria {

    /**
     * L'identificador de la prestatgeria.
     */
    private int id;
    /**
     * El nombre de prestatges de la prestatgeria.
     */
    private int numPrestatges;

    /**
     * Crea una nova prestatgeria amb l'identificador i el nombre de prestatges donats.
     * @param id L'identificador de la prestatgeria.
     * @param numPrestatges El nombre de prestatges de la prestatgeria.
     * @throws MyException Si l'identificador és negatiu o el número de prestatges és zero o negatiu.
     */
    public Prestatgeria(int id, int numPrestatges) throws MyException {
        if (id < 0) {
            throw new MyException("El id no pot ser negatiu");
        }
        if (numPrestatges <= 0) {
            throw new MyException("El número de prestatges ha de ser positiu.");
        }
        this.id = id;
        this.numPrestatges = numPrestatges;
    }

    /**
     * Obte l'identificador de la prestatgeria.
     * @return L'identificador de la prestatgeria.
     */
    public int getId() {
        return id;
    }

    /**
     * Obte el nombre de prestatges de la prestatgeria.
     * @return El nombre de prestatges de la prestatgeria.
     */
    public int getNumPrestatges() {
        return numPrestatges;
    }

    /**
     * Modifica l'identificador de la prestatgeria.
     * @param id El nou identificador de la prestatgeria.
     * @throws MyException Si l'identificador és negatiu.
     */
    public void setId(int id) throws MyException {
        if (id < 0) {
            throw new MyException("El id no pot ser negatiu");
        }
        this.id = id;
    }

    /**
     * Modifica el nombre de prestatges de la prestatgeria.
     * @param numPrestatges El nou nombre de prestatges de la prestatgeria.
     * @throws MyException Si el número de prestatges és zero o negatiu.
     */
    public void setNumPrestatges(int numPrestatges) throws MyException {
        if (numPrestatges <= 0) {
            throw new MyException("El número de prestatges ha de ser positiu.");
        } else {
            this.numPrestatges = numPrestatges;
        }
    }
}