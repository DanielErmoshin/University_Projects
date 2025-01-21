package persistencia;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.MyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquesta classe representa el gestor de productes.
 * Aquest gestor permet gestionar els productes del sistema.
 */
public class GestorProductes extends GestorGeneric{

    /**
     * L'única instància de la classe GestorProductes.
     */
    private static GestorProductes singletonObject;

    /**
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    private final Gson gson;

    /**
     * La ruta de l'arxiu on es guarden els productes.
     */
    private static final String FILE_PATH = "./src/main/java/data/Productes.json";

    /**
     * Aquesta classe representa les dades d'un producte.
     */
    public static class DataProducte {

        /**
         * L'identificador del producte.
         */
        private final int id;

        /**
         * El nom del producte.
         */
        private final String nom;

        /**
         * La categoria del producte.
         */
        private final String categoria;

        /**
         * El preu del producte.
         */
        private final double preu;

        /**
         * Crea un nou objecte DataProducte.
         * @param id L'identificador del producte
         * @param nom El nom del producte
         * @param categoria La categoria del producte
         * @param preu El preu del producte
         */
        public DataProducte(int id, String nom, String categoria, double preu) {
            this.id = id;
            this.nom = nom;
            this.categoria = categoria;
            this.preu = preu;
        }

        /**
         * Obte l'identificador del producte.
         * @return L'identificador del producte
         */
        public int getId() {
            return id;
        }

        /**
         * Obte el nom del producte.
         * @return El nom del producte
         */
        public String getNom() {
            return nom;
        }

        /**
         * Obte la categoria del producte.
         * @return La categoria del producte
         */
        public String getCategoria() {
            return categoria;
        }

        /**
         * Obte el preu del producte.
         * @return El preu del producte
         */
        public double getPreu() {
            return preu;
        }

        /**
         * Converteix les dades del producte en una llista de strings.
         * @return Una llista de strings amb les dades del producte
         */
        public List<String> toList() {
            return List.of(
                    String.valueOf(id),
                    nom,
                    categoria,
                    String.valueOf(preu)
            );
        }
    }

