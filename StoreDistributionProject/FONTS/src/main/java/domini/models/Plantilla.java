package domini.models;

import exceptions.MyException;

import java.util.*;

/**
 * Aquesta classe representa una plantilla de productes.
 * Cada plantilla té un nom, una llista de productes, una llista de categories i una llista de prestatgeries.
 */
public class Plantilla {
    /**
     *  Nom de la plantilla.
     */
    private String nom;

    /**
     * Llista de productes de la plantilla.
     */
    private final HashMap<Integer, Producte> Llista_Productes;

    /**
     * Llista de categories de la plantilla.
     */
    private final HashMap<String, Categoria> Llista_Categories;

    /**
     * Llista de prestatgeries de la plantilla.
     */
    private final HashMap<Integer, Prestatgeria> Llista_Prestatges;

    /**
     * Crea una nova plantilla amb el nom donat.
     * @param nom El nom de la plantilla.
     */
    public Plantilla(String nom) {
        this.nom = nom;
        this.Llista_Productes = new HashMap<>();
        this.Llista_Categories = new HashMap<>();
        this.Llista_Prestatges = new HashMap<>();
    }

    /**
     * Obte el nom de la plantilla.
     * @return El nom de la plantilla.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifica el nom de la plantilla.
     * @param nom El nou nom de la plantilla.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Crear un producte en la llista de la plantilla amb els atributs donats.
     * @param id L'identificador del producte.
     * @param nom El nom del producte.
     * @param nom_categ El nom de la categoria del producte.
     * @param preu El preu del producte.
     * @throws MyException Si el producte ja existeix a la plantilla, o la categoria no existeix.
     */
    public void crearProducte(int id, String nom, String nom_categ, double preu) throws MyException {
        Categoria categ = Llista_Categories.get(nom_categ);
        if (Llista_Productes.containsKey(id)) {
            throw new MyException("El producte amb aquesta ID ja existeix a la plantilla activa");
        }
        if (categ == null) {
            throw new MyException("La categoria " + nom_categ + " no existeix per aquest producte");
        }
        Producte prod = new Producte(id, nom, categ, preu);
        Llista_Productes.put(id, prod);
    }

    /**
     * Eliminar un producte de la llista de la plantilla amb l'identificador donat.
     * @param id L'identificador del producte a eliminar.
     * @throws MyException Si el producte no existeix a la plantilla.
     */
    public void eliminarProducte(int id) throws MyException {
        Producte prod = Llista_Productes.get(id);
        if (prod == null) {
            throw new MyException("El producte amb aquesta ID no existeix a la plantilla activa");
        }
        prod.eliminarProducte();
        Llista_Productes.remove(id);
        for (Producte pro : Llista_Productes.values()) {
            if (pro.existeixSimilitud(id)) pro.eliminarSimilitud(id);
        }
    }

    /**
     * Modificar un producte de la llista de la plantilla amb l'identificador donat.
     * @param id L'identificador del producte a modificar.
     * @param nouNom El nou nom del producte.
     * @param nouPreu El nou preu del producte.
     * @throws MyException Si el producte no existeix a la plantilla.
     */
    public void modificarProducte(int id, String nouNom, double nouPreu) throws MyException {
        Producte prod = Llista_Productes.get(id);
        if (prod == null) {
            throw new MyException("El producte amb aquesta ID no existeix a la plantilla activa");
        }
        prod.setNom(nouNom);
        prod.setPreu(nouPreu);
    }

    /**
     * Obte la llista de productes de la plantilla.
     * @return La llista de productes de la plantilla.
     */
    public HashMap<Integer, Producte> getProductes() {
        return Llista_Productes;
    }

    /**
     * Obte la llista de productes de la plantilla en format de llistes de strings.
     * @return La llista de productes de la plantilla en format de llistes de strings.
     */
    public List<List<String>> getLlistaProductes() {
        List<List<String>> llista = new ArrayList<>();
        for (Producte prod : Llista_Productes.values()) {
            List<String> producte = new ArrayList<>();
            producte.add(Integer.toString(prod.getId()));
            producte.add(prod.getNom());
            producte.add(prod.getCategoria().getNom());
            producte.add(Double.toString(prod.getPreu()));
            llista.add(producte);
        }
        return llista;
    }

