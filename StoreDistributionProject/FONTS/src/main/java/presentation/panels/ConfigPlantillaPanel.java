package presentation.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;

/**
 * Classe que representa el panell de configuració de la plantilla.
 */
public class ConfigPlantillaPanel extends JPanel {

    /**
     * Controlador de presentació
     */
    private final CtrlPresentacio cP;

    /**
     * Finestra principal de l'aplicació
     */
    private final MyFrame mV;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Camp de text on s'escriu el nom
     */
    private JTextField textField;

    /**
     * Nom de la plantilla per defecte
     */
    private String default_name;

    /**
     * Indica si els components ja s'han creat
     */
    private boolean componentsCreated = false;

    /**
     * Constructora de la classe ConfigPlantillaPanel
     * @param mV Finestra principal de l'aplicació
     * @param cP Controlador de presentació
     */
    public ConfigPlantillaPanel(MyFrame mV, CtrlPresentacio cP) {
        this.cP = cP;
        this.mV = mV;
        this.pUp = new popUp(mV);
        // Configurar el panel y el fondo
        setBackground(new Color(238, 245, 255));
        setLayout(new BorderLayout()); // Usamos BorderLayout para alinear componentes
    }

    /**
     * Crea els components del panell
     */
    public void def() {
        if(componentsCreated) return;

        try {
            default_name = cP.getNomPlantilla();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        JPanel mainPanel = new JPanel(new FlowLayout()); // Usamos FlowLayout para alinear componentes
        mainPanel.setBackground(new Color(238, 245, 255));
        // Crear un campo de texto (cajón) con un texto por defecto
        textField = new JTextField(default_name, 20);
        textField.setForeground(new Color(169, 169, 169)); // Gris oscuro para el texto por defecto

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Si el campo obtiene el foco y está vacío, ponlo en negro
                if (textField.getText().equals(default_name)) {
                    textField.setText("");  // Limpiar texto si es el valor por defecto
                    textField.setForeground(Color.BLACK);  // Cambiar a negro cuando el usuario empieza a escribir
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Si el campo pierde el foco y está vacío, vuelve a mostrar el texto gris
                if (textField.getText().isEmpty()) {
                    textField.setText(default_name); // Restablecer el texto por defecto
                    textField.setForeground(new Color(169, 169, 169)); // Gris oscuro para el texto por defecto
                }
            }
        });
        mainPanel.add(textField);

        // Crear el botón "Modificar"
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(e -> {modificarNombre();});
        modificarButton.setFocusable(false);
        mainPanel.add(modificarButton);
        add(mainPanel, BorderLayout.NORTH);

        // Crear el panel para el botón de eliminar, centrado en el panel
        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setBackground(new Color(238, 245, 255));
        deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Crear el botón "Eliminar Plantilla"
        JButton deleteButton = new JButton("Eliminar Plantilla");
        deleteButton.addActionListener(e -> {eliminarPlantilla();});
        deleteButtonPanel.add(deleteButton);
        deleteButton.setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10)); // Margen alrededor del panel
        // Añadir el panel con el botón de eliminar en la parte inferior
        add(deleteButtonPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
        componentsCreated = true;
    }

    /**
     * Inicia el panell amb el nom de la plantilla
     */
    private void inici(String name) {
        textField.setName(name);
        textField.setForeground(new Color(169, 169, 169));
    }

    /**
     * Modifica el nom de la plantilla
     */
    private void modificarNombre() {
        String oldName = textField.getName();
        try {
            String nuevoNombre = textField.getText();
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                if(nuevoNombre.equals(cP.getNomPlantilla())) throw new MyException("El nou nom de la plantilla no pot ser el mateix que el nom actual");
                cP.setNomPlantilla(nuevoNombre);

                // Mostrar el mensaje de confirmación
                JOptionPane.showMessageDialog(
                        this, // El componente padre
                        "El nom s'ha canviat correctament", // El mensaje
                        "Confirmacio", // El título del popup
                        JOptionPane.INFORMATION_MESSAGE // Tipo de mensaje (información)
                );

                // Cambiar el texto por defecto del campo
                textField.setText(nuevoNombre);
            }
        } catch (MyException ex) {
            textField.setText(oldName);
            pUp.mostrarError(ex);
        }
    }

    /**
     * Elimina la plantilla
     */
    private void eliminarPlantilla() {
        try {
            // Mostrar un cuadro de confirmación antes de eliminar
            int result = pUp.showConfirmationDialog(2);
            // Si el usuario selecciona "Sí"
            if (result == JOptionPane.YES_OPTION) {
                cP.eliminarPlantilla(cP.getNomPlantilla());
                mV.changeInm(false);
                // Aquí puedes implementar la lógica de eliminación
                JOptionPane.showMessageDialog(
                        this,
                        "La plantilla ha sigut eliminada",
                        "Confirmacio",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }
}