    /**
     * Obte l'única instància de la classe GestorProductes.
     * @return L'única instància de la classe GestorProductes
     * @throws MyException Si no es pot obtenir l'única instància de la classe GestorProductes
     */
    public static GestorProductes getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new GestorProductes();
            comprobarJson(FILE_PATH);
        }
        return singletonObject;
    }

    /**
     * Crea una nova instància de la classe GestorProductes.
     */
    private GestorProductes() {
        gson = new Gson();
    }

    /**
     * Comprova si l'arxiu JSON existeix, si no existeix el crea.
     * @param filePath La ruta de l'arxiu JSON
     * @throws MyException Si no es pot comprovar l'arxiu JSON
     */
    public static void comprobarJson(String filePath) throws MyException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("[]");
                    }
                } else {
                    throw new MyException("Error al crear l'arxiu " + filePath);
                }
            }
        } catch (IOException e) {
            throw new MyException("Error al comprobar l'arxiu " + filePath);
        }
    }

    /**
     * Obte les dades d'un producte.
     * @param id L'identificador del producte
     * @return Les dades del producte
     * @throws MyException Si no es poden obtenir les dades del producte
     */
    public List<String> getProducte(int id) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (product.id == id) {
                    return product.toList();
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        throw new MyException("El producte amb ID " + id + " no existeix en el sistema.");
    }

    /**
     * Obte una llista amb les dades dels productes amb els identificadors donats.
     * @param ids Els identificadors dels productes
     * @return Una llista amb les dades dels productes
     * @throws MyException
     */
    public List<List<String>> getProductes(List<Integer> ids) throws MyException {
        List<List<String>> products = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (ids.contains(product.id)) {
                    products.add(product.toList());
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return products;
    }

    /**
     * Obte una llista amb els identificadors de tots els productes.
     * @return Una llista amb els identificadors de tots els productes
     * @throws MyException Si no es poden obtenir els identificadors de tots els productes
     */
    public List<String> getIdAllProductes() throws MyException {
        List<String> products = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                products.add(Integer.toString(product.getId()));
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return products;
    }

    /**
     * Obté la ruta de l'arxiu de productes.
     *
     * @return La ruta de l'arxiu de productes
     */
    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    /**
     * Afegir un producte al sistema.
     * @param id Identificador del producte
     * @param nom Nom del producte
     * @param categoria Categoria del producte
     * @param preu Preu del producte
     * @throws MyException Si no es pot afegir el producte al sistema
     */
    public void afegirProducte(int id, String nom, String categoria, double preu) throws MyException {
        DataProducte newProduct = new DataProducte(id, nom, categoria, preu);
        if (!GestorCategories.getInstance().existeixCategoria(categoria)) {
            throw new MyException("La categoria " + categoria + " no existeix en el sistema.");
        }
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            // Write existing products
            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (product.getId() == newProduct.getId()) {
                    new File(TEMP_FILE_PATH).delete();
                    throw new MyException("Ja existeix un producte amb ID " + newProduct.getId() + " al sistema.");
                }
                gson.toJson(product, DataProducte.class, writer);
            }

            reader.endArray();

            // Add the new product
            gson.toJson(newProduct, DataProducte.class, writer);
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al afegir el producte amb ID " + newProduct.getId() + " al sistema");
        }

        substituirArxiuOriginal(FILE_PATH);
        GestorCategories.getInstance().producteHaSigutAfegit(newProduct.getCategoria());
    }

    /**
     * Transforma una llista de llistes de strings amb les dades de productes en una llista de productes.
     * @param productList La llista de llistes de strings amb les dades dels productes
     * @return La llista de productes
     * @throws MyException Si no es poden transformar les dades dels productes
     */
    private List<DataProducte> transformListStringToListProductes(List<List<String>> productList) throws MyException {
        List<DataProducte> products = new ArrayList<>();
        for (List<String> productData : productList) {
            if (productData.size() == 4) {
                try {
                    int id = Integer.parseInt(productData.get(0));
                    String nom = productData.get(1);
                    String categoria = productData.get(2);
                    double preu = Double.parseDouble(productData.get(3));
                    products.add(new DataProducte(id, nom, categoria, preu));
                } catch (NumberFormatException e) {
                    throw new MyException("Error al transformar les dades del producte");
                }
            }
        }
        return products;
    }

    /**
     * Afegeix una llista de productes al sistema.
     * @param productList  La llista de llistes de strings amb les dades dels productes
     * @throws MyException Si no es poden afegir els productes al sistema
     */
    public void afegirProductes(List<List<String>> productList) throws MyException {
        List<DataProducte> productsToAdd = transformListStringToListProductes(productList);
        boolean producteAfegit = false;
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                gson.toJson(product, DataProducte.class, writer);
            }

            reader.endArray();

            producteAfegit = productsToAdd.size() != 0;
            for (DataProducte newProduct : productsToAdd) {
                gson.toJson(newProduct, DataProducte.class, writer);
            }

            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al afegir els productes en el sistema");
        }
        try {
            if (producteAfegit) {
                substituirArxiuOriginal(FILE_PATH);
                for (DataProducte newProduct : productsToAdd) {
                    GestorCategories.getInstance().producteHaSigutAfegit(newProduct.getCategoria());
                }
            } else {
                new File(TEMP_FILE_PATH).delete();
            }
        }
        catch (MyException e) {
            throw new MyException("Error al afegir els productes en el sistema");
        }
    }

    /**
     * Elimina un producte del sistema amb un identificador donat.
     * @param id Identificador del producte
     * @throws MyException Si no es pot eliminar el producte del sistema
     */
    public void eliminarProducte(int id) throws MyException {
        boolean productFound = false;
        String productCategoria = null;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (!productFound && product.getId() == id) {
                    productFound = true;
                    productCategoria = product.getCategoria(); // Capture the category of the deleted product
                    // Skip this product
                } else {
                    gson.toJson(product, DataProducte.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al eliminar el producte amb ID " + id + " del sistema");
        }

        if (productFound) {
            substituirArxiuOriginal(FILE_PATH);

            // Notify GestorCategories
            if (productCategoria != null) {
                GestorCategories.getInstance().producteHaSigutEliminat(productCategoria);
            }
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("El producte amb " + id + " no existeix en el sistema.");
        }
    }

    /**
     * Elimina una llista de productes del sistema amb els identificadors donats.
     * @param ids L'identificador dels productes
     * @throws MyException Si no es poden eliminar els productes del sistema
     */
    public void eliminarProductes(List<Integer> ids) throws MyException {
        boolean anyProductDeleted = false;
        List<String> categoriesToNotify = new ArrayList<>();

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (ids.contains(product.getId())) {
                    anyProductDeleted = true;
                    categoriesToNotify.add(product.getCategoria());
                } else {
                    gson.toJson(product, DataProducte.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al eliminar els productes del sistema");
        }

        if (anyProductDeleted) {
            substituirArxiuOriginal(FILE_PATH);

            // Call producteHaSigutEliminat after substituting the original file
            for (String categoria : categoriesToNotify) {
                GestorCategories.getInstance().producteHaSigutEliminat(categoria);
            }
        } else {
            new File(TEMP_FILE_PATH).delete();
        }
    }

    /**
     * Actualitza el nom i el preu d'un producte amb un identificador donat.
     * @param id Identificador del producte
     * @param nouNom Nou nom del producte
     * @param nouPreu Nou preu del producte
     * @throws MyException Si no es poden actualitzar les dades del producte
     */
    public void actualitzarProducte(int id, String nouNom, double nouPreu) throws MyException {
        boolean productFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataProducte product = gson.fromJson(reader, DataProducte.class);
                if (!productFound && product.getId() == id) {
                    DataProducte updatedProduct = new DataProducte(id, nouNom, product.getCategoria(), nouPreu);
                    gson.toJson(updatedProduct, DataProducte.class, writer);
                    productFound = true;
                } else {
                    gson.toJson(product, DataProducte.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al actualitzar el producte amb ID " + id + " del sistema");
        }

        if (productFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("El producte amb ID " + id + " no existeix en el sistema.");
        }
    }

}
