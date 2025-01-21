package main.models;

import main.exceptions.MyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Map;


public class Plantilla implements Serializable {
    private String nom;
    private String path;
    private final HashMap<Integer, Producte> Llista_Productes;
    private final HashMap<String, Categoria> Llista_Categories;
    private final HashMap<Integer, Prestatgeria> Llista_Prestatges;

    // Constructor
    public Plantilla(String nom) {
        this.nom = nom;
        this.path = null;
        this.Llista_Productes = new HashMap<>();
        this.Llista_Categories = new HashMap<>();
        this.Llista_Prestatges = new HashMap<>();
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPath() {
        return path;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Metodes de productes
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException {
            Categoria categ = Llista_Categories.get(nom_categ);
            if (categ == null) {
                throw new MyException("La categoria no existeix");
            }
            if (Llista_Productes.containsKey(id)) {
                throw new MyException("El producte amb aquesta ID ja existeix");
            }
            Producte prod = new Producte(id, nom, categ, preu);
            Llista_Productes.put(id, prod);
    }

    public void eliminarProducte(int id) throws MyException {
            Producte prod = Llista_Productes.get(id);
            if (prod == null) {
                throw new MyException("El producte amb aquesta ID no existeix");
            }
            prod.eliminarProducte();
            Llista_Productes.remove(id);
            for (Producte pro : Llista_Productes.values()) {
                if (pro.existeixSimilitud(id)) pro.eliminarSimilitud(id);
            }
    }

    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
            Producte prod = Llista_Productes.get(id);
            if (prod == null) {
                throw new MyException("El producte amb aquesta ID no existeix");
            }
            prod.setNom(nouNom);
            prod.setPreu(nouPreu);
    }

    public HashMap<Integer, Producte> getLlista_Productes() {
        return Llista_Productes;
    }

    public String getAtributs_Productes() {
        StringBuilder atributs = new StringBuilder();

        ArrayList<Integer> sortedKeys = new ArrayList<>(Llista_Productes.keySet());
        Collections.sort(sortedKeys);

        int index = 1;
        for (Integer key : sortedKeys) {
            Producte prod = Llista_Productes.get(key);

            atributs.append(index).append(" ")
                    .append(prod.getId()).append(" ")
                    .append(prod.getNom()).append(" ")
                    .append(prod.getCategoria().getNom()).append(" ")
                    .append(prod.getPreu()).append("\n");
            index++;
        }

        return atributs.toString();
    }

    // Metodes de categories
    public void crearCategoria(String nom) throws MyException {
            if (Llista_Categories.containsKey(nom)) {
                throw new MyException("La categoria ja existeix");
            }
            Categoria categ = new Categoria(nom);
            Llista_Categories.put(nom, categ);
    }

    public void crearCategoria(String nom, String descripcio) throws MyException {
            if (Llista_Categories.containsKey(nom)) {
                throw new MyException("La categoria ja existeix");
            }
            Categoria categ = new Categoria(nom, descripcio);
            Llista_Categories.put(nom, categ);
    }

    public void eliminarCategoria(String nom) throws MyException {
            Categoria categ = Llista_Categories.get(nom);
            if (categ == null) {
                throw new MyException("La categoria no existeix.");
            } else if (categ.getNumProducte() > 0) {
                throw new MyException("La categoria encara te productes");
            }
            Llista_Categories.remove(nom);
    }

    public void editarDescripcioCategoria(String nom, String novaDescripcio) throws MyException {
            Categoria categ = Llista_Categories.get(nom);
            if (categ == null) {
                throw new MyException("La categoria no existeix");
            }
            categ.setDescripcio(novaDescripcio);
    }

    public HashMap<String, Categoria> getLlista_Categories() {
        return Llista_Categories;
    }

    public String getAtributs_Categories() {
        StringBuilder atributs = new StringBuilder();

        ArrayList<String> sortedKeys = new ArrayList<>(Llista_Categories.keySet());
        Collections.sort(sortedKeys);

        int index = 1;
        for (String key : sortedKeys) {
            Categoria cat = Llista_Categories.get(key);

            atributs.append(index).append(" ")
                    .append(cat.getNom()).append(" ")
                    .append(cat.getNumProducte()).append("\n").append("    ")
                    .append(cat.getDescripcio()).append("\n");
            index++;
        }

        return atributs.toString();
    }

    // Metodes de prestatgeria
    public void crearPrestatgeria(int id, int num_prestatges) throws MyException {
            if (Llista_Prestatges.containsKey(id)) {
                throw new MyException("La prestatgeria amb aquesta ID ja existeix");
            }
            Prestatgeria prestatgeria = new Prestatgeria(id, num_prestatges);
            Llista_Prestatges.put(id, prestatgeria);
    }

    public void modificarNumPrestatges(int id, int num_prestatges) throws MyException {
            Prestatgeria prestatgeria = Llista_Prestatges.get(id);
            if (prestatgeria == null) {
                throw new MyException("La prestatgeria amb aquesta ID no existeix");
            }
            prestatgeria.setNumPrestatges(num_prestatges);
    }

    public void eliminarPrestatgeria(int id) throws MyException {
            if (!Llista_Prestatges.containsKey(id)) {
                throw new MyException("La prestatgeria amb aquesta ID no existeix");
            }
            Llista_Prestatges.remove(id);
    }

    public int numTotalPrestatges() {
        int total = 0;
        for (Prestatgeria prestatgeria : Llista_Prestatges.values()) {
            total += prestatgeria.getNumPrestatges();
        }
        return total;
    }

    public HashMap<Integer, Prestatgeria> getLlista_Prestatges() {
        return new HashMap<>(Llista_Prestatges);
    }

    public String getAtributs_Prestatgeria() {
        StringBuilder atributs = new StringBuilder();

        ArrayList<Integer> sortedKeys = new ArrayList<>(Llista_Prestatges.keySet());
        Collections.sort(sortedKeys);

        int index = 1;
        for (Integer key : sortedKeys) {
            Prestatgeria pres = Llista_Prestatges.get(key);

            atributs.append(index).append(" ")
                    .append(pres.getId()).append(" ")
                    .append(pres.getNumPrestatges()).append("\n");
            index++;
        }

        return atributs.toString();
    }

    // Metodes de similituds
    public void afegirSimilitud(int id1, int id2, double grauSimilitud) throws MyException {
            if (id1 == id2) {
                throw new MyException("Els productes son els mateixos");
            }
            Producte prod1 = Llista_Productes.get(id1);
            Producte prod2 = Llista_Productes.get(id2);
            if (prod1 == null || prod2 == null) {
                throw new MyException("Algun dels productes no existeix");
            }
            prod1.afegirSimilitud(id2, grauSimilitud);
            prod2.afegirSimilitud(id1, grauSimilitud);
    }

    public void eliminarSimilitud(int id1, int id2) throws MyException {
            if (id1 == id2) {
                throw new MyException("Els productes son els mateixos");
            }
            Producte prod1 = Llista_Productes.get(id1);
            Producte prod2 = Llista_Productes.get(id2);
            if (prod1 == null || prod2 == null) {
                throw new MyException("Algun dels productes no existeix");
            }
            prod1.eliminarSimilitud(id2);
            prod2.eliminarSimilitud(id1);
    }

    public String getAtributs_Similituds() {
        StringBuilder atributs = new StringBuilder();

        ArrayList<Integer> sortedKeys = new ArrayList<>(Llista_Productes.keySet());
        Collections.sort(sortedKeys);

        int index = 1;
        for (Integer key : sortedKeys) {
            Producte prod = Llista_Productes.get(key);
            int id = prod.getId();
            Map<Integer, Double> sim = prod.getSimilituds();
            for(Integer id2: sim.keySet()) {
                atributs.append(id).append("-").append(id2).append(" ").append(sim.get(id2)).append("\n");
            }

        }
        return atributs.toString();
    }
}