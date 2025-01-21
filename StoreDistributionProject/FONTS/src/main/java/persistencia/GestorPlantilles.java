package persistencia;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.MyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aquesta classe representa el gestor de les plantilles.
 * Aquest gestor permet gestionar les plantilles de productes i prestatges.
 */
public class GestorPlantilles extends GestorGeneric {

    /**
     * L'única instància de la classe GestorPlantilles.
     */
    private static GestorPlantilles singletonObject;

    /**
     * La ruta de l'arxiu de plantilles.
     */
    private static final String FILE_PATH = "./src/main/java/data/Plantillas.json";

    /**
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    private final Gson gson;

    /**
     * Crea una nova instància de la classe GestorPlantilles.
     */
    private GestorPlantilles() {
        gson = new Gson();
    }

    /**
     * Obte l'única instància de la classe GestorPlantilles.
     * @return L'única instància de la classe GestorPlantilles.
     * @throws MyException si es produeix un error al llegir l'arxiu de plantilles
     */
    public static GestorPlantilles getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new GestorPlantilles();
            comprobarJson(FILE_PATH);
        }
        return singletonObject;
    }

    /**
     * Aquesta classe representa les dades d'una plantilla.
     */
    public static class DataPlantilla {

        /**
         * El nom de la plantilla.
         */
        private final String nom;

        /**
         * Els identificadors dels productes de la plantilla.
         */
        private final List<Integer> id_prod;

        /**
         * Els identificadors de les categories dels productes de la plantilla.
         */
        private final List<String> name_cat;

        /**
         * Els identificadors de les prestatgeries de la plantilla.
         */
        private final List<Integer> id_prest;

        /**
         * Crea una nova instància de la classe DataPlantilla.
         * @param nom El nom de la plantilla
         * @param id_prod Els identificadors dels productes de la plantilla
         * @param name_cat Els identificadors de les categories dels productes de la plantilla
         * @param id_prest Els identificadors de les prestatgeries de la plantilla
         */
        public DataPlantilla(String nom, List<Integer> id_prod, List<String> name_cat, List<Integer> id_prest) {
            this.nom = nom;
            this.id_prod = id_prod;
            this.name_cat = name_cat;
            this.id_prest = id_prest;
        }

        /**
         * Obte el nom de la plantilla.
         * @return El nom de la plantilla
         */
        public String getNom() {
            return nom;
        }

        /**
         * Obte els identificadors dels productes de la plantilla.
         * @return Els identificadors dels productes de la plantilla
         */
        public List<Integer> getIdProd() {
            return id_prod;
        }

        /**
         * Obte els identificadors de les categories dels productes de la plantilla.
         * @return Els identificadors de les categories dels productes de la plantilla
         */
        public List<String> getNameCat() {
            return name_cat;
        }

        /**
         * Obte els identificadors de les prestatgeries de la plantilla.
         * @return Els identificadors de les prestatgeries de la plantilla
         */
        public List<Integer> getIdPrest() {
            return id_prest;
        }

        /**
         * Converteix les dades de la plantilla en un Map.
         * @return Les dades de la plantilla com a Map
         */
        public Map<String, Object> toMap() {
            return Map.of(
                    "nom", nom,
                    "id_prod", id_prod,
                    "name_cat", name_cat,
                    "id_prest", id_prest
            );
        }
    }

    /**
     * Comprova si l'arxiu JSON existeix i el crea si no.
     * @param filePath La ruta de l'arxiu JSON
     * @throws MyException si es produeix un error al comprovar l'arxiu
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
     * Obte les dades de la plantilla amb el nom especificat.
     * @param name El nom de la plantilla
     * @return Les dades de la plantilla com a Map
     * @throws MyException si la plantilla no existeix o es produeix un error al llegir l'arxiu
     */
    public Map<String, Object> getPlantilla(String name) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                if (plantilla.getNom().equals(name)) {
                    return plantilla.toMap();
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        throw new MyException("La plantilla amb nom \"" + name + "\" no existeix en el sistema.");
    }

    /**
     * Obte el nom de totes les plantilles.
     * @return El nom de totes les plantilles
     * @throws MyException si es produeix un error al llegir l'arxiu
     */
    public List<String> getNomAllPlantilles() throws MyException {
        List<String> plantilles = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                plantilles.add(plantilla.getNom());
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return plantilles;
    }

    /**
     * Modificar el nom d'una plantilla en el sistema
     * @param nom El nom de la plantilla a modificar
     * @param nouNom El nou nom de la plantilla
     * @throws MyException si la plantilla amb el nou nom ja existeix o si es produeix un error al llegir l'arxiu
     */
    public void setNomPlantilla(String nom, String nouNom) throws MyException {
        boolean plantillaFound = false;
        if (existeixPlantilla(nouNom)) {
            throw new MyException("La plantilla amb el nom " + nouNom + " ja existeix en el sistema.");
        }
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);

                if (!plantillaFound && plantilla.getNom().equals(nom)) {
                    plantilla = new DataPlantilla(nouNom, plantilla.getIdProd(), plantilla.getNameCat(), plantilla.getIdPrest());
                    plantillaFound = true;
                }

                gson.toJson(plantilla, DataPlantilla.class, writer);
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        if (plantillaFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("La plantilla amb el nom " + nom + " no existeix en el sistema.");
        }
    }

    /**
     * Obté la ruta de l'arxiu JSON de plantilles.
     *
     * @return La ruta de l'arxiu JSON de plantilles
     */
    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    /**
     * Comprova si una plantilla amb el nom especificat existeix en el sistema.
     * @param name El nom de la plantilla
     * @return Cert si la plantilla existeix en el sistema
     * @throws MyException si es produeix un error al llegir l'arxiu
     */
    public boolean existeixPlantilla(String name) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                if (plantilla.getNom().equals(name)) {
                    return true;
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return false;
    }

    /**
     *  Afegeix una plantilla al sistema.
     * @param nom El nom de la plantilla
     * @param productes Els identificadors dels productes de la plantilla
     * @param categories Els identificadors de les categories de la plantilla
     * @param prestatgeries Els identificadors de les prestatgeries de la plantilla
     * @throws MyException
     */
    public void afegirPlantilla(String nom, List<Integer> productes, List<String> categories, List<Integer> prestatgeries) throws MyException {

        DataPlantilla newPlantilla = new DataPlantilla(nom, productes, categories, prestatgeries);

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                if (plantilla.getNom().equals(newPlantilla.getNom())) {
                    new File(TEMP_FILE_PATH).delete();
                    throw new MyException("La plantilla amb el nom " + newPlantilla.getNom() + " ja existeix en el sistema.");
                }
                gson.toJson(plantilla, DataPlantilla.class, writer);
            }

            reader.endArray();

            gson.toJson(newPlantilla, DataPlantilla.class, writer);
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        substituirArxiuOriginal(FILE_PATH);
    }

    /**
     * Elimina una plantilla del sistema.
     * @param plantillaName El nom de la plantilla
     * @throws MyException si la plantilla no existeix o es produeix un error al llegir l'arxiu
     */
    public void eliminarPlantilla(String plantillaName) throws MyException {

        boolean plantillaFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                if (!plantillaFound && plantilla.getNom().equals(plantillaName)) {
                    plantillaFound = true;
                } else {
                    gson.toJson(plantilla, DataPlantilla.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        if (plantillaFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("La plantilla amb el nom " + plantillaName + " no existeix en el sistema.");
        }
    }

    /**
     * Actualitza les dades d'una plantilla del sistema amb les noves dades especificades.
     * @param plantillaName El nom de la plantilla a actualitzar
     * @param newIdProd Els identificadors dels productes de la plantilla
     * @param newNameCat Els identificadors de les categories dels productes de la plantilla
     * @param newIdPrest Els identificadors de les prestatgeries de la plantilla
     * @throws MyException si la plantilla no existeix o es produeix un error al llegir l'arxiu
     */
    public void actualitzarPlantilla(String plantillaName, List<Integer> newIdProd, List<String> newNameCat, List<Integer> newIdPrest) throws MyException {

        boolean plantillaFound = false;
        DataPlantilla newPlantilla = new DataPlantilla(
                plantillaName,
                newIdProd,
                newNameCat,
                newIdPrest
        );
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataPlantilla plantilla = gson.fromJson(reader, DataPlantilla.class);
                if (!plantillaFound && plantilla.getNom().equals(plantillaName)) {

                    plantillaFound = true;
                    gson.toJson(newPlantilla, DataPlantilla.class, writer);
                }
                else gson.toJson(plantilla, DataPlantilla.class, writer);
            }

            reader.endArray();

            if (!plantillaFound) {
                gson.toJson(newPlantilla, DataPlantilla.class, writer); //si no troba la plantilla, la afegeix
            }

            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        substituirArxiuOriginal(FILE_PATH);
    }

    /**
     * Exporta una plantilla a un arxiu JSON.
     * @param contents El contingut de la plantilla
     * @param filepath La ruta de l'arxiu JSON on exportar la plantilla
     * @throws MyException si es produeix un error al exportar la plantilla
     */
    public void exportarPlantilla(String contents, String filepath) throws MyException {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(contents);
        } catch (IOException e) {
            throw new MyException("Error al exportar la plantilla a " + filepath);
        }
    }

    /**
     * Importa una plantilla des d'un arxiu JSON.
     * @param filepath La ruta de l'arxiu JSON de la plantilla
     * @return El contingut de la plantilla
     * @throws MyException si no es troba l'arxiu o es produeix un error en importar la plantilla
     */
    public String importarPlantilla(String filepath) throws MyException {
        try {
            return Files.readString(Paths.get(filepath));
        } catch (FileNotFoundException e) {
            throw new MyException("No s'ha trobat cap arxiu a la ruta: " + filepath);
        } catch (IOException e) {
            throw new MyException("Error al importar la plantilla de " + filepath);
        }
    }
}