    /**
     * Obte la llista dels identificadors dels productes de la plantilla.
     * @return La llista dels identificadors dels productes de la plantilla.
     */
    public List<Integer> getIDProductes() {
        return new ArrayList<>(Llista_Productes.keySet());
    }

    /**
     * Obte la llista dels noms de les categories de la plantilla.
     * @return La llista dels noms de les categories de la plantilla.
     */
    public List<String> getNomCategories() {
        return new ArrayList<>(Llista_Categories.keySet());
    }

    /**
     * Obte la llista dels identificadors de les prestatgeries de la plantilla.
     * @return La llista dels identificadors de les prestatgeries de la plantilla.
     */
    public List<Integer> getIDPrestatges() {
        return new ArrayList<>(Llista_Prestatges.keySet());
    }

    /**
     * Crear una categoria en la llista de la plantilla amb el nom donat.
     * @param nom El nom de la categoria.
     * @throws MyException Si la categoria ja existeix a la plantilla.
     */
    public void crearCategoria(String nom) throws MyException {
        if (Llista_Categories.containsKey(nom)) {
            throw new MyException("La categoria ja existeix a la plantilla activa");
        }
        Categoria categ = new Categoria(nom, "");
        Llista_Categories.put(nom, categ);
    }

    /**
     * Crear una categoria en la llista de la plantilla amb el nom i la descripció donats.
     * @param nom El nom de la categoria.
     * @param descripcio La descripció de la categoria.
     * @throws MyException Si la categoria ja existeix a la plantilla.
     */
    public void crearCategoria(String nom, String descripcio) throws MyException {
        if (Llista_Categories.containsKey(nom)) {
            throw new MyException("La categoria ja existeix a la plantilla activa");
        }
        Categoria categ = new Categoria(nom, descripcio);
        Llista_Categories.put(nom, categ);
    }

    /**
     * Eliminar una categoria de la llista de la plantilla amb el nom donat.
     * @param nom El nom de la categoria a eliminar.
     * @throws MyException Si la categoria no existeix a la plantilla, o té productes assignats.
     */
    public void eliminarCategoria(String nom) throws MyException {
        Categoria categ = Llista_Categories.get(nom);
        if (categ == null) {
            throw new MyException("La categoria no existeix a la plantilla activa");
        } else if (categ.getNumProducte() > 0) {
            throw new MyException("La categoria te productes assignats");
        }
        Llista_Categories.remove(nom);
    }

    /**
     * Modificar una categoria de la llista de la plantilla amb el nom donat.
     * @param nom El nom de la categoria a modificar.
     * @param novaDescripcio La nova descripció de la categoria.
     * @throws MyException Si la categoria no existeix a la plantilla.
     */
    public void modificarCategoria(String nom, String novaDescripcio) throws MyException {
        Categoria categ = Llista_Categories.get(nom);
        if (categ == null) {
            throw new MyException("La categoria no existeix a la plantilla activa");
        }
        categ.setDescripcio(novaDescripcio);
    }

    /**
     * Obte la llista de categories de la plantilla en format de llistes de strings.
     * @return La llista de categories de la plantilla en format de llistes de strings.
     */
    public List<List<String>> getLlistaCategories() {
        List<List<String>> llista = new ArrayList<>();
        for (Categoria categ : Llista_Categories.values()) {
            List<String> categoria = new ArrayList<>();
            categoria.add(categ.getNom());
            categoria.add(categ.getDescripcio());
            categoria.add(Integer.toString(categ.getNumProducte()));
            llista.add(categoria);
        }
        return llista;
    }

    /**
     * Obte la llista de categories de la plantilla.
     * @return La llista de categories de la plantilla.
     */
    public HashMap<String, Categoria> getCategories() {
        return new HashMap<>(Llista_Categories);
    }

    /**
     * Crear una prestatgeria en la llista de la plantilla amb l'identificador i el nombre de prestatges donats.
     * @param id L'identificador de la prestatgeria.
     * @param num_prestatges El nombre de prestatges de la prestatgeria.
     * @throws MyException Si la prestatgeria ja existeix a la plantilla.
     */
    public void crearPrestatgeria(int id, int num_prestatges) throws MyException {
        if (Llista_Prestatges.containsKey(id)) {
            throw new MyException("La prestatgeria amb aquesta ID ja existeix a la plantilla activa");
        }

        Prestatgeria prestatgeria = new Prestatgeria(id, num_prestatges);
        Llista_Prestatges.put(id, prestatgeria);
    }

