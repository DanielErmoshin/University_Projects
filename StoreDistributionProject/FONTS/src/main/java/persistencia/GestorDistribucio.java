package persistencia;

import exceptions.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Aquesta classe representa el gestor de la distribució.
 * Aquest gestor permet exportar la distribució de productes en prestatges a un fitxer.
 */
public class GestorDistribucio {

    /**
     * L'única instància de la classe GestorDistribucio.
     */
    private static GestorDistribucio singletonObject;

    /**
     * Obte l'única instància de la classe GestorDistribucio.
     * @return L'única instància de la classe GestorDistribucio.
     */
    public static GestorDistribucio getInstance() {
        if (singletonObject == null)
            singletonObject = new GestorDistribucio();
        return singletonObject;
    }

    /**
     * Crea una nova instància de la classe GestorDistribucio.
     */
    public GestorDistribucio() {}

    /**
     * Exporta la distribució de productes en prestatges a un fitxer.
     * @param distribucio La distribució de productes en prestatges
     * @param filePath La ruta del fitxer on exportar la distribució
     * @throws MyException Si la distribució o la ruta del fitxer són null
     */
    public void exportarDistribucio(List<List<String>> distribucio, String filePath) throws MyException {
        if (distribucio == null || filePath == null) {
            throw new MyException("Los parámetros distribucio y nomArxiu no pueden ser null");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<String> fila : distribucio) {
                writer.newLine();
                writer.write("+");
                for (String columna : fila) {
                    writer.write("-");
                    for (int j = 0; j < columna.length(); j++) {
                        writer.write("-");
                    }
                    writer.write("-+");
                }
                writer.newLine();

                writer.write("|");
                for (String columna : fila) {
                    writer.write(String.format(" %-2s |", columna));
                }
                writer.newLine();

                writer.write("+");
                for (String columna : fila) {
                    for (int j = 0; j < columna.length(); j++) {
                        writer.write("-");
                    }
                    writer.write("--+");
                }
                writer.newLine();

                writer.newLine();
            }
        } catch (IOException e) {
            throw new MyException("Error al exportar la distribución");
        }
    }
}
