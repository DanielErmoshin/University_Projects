package persistencia;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.MyException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Aquesta classe representa el gestor de les prestatgeries.
 * Aquest gestor permet gestionar les prestatgeries del sistema.
 */
public class GestorPrestatgeries extends GestorGeneric {

    /**
     * L'única instància de la classe GestorPrestatgeries.
     */
    private static GestorPrestatgeries singletonObject;

    /**
     * La ruta de l'arxiu on es guarden les prestatgeries.
     */
    private static final String FILE_PATH = "./src/main/java/data/Prestatgeries.json";

    /**
     * L'objecte Gson utilitzat per a la lectura i escriptura de JSON.
     */
    private final Gson gson;

    /**
     * Aquesta classe representa les dades d'una prestatgeria.
     */
    public static class DataPrestatgeria {

        /**
         * L'identificador de la prestatgeria.
         */
        private final int id;

        /**
         * El número de prestatges de la prestatgeria.
         */
        private final int numPrestatges;

        /**
         * Crea una nova instància de la classe DataPrestatgeria.
         * @param id L'identificador de la prestatgeria
         * @param numPrestatges El número de prestatges de la prestatgeria
         */
        public DataPrestatgeria(int id, int numPrestatges) {
            this.id = id;
            this.numPrestatges = numPrestatges;
        }

        /**
         * Obte l'identificador de la prestatgeria.
         * @return L'identificador de la prestatgeria
         */
        public int getId() {
            return id;
        }

        /**
         * Obte el número de prestatges de la prestatgeria.
         * @return El número de prestatges de la prestatgeria
         */
        public int getNumPrestatges() {
            return numPrestatges;
        }

        /**
         * Converteix les dades de la prestatgeria a una llista de strings.
         * @return Una llista de strings amb les dades de la prestatgeria
         */
        public List<String> toList() {
            return List.of(
                    String.valueOf(id),
                    String.valueOf(numPrestatges)
            );
        }
    }

    /**
     * Crea una nova instància de la classe GestorPrestatgeries.
     */
    private GestorPrestatgeries() {
        gson = new Gson();
    }

    /**
     * Obte l'única instància de la classe GestorPrestatgeries.
     * @return L'única instància de la classe GestorPrestatgeries.
     * @throws MyException Si no es pot obtenir l'única instància de la classe GestorPrestatgeries
     */
    public static GestorPrestatgeries getInstance() throws MyException {
        if (singletonObject == null) {
            singletonObject = new GestorPrestatgeries();
            comprobarJson(FILE_PATH);
        }
        return singletonObject;
    }

