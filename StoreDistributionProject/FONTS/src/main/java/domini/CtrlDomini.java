package domini;

import com.google.gson.Gson;
import domini.models.Plantilla;
import domini.models.Prestatgeria;
import domini.models.Producte;
import exceptions.MyException;
import persistencia.CtrlPersistencia;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Aquesta classe és el controlador de domini del programa.
 * S'encarrega de gestionar les plantilles, categories, productes, prestatgeries, similituds, i la distribució.
 * També es comunica amb la capa de persistencia per a guardar i carregar dades, i amb la capa de presentació per a mostrar resultats.
 */
public class CtrlDomini {

    /**
     * L'única instància de la classe.
     */
    private static CtrlDomini singletonObject;

    /**
     * La plantilla activa.
     */
    private Plantilla plantilla;

    /**
     * El controlador de distribució.
     */
    private final CtrlDistribucio CDistribucio;

    /**
     * El controlador de persistència.
     */
    private final CtrlPersistencia CPersistencia;

    /**
     * Obte l'única instància de la classe.
     * @return L'única instància de la classe.
     * @throws MyException Si no s'ha pogut crear l'única instància de la classe.
     */
    public static CtrlDomini getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new CtrlDomini();
        }
        return singletonObject;
    }

    /**
     * Crea un nou controlador de domini, inicialitzant els controladors de persistència i de distribució.
     * @throws MyException Si no s'ha pogut crear el controlador de persistència.
     */
    private CtrlDomini() throws MyException {
        CPersistencia = CtrlPersistencia.getInstance();
        plantilla = null;
        CDistribucio = CtrlDistribucio.getInstance();
    }

    /**
     * Elimina totes les dades del sistema.
     * @throws MyException Si no s'han pogut eliminar les dades.
     */
    public void eliminarDades() throws MyException {
        CPersistencia.eliminarDades();
        plantilla = null;
        if(CDistribucio.DistribucioBrutaEstaActiva()) CDistribucio.eliminarDistribucioBruta();
        if(CDistribucio.DistribucioAproximadaEstaActiva()) CDistribucio.eliminarDistribucioAproximada();
    }

    /**
     * Crea una nova plantilla amb el nom donat.
     * @param nom El nom de la plantilla.
     * @throws MyException Si ja hi ha una plantilla activa o si ja existeix una plantilla amb el mateix nom.
     */
    public void crearPlantillaActiva(String nom) throws MyException{
        if (plantilla != null) {
            if (plantilla.getNom().equals(nom)) {
                throw new MyException("Aquesta plantilla ja és activa.");
            }
            throw new MyException("Ja hi ha una plantilla activa.");
        }
        CPersistencia.crearPlantilla(nom);
        plantilla = new Plantilla(nom);
    }

    /**
     * Tanca la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void tancarPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        plantilla = null;
        if(CDistribucio.DistribucioBrutaEstaActiva()) CDistribucio.eliminarDistribucioBruta();
        if(CDistribucio.DistribucioAproximadaEstaActiva()) CDistribucio.eliminarDistribucioAproximada();
    }

    /**
     * Elimina la plantilla amb el nom donat.
     * @param nom El nom de la plantilla a eliminar.
     * @throws MyException Si no existeix cap plantilla amb aquest nom.
     */
    public void eliminarPlantilla(String nom) throws MyException {
        CPersistencia.eliminarPlantilla(nom);
        if(plantilla != null && nom.equals(plantilla.getNom())) {
            plantilla = null;
            if(CDistribucio.DistribucioBrutaEstaActiva()) CDistribucio.eliminarDistribucioBruta();
            if(CDistribucio.DistribucioAproximadaEstaActiva()) CDistribucio.eliminarDistribucioAproximada();
        }
    }

    /**
     * Canvia el nom de la plantilla activa.
     * @param nouNom El nou nom de la plantilla.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void setNomPlantillaActiva(String nouNom) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.setNomPlantilla(plantilla.getNom(), nouNom);
        plantilla.setNom(nouNom);
    }

    /**
     * Obte el nom de la plantilla activa.
     * @return El nom de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public String getNomPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getNom();
    }

    /**
     * Carrega del sistema la plantilla amb el nom donat.
     * @param nom El nom de la plantilla a carregar.
     * @throws MyException Si ja hi ha una plantilla activa o si no existeix cap plantilla amb aquest nom.
     */
    public void cargarPlantilla(String nom) throws MyException {
        if (plantilla != null) {
            if (plantilla.getNom().equals(nom)) throw new MyException("Aquesta plantilla ja és activa.");
            else throw new MyException("Ja hi ha una plantilla activa.");
        }
        if(CPersistencia.existeixPlantilla(nom)) {
            Map<String, Object> aux = CPersistencia.getPlantilla(nom);
            plantilla = new Plantilla((String) aux.get("nom"));
            plantilla.importarLlistaCategories(CPersistencia.getCategoriesPerLlistaId((List<String>) aux.get("name_cat")));       //L'ordre importa, categories s'han de cargar abans de productes
            plantilla.importarLlistaProductes(CPersistencia.getProductesPerLlistaId((List<Integer>) aux.get("id_prod")));
            plantilla.importarLlistaPrestatgeries(CPersistencia.getPrestatgeriesPerLlistaId((List<Integer>) aux.get("id_prest")));
            plantilla.importarLlistSimilituds(CPersistencia.getSimilitudDeLlistaProductes(plantilla.getIDProductes()));
        } else {
            throw new MyException("La plantilla no existeix");
        }
    }

    /**
     * Importa la plantilla des del fitxer amb el path donat.
     * @param filePath El path del fitxer a importar.
     * @throws MyException Si ja hi ha una plantilla activa, si no s'ha seleccionat cap fitxer o si no s'ha pogut importar la plantilla.
     */
    public void importarPlantilla(String filePath) throws MyException{
        if (plantilla != null) {
            throw new MyException("Ja hi ha una plantilla activa.");
        }
        if (filePath == null) {
            throw new MyException("No s'ha seleccionat cap fitxer.");
        }
        String contents = CPersistencia.importarPlantilla(filePath);
        Gson gson = new Gson();
        plantilla = gson.fromJson(contents, Plantilla.class);
        //es sobreescriuen
        CPersistencia.eliminarProductes(plantilla.getIDProductes());
        CPersistencia.eliminarCategories(plantilla.getNomCategories());
        CPersistencia.eliminarPrestatgeries(plantilla.getIDPrestatges());
        CPersistencia.afegirCategories(plantilla.getLlistaCategories()); //aquest ordre es important, sino els comptadors
        // de categories no s'actualitzaran al afegir els productes, les categories sempre s'haurien d'afegir abans que els productes
        CPersistencia.afegirProductes(plantilla.getLlistaProductes());
        CPersistencia.afegirPrestatgeries(plantilla.getLlistaPrestatgeria());
        CPersistencia.eliminarSimilituds(plantilla.getIDProductes());
        Map.Entry<double[][], List<Integer>> similitudsEntry = plantilla.getLlistaSimilituds();
        CPersistencia.afegirSimilituds(similitudsEntry.getKey(),similitudsEntry.getValue());
        guardarPlantillaActiva();
    }

    /**
     * Exporta la plantilla activa al fitxer amb el path donat.
     * @param filePath El path del fitxer a exportar.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut exportar la plantilla.
     */
    public void exportarPlantillaActiva(String filePath) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        Gson gson = new Gson();
        String contents = gson.toJson(plantilla);
        CPersistencia.exportarPlantilla(contents, filePath);
    }

    /**
     * Guarda la plantilla activa al sistema.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut guardar la plantilla.
     */
    public void guardarPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.guardarPlantilla(plantilla.getNom(), plantilla.getIDProductes(), plantilla.getNomCategories(), plantilla.getIDPrestatges());
    }

    /**
     * Crea en la plantilla activa i en el sistema un nou producte amb l'id, nom, categoria i preu donats.
     * @param id L'identificador del producte.
     * @param nom El nom del producte.
     * @param nom_categ El nom de la categoria del producte.
     * @param preu El preu del producte.
     * @throws MyException Si no hi ha una plantilla activa, si la categoria no existeix, si el nom és null o buit, si l'id és negatiu o si el preu és negatiu.
     */
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (nom_categ == null) {
            throw new MyException("La categoria no existeix");
        }
        if (nom == null || nom.equals("")) {
            throw new MyException("El nom no pot ser buit");
        }
        if (id < 0) {
            throw new MyException("El id no pot ser negatiu");
        }
        if (preu < 0) {
            throw new MyException("El preu no pot ser negatiu");
        }
        CPersistencia.afegirProducte(id, nom, nom_categ, preu);
        plantilla.crearProducte(id, nom, nom_categ, preu);
    }

    /**
     * Crea en la plantilla activa i en el sistema una nova categoria amb el nom donat.
     * @param nom El nom de la categoria.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void crearCategoria(String nom) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.afegirCategoria(nom, "");
        plantilla.crearCategoria(nom, "");
    }

    /**
     * Crea en la plantilla activa i en el sistema una nova categoria amb el nom i la descripció donats.
     * @param nom El nom de la categoria.
     * @param descripcio La descripció de la categoria.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void crearCategoria(String nom, String descripcio) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.afegirCategoria(nom, descripcio);
        plantilla.crearCategoria(nom, descripcio);
    }

    /**
     * Crea en la plantilla activa i en el sistema una nova prestatgeria amb l'id i el nombre de prestatges donats.
     * @param id L'identificador de la prestatgeria.
     * @param num_prestatges El nombre de prestatges de la prestatgeria.
     * @throws MyException Si no hi ha una plantilla activa, si l'id és negatiu o si el nombre de prestatges és zero o negatiu.
     */
    public void crearPrestatgeria(int id, int num_prestatges) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (id < 0) {
            throw new MyException("El id no pot ser negatiu");
        }
        if (num_prestatges <= 0) {
            throw new MyException("El número de prestatges ha de ser positiu.");
        }
        CPersistencia.afegirPrestatgeria(id, num_prestatges);
        plantilla.crearPrestatgeria(id, num_prestatges);
    }

    /**
     * Elimina el producte amb l'id donat de la plantilla activa i del sistema.
     * @param id L'identificador del producte a eliminar.
     * @throws MyException Si no hi ha una plantilla activa o si el producte no existeix.
     */
    public void eliminarProducte(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.eliminarProducte(id);
        if (plantilla.getIDProductes().contains(id)) {
            plantilla.eliminarProducte(id);
        }
    }

    /**
     * Elimina la categoria amb el nom donat de la plantilla activa i del sistema.
     * @param nom El nom de la categoria a eliminar.
     * @throws MyException Si no hi ha una plantilla activa, si la categoria no existeix o si la categoria té productes associats.
     */
    public void eliminarCategoria(String nom) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if(CPersistencia.getNumProductesCategoria(nom) > 0) {
            throw new MyException("No es pot eliminar una categoria amb productes");
        }
        CPersistencia.eliminarCategoria(nom);
        if (plantilla.getNomCategories().contains(nom)) {
            plantilla.eliminarCategoria(nom);
        }
    }

    /**
     * Elimina la prestatgeria amb l'id donat de la plantilla activa i del sistema.
     * @param id L'identificador de la prestatgeria a eliminar.
     * @throws MyException Si no hi ha una plantilla activa o si la prestatgeria no existeix.
     */
    public void eliminarPrestatgeria(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.eliminarPrestatgeria(id);
        if (plantilla.getIDPrestatges().contains(id)) {
            plantilla.eliminarPrestatgeria(id);
        }

    }

    /**
     * Modifica el nom i el preu del producte amb l'id donat de la plantilla activa i del sistema.
     * @param id L'identificador del producte a modificar.
     * @param nouNom El nou nom del producte.
     * @param nouPreu El nou preu del producte.
     * @throws MyException Si no hi ha una plantilla activa o si el producte no existeix.
     */
    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (!plantilla.getIDProductes().contains(id)) {
            throw new MyException("El producte no existeix a la plantilla activa");
        }
        CPersistencia.modificarProducte(id, nouNom, nouPreu);
        plantilla.modificarProducte(id, nouNom, nouPreu);
    }

    /**
     * Modifica la descripció de la categoria amb el nom donat de la plantilla activa i del sistema.
     * @param nom El nom de la categoria a modificar.
     * @param novaDescripcio La nova descripció de la categoria.
     * @throws MyException Si no hi ha una plantilla activa o si la categoria no existeix.
     */
    public void modificarCategoria(String nom, String novaDescripcio) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (!plantilla.getNomCategories().contains(nom)) {
            throw new MyException("La categoria no existeix a la plantilla activa");
        }
        CPersistencia.modificarCategoria(nom, novaDescripcio);
        plantilla.modificarCategoria(nom, novaDescripcio);
    }

    /**
     * Modifica el nombre de prestatges de la prestatgeria amb l'id donat de la plantilla activa i del sistema.
     * @param id L'identificador de la prestatgeria a modificar.
     * @param num_prestatges El nou nombre de prestatges de la prestatgeria.
     * @throws MyException Si no hi ha una plantilla activa o si la prestatgeria no existeix.
     */
    public void modificarPrestatgeria(int id, int num_prestatges) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (!plantilla.getIDPrestatges().contains(id)) {
            throw new MyException("La prestatgeria no existeix a la plantilla activa");
        }
        CPersistencia.modificarPrestatgeria(id, num_prestatges);
        plantilla.modificarPrestatgeria(id, num_prestatges);
    }

    /**
     * Afegeix una nova similitud entre dos productes de la plantilla activa i del sistema. Si ja existeix una similitud entre els dos productes, aquesta es modifica.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @param grauSimilitud El grau de similitud entre els dos productes.
     * @throws MyException Si no hi ha una plantilla activa, si els productes són el mateix, si el grau de similitud no està entre 0 i 1.
     */
    public void modificarSimilitud(int id1, int id2, double grauSimilitud) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (id1 == id2) {
            throw new MyException("Els productes son els mateixos");
        }
        if (grauSimilitud < 0 || grauSimilitud > 1) {
            throw new MyException("El grau de similitud ha d'estar entre 0 i 1");
        }
        plantilla.afegirSimilitud(id1, id2, grauSimilitud);
        CPersistencia.modificarSimilitud(id1, id2, grauSimilitud);
    }

    /**
     * Elimina un producte de la plantilla activa.
     * @param id L'identificador del producte a eliminar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void treureProducte(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        plantilla.eliminarProducte(id);
    }

    /**
     * Elimina una categoria de la plantilla activa.
     * @param nom El nom de la categoria a eliminar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void treureCategoria(String nom) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        plantilla.eliminarCategoria(nom);
    }

    /**
     * Elimina una prestatgeria de la plantilla activa.
     * @param id L'identificador de la prestatgeria a eliminar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void treurePrestatgeria(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        plantilla.eliminarPrestatgeria(id);
    }

    /**
     * Obte un producte del sistema i el crea a la plantilla activa.
     * @param id L'identificador del producte a importar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void importarProducte(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        List<String> producte = CPersistencia.getProducte(id);
        plantilla.crearProducte(Integer.parseInt(producte.get(0)), producte.get(1), producte.get(2), Double.parseDouble(producte.get(3)));
    }

    /**
     * Obte una categoria del sistema i la crea a la plantilla activa.
     * @param nom El nom de la categoria a importar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void importarCategoria(String nom) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        List<String> categoria = CPersistencia.getCategoria(nom);
        plantilla.crearCategoria(categoria.get(0), categoria.get(1));
    }

    /**
     * Obte una prestatgeria del sistema i la crea a la plantilla activa.
     * @param id L'identificador de la prestatgeria a importar.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void importarPrestatgeria(int id) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        List<String> prestatgeria = CPersistencia.getPrestatgeria(id);
        plantilla.crearPrestatgeria(Integer.parseInt(prestatgeria.get(0)), Integer.parseInt(prestatgeria.get(1)));
    }

    /**
     * Obte el nombre total de prestatges de la plantilla activa.
     * @return El nombre total de prestatges de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    private int numTotalPrestatgesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.numTotalPrestatges();
    }

    /**
     * Obte els productes de la plantilla activa.
     * @return Els productes de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    private HashMap<Integer, Producte> getProductesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getProductes();
    }

    /**
     * Obte la llista de productes de la plantilla activa en format de llistes de strings.
     * @return La llista de productes de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> getLlistaProductesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getLlistaProductes();
    }

    /**
     * Obte la llista de categories de la plantilla activa en format de llistes de strings.
     * @return La llista de categories de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> getLlistaCategoriesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getLlistaCategories();
    }

    /**
     * Obte les prestatgeries de la plantilla activa.
     * @return Les prestatgeries de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public HashMap<Integer, Prestatgeria> getPrestatgeriesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getPrestatges();
    }

    /**
     * Obte la llista de prestatgeries de la plantilla activa en format de llistes de strings.
     * @return La llista de prestatgeries de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> getLlistaPrestatgeriesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getLlistaPrestatgeria();
    }

    /**
     * Obte els identificadors de totes les categories del sistema.
     * @return Els identificadors de totes les categories del sistema.
     * @throws MyException Si no s'han pogut obtenir els identificadors de les categories.
     */
    public List<String> getIdAllCategories() throws MyException {
        return CPersistencia.getIdAllCategories();
    }

    /**
     * Obte els identificadors de tots els productes del sistema.
     * @return Els identificadors de tots els productes del sistema.
     * @throws MyException Si no s'han pogut obtenir els identificadors dels productes.
     */
    public List<String> getIdAllProductes() throws MyException {
        return CPersistencia.getIdAllProductes();
    }

    /**
     * Obte els identificadors de totes les prestatgeries del sistema.
     * @return Els identificadors de totes les prestatgeries del sistema.
     * @throws MyException Si no s'han pogut obtenir els identificadors de les prestatgeries.
     */
    public List<String> getIdAllPrestatgeries() throws MyException {
        return CPersistencia.getIdAllPrestatgeries();
    }

    /**
     * Obte els noms de totes les plantilles del sistema.
     * @return Els noms de totes les plantilles del sistema.
     * @throws MyException Si no s'han pogut obtenir els noms de les plantilles.
     */
    public List<String> getNomAllPlantilles() throws MyException {
        return CPersistencia.getNomAllPlantilles();
    }

    /**
     * Obte els identificadors de totes les categories de la plantilla activa.
     * @return Els identificadors de totes les categories de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<String> getIdCategoriesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getNomCategories();
    }

    /**
     * Obte els identificadors de tots els productes de la plantilla activa.
     * @return Els identificadors de tots els productes de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<Integer> getIdProductesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getIDProductes();
    }

    /**
     * Obte els identificadors de totes les prestatgeries de la plantilla activa.
     * @return Els identificadors de totes les prestatgeries de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<Integer> getIdPrestatgeriesPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.getIDPrestatges();
    }

    /**
     * Elimina la similitud entre dos productes de la plantilla activa i del sistema.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @throws MyException Si no hi ha una plantilla activa, si els productes són el mateix o si no existeix la similitud.
     */
    public void eliminarSimilitudPlantillaActiva(int id1, int id2) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        if (id1 == id2) {
            throw new MyException("Els productes son els mateixos");
        }
        plantilla.eliminarSimilitud(id1, id2);
        CPersistencia.eliminarSimilitud(id1, id2);
    }

    /**
     * Calcula la distribució bruta de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void calcularDistribucioBruta() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.calcularDistribucioBruta(getProductesPlantillaActiva(), getPrestatgeriesPlantillaActiva(), numTotalPrestatgesPlantillaActiva());
    }

    /**
     * Calcula la distribució aproximada de la plantilla activa.
     * @param iterations El nombre d'iteracions per calcular la distribució aproximada.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void calcularDistribucioAproximada(int iterations) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.calcularDistribucioAproximada(getProductesPlantillaActiva(), getPrestatgeriesPlantillaActiva(), numTotalPrestatgesPlantillaActiva(), iterations);
    }

    /**
     * Modifica la distribució bruta de la plantilla activa canviant la posicion de dos productes.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void modificarDistribucioAproximada(int id1, int id2) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.modificarDistribucioAproximada(id1, id2);
    }

    /**
     * Modifica la distribució bruta de la plantilla activa canviant la posicion de dos productes.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void modificarDistribucioBruta(int id1, int id2) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.modificarDistribucioBruta(id1, id2);
    }

    /**
     * Elimina la distribució bruta activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void eliminarDistribucioBruta() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.eliminarDistribucioBruta();
    }

    /**
     * Elimina la distribució aproximada activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public void eliminarDistribucioAproximada() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CDistribucio.eliminarDistribucioAproximada();
    }

    /**
     * Comprova si existeix una distribució bruta.
     * @return Cert si existeix una distribució bruta, fals altrament.
     */
    public boolean existeixDistribucioBruta() {
        return CDistribucio.DistribucioBrutaEstaActiva();
    }

    /**
     * Comprova si existeix una distribució aproximada.
     * @return Cert si existeix una distribució aproximada, fals altrament.
     */
    public boolean existeixDistribucioAproximada() {
        return CDistribucio.DistribucioAproximadaEstaActiva();
    }

    /**
     * Retorna una matriu amb les similituds de la plantilla activa.
     * @return Una matriu amb les similituds de la plantilla activa.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public double[][] imprimirSimilitudsPlantillaActiva() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return plantilla.imprimirSimilituds();
    }

    /**
     * Retorna la distribució bruta de la plantilla activa en format de llistes de strings, on cada llista representa una prestatgeria i els elements d'aquesta llista són els identificadors dels productes en la prestatgeria.
     * @return La distribució bruta de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> imprimirDistribucioBrutaPerID() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return CDistribucio.imprimirDistribucioBrutaPerId();
    }

    /**
     * Retorna la distribució bruta de la plantilla activa en format de llistes de strings, on cada llista representa una prestatgeria i els elements d'aquesta llista són els noms dels productes en la prestatgeria.
     * @return La distribució bruta de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> imprimirDistribucioBrutaPerNom() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return CDistribucio.imprimirDistribucioBrutaPerNom();
    }

    /**
     * Retorna la distribució aproximada de la plantilla activa en format de llistes de strings, on cada llista representa una prestatgeria i els elements d'aquesta llista són els identificadors dels productes en la prestatgeria.
     * @return La distribució aproximada de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> imprimirDistribucioAproximadaPerID() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return CDistribucio.imprimirDistribucioAproximadaPerId();
    }

    /**
     * Retorna la distribució aproximada de la plantilla activa en format de llistes de strings, on cada llista representa una prestatgeria i els elements d'aquesta llista són els noms dels productes en la prestatgeria.
     * @return La distribució aproximada de la plantilla activa en format de llistes de strings.
     * @throws MyException Si no hi ha una plantilla activa.
     */
    public List<List<String>> imprimirDistribucioAproximadaPerNom() throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        return CDistribucio.imprimirDistribucioAproximadaPerNom();
    }

    /**
     * Exporta la distribució bruta per identificadors de la plantilla activa al fitxer amb el path donat.
     * @param filePath El path del fitxer a exportar.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut exportar la distribució.
     */
    public void exportarDistribucioBrutaPerID(String filePath) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.exportarDistribucio(CDistribucio.imprimirDistribucioBrutaPerId(), filePath);
    }

    /**
     * Exporta la distribució bruta per noms de la plantilla activa al fitxer amb el path donat.
     * @param filePath El path del fitxer a exportar.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut exportar la distribució.
     */
    public void exportarDistribucioBrutaPerNom(String filePath) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.exportarDistribucio(CDistribucio.imprimirDistribucioBrutaPerNom(), filePath);
    }

    /**
     * Exporta la distribució aproximada per identificadors de la plantilla activa al fitxer amb el path donat.
     * @param filePath El path del fitxer a exportar.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut exportar la distribució.
     */
    public void exportarDistribucioAproximadaPerID(String filePath) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.exportarDistribucio(imprimirDistribucioAproximadaPerID(), filePath);
    }

    /**
     * Exporta la distribució aproximada per noms de la plantilla activa al fitxer amb el path donat.
     * @param filePath El path del fitxer a exportar.
     * @throws MyException Si no hi ha una plantilla activa o si no s'ha pogut exportar la distribució.
     */
    public void exportarDistribucioAproximadaPerNom(String filePath) throws MyException {
        if (plantilla == null) {
            throw new MyException("No hi ha una plantilla activa.");
        }
        CPersistencia.exportarDistribucio(imprimirDistribucioAproximadaPerNom(), filePath);
    }
}