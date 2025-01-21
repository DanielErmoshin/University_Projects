package presentation.panels;

import javax.swing.*;
import java.awt.*;
import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;
import presentation.tools.FileChooser;
import java.util.List;

/**
 * Classe que representa el panell per mostrar la distribució.
 */
public class ShowDistribPanel extends JPanel {
    /**
     * Llista de productes de la distribució
     */
    private List<List<String>> products_List;

    /**
     * Indica si la distribució és aproximada o de força bruta
     */
    private final boolean aprox;

    /**
     * Indica si la distribució es mostra per ID o per nom
     */
    private boolean ID;

    /**
     * Controlador de presentació
     */
    private final CtrlPresentacio cP;

    /**
     * ScrollPane principal
     */
    private JScrollPane mainScrollPane;

    /**
     * Finestra principal de l'aplicació
     */
    private final MyFrame mV;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Constructora de la classe ShowDistribPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     * @param aprox Indica si la distribució és aproximada o de força bruta
     */
    public ShowDistribPanel(CtrlPresentacio cP, MyFrame mV, boolean aprox) {
        this.cP = cP;
        this.mV = mV;
        this.aprox = aprox;
        this.ID = true;
        this.pUp = new popUp(mV);
        setBackground(new Color(134, 182, 246));
    }


