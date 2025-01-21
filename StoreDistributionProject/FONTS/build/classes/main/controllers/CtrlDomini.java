package main.controllers;

import main.models.Plantilla;
import main.models.Prestatgeria;
import main.models.Producte;
import main.exceptions.MyException;

import java.util.HashMap;

public class CtrlDomini {

    private static CtrlDomini singletonObject;
    private final CtrlPlantilla CPlantilla;

    public static CtrlDomini getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlDomini();
        }
        return singletonObject;
    }

    private CtrlDomini() {
        CPlantilla = CtrlPlantilla.getInstance();
    }


    // Metodes per a plantilles
    public void crearPlantilla(String nom) throws MyException {
        CPlantilla.crearPlantilla(nom);
    }

    public void eliminarPlantilla() throws MyException {
        CPlantilla.eliminarPlantilla();
    }

    public Plantilla obtenirPlantillaActual() throws MyException {
        return CPlantilla.obtenirPlantillaActual();
    }

    public void setNomPlantilla(String nouNom) throws MyException {
        CPlantilla.setNomPlantilla(nouNom);
    }

    public void guardarPlantilla() throws MyException {
        CPlantilla.guardarPlantilla();
    }

    public void guardarPlantillaNova() throws MyException {
        CPlantilla.guardarPlantillaNova();
    }

    public void gestionarGuardarPlantilla(Plantilla plantilla, boolean nuevo) {
        //if (plantilla.getPath() == null || nuevo)
    }

    // Metodes per a productes
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException {
        CPlantilla.crearProducte(id, nom, nom_categ, preu);
    }

    public void eliminarProducte(int id) throws MyException {
        CPlantilla.eliminarProducte(id);
    }

    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
        CPlantilla.modificarProducte(id, nouNom, nouPreu);
    }

    public HashMap<Integer, Producte> getLlista_Productes() {
        return CPlantilla.getLlista_Productes();
    }

    public String getAtributs_Productes() throws MyException {
        return CPlantilla.getAtributs_Productes();
    }

    // Metodes per a categories
    public void crearCategoria(String nom) throws MyException {
        CPlantilla.crearCategoria(nom);
    }

    public void crearCategoria(String nom, String descripcio) throws MyException {
        CPlantilla.crearCategoria(nom, descripcio);
    }

    public void eliminarCategoria(String nom) throws MyException {
        CPlantilla.eliminarCategoria(nom);
    }

    public void editarDescripcioCategoria(String nom, String novaDescripcio) throws MyException {
        CPlantilla.editarDescripcioCategoria(nom, novaDescripcio);
    }

    public String getAtributs_Categories() throws MyException {
        return CPlantilla.getAtributs_Categories();
    }

    // Metodes per a prestatgeries
    public void crearPrestatgeria(int id, int num_prestatges) throws MyException {
        CPlantilla.crearPrestatgeria(id, num_prestatges);
    }

    public void modificarNumPrestatges(int id, int num_prestatges) throws MyException {
        CPlantilla.modificarNumPrestatges(id, num_prestatges);
    }

    public void eliminarPrestatgeria(int id) throws MyException {
        CPlantilla.eliminarPrestatgeria(id);
    }

    public int numTotalPrestatges() throws MyException {
        return CPlantilla.numTotalPrestatges();
    }

    public HashMap<Integer, Prestatgeria> getLlista_Prestatges() throws MyException {
        return CPlantilla.getLlista_Prestatges();
    }

    public String getAtributs_Prestatges() throws MyException {
        return CPlantilla.getAtributs_Prestatges();
    }

    //Metodes de similituds
    public void afegirSimilitud(int id1, int id2, double grauSimilitud) throws MyException {
        CPlantilla.afegirSimilitud(id1, id2, grauSimilitud);
    }

    public void eliminarSimilitud(int id1, int id2) throws MyException {
        CPlantilla.eliminarSimilitud(id1, id2);
    }

    public String getAtributs_Similitud() throws MyException {
        return CPlantilla.getAtributs_Similituds();
    }

}