    /**
     * Modificar una prestatgeria de la llista de la plantilla amb l'identificador donat.
     * @param id L'identificador de la prestatgeria a modificar.
     * @param num_prestatges El nou nombre de prestatges de la prestatgeria.
     * @throws MyException Si la prestatgeria no existeix a la plantilla.
     */
    public void modificarPrestatgeria(int id, int num_prestatges) throws MyException {
        Prestatgeria prestatgeria = Llista_Prestatges.get(id);
        if (prestatgeria == null) {
            throw new MyException("La prestatgeria amb aquesta ID no existeix a la plantilla activa");
        }
        prestatgeria.setNumPrestatges(num_prestatges);
    }

    /**
     * Eliminar una prestatgeria de la llista de la plantilla amb l'identificador donat.
     * @param id L'identificador de la prestatgeria a eliminar.
     * @throws MyException Si la prestatgeria no existeix a la plantilla.
     */
    public void eliminarPrestatgeria(int id) throws MyException {
        if (!Llista_Prestatges.containsKey(id)) {
            throw new MyException("La prestatgeria amb aquesta ID no existeix a la plantilla activa");
        }
        Llista_Prestatges.remove(id);
    }

    /**
     * Obte el nombre total de prestatges de la plantilla.
     * @return El nombre total de prestatges de la plantilla.
     */
    public int numTotalPrestatges() {
        int total = 0;
        for (Prestatgeria prestatgeria : Llista_Prestatges.values()) {
            total += prestatgeria.getNumPrestatges();
        }
        return total;
    }

    /**
     * Obte la llista de prestatgeries de la plantilla.
     * @return La llista de prestatgeries de la plantilla.
     */
    public HashMap<Integer, Prestatgeria> getPrestatges() {
        return new HashMap<>(Llista_Prestatges);
    }

    /**
     * Obte la llista de prestatgeries de la plantilla en format de llistes de strings.
     * @return La llista de prestatgeries de la plantilla en format de llistes de strings.
     */
    public List<List<String>> getLlistaPrestatgeria() {
        List<List<String>> llista = new ArrayList<>();
        for (Prestatgeria prestatgeria : Llista_Prestatges.values()) {
            List<String> prestatgeriaL = new ArrayList<>();
            prestatgeriaL.add(Integer.toString(prestatgeria.getId()));
            prestatgeriaL.add(Integer.toString(prestatgeria.getNumPrestatges()));
            llista.add(prestatgeriaL);
        }
        return llista;
    }

    /**
     * Afegeix una similitud entre dos productes de la plantilla amb els identificadors donats.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @param grauSimilitud El grau de similitud entre els dos productes.
     * @throws MyException Si algun dels productes no existeix a la plantilla, o el grau de similitud no està entre 0 i 1 o si s'intenta afegir similitud entre el mateix producte.
     */
    public void afegirSimilitud(int id1, int id2, double grauSimilitud) throws MyException {
        Producte prod1 = Llista_Productes.get(id1);
        Producte prod2 = Llista_Productes.get(id2);
        if (prod1 == null || prod2 == null) {
            throw new MyException("Algun dels productes no existeix a la plantilla activa");
        }
        if (grauSimilitud < 0 || grauSimilitud > 1) {
            throw new MyException("El grau de similitud ha d'estar entre 0 i 1");
        }
        if(id1 == id2) {
            throw new MyException("No es pot afegir similitud entre el mateix producte");
        }
        prod1.afegirSimilitud(id2, grauSimilitud);
        prod2.afegirSimilitud(id1, grauSimilitud);
    }

    /**
     * Eliminar una similitud entre dos productes de la plantilla amb els identificadors donats.
     * @param id1 L'identificador del primer producte.
     * @param id2 L'identificador del segon producte.
     * @throws MyException Si algun dels productes no existeix a la plantilla, o si s'intenta eliminar similitud entre el mateix producte.
     */
    public void eliminarSimilitud(int id1, int id2) throws MyException {
        Producte prod1 = Llista_Productes.get(id1);
        Producte prod2 = Llista_Productes.get(id2);
        if (prod1 == null || prod2 == null) {
            throw new MyException("Algun dels productes no existeix a la plantilla activa");
        }
        if(id1 == id2) {
            throw new MyException("No es pot eliminar similitud entre el mateix producte");
        }
        prod1.eliminarSimilitud(id2);
        prod2.eliminarSimilitud(id1);
    }

