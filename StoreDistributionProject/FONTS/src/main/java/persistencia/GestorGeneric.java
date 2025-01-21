package persistencia;

import exceptions.MyException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe abstracta que proporciona funcionalitats genèriques de gestió d'arxius.
 */
public abstract class GestorGeneric {

    /**
     * Ruta a l'arxiu temporal utilitzat per a operacions intermèdies.
     */
    protected static final String TEMP_FILE_PATH = "./src/main/java/data/temp.json";

    /**
     * Retorna la ruta de l'arxiu específic gestionat per la subclasse.
     *
     * @return la ruta de l'arxiu com a String
     */
    protected abstract String getFilePath();

    /**
     * Neteja les dades de l'arxiu escrivint un array JSON buit.
     *
     * @throws MyException si es produeix un error d'E/S en netejar les dades
     */
    public void clearDades() throws MyException {
        try (FileWriter writer = new FileWriter(getFilePath())) {
            writer.write("[]");
        } catch (IOException e) {
            throw new MyException("Error al netejar les dades");
        }
    }

    /**
     * Substitueix l'arxiu original per l'arxiu temporal.
     *
     * @param filePath la ruta de l'arxiu original a substituir
     * @throws MyException si es produeix un error en substituir l'arxiu
     */
    protected void substituirArxiuOriginal(String filePath) throws MyException {
        File originalFile = new File(filePath);
        File tempFile = new File(TEMP_FILE_PATH);
        try{
            originalFile.delete();
            tempFile.renameTo(originalFile);
        } catch (Exception e) {
            throw new MyException("Error al substituir l'arxiu original");
        }
    }

}