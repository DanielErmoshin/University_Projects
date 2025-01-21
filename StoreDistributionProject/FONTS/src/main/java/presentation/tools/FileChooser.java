package presentation.tools;

import javax.swing.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Classe que representa un explorador de fitxers.
 */
public class FileChooser extends JFileChooser{

    /**
     * Constructora de la classe FileChooser.
     */
    public FileChooser(){ new JFileChooser();};

    /**
     * Funció que obre l'explorador de fitxers i retorna el filepath del fitxer seleccionat.
     * @return Filepath del fitxer seleccionat.
     */
    public String openFileExplorer() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos JSON (*.json)", "json");
        setFileFilter(filter);

        setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = this.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = this.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        else if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return null;
    }

    /**
     * Obre un quadre de diàleg perquè l'usuari guardi un fitxer amb l'extensió adequada
     * @param isPlantilla Booleà que indica si el fitxer és una plantilla
     * @return El path del fitxer seleccionat
     */
    public String openSaveFileDialog(boolean isPlantilla) {
        // Determinar el filtro según el tipo de archivo
        FileNameExtensionFilter filter;
        String extension;
        if (isPlantilla) {
            filter = new FileNameExtensionFilter("Archivos JSON (*.json)", "json");
            extension = ".json";
        } else {
            filter = new FileNameExtensionFilter("Archivos TXT (*.txt)", "txt");
            extension = ".txt";
        }

        // Establecer el filtro en el JFileChooser
        setFileFilter(filter);

        // Establecer que solo se pueden seleccionar archivos para guardar
        setDialogType(JFileChooser.SAVE_DIALOG);

        // Abrir el cuadro de diálogo de selección de archivo
        int result = showSaveDialog(null); // null indica que el cuadro de diálogo no tiene un componente padre

        // Si el usuario selecciona un archivo
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            // Nos aseguramos de que seleccione un archivo de la extensión necesaria.
            if (!filePath.endsWith(extension)) {
                filePath += extension;
            }
            return filePath;
        }
        return null;
    }
}
