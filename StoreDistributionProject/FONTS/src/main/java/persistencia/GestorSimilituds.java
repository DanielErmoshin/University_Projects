package persistencia;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.MyException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Aquesta classe representa el gestor de les similituds entre productes.
 * Aquest gestor permet gestionar les similituds entre productes.
 */
public class GestorSimilituds extends GestorGeneric {

    /**
     * L'única instància de la classe GestorSimilituds.
     */
    private static GestorSimilituds singletonObject;

    /**
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    private final Gson gson;

    /**
     * La ruta de l'arxiu de les similituds.
     */
    private static final String FILE_PATH = "./src/main/java/data/Similituds.json";

    /**
     * Aquesta classe representa les dades d'una similitud.
     */
    public static class DataSimilitud {

        /**
         * El identificador del primer producte.
         */
        private final int id1;

        /**
         * El identificador del segon producte.
         */
        private final int id2;

        /**
         * El valor de la similitud entre els dos productes.
         */
        private final double valorSimilitud;

        /**
         * Crea una nova instància de la classe DataSimilitud.
         * @param id1 L'identificador del primer producte
         * @param id2 L'identificador del segon producte
         * @param valorSimilitud El valor de la similitud entre els dos productes
         */
        public DataSimilitud(int id1, int id2, double valorSimilitud) {
            this.id1 = Math.min(id1, id2);
            this.id2 = Math.max(id1, id2);
            this.valorSimilitud = valorSimilitud;
        }

        /**
         * Obte l'identificador del primer producte.
         * @return L'identificador del primer producte
         */
        public int getId1() {
            return id1;
        }

        /**
         * Obte l'identificador del segon producte.
         * @return L'identificador del segon producte
         */
        public int getId2() {
            return id2;
        }

        /**
         * Obte el valor de la similitud entre els dos productes.
         * @return El valor de la similitud entre els dos productes
         */
        public double getValorSimilitud() {
            return valorSimilitud;
        }
    }