    /**
     * Comprova si l'arxiu JSON existeix, i si no, el crea.
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
     * Obte les dades d'una prestatgeria a partir del seu identificador.
     * @param id L'identificador de la prestatgeria
     * @return Una llista de strings amb les dades de la prestatgeria
     * @throws MyException Si no es pot obtenir les dades de la prestatgeria
     */
    public List<String> getPrestatgeria(int id) throws MyException {
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                if (prestatgeria.getId() == id) {
                    return prestatgeria.toList();
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        throw new MyException("La prestatgeria amb ID " + id + " no existeix en el sistema.");
    }

    /**
     * Obte les dades de les prestatgeries a partir dels seus identificadors.
     * @param ids La llista d'identificadors de les prestatgeries
     * @return Una llista de llistes de strings amb les dades de les prestatgeries
     * @throws MyException Si no es poden obtenir les dades de les prestatgeries
     */
    public List<List<String>> getPrestatgeries(List<Integer> ids) throws MyException {
        List<List<String>> prestatgeries = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                if (ids.contains(prestatgeria.getId())) {
                    prestatgeries.add(prestatgeria.toList());
                }
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return prestatgeries;
    }

    /**
     * Obte els identificadors de totes les prestatgeries del sistema.
     * @return Una llista amb els identificadors de totes les prestatgeries
     * @throws MyException Si no es poden obtenir els identificadors de les prestatgeries
     */
    public List<String> getIdAllPrestatgeries() throws MyException {
        List<String> prestatgeries = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            reader.beginArray();
            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                prestatgeries.add(Integer.toString(prestatgeria.getId()));
            }
            reader.endArray();
        } catch (IOException e) {
            throw new MyException("Error al llegir l'arxiu " + FILE_PATH);
        }
        return prestatgeries;
    }

    /** Obté la ruta de l'arxiu on es guarden les prestatgeries.
     * @return La ruta de l'arxiu on es guarden les prestatgeries
     */
    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    /** Afegeix una nova prestatgeria al sistema.
     * @param id L'identificador de la prestatgeria
     * @param numPrestatges El número de prestatges de la prestatgeria
     * @throws MyException Si no es pot afegir la prestatgeria al sistema
     */
    public void afegirPrestatgeria(int id, int numPrestatges) throws MyException {
       DataPrestatgeria newPrestatgeria = new DataPrestatgeria(id, numPrestatges);
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            // Write existing prestatgeries
            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                if (prestatgeria.getId() == newPrestatgeria.getId()) {
                    new File(TEMP_FILE_PATH).delete();
                    throw new MyException("La prestatgeria amb ID " + newPrestatgeria.getId() + " ja existeix en el sistema");
                }
                gson.toJson(prestatgeria,DataPrestatgeria.class, writer);
            }

            reader.endArray();

            // Add the new prestatgeria
            gson.toJson(newPrestatgeria,DataPrestatgeria.class, writer);
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al afegir la prestatgeria amb ID " + newPrestatgeria.getId() + " al sistema");
        }

        substituirArxiuOriginal(FILE_PATH);
    }

    /**
     * Transforma una llista de llistes de strings de les dades de prestatgeries a una llista de prestatgeries.
     * @param prestatgeriesList La llista de llistes de strings de les dades de prestatgeries
     * @return La llista de prestatgeries
     * @throws MyException Si no es poden transformar les llistes de strings a prestatgeries
     */
    public List<DataPrestatgeria> transformListStringToListPrestatgeries(List<List<String>> prestatgeriesList) throws MyException {
        List<DataPrestatgeria> prestatgeries = new ArrayList<>();
        for (List<String> prestatgeriaData : prestatgeriesList) {
            if (prestatgeriaData.size() == 2) {
                try {
                    int id = Integer.parseInt(prestatgeriaData.get(0));
                    int numPrestatges = Integer.parseInt(prestatgeriaData.get(1));
                    prestatgeries.add(new DataPrestatgeria(id, numPrestatges));
                } catch (NumberFormatException e) {
                    throw new MyException("Error al transformar la llista de prestatgeries");
                }
            }
        }
        return prestatgeries;
    }


    /**
     * Afegeix una llista de prestatgeries al sistema a partir d'una llista de llistes de strings amb les dades de les prestatgeries.
     * @param prestatgeriesList La llista de llistes de strings de les dades de prestatgeries
     * @throws MyException Si no es poden afegir les prestatgeries al sistema
     */
    public void afegirPrestatgeries(List<List<String>> prestatgeriesList) throws MyException {
        List<DataPrestatgeria> prestatgeriesToAdd = transformListStringToListPrestatgeries(prestatgeriesList);

        Map<Integer, DataPrestatgeria> prestatgeriesMap = prestatgeriesToAdd.stream()
                .collect(Collectors.toMap(DataPrestatgeria::getId, prestatgeria -> prestatgeria));

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            Set<Integer> existingNames = new HashSet<>();

            while (reader.hasNext()) {
                DataPrestatgeria existingPrestatgeria = gson.fromJson(reader, DataPrestatgeria.class);
                existingNames.add(existingPrestatgeria.getId());

                if (prestatgeriesMap.containsKey(existingPrestatgeria.getId())) {
                    existingPrestatgeria = prestatgeriesMap.get(existingPrestatgeria.getId());
                }

                gson.toJson(existingPrestatgeria, DataPrestatgeria.class, writer);
            }

            reader.endArray();

            for (DataPrestatgeria newPrestatgeria : prestatgeriesToAdd) {
                if (!existingNames.contains(newPrestatgeria.getId())) {
                    gson.toJson(newPrestatgeria, DataPrestatgeria.class, writer);
                } else {
                    throw new MyException("La prestatgeria amb ID " + newPrestatgeria.getId() + " ja existeix en el sistema");
                }
            }

            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al afegir les prestatgeries al sistema");
        }

        substituirArxiuOriginal(FILE_PATH);
    }


    /**
     * Elimina una prestatgeria del sistema a partir del seu identificador.
     * @param id L'identificador de la prestatgeria
     * @throws MyException Si no es pot eliminar la prestatgeria del sistema
     */
    public void eliminarPrestatgeria(int id) throws MyException {
        boolean prestatgeriaFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                if (!prestatgeriaFound && prestatgeria.getId() == id) {
                    prestatgeriaFound = true;
                    // ens saltem aquesta perstatgeria
                } else {
                    gson.toJson(prestatgeria,DataPrestatgeria.class, writer);
                }
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al eliminar la prestatgeria amb ID " + id + " del sistema");
        }

        if (prestatgeriaFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("La prestatgeria amb ID " + id + " no existeix en el sistema.");
        }
    }

    /**
     * Elimina una llista de prestatgeries del sistema a partir d'una llista amb els seus identificadors.
     * @param ids La llista d'identificadors de les prestatgeries
     * @throws MyException Si no es poden eliminar les prestatgeries del sistema
     */
    public void eliminarPrestatgeries(List<Integer> ids) throws MyException {
        boolean anyPrestatgeriaDeleted = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
                DataPrestatgeria prestatgeria = gson.fromJson(reader, DataPrestatgeria.class);
                if (ids.contains(prestatgeria.getId())) {
                    anyPrestatgeriaDeleted = true;

                } else {
                    gson.toJson(prestatgeria, DataPrestatgeria.class, writer);
                }
            }
            reader.endArray();
            writer.endArray();
        } catch (IOException e) {
            throw new MyException("Error al eliminar les prestatgeries amb ID " + ids + " del sistema");
        }

        if (anyPrestatgeriaDeleted) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
        }
    }

    /**
     * Actualitza el número de prestatges d'una prestatgeria.
     * @param id L'identificador de la prestatgeria a actualitzar
     * @param newNumPrestatges El nou número de prestatges de la prestatgeria
     * @throws MyException Si no es pot actualitzar la prestatgeria del sistema
     */
    public void actualizarPrestatgeria(int id, int newNumPrestatges) throws MyException {
        boolean prestatgeriaFound = false;

        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
             JsonWriter writer = new JsonWriter(new FileWriter(TEMP_FILE_PATH))) {

            writer.beginArray();
            reader.beginArray();

            while (reader.hasNext()) {
               DataPrestatgeria prestatgeria = gson.fromJson(reader,DataPrestatgeria.class);
                if (!prestatgeriaFound && prestatgeria.getId() == id) {
                    prestatgeria = new DataPrestatgeria(id, newNumPrestatges); // Update the number of prestatges
                    prestatgeriaFound = true;
                }
                gson.toJson(prestatgeria,DataPrestatgeria.class, writer);
            }

            reader.endArray();
            writer.endArray();

        } catch (IOException e) {
            throw new MyException("Error al modificar la prestatgeria amb ID " + id + " al sistema");
        }

        if (prestatgeriaFound) {
            substituirArxiuOriginal(FILE_PATH);
        } else {
            new File(TEMP_FILE_PATH).delete();
            throw new MyException("La prestatgeria amb ID " + id + " no existeix en el sistema.");
        }
    }

}
