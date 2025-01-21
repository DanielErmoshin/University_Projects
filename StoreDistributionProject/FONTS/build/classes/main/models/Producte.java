package main.models;


import java.io.Serializable;
import java.util.Map;

import main.exceptions.MyException;
import main.models.Categoria;

public class Producte implements Serializable {
    // Atributs
    private int id;
    private String nom;
    private Categoria categoria;
    private double preu;
    private Map<Integer, Double> similituds;

    // Constructor
    public Producte(int id, String nom, Categoria categoria, double preu) {
        this.id = id;
        this.nom = nom;
        this.categoria = categoria;
        this.preu = preu;
        this.categoria.incrementarNumProducte(); // Incrementa el contador al crear un producte
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPreu() {
        return preu;
    }

    public Map<Integer, Double> getSimilituds() {
        return similituds;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria.decrementarNumProducte(); // Decrementa el contador en la antiga categoría
        this.categoria = categoria;
        this.categoria.incrementarNumProducte(); // Incrementa el contador en la nova categoría
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    // Metode per eliminar el producte
    public void eliminarProducte() {
        this.categoria.decrementarNumProducte(); // Decrementa el contador al eliminar un producte
    }
    // Metode per afegir similitud
    public void afegirSimilitud(int productId, double grauSimilitud) throws MyException {
        if (grauSimilitud >= 0 && grauSimilitud <= 1) {
            this.similituds.put(productId, grauSimilitud);
        } else {
            throw new MyException("El grau de similitud ha d'estar entre 0 y 1");
        }
    }

    // Metode per eliminar similitud
    public void eliminarSimilitud(int productId) throws MyException {
        if (!this.similituds.containsKey(productId)) {
            throw new MyException("No hi ha similitud entre aquests productes");
        } else {
           this.similituds.remove(productId);
        }
    }

    public boolean existeixSimilitud(int productId) {
        return this.similituds.containsKey(productId);
    }
}
