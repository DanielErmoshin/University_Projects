package main.models;

import java.io.Serializable;


public class Categoria implements Serializable {
    // Atributs
    private String descripcio;
    private String nom;
    private int numProducte;


    // Constructor
    public Categoria(String nom) {
        this.nom = nom;
        this.numProducte = 0;
    }

    public Categoria(String nom, String descripcio) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.numProducte = 0;
    }



    // Getters
    public String getNom() {
        return nom;
    }


    public int getNumProducte() {
        return numProducte;
    }

    public String getDescripcio() {
        return descripcio;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }


        // Metodes per gestionar el número de productes
    public void incrementarNumProducte() {
        this.numProducte++;
    }


    public void decrementarNumProducte() {
        if (this.numProducte > 0) {
            this.numProducte--;
        }
    }
}
