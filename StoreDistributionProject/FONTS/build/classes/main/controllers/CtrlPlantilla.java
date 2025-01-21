package main.controllers;

import main.models.Plantilla;
import main.models.Prestatgeria;
import main.models.Producte;
import main.exceptions.MyException;

import java.util.HashMap;

public class CtrlPlantilla {

    private static CtrlPlantilla singletonObject;

    public static CtrlPlantilla getInstance() {
        if (singletonObject == null) {
            singletonObject = new CtrlPlantilla();
        }
        return singletonObject;
    }

    private CtrlPlantilla() {}

    private Plantilla plantillaActual;

    public void crearPlantilla(String nom) throws MyException {
        try {
            if (plantillaActual != null) {
                throw new MyException("Ja hi ha una plantilla activa.");
            }
            plantillaActual = new Plantilla(nom);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
    }

    public boolean PlantillaEstaActiva() throws MyException {
        try {
            if (plantillaActual == null) {
                throw new MyException("No hi ha una plantilla activa.");
            }
            return true;
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
    }

    public void eliminarPlantilla() throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual = null;
    }

    public Plantilla obtenirPlantillaActual() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual;
    }

    public void setNomPlantilla(String nouNom) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.setNom(nouNom);
    }

    public void guardarPlantilla() throws MyException {
        if(!PlantillaEstaActiva()) return;
        CtrlDomini.getInstance().gestionarGuardarPlantilla(plantillaActual, false);
    }

    public void guardarPlantillaNova() throws MyException {
        if(!PlantillaEstaActiva()) return;
        CtrlDomini.getInstance().gestionarGuardarPlantilla(plantillaActual, true);
    }

    // Metodes de productes
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.crearProducte(id, nom, nom_categ, preu);
    }

    public void eliminarProducte(int id) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.eliminarProducte(id);

    }

    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.modificarProducte(id, nouNom, nouPreu);
    }

    // Necesari para poder pasar la llista de productes a la classe Distribucio
    public HashMap<Integer, Producte> getLlista_Productes() {
        return plantillaActual.getLlista_Productes();
    }

    public String getAtributs_Productes() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual.getAtributs_Productes();
    }

    // Metodes de categories
    public void crearCategoria(String nom) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.crearCategoria(nom);
    }

    public void crearCategoria(String nom, String descripcio) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.crearCategoria(nom, descripcio);
    }

    public void eliminarCategoria(String nom) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.eliminarCategoria(nom);

    }

    public void editarDescripcioCategoria(String nom, String novaDescripcio) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.editarDescripcioCategoria(nom, novaDescripcio);
    }

    public String getAtributs_Categories() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual.getAtributs_Categories();
    }

    // Metodos de prestatgería
    public void crearPrestatgeria(int id, int num_prestatges) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.crearPrestatgeria(id, num_prestatges);

    }

    public void modificarNumPrestatges(int id, int num_prestatges) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.modificarNumPrestatges(id, num_prestatges);
    }

    public void eliminarPrestatgeria(int id) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.eliminarPrestatgeria(id);

    }

    public int numTotalPrestatges() throws MyException {
        if(!PlantillaEstaActiva()) return -1;
        return plantillaActual.numTotalPrestatges();
    }

    // Necesari per poder passar la llista de prestatges a la classe Distribucio
    public HashMap<Integer, Prestatgeria> getLlista_Prestatges() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual.getLlista_Prestatges();
    }

    public String getAtributs_Prestatges() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual.getAtributs_Prestatgeria();
    }

    //Metodes de similituds
    public void afegirSimilitud(int id1, int id2, double grauSimilitud) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.afegirSimilitud(id1, id2, grauSimilitud);
    }

    public void eliminarSimilitud(int id1, int id2) throws MyException {
        if(!PlantillaEstaActiva()) return;
        plantillaActual.eliminarSimilitud(id1, id2);
    }

    public String getAtributs_Similituds() throws MyException {
        if(!PlantillaEstaActiva()) return null;
        return plantillaActual.getAtributs_Similituds();
    }

}
