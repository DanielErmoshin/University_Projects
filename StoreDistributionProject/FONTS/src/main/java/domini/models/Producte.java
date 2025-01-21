package domini.models;

import java.util.HashMap;
import java.util.Map;
import exceptions.MyException;

/**
 * Aquesta classe representa un producte.
 * Cada producte té un identificador, un nom, una categoria, un preu i un conjunt de similituds amb altres productes.
 */
public class Producte {
    /**
     * L'identificador del producte.
     */
    private int id;
    /**
     * El nom del producte.
     */
    private String nom;
    /**
     * La categoria del producte.
     */
    private Categoria categoria;
    /**
     * El preu del producte.
     */
    private double preu;
    /**
     * Les similituds del producte amb altres productes.
     */
    private Map<Integer, Double> similituds;

    /**
     * Crea un nou producte amb l'identificador, el nom, la categoria i el preu donats.
     * @param id L'identificador del producte.
     * @param nom El nom del producte.
     * @param categoria La categoria del producte.
     * @param preu El preu del producte.
     * @throws IllegalArgumentException Si l'identificador és negatiu, el nom és null o buit, la categoria és null o el preu és negatiu.
     */
    public Producte(int id, String nom, Categoria categoria, double preu)  {
        if (id < 0) {
            throw new IllegalArgumentException("L'id ha de ser positiu");
        }
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("El nom no pot ser null o buit");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria no pot ser null");
        }
        if (preu < 0) {
            throw new IllegalArgumentException("El preu ha de ser positiu");
        }
        this.id = id;
        this.nom = nom;
        this.categoria = categoria;
        this.preu = preu;
        this.categoria.incrementarNumProducte(); // Incrementa el contador al crear un producte
        this.similituds = new HashMap<>();
    }

    /**
     * Obte l'identificador del producte.
     * @return L'identificador del producte.
     */
    public int getId() {
        return id;
    }

    /**
     * Obte el nom del producte.
     * @return El nom del producte.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obte la categoria del producte.
     * @return La categoria del producte.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Obte el preu del producte.
     * @return El preu del producte.
     */
    public double getPreu() {
        return preu;
    }

    /**
     * Obte les similituds del producte amb altres productes.
     * @return Les similituds del producte amb altres productes.
     */
    public Map<Integer, Double> getSimilituds() {
        return similituds;
    }

    /**
     * Modifica l'identificador del producte.
     * @param id El nou identificador del producte.
     * @throws IllegalArgumentException Si l'identificador és negatiu.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica el nom del producte.
     * @param nom El nou nom del producte.
     * @throws IllegalArgumentException Si el nom és null o buit.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifica el preu del producte.
     * @param preu El nou preu del producte.
     */
    public void setPreu(double preu) {
        this.preu = preu;
    }

    /**
     * Decrementa el nombre de productes associats a la categoria del producte.
     */
    public void eliminarProducte() {
        this.categoria.decrementarNumProducte(); // Decrementa el contador al eliminar un producte
    }

    /**
     * Afegeix una nova similitud amb un altre producte.
     * @param productId L'identificador del producte amb el qual es vol afegir la similitud.
     * @param grauSimilitud El grau de similitud amb el producte donat.
     * @throws MyException Si el grau de similitud no està entre 0 i 1.
     */
    public void afegirSimilitud(int productId, double grauSimilitud) throws MyException {
        if (grauSimilitud < 0 || grauSimilitud > 1) {
            throw new MyException("El grau de similitud ha d'estar entre 0 i 1");
        }
        this.similituds.put(productId, grauSimilitud);

    }

    /**
     * Elimina una similitud amb un altre producte.
     * @param productId L'identificador del producte amb el qual es vol eliminar la similitud.
     * @throws MyException Si no hi ha similitud entre aquests productes.
     */
    public void eliminarSimilitud(int productId) throws MyException {
        if (!this.similituds.containsKey(productId)) {
            throw new MyException("No hi ha similitud entre aquests productes");
        } else {
           this.similituds.remove(productId);
        }
    }

    /**
     * Comprova si existeix una similitud amb un altre producte.
     * @param productId L'identificador del producte amb el qual es vol comprovar la similitud.
     * @return Cert si existeix la similitud, fals altrament.
     */
    public boolean existeixSimilitud(int productId) {
        return this.similituds.containsKey(productId);
    }
}