    /**
     * Obte la llista de similituds de la plantilla en format de matriu i llista d'identificadors ordenats.
     * @return La llista de similituds de la plantilla en format de matriu i llista d'identificadors ordenats.
     */
    public Map.Entry<double[][], List<Integer>> getLlistaSimilituds() {
        ArrayList<Integer> sortedKeys = new ArrayList<>(Llista_Productes.keySet());
        Collections.sort(sortedKeys);

        int size = sortedKeys.size();
        double[][] similituds = new double[size][size];

        for (int i = 0; i < size; i++) {
            int key1 = sortedKeys.get(i);
            Producte prod1 = Llista_Productes.get(key1);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    similituds[i][j] = 0;
                } else {
                    int key2 = sortedKeys.get(j);
                    similituds[i][j] = prod1.getSimilituds().getOrDefault(key2, 0.0);
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(similituds, sortedKeys);
    }

    /**
     * Obte la llista de similituds de la plantilla en format de matriu.
     * @return La llista de similituds de la plantilla en format de matriu.
     */
    public double[][] imprimirSimilituds() {
        ArrayList<Integer> sortedKeys = new ArrayList<>(Llista_Productes.keySet());
        Collections.sort(sortedKeys);

        int size = sortedKeys.size();
        double[][] similituds = new double[size + 1][size + 1];

        similituds[0][0] = Double.NaN;
        for (int i = 0; i < size; i++) {
            similituds[0][i + 1] = sortedKeys.get(i);
            similituds[i + 1][0] = sortedKeys.get(i);
        }

        for (int i = 0; i < size; i++) {
            int key1 = sortedKeys.get(i);
            Producte prod1 = Llista_Productes.get(key1);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    similituds[i + 1][j + 1] = 0.0;
                } else {
                    int key2 = sortedKeys.get(j);
                    similituds[i + 1][j + 1] = prod1.getSimilituds().getOrDefault(key2, 0.0);
                }
            }
        }

        return similituds;
    }

    /**
     * Importar una llista de categories a la plantilla.
     * @param categories La llista de categories a importar en format de llistes de strings.
     * @throws MyException Si hi ha algun error en la importació de categories.
     */
    public void importarLlistaCategories(List<List<String>> categories) throws MyException {
        for (List<String> categ : categories) {
            if (categ.size() == 1) {
                crearCategoria(categ.get(0));
            } else if (categ.size() == 2 || categ.size() == 3) {
                crearCategoria(categ.get(0), categ.get(1));
            } else {
                throw new MyException("Error en la importació de categories");
            }
        }
    }

    /**
     * Importar una llista de productes a la plantilla.
     * @param productes La llista de productes a importar en format de llistes de strings.
     * @throws MyException Si hi ha algun error en la importació de productes.
     */
    public void importarLlistaProductes(List<List<String>> productes) throws MyException {
        for (List<String> prod : productes) {
            if (prod.size() != 4) {
                throw new MyException("Error en la importació de productes");
            }
            crearProducte(Integer.parseInt(prod.get(0)), prod.get(1), prod.get(2), Double.parseDouble(prod.get(3)));
        }
    }

    /**
     * Importar una llista de prestatgeries a la plantilla.
     * @param prestatgeries La llista de prestatgeries a importar en format de llistes de strings.
     * @throws MyException Si hi ha algun error en la importació de prestatgeries.
     */
    public void importarLlistaPrestatgeries(List<List<String>> prestatgeries) throws MyException {
        for (List<String> prest : prestatgeries) {
            if (prest.size() != 2) {
                throw new MyException("Error en la importació de prestatgeries");
            }
            crearPrestatgeria(Integer.parseInt(prest.get(0)), Integer.parseInt(prest.get(1)));
        }
    }

    /**
     * Importar una llista de similituds a la plantilla.
     * @param similituds La llista de similituds a importar en format de llistes de strings.
     * @throws MyException Si hi ha algun error en la importació de similituds.
     */
    public void importarLlistSimilituds(List<List<String>> similituds) throws MyException {
        for (List<String> sim : similituds) {
            if (sim.size() != 3) {
                throw new MyException("Error en la importació de similituds");
            }
            afegirSimilitud(Integer.parseInt(sim.get(0)), Integer.parseInt(sim.get(1)), Double.parseDouble(sim.get(2)));
        }
    }

}