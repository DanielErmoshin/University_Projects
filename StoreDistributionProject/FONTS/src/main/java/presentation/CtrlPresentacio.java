package presentation;

import domini.CtrlDomini;
import exceptions.MyException;

import java.util.List;

/**
 * Aquesta classe representa el controlador de la presentació.
 * Aquest controlador permet gestionar la comunicació entre la interfície gràfica i el domini.
 */
public class CtrlPresentacio {

    /**
     * L'única instància de la classe CtrlPresentacio.
     */
    private static CtrlPresentacio singletonObject;

    /**
     * El controlador de domini.
     */
    private final CtrlDomini CD;

    /**
     * Crea una nova instància de la classe CtrlPresentacio.
     * @throws MyException Si no es pot crear la instància de CtrlDomini
     */
    private CtrlPresentacio() throws MyException {
        CD = CtrlDomini.getInstance();
    }

    /**
     * Obte l'única instància de la classe CtrlPresentacio.
     * @return L'única instància de la classe CtrlPresentacio.
     * @throws MyException Si no es pot crear la instància de CtrlPresentacio
     */
    public static CtrlPresentacio getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new CtrlPresentacio();
        }
        return singletonObject;
    }

    /**
     * Inicia la vista de la presentació.
     */
    public void iniciarVista() {
        new MyFrame(this);
    }

    /**
     * Elimina les dades de la base de dades.
     * @throws MyException Si no es poden eliminar les dades
     */
    public void eliminarDades() throws MyException {
            CD.eliminarDades();
    }

    /**
     * Crea una nova plantilla.
     * @param nom El nom de la plantilla
     * @throws MyException Si no es pot crear la plantilla
     */
    public void crearPlantilla(String nom) throws MyException{
        CD.crearPlantillaActiva(nom);
    }

    /**
     * Tanca la plantilla activa.
     * @throws MyException Si no es pot tancar la plantilla activa
     */
    public void tancarPlantilla() throws MyException{
        CD.tancarPlantillaActiva();
    }

    /**
     * Elimina una plantilla.
     * @param nom El nom de la plantilla
     * @throws MyException Si no es pot eliminar la plantilla
     */
    public void eliminarPlantilla(String nom) throws MyException{
        CD.eliminarPlantilla(nom);
    }

    /**
     * Guarda la plantilla activa.
     * @throws MyException Si no es pot guardar la plantilla activa
     */
    public void guardarPlantilla() throws MyException{
        CD.guardarPlantillaActiva();
    }

    /**
     * Canvia el nom de la plantilla activa.
     * @param nouNom El nou nom de la plantilla
     * @throws MyException Si no es pot canviar la plantilla activa
     */
    public void setNomPlantilla(String nouNom) throws MyException{
        CD.setNomPlantillaActiva(nouNom);
    }

    /**
     * Obte el nom de la plantilla activa.
     * @return El nom de la plantilla activa
     * @throws MyException Si no es pot obtenir el nom de la plantilla activa
     */
    public String getNomPlantilla() throws MyException {
        return CD.getNomPlantillaActiva();
    }

    /**
     * Carrega una plantilla des del sistema.
     * @param nom El nom de la plantilla
     * @throws MyException Si no es pot carregar la plantilla
     */
    public void cargarPlantilla(String nom) throws MyException{
        CD.cargarPlantilla(nom);
    }

    /**
     * Importa una plantilla des d'un fitxer JSON.
     * @param filePath El path del fitxer de la plantilla
     * @throws MyException Si no es pot importar la plantilla
     */
    public void importarPlantilla(String filePath) throws MyException{
        CD.importarPlantilla(filePath);
    }

    /**
     * Exporta la plantilla activa a un fitxer JSON.
     * @param filePath El path del fitxer de la plantilla
     * @throws MyException Si no es pot exportar la plantilla
     */
    public void exportarPlantilla(String filePath) throws MyException{
        CD.exportarPlantillaActiva(filePath);
    }

    /**
     * Crea un producte a la plantilla activa i al sistema.
     * @param id L'identificador del producte
     * @param nom El nom del producte
     * @param nom_categ El nom de la categoria del producte
     * @param preu El preu del producte
     * @throws MyException Si no es pot crear el producte
     */
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException{
        CD.crearProducte(id, nom, nom_categ, preu);
    }

    /**
     * Crea una categoria a la plantilla activa i al sistema.
     * @param nom El nom de la categoria
     * @throws MyException Si no es pot crear la categoria
     */
    public void crearCategoria(String nom) throws MyException {
        CD.crearCategoria(nom);
    }

    /**
     * Crea una categoria a la plantilla activa i al sistema.
     * @param nom El nom de la categoria
     * @param descripcio La descripció de la categoria
     * @throws MyException Si no es pot crear la categoria
     */
    public void crearCategoria(String nom, String descripcio) throws MyException {
        CD.crearCategoria(nom, descripcio);
    }

    /**
     * Crea una prestatgeria a la plantilla activa i al sistema.
     * @param id L'identificador de la prestatgeria
     * @param numPrestatges El número de prestatges de la prestatgeria
     * @throws MyException Si no es pot crear la prestatgeria
     */
    public void crearPrestatgeria(int id, int numPrestatges) throws MyException {
        CD.crearPrestatgeria(id, numPrestatges);
    }

    /**
     * Elimina un producte del sistema i si existeix també de la plantilla activa.
     * @param id L'identificador del producte
     * @throws MyException Si no es pot eliminar el producte
     */
    public void eliminarProducte(int id) throws MyException{
        CD.eliminarProducte(id);
    }

    /**
     * Elimina una categoria del sistema i si existeix també de la plantilla activa.
     * @param nom El nom de la categoria
     * @throws MyException Si no es pot eliminar la categoria
     */
    public void eliminarCategoria(String nom) throws MyException {
        CD.eliminarCategoria(nom);
    }

    /**
     * Elimina una prestatgeria del sistema i si existeix també de la plantilla activa.
     * @param id L'identificador de la prestatgeria
     * @throws MyException Si no es pot eliminar la prestatgeria
     */
    public void eliminarPrestatgeria(int id) throws MyException{
        CD.eliminarPrestatgeria(id);
    }

    /**
     * Modifica un producte del sistema i de la plantilla activa.
     * @param id L'identificador del producte
     * @param nom El nou nom del producte
     * @param preu El nou preu del producte
     * @throws MyException Si no es pot modificar el producte
     */
    public void modificarProducte(int id, String nom, double preu) throws MyException{
        CD.modificarProducte(id, nom, preu);
    }

    /**
     * Modifica una categoria del sistema i de la plantilla activa.
     * @param nom El nom de la categoria
     * @param novaDescripcio La nova descripció de la categoria
     * @throws MyException Si no es pot modificar la categoria
     */
    public void modificarCategoria(String nom, String novaDescripcio) throws MyException {
        CD.modificarCategoria(nom, novaDescripcio);
    }

    /**
     * Modifica una prestatgeria del sistema i de la plantilla activa.
     * @param id L'identificador de la prestatgeria
     * @param nouNumPrestatges El nou número de prestatges de la prestatgeria
     * @throws MyException Si no es pot modificar la prestatgeria
     */
    public void modificarPrestatgeria(int id, int nouNumPrestatges) throws MyException {
        CD.modificarPrestatgeria(id, nouNumPrestatges);
    }

    /**
     * Modifica la similitud entre dos productes de la plantilla activa i del sistema.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @param similitud La nova similitud entre els dos productes
     * @throws MyException Si no es pot modificar la similitud
     */
    public void modificarSimilitud(int id1, int id2, double similitud) throws MyException {
        CD.modificarSimilitud(id1, id2, similitud);
    }

    /**
     * Treu un producte de la plantilla activa.
     * @param id L'identificador del producte
     * @throws MyException Si no es pot treure el producte de la plantilla
     */
    public void treureProductePlantilla(int id) throws MyException {
        CD.treureProducte(id);
    }

    /**
     * Treu una categoria de la plantilla activa.
     * @param nom El nom de la categoria
     * @throws MyException Si no es pot treure la categoria de la plantilla
     */
    public void treureCategoriaPlantilla(String nom) throws MyException {
        CD.treureCategoria(nom);
    }

    /**
     * Treu una prestatgeria de la plantilla activa.
     * @param id L'identificador de la prestatgeria
     * @throws MyException Si no es pot treure la prestatgeria de la plantilla
     */
    public void treurePrestatgeriaPlantilla(int id) throws MyException {
        CD.treurePrestatgeria(id);
    }

    /**
     * Afegeix un producte del sistema a la plantilla activa.
     * @param id L'identificador del producte
     * @throws MyException Si no es pot afegir el producte a la plantilla
     */
    public void importarProducte(int id) throws MyException {
        CD.importarProducte(id);
    }

    /**
     * Afegeix una categoria del sistema a la plantilla activa.
     * @param nom El nom de la categoria
     * @throws MyException Si no es pot afegir la categoria a la plantilla
     */
    public void importarCategoria(String nom) throws MyException {
        CD.importarCategoria(nom);
    }

    /**
     * Afegeix una prestatgeria del sistema a la plantilla activa.
     * @param id L'identificador de la prestatgeria
     * @throws MyException Si no es pot afegir la prestatgeria a la plantilla
     */
    public void importarPrestatgeria(int id) throws MyException {
        CD.importarPrestatgeria(id);
    }

    /**
     * Obte la llista de productes de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa un producte.
     * @return La llista de productes de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la llista de productes de la plantilla
     */
    public List<List<String>> getLlistaProductesPlantilla() throws MyException {
        return CD.getLlistaProductesPlantillaActiva();
    }

    /**
     * Obte la llista de categories de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una categoria.
     * @return La llista de categories de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la llista de categories de la plantilla
     */
    public List<List<String>> getLlistaCategoriesPlantilla() throws MyException {
        return CD.getLlistaCategoriesPlantillaActiva();
    }

    /**
     * Obte la llista de prestatgeries de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria.
     * @return La llista de prestatgeries de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la llista de prestatgeries de la plantilla
     */
    public List<List<String>> getLlistaPrestatgeriesPlantilla() throws MyException {
        return CD.getLlistaPrestatgeriesPlantillaActiva();
    }

    /**
     * Obte una llista amb els noms de totes les plantilles del sistema.
     * @return La llista amb els noms de totes les plantilles del sistema
     * @throws MyException Si no es poden obtenir els noms de les plantilles
     */
    public List<String> getNomAllPlantilles() throws MyException{
        return CD.getNomAllPlantilles();
    }

    /**
     * Obte una llista amb els identificadors de totes les categories del sistema.
     * @return La llista amb els identificadors de totes les categories del sistema
     * @throws MyException Si no es poden obtenir els noms de les categories
     */
    public List<String> getIdAllCategories() throws MyException {
        return CD.getIdAllCategories();
    }

    /**
     * Obte una llista amb els identificadors de tots els productes del sistema.
     * @return La llista amb els identificadors de tots els productes del sistema
     * @throws MyException Si no es poden obtenir els noms dels productes
     */
    public List<String> getIdAllProductes() throws MyException {
        return CD.getIdAllProductes();
    }

    /**
     * Obte una llista amb els identificadors de totes les prestatgeries del sistema.
     * @return La llista amb els identificadors de totes les prestatgeries del sistema
     * @throws MyException Si no es poden obtenir els noms de les prestatgeries
     */
    public List<String> getIdAllPrestatgeries() throws MyException {
        return CD.getIdAllPrestatgeries();
    }

    /**
     * Obte una llista amb els identificadors de totes les categories de la plantilla activa.
     * @return La llista amb els identificadors de totes les categories de la plantilla activa
     * @throws MyException Si no es poden obtenir els noms de les categories de la plantilla
     */
    public List<String> getIdCategoriesPlantilla() throws MyException {
        return CD.getIdCategoriesPlantillaActiva();
    }

    /**
     * Obte una llista amb els identificadors de tots els productes de la plantilla activa.
     * @return La llista amb els identificadors de tots els productes de la plantilla activa
     * @throws MyException Si no es poden obtenir els noms dels productes de la plantilla
     */
    public List<Integer> getIdProductesPlantilla() throws MyException {
        return CD.getIdProductesPlantillaActiva();
    }

    /**
     * Obte una llista amb els identificadors de totes les prestatgeries de la plantilla activa.
     * @return La llista amb els identificadors de totes les prestatgeries de la plantilla activa
     * @throws MyException Si no es poden obtenir els noms de les prestatgeries de la plantilla
     */
    public List<Integer> getIdPrestatgeriesPlantilla() throws MyException {
        return CD.getIdPrestatgeriesPlantillaActiva();
    }

    /**
     * Modifica la distribució aproximada entre dos productes, canviant la posició d'un producte per la del altre.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @throws MyException Si no es pot modificar la distribució aproximada
     */
    public void modificarDistribucioAproximada(int id1, int id2) throws MyException {
        CD.modificarDistribucioAproximada(id1,id2);
    }

    /**
     * Modifica la distribució bruta entre dos productes, canviant la posició d'un producte per la del altre.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @throws MyException Si no es pot modificar la distribució bruta
     */
    public void modificarDistribucioBruta(int id1, int id2) throws MyException {
        CD.modificarDistribucioBruta(id1,id2);
    }

    /**
     * Elimina la similitud entre dos productes de la plantilla activa i del sistema.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @throws MyException Si no es pot eliminar la similitud
     */
    public void eliminarSimilitud(int id1, int id2) throws MyException {
        CD.eliminarSimilitudPlantillaActiva(id1, id2);
    }

    /**
     * Calcula la distribució aproximada de la plantilla activa.
     * @param iterations El nombre d'iteracions de l'algorisme
     * @throws MyException Si no es pot calcular la distribució aproximada
     */
    public void calcularDistribucioAproximada(int iterations) throws MyException {
        CD.calcularDistribucioAproximada(iterations);
    }

    /**
     * Calcula la distribució bruta de la plantilla activa.
     * @throws MyException Si no es pot calcular la distribució bruta
     */
    public void calcularDistribucioBruta() throws MyException {
        CD.calcularDistribucioBruta();
    }

    /**
     * Obte una matriz amb les similituds entre tots els productes de la plantilla activa.
     * A la primera fila i a la primera columna hi ha els identificadors dels productes.
     * @return La matriz amb les similituds entre tots els productes de la plantilla activa
     * @throws MyException Si no es poden obtenir les similituds de la plantilla
     */
    public double[][] imprimirSimilituds() throws MyException {
        return CD.imprimirSimilitudsPlantillaActiva();
    }

    /**
     * S'elimina la distribució aproximada de la plantilla activa.
     * @throws MyException Si no es pot eliminar la distribució aproximada
     */
    public void eliminarDistribucioAproximada() throws MyException {
        CD.eliminarDistribucioAproximada();
    }

    /**
     * S'elimina la distribució bruta de la plantilla activa.
     * @throws MyException Si no es pot eliminar la distribució bruta
     */
    public void eliminarDistribucioBruta() throws MyException {
        CD.eliminarDistribucioBruta();
    }

    /**
     * Comprova si existeix la distribució bruta de la plantilla activa.
     * @return True si existeix la distribució bruta, false altrament
     */
    public boolean existeixDistribucioBruta() {
        return CD.existeixDistribucioBruta();
    }

    /**
     * Comprova si existeix la distribució aproximada de la plantilla activa.
     * @return True si existeix la distribució aproximada, false altrament
     */
    public boolean existeixDistribucioAproximada() {
        return CD.existeixDistribucioAproximada();
    }

    /**
     * Obte la distribució bruta de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria amb els identificadors dels productes que hi ha.
     * @return La distribució bruta de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la distribució bruta de la plantilla
     */
    public List<List<String>> imprimirDistribucioBrutaPerID() throws MyException {
        return CD.imprimirDistribucioBrutaPerID();
    }

    /**
     * Obte la distribució bruta de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria amb els noms dels productes que hi ha.
     * @return La distribució bruta de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la distribució bruta de la plantilla
     */
    public List<List<String>> imprimirDistribucioBrutaPerNom() throws MyException {
        return CD.imprimirDistribucioBrutaPerNom();
    }

    /**
     * Obte la distribució aproximada de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria amb els identificadors dels productes que hi ha.
     * @return La distribució aproximada de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la distribució aproximada de la plantilla
     */
    public List<List<String>> imprimirDistribucioAproximadaPerID() throws MyException {
        return CD.imprimirDistribucioAproximadaPerID();
    }

    /**
     * Obte la distribució aproximada de la plantilla activa en format de llista de llistes de strings, on cada llista de strings representa una prestatgeria amb els noms dels productes que hi ha.
     * @return La distribució aproximada de la plantilla activa en format de llista de llistes de strings
     * @throws MyException Si no es pot obtenir la distribució aproximada de la plantilla
     */
    public List<List<String>> imprimirDistribucioAproximadaPerNom() throws MyException {
        return CD.imprimirDistribucioAproximadaPerNom();
    }

    /**
     * Exporta la distribució bruta dels identificadors dels productes de la plantilla activa a un fitxer txt.
     * @param filePath El path del fitxer de la distribució bruta
     * @throws MyException Si no es pot exportar la distribució bruta
     */
    public void exportarDistribucioBrutaPerID(String filePath) throws MyException{
        CD.exportarDistribucioBrutaPerID(filePath);
    }

    /**
     * Exporta la distribució bruta dels noms dels productes de la plantilla activa a un fitxer txt.
     * @param filePath El path del fitxer de la distribució bruta
     * @throws MyException Si no es pot exportar la distribució bruta
     */
    public void exportarDistribucioBrutaPerNom(String filePath) throws MyException{
        CD.exportarDistribucioBrutaPerNom(filePath);
    }

    /**
     * Exporta la distribució aproximada dels identificadors dels productes de la plantilla activa a un fitxer txt.
     * @param filePath El path del fitxer de la distribució aproximada
     * @throws MyException Si no es pot exportar la distribució aproximada
     */
    public void exportarDistribucioAproximadaPerID(String filePath) throws MyException{
        CD.exportarDistribucioAproximadaPerID(filePath);
    }

    /**
     * Exporta la distribució aproximada dels noms dels productes de la plantilla activa a un fitxer txt.
     * @param filePath El path del fitxer de la distribució aproximada
     * @throws MyException Si no es pot exportar la distribució aproximada
     */
    public void exportarDistribucioAproximadaPerNom(String filePath) throws MyException {
        CD.exportarDistribucioAproximadaPerNom(filePath);
    }
}
