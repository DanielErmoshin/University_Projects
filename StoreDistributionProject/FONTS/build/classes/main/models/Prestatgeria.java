package main.models;


import java.io.Serializable;


public class Prestatgeria implements Serializable {
    // Atributs
    private int id;
    private int numPrestatges;


    // Constructor
    public Prestatgeria(int id, int num_prestatges) {
        this.id = id;
        this.numPrestatges = num_prestatges;
    }


    // Metodes
    public int getId() {
        return id;
    }

    public int getNumPrestatges() { // Nou getter para numPrestatges
        return numPrestatges;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setNumPrestatges(int num_prestatges) {
        this.numPrestatges = num_prestatges;
    }
}