    /**
     * Inicialitza el panell
     */
    public void initializePanel() {
        setLayout(new BorderLayout());
        // Initialize products_List based on aprox value
        try {
            products_List = aprox ? cP.imprimirDistribucioAproximadaPerID() : cP.imprimirDistribucioBrutaPerID();
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }

        // Add components to the panel
        add(createTitlePanel(), BorderLayout.NORTH);

        mainScrollPane = createMainPanel();
        add(mainScrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Crea el panell amb el títol
     * @return Panell amb el títol
     */
    private JPanel createTitlePanel() {
        JLabel titleLabel = new JLabel("Distribucio actual");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JRadioButton radioNom = new JRadioButton("Noms");
        JRadioButton radioID = new JRadioButton("IDs");

        radioID.setSelected(true);
        radioNom.setSelected(false);
        radioID.setBackground(new Color(134, 182, 246));
        radioNom.setBackground(new Color(134, 182, 246));

        radioID.addActionListener(e -> {
            try {
                if (!ID) {
                    if (aprox) products_List = cP.imprimirDistribucioAproximadaPerID();
                    else products_List = cP.imprimirDistribucioBrutaPerID();
                    ID = true;
                    radioID.setSelected(true);
                    radioNom.setSelected(false);

                    // Actualiza el contenido del panel central
                    mainScrollPane.setViewportView(createShelfPanel());
                    revalidate();
                    repaint();
                }
            } catch (MyException ex) {
                pUp.mostrarError(ex);
            }
        });
        radioNom.addActionListener(e -> {
            try {
                if (ID) {
                    if (aprox) products_List = cP.imprimirDistribucioAproximadaPerNom();
                    else products_List = cP.imprimirDistribucioBrutaPerNom();
                    ID = false;
                    radioID.setSelected(false);
                    radioNom.setSelected(true);

                    // Actualiza el contenido del panel central
                    mainScrollPane.setViewportView(createShelfPanel());
                    revalidate();
                    repaint();
                }
            } catch (MyException ex) {
                pUp.mostrarError(ex);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(radioID);
        group.add(radioNom);

        // Panel principal con GridBagLayout
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(new Color(134, 182, 246));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre los componentes

        // Añadir el título
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Expandir horizontalmente
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        titlePanel.add(titleLabel, gbc);

        // Panel de botones de radio
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        radioPanel.setBackground(new Color(134, 182, 246));
        radioPanel.add(radioID);
        radioPanel.add(radioNom);

        // Añadir los botones de radio
        gbc.gridx = 1; // Colocarlos en la misma fila, pero a la derecha
        gbc.gridy = 0;
        gbc.weightx = 0; // No expandir horizontalmente
        gbc.anchor = GridBagConstraints.EAST;
        titlePanel.add(radioPanel, gbc);

        return titlePanel;
    }

    /**
     * Crea el panell amb les dades de la distribució
     * @return Panell amb les dades de la distribució
     */
    private JPanel createShelfPanel() {
        JPanel shelf = new JPanel();
        shelf.setLayout(new BoxLayout(shelf, BoxLayout.Y_AXIS));
        shelf.setBackground(new Color(134, 182, 246));
        shelf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (List<String> products : products_List) {
            shelf.add(createRowPanel(products));
        }
        return shelf;
    }

    /**
     * Crea el panell principal amb ScrollPane
     * @return ScrollPane amb el panell principal
     */
    private JScrollPane createMainPanel() {
        JScrollPane scrollPane = new JScrollPane(createShelfPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    /**
     * Crea un panell amb una fila de productes
     * @param products Llista de productes
     * @return Panell amb una fila de productes
     */
    private JPanel createRowPanel(List<String> products) {
        JPanel rowPanel = new JPanel(new GridBagLayout());
        rowPanel.setBackground(new Color(134, 182, 246));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        for (String product : products) {
            JLabel label = new JLabel(product);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20))
            );
            label.setBackground(new Color(200, 230, 255));
            label.setOpaque(true);
            label.setFont(new Font("Arial", Font.PLAIN, 15));
            rowPanel.add(label, gbc);
            gbc.gridx++;
        }

        return rowPanel;
    }

    /**
     * Crea el panell amb els botons d'exportar, eliminar i modificar la distribució
     * @return Panell amb els botons d'exportar, eliminar i modificar la distribució
     */
    private JPanel createButtonPanel() {
        JButton button = new JButton("Exportar Distribucio");
        JButton eliminar = new JButton("Eliminar Distribucio");
        JButton modificarDistrib = new JButton("Modificar Distribucio");
        button.setFocusable(false);
        eliminar.setFocusable(false);
        modificarDistrib.setFocusable(false);

        button.addActionListener(e -> {
                exportarDistribucio();
        });
        eliminar.addActionListener(e-> {
            int result = pUp.showConfirmationDialog(3);
            if (result == JOptionPane.YES_OPTION) {
                if (aprox) {
                    try {
                        cP.eliminarDistribucioAproximada();
                    } catch (MyException ex) {
                        pUp.mostrarError( ex);
                    }
                }
                else {
                    try {
                        cP.eliminarDistribucioBruta();
                    } catch (MyException ex) {
                        pUp.mostrarError( ex);
                    }
                }
                mV.changePanel("DistribPanel");
                mV.resetDefault();
            }
        });
        modificarDistrib.addActionListener(e -> {mostrarPopupLista();});

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        buttonPanel.setBackground(new Color(134, 182, 246));
        buttonPanel.add(eliminar);
        buttonPanel.add(modificarDistrib);
        buttonPanel.add(button);

        return buttonPanel;
    }

    /**
     * Mostra un popup amb una llista de productes per a que l'usuari pugui seleccionar-ne dos.
     */
    private void mostrarPopupLista() {
        try {
            List<Integer> productes = cP.getIdProductesPlantilla();

            // Convertir la lista de Integer a String[] para los JComboBox
            String[] productesArray = productes.stream()
                    .map(String::valueOf) // Convertir Integer a String
                    .toArray(String[]::new);

            // Crear las dos listas desplegables basadas en la misma lista de productos
            JComboBox<String> comboBox1 = new JComboBox<>(productesArray);
            JComboBox<String> comboBox2 = new JComboBox<>(productesArray);

            // Panel para organizar los ComboBoxes en el popup
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Producte 1:"));
            panel.add(comboBox1);
            panel.add(new JLabel("Producte 2:"));
            panel.add(comboBox2);

            // Mostrar el popup con los ComboBoxes
            int option = JOptionPane.showOptionDialog(
                    this,
                    panel,
                    "Selecció de productes",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new Object[]{"Acceptar", "Cancelar"},
                    "Acceptar"
            );

            if (option == JOptionPane.OK_OPTION) {
                String selected1 = (String) comboBox1.getSelectedItem();
                String selected2 = (String) comboBox2.getSelectedItem();

                int p1 = Integer.parseInt(selected1);
                int p2 = Integer.parseInt(selected2);

                if (p1 == p2) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error: No pots seleccionar el mateix element en ambdues llistes.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Realitzar accions amb els elements seleccionats
                    if (aprox) {
                        cP.modificarDistribucioAproximada(p1, p2);
                    } else {
                        cP.modificarDistribucioBruta(p1, p2);
                    }
                    if (!ID) products_List = aprox ? cP.imprimirDistribucioAproximadaPerNom() : cP.imprimirDistribucioBrutaPerNom();
                    else products_List = aprox ? cP.imprimirDistribucioAproximadaPerID() : cP.imprimirDistribucioBrutaPerID();

                    // Actualiza el contenido del panel central
                    mainScrollPane.setViewportView(createShelfPanel());
                    revalidate();
                    repaint();
                }
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }

    /**
     * Exporta la distribució a un fitxer
     */
    private void exportarDistribucio() {
        try {
            String filepath = new FileChooser().openSaveFileDialog(false);
            if(filepath == null) return;
            if (ID) {
                if (aprox) cP.exportarDistribucioAproximadaPerID(filepath);
                else cP.exportarDistribucioBrutaPerID(filepath);
            } else {
                if (aprox) cP.exportarDistribucioAproximadaPerNom(filepath);
                else cP.exportarDistribucioBrutaPerNom(filepath);
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }
}
