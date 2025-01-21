package persistencia;

import exceptions.MyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aquesta classe representa el controlador de la persistència.
 * Aquest controlador permet gestionar la persistència de les dades.
 */
public class CtrlPersistencia {

    /**
     * L'única instància de la classe CtrlPersistencia.
     */
    private static CtrlPersistencia singletonObject;

    /**
     * El gestor de categories en les dades del sistema.
     */
    private static GestorCategories GESTOR_CATEGORIES;

    /**
     * El gestor de plantilles en les dades del sistema.
     */
    private static GestorPlantilles GESTOR_PLANTILLES;

    /**
     * El gestor de prestatgeries en les dades del sistema.
     */
    private static GestorPrestatgeries GESTOR_PRESTATGERIES;

    /**
     * El gestor de productes en les dades del sistema.
     */
    private static GestorProductes GESTOR_PRODUCTES;

    /**
     * El gestor d'exportacions de distribucions.
     */
    private static GestorDistribucio GESTOR_DISTRIBUCIO;

    /**
     * El gestor de similituds en les dades del sistema.
     */
    private static GestorSimilituds GESTOR_SIMILITUDS;

    /**
     * Obte l'única instància de la classe CtrlPersistencia.
     * @return L'única instància de la classe CtrlPersistencia.
     * @throws MyException Si no es pot obtenir l'única instància de la classe CtrlPersistencia.
     */
    public static CtrlPersistencia getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new CtrlPersistencia();
        }
        return singletonObject;
    }

    /**
     * Crea una nova instància de la classe CtrlPersistencia.
     * @throws MyException Si no es pot crear la nova instància de la classe CtrlPersistencia.
     */
    private CtrlPersistencia() throws MyException {
        GESTOR_CATEGORIES = GestorCategories.getInstance();
        GESTOR_PLANTILLES = GestorPlantilles.getInstance();
        GESTOR_PRESTATGERIES = GestorPrestatgeries.getInstance();
        GESTOR_PRODUCTES = GestorProductes.getInstance();
        GESTOR_DISTRIBUCIO = GestorDistribucio.getInstance();
        GESTOR_SIMILITUDS = GestorSimilituds.getInstance();
    }

    /**
     * Exporta una distribució donada en forma de llista de llistes de strings a un arxiu donat pel seu path.
     * @param distribucio La distribució a exportar
     * @param filePath El path de l'arxiu on exportar la distribució
     * @throws MyException Si no es pot exportar la distribució
     */
    public void exportarDistribucio(List<List<String>> distribucio, String filePath) throws MyException {
        GESTOR_DISTRIBUCIO.exportarDistribucio(distribucio, filePath);
    }

    /**
     * Elimina totes les dades del sistema.
     * @throws MyException Si no es poden eliminar les dades del sistema.
     */
    public void eliminarDades() throws MyException {
        GESTOR_CATEGORIES.clearDades();
        GESTOR_PLANTILLES.clearDades();
        GESTOR_PRESTATGERIES.clearDades();
        GESTOR_PRODUCTES.clearDades();
        GESTOR_SIMILITUDS.clearDades();
    }

    /**
     * Crea una nova plantilla amb el nom donat al sistema.
     * @param nom El nom de la plantilla
     * @throws MyException Si no es pot crear la plantilla
     */
    public void crearPlantilla(String nom) throws MyException {
        GESTOR_PLANTILLES.afegirPlantilla(nom, new ArrayList<Integer>(), new ArrayList<String>(), new ArrayList<Integer>());
    }

    /**
     * Elimina una plantilla del sistema.
     * @param plantilla La plantilla a eliminar
     * @throws MyException Si no es pot eliminar la plantilla
     */
    public void eliminarPlantilla(String plantilla) throws MyException {
        GESTOR_PLANTILLES.eliminarPlantilla(plantilla);
    }

    /**
     * Comprova si una plantilla existeix.
     *
     * @param nom el nom de la plantilla
     * @return true si la plantilla existeix, false en cas contrari
     * @throws MyException si hi ha un error en la comprovació
     */
    public boolean existeixPlantilla(String nom) throws MyException {
        return GESTOR_PLANTILLES.existeixPlantilla(nom);
    }

    /**
     * Canvia el nom d'una plantilla.
     *
     * @param nom el nom actual de la plantilla
     * @param nouNom el nou nom de la plantilla
     * @throws MyException si hi ha un error en el canvi de nom
     */
    public void setNomPlantilla(String nom, String nouNom) throws MyException {
        GESTOR_PLANTILLES.setNomPlantilla(nom, nouNom);
    }

    /**
     * Guarda una plantilla amb els seus productes, categories i prestatgeries.
     *
     * @param nom el nom de la plantilla
     * @param productes la llista de productes de la plantilla
     * @param categories la llista de categories de la plantilla
     * @param prestatgeries la llista de prestatgeries de la plantilla
     * @throws MyException si hi ha un error en la guarda de la plantilla
     */
    public void guardarPlantilla(String nom, List<Integer> productes, List<String> categories, List<Integer> prestatgeries) throws MyException {
        GESTOR_PLANTILLES.actualitzarPlantilla(nom, productes, categories, prestatgeries);
    }

    /**
     * Exporta una plantilla a un fitxer.
     *
     * @param contents el contingut de la plantilla a exportar
     * @param filePath el camí del fitxer on exportar la plantilla
     * @throws MyException si hi ha un error en l'exportació
     */
    public void exportarPlantilla(String contents, String filePath) throws MyException {
        GESTOR_PLANTILLES.exportarPlantilla(contents, filePath);
    }

    /**
     * Importa una plantilla des d'un fitxer.
     *
     * @param filepath el camí del fitxer des d'on importar la plantilla
     * @return el contingut de la plantilla importada
     * @throws MyException si hi ha un error en la importació
     */
    public String importarPlantilla(String filepath) throws MyException {
        return GESTOR_PLANTILLES.importarPlantilla(filepath);
    }

    /**
     * Retorna una plantilla amb el seu nom.
     *
     * @param nom el nom de la plantilla
     * @return un mapa amb els detalls de la plantilla
     * @throws MyException si hi ha un error en la recuperació de la plantilla
     */
    public Map<String, Object> getPlantilla(String nom) throws MyException {
        return GESTOR_PLANTILLES.getPlantilla(nom);
    }

    /**
     * Retorna una categoria amb el seu nom.
     *
     * @param nom el nom de la categoria
     * @return una llista amb els detalls de la categoria
     * @throws MyException si hi ha un error en la recuperació de la categoria
     */
    public List<String> getCategoria(String nom) throws MyException {
        return GESTOR_CATEGORIES.getCategoria(nom);
    }

    /**
     * Retorna un producte amb el seu identificador.
     *
     * @param id l'identificador del producte
     * @return una llista amb els detalls del producte
     * @throws MyException si hi ha un error en la recuperació del producte
     */
    public List<String> getProducte(int id) throws MyException {
        return GESTOR_PRODUCTES.getProducte(id);
    }

    /**
     * Retorna una prestatgeria amb el seu identificador.
     *
     * @param id l'identificador de la prestatgeria
     * @return una llista amb els detalls de la prestatgeria
     * @throws MyException si hi ha un error en la recuperació de la prestatgeria
     */
    public List<String> getPrestatgeria(int id) throws MyException {
        return GESTOR_PRESTATGERIES.getPrestatgeria(id);
    }

    /**
     * Retorna una llista de categories per una llista dels identificadors.
     *
     * @param names la llista de noms de categories
     * @return una llista de llistes amb els detalls de les categories
     * @throws MyException si hi ha un error en la recuperació de les categories
     */
    public List<List<String>> getCategoriesPerLlistaId(List<String> names) throws MyException {
        return GESTOR_CATEGORIES.getCategories(names);
    }

    /**
     * Retorna una llista de productes per una llista dels identificadors.
     *
     * @param ids la llista d'identificadors de productes
     * @return una llista de llistes amb els detalls dels productes
     * @throws MyException si hi ha un error en la recuperació dels productes
     */
    public List<List<String>> getProductesPerLlistaId(List<Integer> ids) throws MyException {
        return GESTOR_PRODUCTES.getProductes(ids);
    }

    /**
     * Retorna una llista de prestatgeries per una llista dels identificadors.
     *
     * @param ids la llista d'identificadors de prestatgeries
     * @return una llista de llistes amb els detalls de les prestatgeries
     * @throws MyException si hi ha un error en la recuperació de les prestatgeries
     */
    public List<List<String>> getPrestatgeriesPerLlistaId(List<Integer> ids) throws MyException {
        return GESTOR_PRESTATGERIES.getPrestatgeries(ids);
    }

    /**
     * Retorna una llista de similituds de productes a partir d'una llista d'identificadors de productes.
     * Les similituds pertanyen a totes les parelles existents de productes de la llista donada.
     *
     * @param ids la llista d'identificadors de productes
     * @return una llista de llistes amb els detalls de les similituds
     * @throws MyException si hi ha un error en la recuperació de les similituds
     */

    public List<List<String>> getSimilitudDeLlistaProductes(List<Integer> ids) throws MyException {
        return GESTOR_SIMILITUDS.getSimilitudDeLlistaProductes(ids);
    }

    /**
     * Retorna una llista amb els identificadors de tots els productes.
     *
     * @return una llista amb els identificadors de tots els productes
     * @throws MyException si hi ha un error en la recuperació dels identificadors
     */
    public List<String> getIdAllProductes() throws MyException {
        return GESTOR_PRODUCTES.getIdAllProductes();
    }

    /**
     * Retorna una llista amb els identificadors de totes les prestatgeries.
     *
     * @return una llista amb els identificadors de totes les prestatgeries
     * @throws MyException si hi ha un error en la recuperació dels identificadors
     */
    public List<String> getIdAllPrestatgeries() throws MyException {
        return GESTOR_PRESTATGERIES.getIdAllPrestatgeries();
    }

    /**
     * Retorna una llista amb els identificadors de totes les categories.
     *
     * @return una llista amb els identificadors de totes les categories
     * @throws MyException si hi ha un error en la recuperació dels identificadors
     */
    public List<String> getIdAllCategories() throws MyException {
        return GESTOR_CATEGORIES.getIdAllCategories();
    }

    /**
     * Retorna una llista amb els noms de totes les plantilles.
     *
     * @return una llista amb els noms de totes les plantilles
     * @throws MyException si hi ha un error en la recuperació dels noms
     */
    public List<String> getNomAllPlantilles() throws MyException {
        return GESTOR_PLANTILLES.getNomAllPlantilles();
    }

    /**
     * Afegeix una nova categoria al sistema.
     *
     * @param nom el nom de la categoria
     * @param descripcio la descripció de la categoria
     * @throws MyException si hi ha un error en l'afegiment de la categoria
     */
    public void afegirCategoria(String nom, String descripcio) throws MyException {
        GESTOR_CATEGORIES.afegirCategoria(nom, descripcio);
    }

    /**
     * Afegeix una llista de categories al sistema.
     *
     * @param categories la llista de categories a afegir
     * @throws MyException si hi ha un error en l'afegiment de les categories
     */
    public void afegirCategories(List<List<String>> categories) throws MyException{
        GESTOR_CATEGORIES.afegirCategories(categories);
    }

    /**
     * Modifica la descripció d'una categoria al sistema.
     *
     * @param nom el nom de la categoria
     * @param novaDescripcio la nova descripció de la categoria
     * @throws MyException si hi ha un error en la modificació de la categoria
     */
    public void modificarCategoria(String nom, String novaDescripcio) throws MyException {
        GESTOR_CATEGORIES.actualitzarCategoria(nom, novaDescripcio);
    }

    /**
     * Elimina una categoria del sistema.
     *
     * @param nom el nom de la categoria
     * @throws MyException si hi ha un error en l'eliminació de la categoria
     */
    public void eliminarCategoria(String nom) throws MyException {
        GESTOR_CATEGORIES.eliminarCategoria(nom);
    }

    /**
     * Elimina una llista de categories del sistema.
     *
     * @param names la llista de noms de categories a eliminar
     * @throws MyException si hi ha un error en l'eliminació de les categories
     */
    public void eliminarCategories(List<String> names) throws MyException{
        GESTOR_CATEGORIES.eliminarCategories(names);
    }

    /**
     * Retorna el nombre de productes associats a una categoria.
     *
     * @param nom el nom de la categoria
     * @return el nombre de productes associats a la categoria
     * @throws MyException si hi ha un error en la recuperació del nombre de productes
     */
    public int getNumProductesCategoria(String nom) throws MyException {
        return GESTOR_CATEGORIES.getNumProductesCategoria(nom);
    }

    /**
     * Afegeix un nou producte al sistema.
     *
     * @param id l'identificador del producte
     * @param nom el nom del producte
     * @param categoria la categoria del producte
     * @param preu el preu del producte
     * @throws MyException si hi ha un error en l'afegiment del producte
     */
    public void afegirProducte(int id, String nom, String categoria, double preu) throws MyException {
        GESTOR_PRODUCTES.afegirProducte(id, nom, categoria, preu);
    }

    /**
     * Afegeix una llista de productes al sistema.
     *
     * @param productes la llista de productes a afegir
     * @throws MyException si hi ha un error en l'afegiment dels productes
     */
    public void afegirProductes(List<List<String>> productes)  throws MyException {
        GESTOR_PRODUCTES.afegirProductes(productes);
    }

    /**
     * Modifica el nom i el preu d'un producte al sistema.
     *
     * @param id l'identificador del producte
     * @param nouNom el nou nom del producte
     * @param nouPreu el nou preu del producte
     * @throws MyException si hi ha un error en la modificació del producte
     */
    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
        GESTOR_PRODUCTES.actualitzarProducte(id, nouNom, nouPreu);
    }

    /**
     * Elimina un producte del sistema.
     *
     * @param id l'identificador del producte
     * @throws MyException si hi ha un error en l'eliminació del producte
     */
    public void eliminarProducte(int id) throws MyException {
        GESTOR_PRODUCTES.eliminarProducte(id);
    }

    /**
     * Elimina una llista de productes del sistema.
     *
     * @param ids la llista d'identificadors de productes a eliminar
     * @throws MyException si hi ha un error en l'eliminació dels productes
     */
    public void eliminarProductes(List<Integer> ids)  throws MyException {
        GESTOR_PRODUCTES.eliminarProductes(ids);
    }

    /**
     * Afegeix una nova prestatgeria al sistema.
     *
     * @param id l'identificador de la prestatgeria
     * @param numPrestatges el nombre de prestatges de la prestatgeria
     * @throws MyException si hi ha un error en l'afegiment de la prestatgeria
     */
    public void afegirPrestatgeria(int id, int numPrestatges) throws MyException {
        GESTOR_PRESTATGERIES.afegirPrestatgeria(id, numPrestatges);
    }

    /**
     * Afegeix una llista de prestatgeries al sistema.
     *
     * @param prestatgeries la llista de prestatgeries a afegir
     * @throws MyException si hi ha un error en l'afegiment de les prestatgeries
     */
    public void afegirPrestatgeries(List<List<String>> prestatgeries) throws MyException {
        GESTOR_PRESTATGERIES.afegirPrestatgeries(prestatgeries);
    }

    /**
     * Modifica el nombre de prestatges d'una prestatgeria al sistema.
     *
     * @param id l'identificador de la prestatgeria
     * @param nouNumPrestatges el nou nombre de prestatges de la prestatgeria
     * @throws MyException si hi ha un error en la modificació de la prestatgeria
     */
    public void modificarPrestatgeria(int id, int nouNumPrestatges) throws MyException {
        GESTOR_PRESTATGERIES.actualizarPrestatgeria(id, nouNumPrestatges);
    }

    /**
     * Elimina una prestatgeria del sistema.
     *
     * @param id l'identificador de la prestatgeria
     * @throws MyException si hi ha un error en l'eliminació de la prestatgeria
     */
    public void eliminarPrestatgeria(int id) throws MyException {
        GESTOR_PRESTATGERIES.eliminarPrestatgeria(id);
    }

    /**
     * Elimina una llista de prestatgeries del sistema.
     *
     * @param ids la llista d'identificadors de prestatgeries a eliminar
     * @throws MyException si hi ha un error en l'eliminació de les prestatgeries
     */
    public void eliminarPrestatgeries(List<Integer> ids) throws MyException {
        GESTOR_PRESTATGERIES.eliminarPrestatgeries(ids);
    }

    /**
     * Afegeix una matríx de similituds entre productes al sistema.
     * @param similituds La matriu de similituds entre productes
     * @param indexProductByPosition La llista d'identificadors de productes
     * @throws MyException si hi ha un error en l'afegiment de les similituds
     */
    public void afegirSimilituds(double[][] similituds, List<Integer> indexProductByPosition) throws MyException {
        GESTOR_SIMILITUDS.afegirSimilituds(similituds,indexProductByPosition);
    }

    /**
     * Modifica la similitud entre dos productes al sistema.
     *
     * @param id1 Identificador del primer producte
     * @param id2 Identificador del segon producte
     * @param nouGrauSimilitud El nou grau de similitud entre els dos productes
     * @throws MyException si hi ha un error en la modificació de la similitud
     */
    public void modificarSimilitud(int id1, int id2, double nouGrauSimilitud) throws MyException {
        GESTOR_SIMILITUDS.actualitzarSimilitud(id1, id2, nouGrauSimilitud);
    }

    /**
     * Elimina la similitud entre dos productes al sistema.
     *
     * @param id1 Identificador del primer producte
     * @param id2 Identificador del segon producte
     * @throws MyException si hi ha un error en l'eliminació de la similitud
     */
    public void eliminarSimilitud(int id1, int id2) throws MyException {
        GESTOR_SIMILITUDS.eliminarSimilitud(id1, id2);
    }

    /**
     * Elimina les similituds associades a una llista d'identificadors de productes.
     *
     * @param ids la llista d'identificadors de productes
     * @throws MyException si hi ha un error en l'eliminació de les similituds
     */
    public void eliminarSimilituds(List<Integer> ids) throws MyException{
        GESTOR_SIMILITUDS.eliminarSimilituds(ids);
    }
}




