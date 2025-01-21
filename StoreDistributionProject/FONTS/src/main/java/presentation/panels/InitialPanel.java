package presentation.panels;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;
import presentation.tools.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe que representa el panell inicial de l'aplicació.
 */
public class InitialPanel extends JPanel {

    /**
     * Finestra principal de l'aplicació
     */
    private MyFrame mV;

    /**
     * Controlador de presentació
     */
    private CtrlPresentacio cP;

    /**
     * Panell principal que conté tots els components relacionats amb els botons.
     */
    private JPanel buttonPanel;

    /**
     * Panell secundari que conté els botons per crear, carregar i importar plantilles.
     */
    private JPanel threeButtonsPanel;

    /**
     * Etiqueta que indica l'opció actual a l'usuari.
     */
    private JLabel menuLabel;

    /**
     * Panell que conté els botons per finalitzar l'aplicació o restablir les dades.
     */
    private JPanel finalitzarSubPanel;

    /**
     * PopUp de la finestra
     */
    private popUp pUp;

    /**
     * Constructora de la classe InitialPanel
     * @param mV Finestra principal de l'aplicació
     * @param cP Controlador de presentació
     */
    public InitialPanel(MyFrame mV, CtrlPresentacio cP) {
        this.mV = mV;
        this.cP = cP;
        this.pUp = new popUp(mV);
        // Crear un panel para los botones
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(134, 182, 246));
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear un subpanel para los dos primeros botones
        threeButtonsPanel = new JPanel();
        threeButtonsPanel.setBackground(new Color(134, 182, 246));
        threeButtonsPanel.setLayout(new GridLayout(1, 2, 20, 10)); // Dos botones alineados horizontalmente

        // Crear label para el menú y agregarlo al panel principal de botones
        menuLabel = new JLabel("Que li agradaria fer avui?");
        menuLabel.setHorizontalAlignment(JLabel.CENTER);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Margen superior e inferior para separarlo de los botones

        // Crear botones
        JButton crear = new JButton("Crear Plantilla");
        JButton carregar = new JButton("Carregar Plantilla Existent");
        JButton importar = new JButton("Importar Plantilla Nova");
        crear.setBackground(new Color(180, 212, 255));
        carregar.setBackground(new Color(180, 212, 255));
        importar.setBackground(new Color(180, 212, 255));
        crear.setFocusable(false);
        carregar.setFocusable(false);
        importar.setFocusable(false);

        //Agregar acciones
        crear.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog(mV.getFrame(), "Nom plantilla:");

                if (input == null) return;

                if (!input.trim().isEmpty()) {
                    boolean errorOcurred = false;  // Bandera para indicar si ocurrió un error
                    cP.crearPlantilla(input);
                    mV.addPlantilla();
                    if (!errorOcurred) {  // Solo continuar si no hubo un error
                        mV.FRAME_NAME = cP.getNomPlantilla();
                        mV.changeInm(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(mV.getFrame(),
                            "Ingresi un nom per la plantilla",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (MyException ex) {
                pUp.mostrarError(ex);
            }
        });
        carregar.addActionListener(e -> {
        mostrarPopupLista();
        });
        importar.addActionListener(e-> {
            try {
                String filepath = new FileChooser().openFileExplorer();
                if (filepath != null) {
                    cP.importarPlantilla(filepath);
                    mV.addPlantilla();
                    mV.changeInm(true);
                }
            } catch (MyException ex) {
                pUp.mostrarError(ex);
            }
        });

        // Crear el botón "Finalitzar"
        JButton finalitzar = new JButton("Finalitzar");
        finalitzar.setBackground(new Color(180, 212, 255));
        finalitzar.setFocusable(false);
        finalitzar.setPreferredSize(new Dimension(finalitzar.getPreferredSize().width, 80));
        // Configurar acción para el botón "Finalitzar"
        finalitzar.addActionListener(e -> System.exit(0));

        //Crear boton "Reset"
        JButton reset = new JButton("Reset");
        reset.setBackground(new Color(180, 212, 255));
        reset.setFocusable(false);
        reset.setForeground(Color.RED);
        reset.setPreferredSize(new Dimension(reset.getPreferredSize().width, 80));

        // Configurar acción para el botón "RESET"
        reset.addActionListener(e -> {
            // Mensaje de advertencia en un cuadro de confirmación
            int result = pUp.showConfirmationDialog(1);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    cP.eliminarDades();
                } catch (MyException ex) {
                    pUp.mostrarError(ex);
                }
            }
        });

        // Inicializar el subpanel finalitzarSubPanel
        finalitzarSubPanel = new JPanel();
        finalitzarSubPanel.setLayout(new GridLayout(1, 1, 20, 0));
        finalitzarSubPanel.setBackground(new Color(134, 182, 246));
        finalitzarSubPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Agregar los botones a los subpaneles
        threeButtonsPanel.add(crear);
        threeButtonsPanel.add(carregar);
        threeButtonsPanel.add(importar);
        finalitzarSubPanel.add(reset);
        finalitzarSubPanel.add(finalitzar);

        buttonPanel.add(menuLabel, BorderLayout.NORTH);
        buttonPanel.add(threeButtonsPanel, BorderLayout.CENTER);
        buttonPanel.add(finalitzarSubPanel, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Mostra un popup amb una llista de plantilles per a que l'usuari pugui seleccionar-ne una.
     */
    private void mostrarPopupLista() {
        try {
            List<String> plantillas = cP.getNomAllPlantilles();

            JList<String> listaPlantillas = new JList<>(plantillas.toArray(new String[0]));

            // Hacer que la lista sea desplazable si tiene más elementos de los que caben
            JScrollPane scrollPane = new JScrollPane(listaPlantillas);
            scrollPane.setPreferredSize(new Dimension(200, 100));  // Definir el tamaño del área visible

            final String[] selectedValue = new String[1];
            // Añadir ListSelectionListener para saber qué elemento fue seleccionado
            listaPlantillas.addListSelectionListener(e -> {
                // Comprobar que la selección es válida y no es una selección múltiple
                if (!e.getValueIsAdjusting()) {
                    selectedValue[0] = listaPlantillas.getSelectedValue();
                }
            });

            // Mostrar el popup con la lista y el scroll
            int option = JOptionPane.showOptionDialog(
                    this,                  // Componente padre
                    scrollPane,            // El contenido que va dentro del popup (en este caso, el JScrollPane con la lista)
                    "Selecciona un Element",  // Título del popup
                    JOptionPane.DEFAULT_OPTION,  // Tipo de opción (sin botones estándar)
                    JOptionPane.PLAIN_MESSAGE,  // Tipo de mensaje (sin íconos)
                    null,                   // Icono opcional
                    new Object[]{"Aceptar", "Cancelar", "Eliminar"},  // Los botones personalizados
                    "Aceptar"              // Valor inicial (botón predeterminado)
            );

            if (option == 0 && selectedValue[0] != null) {
                cP.cargarPlantilla(selectedValue[0]);
                mV.addPlantilla();
                mV.changeInm(true);
            } else if (option == 2 && selectedValue[0] != null) {
                cP.eliminarPlantilla(selectedValue[0]);
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }
}