    /**
     * Obte l'única instància de la classe GestorSimilituds.
     * @return L'única instància de la classe GestorSimilituds
     * @throws MyException Si no s'ha pogut obtenir l'única instància de la classe GestorSimilituds
     */
    public static GestorSimilituds getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new GestorSimilituds();
            comprobarJson(FILE_PATH);
        }
        return singletonObject;
    }

    /**
     * Crea una nova instància de la classe GestorSimilituds.
     */
    private GestorSimilituds() {
        gson = new Gson();
    }

    /**
     * Comprova si l'arxiu de les similituds existeix, i si no, el crea.
     * @param filePath La ruta de l'arxiu de les similituds
     * @throws MyException Si no s'ha pogut comprovar l'arxiu de les similituds
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
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    /**
     * Obte la similitud entre dos productes donats els seus identificadors.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @return La similitud entre els dos productes
     * @throws MyException Si no s'ha pogut obtenir la similitud entre els dos productes
     */
    public Double getSimilitud(int id1, int id2) throws MyException {
        int smaller = Math.min(id1, id2);
        int larger = Math.max(id1, id2);

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (similitud.getId1() == smaller && similitud.getId2() == larger) {
                    return similitud.getValorSimilitud(); // Return the similarity value
                }
            }

            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        throw new MyException("Similitud amb IDs (" + smaller + ", " + larger + ") no trobada en el sistema");
    }

    /**
     * Obte totes les similituds d'un producte nou amb una llista de productes donats els seus identificadors.
     * @return Totes les similituds de productes amb el nou producte
     * @throws MyException Si no s'han pogut obtenir totes les similituds de productes amb el nou producte
     */
    public List<List<String>> getSimilitudsDeProducte(List<Integer> ids, Integer idNouProducte) throws MyException {
        List<List<String>> similituds = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (ids.contains(similitud.getId1()) && similitud.getId2() == idNouProducte ||
                        ids.contains(similitud.getId2()) && similitud.getId1() == idNouProducte) {
                    similituds.add(List.of(
                            String.valueOf(similitud.getId1()),
                            String.valueOf(similitud.getId2()),
                            String.valueOf(similitud.getValorSimilitud())
                    ));
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        return similituds;
    }

    /**
     * Obte totes les similituds d'una llista de productes donats els seus identificadors.
     * @return Totes les similituds de productes
     * @throws MyException Si no s'han pogut obtenir totes les similituds de productes
     */
    public List<List<String>> getSimilitudDeLlistaProductes(List<Integer> ids) throws MyException {
        List<List<String>> similituds = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (ids.contains(similitud.getId1()) && ids.contains(similitud.getId2())) {
                    similituds.add(List.of(
                            String.valueOf(similitud.getId1()),
                            String.valueOf(similitud.getId2()),
                            String.valueOf(similitud.getValorSimilitud())
                    ));
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        return similituds;
    }

    /**
     * Afegeix les similituds entre productes al sistema donada una matriu de similituds i una llista d'identificadors de productes.
     * Per a cada similitud en la posicion i, j de la matriu, s'afegeix la similitud entre els productes amb identificadors indexProductByPosition[i] i indexProductByPosition[j].
     * @param similituds La matriu de similituds
     * @param indexProductByPosition La llista d'identificadors de productes
     * @throws MyException Si no s'han pogut afegir les similituds entre productes al sistema
     */
    public void afegirSimilituds(double[][] similituds, List<Integer> indexProductByPosition) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            // Write existing similituds
            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                gson.toJson(similitud, DataSimilitud.class, writer);
            }

            reader.endArray();

            int size = similituds.length;
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    double valorSimilitud = similituds[i][j];
                    DataSimilitud newSimilitud = new DataSimilitud(indexProductByPosition.get(i), indexProductByPosition.get(j), valorSimilitud);
                    gson.toJson(newSimilitud, DataSimilitud.class, writer);
                }
            }

            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        substituirArxiuOriginal(FILE_PATH);
    }

    /**
     * S'elimina la similitud entre dos productes donats els seus identificadors.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @throws MyException Si no s'ha pogut eliminar la similitud entre els dos productes
     */
    public void eliminarSimilitud(int id1, int id2) throws MyException {
        int smaller = Math.min(id1, id2);
        int larger = Math.max(id1, id2);

        boolean similitudFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (similitud.getId1() == smaller && similitud.getId2() == larger) {
                    similitudFound = true;
                    // Skip this entry
                } else {
                    gson.toJson(similitud, DataSimilitud.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }

        if (similitudFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("Similitud amb IDs (" + smaller + ", " + larger + ") no trobada en el sistema");
        }
    }

    /**
     * Elimina les similituds d'una llista de productes donats els seus identificadors.
     * @param ids La llista d'identificadors dels productes
     * @throws MyException Si no s'han pogut eliminar les similituds entre productes
     */
    public void eliminarSimilituds(List<Integer> ids) throws MyException {

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (!ids.contains(similitud.getId1()) || !ids.contains(similitud.getId2())) {
                    gson.toJson(similitud, DataSimilitud.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
    }

    /**
     * Actualitza la similitud entre dos productes donats els seus identificadors.
     * @param id1 L'identificador del primer producte
     * @param id2 L'identificador del segon producte
     * @param nouValorSimilitud El nou valor de la similitud entre els dos productes
     * @throws MyException Si no s'ha pogut actualitzar la similitud entre els dos productes
     */
    public void actualitzarSimilitud(int id1, int id2, double nouValorSimilitud) throws MyException {
        int smaller = Math.min(id1, id2);
        int larger = Math.max(id1, id2);

        boolean similitudFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataSimilitud similitud = gson.fromJson(reader, DataSimilitud.class);
                if (similitud.getId1() == smaller && similitud.getId2() == larger) {
                    similitudFound = true;
                    // Update the value
                    DataSimilitud updatedSimilitud = new DataSimilitud(smaller, larger, nouValorSimilitud);
                    gson.toJson(updatedSimilitud, DataSimilitud.class, writer);
                } else {
                    gson.toJson(similitud, DataSimilitud.class, writer);
                }
            }

            reader.endArray();
            if (!similitudFound) {
                gson.toJson(new DataSimilitud(smaller, larger, nouValorSimilitud), DataSimilitud.class, writer);
            }
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        substituirArxiuOriginal(FILE_PATH);
    }
}
