package persistencia;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.MyException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Aquesta classe representa el gestor de les categories.
 * Aquest gestor permet gestionar les categories del sistema.
 */
public class GestorCategories extends GestorGeneric {

    /**
     * L'única instància de la classe GestorCategories.
     */
    private static GestorCategories singletonObject;

    /**
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    private final Gson gson;

    /**
     * La ruta de l'arxiu de les categories del sistema.
     */
    private static final String FILE_PATH = "./src/main/java/data/Categories.json";

    /**
     * Crea una nova instància de la classe GestorCategories.
     */
    private GestorCategories() {
        gson = new Gson();

    }

    /**
     * Obte l'única instància de la classe GestorCategories.
     * @return L'única instància de la classe GestorCategories.
     * @throws MyException Si no es pot obtenir l'única instància de la classe GestorCategories.
     */
    public static GestorCategories getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new GestorCategories();
            comprobarJson(FILE_PATH);
        }
        return singletonObject;
    }

    /**
     * Aquesta classe representa les dades d'una categoria.
     */
    public static class DataCategoria {
        /**
         * El nom de la categoria.
         */
        private final String nom;

        /**
         * La descripció de la categoria.
         */
        private final String descripcio;

        /**
         * El número de productes de la categoria.
         */
        private final int numProducte;

        /**
         * Crea una nova instància de la classe DataCategoria.
         * @param nom El nom de la categoria.
         * @param descripcio La descripció de la categoria.
         * @param numProducte El número de productes de la categoria.
         */
        public DataCategoria(String nom, String descripcio, int numProducte) {
            this.nom = nom;
            this.descripcio = descripcio;
            this.numProducte = numProducte;
        }

        /**
         * Obte el nom de la categoria.
         * @return El nom de la categoria.
         */
        public String getNom() {
            return nom;
        }

        /**
         * Obte la descripció de la categoria.
         * @return La descripció de la categoria.
         */
        public String getDescripcio() {
            return descripcio;
        }

        /**
         * Obte el número de productes de la categoria.
         * @return El número de productes de la categoria.
         */
        public int getNumProducte() {
            return numProducte;
        }

        /**
         * Converteix les dades de la categoria en una llista de strings.
         * @return Les dades de la categoria en una llista de strings.
         */
        public List<String> toList() {
            return List.of(
                    nom,
                    descripcio,
                    String.valueOf(numProducte)
            );
        }
    }

    /**
     * Comprova si l'arxiu JSON existeix i si no, el crea.
     * @param filePath La ruta de l'arxiu JSON.
     * @throws MyException Si no es pot comprovar l'arxiu JSON.
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
     * Obte les dades de la categoria amb nom name.
     * @param name El nom de la categoria.
     * @return Les dades de la categoria amb nom name.
     * @throws MyException Si no es pot obtenir les dades de la categoria amb nom name.
     */
    public List<String> getCategoria(String name) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (categoria.getNom().equals(name)) {
                    return categoria.toList();
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu de categories");
        }
        throw new MyException("La categoria amb nom \"" + name + "\" no existeix en el sistema.");
    }

    /**
     * Obte les categories amb noms names.
     * @param names Els noms de les categories.
     * @return Les categories amb noms names.
     * @throws MyException Si no es poden obtenir les categories amb noms names.
     */
    public List<List<String>> getCategories(List<String> names) throws MyException {
        List<List<String>> categories = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (names.contains(categoria.getNom())) {
                    categories.add(categoria.toList());
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu de categories");
        }
        return categories;
    }

    /**
     * Obte totes les categories del sistema.
     * @return Totes les categories del sistema.
     * @throws MyException Si no es poden obtenir totes les categories del sistema.
     */
    public List<String> getIdAllCategories() throws MyException {
        List<String> categories = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                categories.add(categoria.getNom());
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu de categories");
        }
        return categories;
    }

    /**
     * Obte el número de productes de la categoria amb nom name.
     * @return El número de productes de la categoria amb nom name.
     * @throws MyException Si no es pot obtenir el número de productes de la categoria amb nom name.
     */
    public int getNumProductesCategoria(String name) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (categoria.getNom().equals(name)) {
                    return categoria.getNumProducte();
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu de categories");
        }
        return -1; // Category not found
    }

    /**
     * Obté la ruta de l'arxiu de categories.
     *
     * @return La ruta de l'arxiu de categories.
     */
    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    /**
     * Comprova si existeix una categoria amb nom categoryName.
     * @param categoryName El nom de la categoria.
     * @return Cert si existeix una categoria amb nom categoryName, fals altrament.
     * @throws MyException Si no es pot comprovar si existeix una categoria amb nom categoryName.
     */
    public boolean existeixCategoria(String categoryName) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (categoria.getNom().equals(categoryName)) {
                    return true;
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu de categories");
        }
        return false;
    }

    /**
     * Afegeix una categoria amb nom nom i descripció descripcio al sistema.
     * @param nom El nom de la categoria.
     * @param descripcio La descripció de la categoria.
     * @throws MyException Si no es pot afegir la categoria al sistema.
     */
    public void afegirCategoria(String nom, String descripcio) throws MyException {
        DataCategoria newCategoria = new DataCategoria(nom, descripcio, 0);
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            // Write existing categories
            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (categoria.getNom().equals(newCategoria.getNom())) {
                    new File(TEMP_FILE_PATH).delete();
                    throw new MyException("Ja existeix una categoria amb nom \"" + newCategoria.getNom() + " en el sistema.");
                }
                gson.toJson(categoria, DataCategoria.class, writer);
            }

            reader.endArray();

            // Add the new category
            gson.toJson(newCategoria, DataCategoria.class, writer);
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al afegir la categoria");
        }

        substituirArxiuOriginal(FILE_PATH);
    }

    /**
     * Transforma una llista de llistes de strings en una llista de categories.
     * @param categoriesList La llista de llistes de strings.
     * @return La llista de categories.
     * @throws MyException Si no es poden transformar les dades de la categoria.
     */
    private List<DataCategoria> transformListStringToListCategoria(List<List<String>> categoriesList) throws MyException {
        List<DataCategoria> categories = new ArrayList<>();
        for (List<String> categoryData : categoriesList) {
            if (categoryData.size() == 3) {
                try {
                    String nom = categoryData.get(0);
                    String descripcio = categoryData.get(1);
                    int numProducte = 0;
                    categories.add(new DataCategoria(nom, descripcio, numProducte));
                } catch (NumberFormatException e) {
                    throw new MyException("Error al transformar les dades de la categoria");
                }
            }
        }
        return categories;
    }

    /**
     * Afegeix les categories de la llista categoriesList al sistema.
     * @param categoriesList La llista de categories.
     * @throws MyException Si no es poden afegir les categories al sistema.
     */
    public void afegirCategories(List<List<String>> categoriesList) throws MyException {

        List<DataCategoria> categoriesToAdd = transformListStringToListCategoria(categoriesList);

        Map<String, DataCategoria> categoriesMap = categoriesToAdd.stream()
                .collect(Collectors.toMap(DataCategoria::getNom, categoria -> categoria));

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            Set<String> existingNames = new HashSet<>();

            while (reader.hasNext()) {
                DataCategoria existingCategoria = gson.fromJson(reader, DataCategoria.class);
                existingNames.add(existingCategoria.getNom());

                if (categoriesMap.containsKey(existingCategoria.getNom())) {
                    DataCategoria matchingCategoria = categoriesMap.get(existingCategoria.getNom());
                    existingCategoria = new DataCategoria(
                            existingCategoria.getNom(),
                            matchingCategoria.getDescripcio(),
                            existingCategoria.getNumProducte()
                    );
                }

                gson.toJson(existingCategoria, DataCategoria.class, writer);
            }

            reader.endArray();

            for (DataCategoria newCategoria : categoriesToAdd) {
                if (!existingNames.contains(newCategoria.getNom())) {
                    gson.toJson(newCategoria, DataCategoria.class, writer);
                } else {
                    throw new MyException("Ja existeix una categoria amb nom \"" + newCategoria.getNom() + " en el sistema");
                }
            }

            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al afegir les categories");
        }

        // Replace the original file with the updated one
        substituirArxiuOriginal(FILE_PATH);

    }

    /**
     * Elimina la categoria amb nom categoryName del sistema.
     * @param categoryName El nom de la categoria.
     * @throws MyException Si no es pot eliminar la categoria del sistema.
     */
    public void eliminarCategoria(String categoryName) throws MyException {
        boolean categoryFound = false;
        boolean canDelete = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (!categoryFound && categoria.getNom().equals(categoryName)) {
                    categoryFound = true;
                    if (categoria.getNumProducte() == 0) {
                        canDelete = true;
                    } else {
                        gson.toJson(categoria, DataCategoria.class, writer); // Keep the category in the file
                        throw new MyException("No es pot eliminar la categoria " + categoryName + " del sistema perque te productes associats");
                    }
                } else {
                    gson.toJson(categoria, DataCategoria.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al eliminar la categoria " + categoryName + " del sistema");
        }

        if (categoryFound && canDelete) {
            substituirArxiuOriginal(FILE_PATH);
        } else if (categoryFound) {
            new File(TEMP_FILE_PATH).delete(); // Delete the temporary file if no changes were made
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("No s'ha trobat cap categoria amb nom \"" + categoryName + " en el sistema");
        }
    }

    /**
     * Elimina les categories amb noms "noms" del sistema.
     * @param noms Els noms de les categories.
     * @throws MyException Si no es poden eliminar les categories del sistema.
     */
    public void eliminarCategories(List<String> noms) throws MyException {
        boolean anyCategoryDeleted = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (noms.contains(categoria.getNom())) {
                    anyCategoryDeleted = true;
                    // Skip this category
                } else {
                    gson.toJson(categoria, DataCategoria.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();
        }
        catch (IOException e) {
            throw new MyException("Error al eliminar les categories del sistema");
        }

        if (anyCategoryDeleted) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
        }
    }

    /**
     * Actualitza la descripció de la categoria amb nom categoryName al sistema.
     * @param categoryName El nom de la categoria.
     * @param newDescription La nova descripció de la categoria.
     * @throws MyException Si no es pot actualitzar la categoria al sistema.
     */
    public void actualitzarCategoria(String categoryName, String newDescription) throws MyException {
        boolean categoryFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (!categoryFound && categoria.getNom().equals(categoryName)) {
                    // Update the description only
                    categoria = new DataCategoria(
                            categoria.getNom(),
                            newDescription,
                            categoria.getNumProducte()
                    );
                    categoryFound = true;
                }
                gson.toJson(categoria, DataCategoria.class, writer);
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al actualitzar la categoria " + categoryName + " en el sistema");
        }

        if (categoryFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("No s'ha trobat cap categoria amb nom " + categoryName + " en el sistema");
        }
    }

    /**
     * Actualitza el comptador de productes de la categoria amb nom categoryName al sistema.
     * @param categoryName El nom de la categoria.
     * @throws MyException Si no es pot actualitzar el comptador de productes de la categoria al sistema.
     */
    public void producteHaSigutAfegit(String categoryName) throws MyException {
        actualitzarComptadorCategories(categoryName, 1);
    }

    /**
     * Actualitza el comptador de productes de la categoria amb nom categoryName al sistema.
     * @param categoryName El nom de la categoria.
     * @throws MyException Si no es pot actualitzar el comptador de productes de la categoria al sistema.
     */
    public void producteHaSigutEliminat(String categoryName) throws MyException {
        actualitzarComptadorCategories(categoryName, -1);
    }

    /**
     * Actualitza el comptador de productes de la categoria amb nom categoryName al sistema.
     * @param categoryName El nom de la categoria.
     * @param change El canvi en el comptador de productes.
     * @throws MyException Si no es pot actualitzar el comptador de productes de la categoria al sistema.
     */
    private void actualitzarComptadorCategories(String categoryName, int change) throws MyException {
        boolean categoryFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataCategoria categoria = gson.fromJson(reader, DataCategoria.class);
                if (!categoryFound && categoria.getNom().equals(categoryName)) {
                    // Update the product count and write the updated category
                    categoria = new DataCategoria(
                            categoria.getNom(),
                            categoria.getDescripcio(),
                            Math.max(categoria.getNumProducte() + change, 0) // Prevent negative product counts
                    );
                    categoryFound = true;
                }
                gson.toJson(categoria, DataCategoria.class, writer);
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al actualitzar el comptador de productes de la categoria " + categoryName + " en el sistema");
        }

        if (categoryFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("No s'ha trobat cap categoria amb nom " + categoryName + " en el sistema");
        }
    }

}
