package presentation.panels;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.*;

/**
 * Classe que representa el panell de productes de la plantilla.
 */
public class ProductPanel extends JPanel {

    /**
     * Taula on es mostren els productes de la plantilla
     */
    private JTable table;

    /**
     * Model de la taula
     */
    private DefaultTableModel tableModel;

    /**
     * Botó per afegir un fila a la taula
     */
    private JButton addButton;

    /**
     * Botó per importar un producte a la plantilla
     */
    private JButton importButton;

    /**
     * Botó per guardar els canvis fets a la taula
     */
    private JButton saveButton;

    /**
     * Botó per eliminar una fila de la taula
     */
    private JButton EliminarButton;

    /**
     * Conjunt amb les files antigues de la taula
     */
    private Set<Integer> oldRows;

    /**
     * Conjunt amb les files modificades de la taula
     */
    private Set<Integer> modifiedRows;

    /**
     * Conjunt amb les categories de la plantilla
     */
    private Set<String> categoriesList;

    /**
     * Controlador de presentació
     */
    private CtrlPresentacio cP;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Constructora de la classe ProductPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     */
    public ProductPanel(CtrlPresentacio cP, MyFrame mV) {
        this.cP = cP;
        pUp = new popUp(mV);
        this.oldRows = new HashSet<>();
        this.modifiedRows = new HashSet<>();
        this.categoriesList = new HashSet<>();
        setLayout(new BorderLayout(15, 15)); // Layout principal
        setBackground(new Color(238, 245, 255));

        // Crear modelo de tabla
        String[] columnNames = {"ID", "Nom", "Preu", "Categoria", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Evitar que las celdas sean editables directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 3) { // Columna "ID"
                    Object value = getValueAt(row, column);
                    return value == null || value.toString().isEmpty();
                }
                return true; // Las demás columnas son siempre editables
            }
        };

        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                // Agregar el índice de la fila modificada al conjunto
                int row = e.getFirstRow();
                modifiedRows.add(row);
            }
        });

        // Crear la tabla
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setBackground(new Color(200, 220, 255));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);

        // Añadir un botón "Eliminar" a cada fila
        table.getColumn("Accions").setCellRenderer(new ButtonRender());
        table.getColumn("Accions").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                EliminarButton = new JButton("Eliminar");
                EliminarButton.setBackground(new Color(255, 92, 92)); // Color de fondo
                EliminarButton.setForeground(Color.WHITE); // Color del texto

                EliminarButton.addActionListener(e -> {
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    try {
                        String productId = (String) table.getValueAt(row, 0);
                        if (productId != null && !productId.isEmpty() && cP.getIdProductesPlantilla().contains(Integer.parseInt(productId))) {
                            cP.treureProductePlantilla(Integer.parseInt(productId));
                            // Eliminar la fila de la tabla
                            tableModel.removeRow(row);
                        } else {
                            tableModel.removeRow(row);
                        }
                        oldRows.remove(row);
                    } catch (MyException ex) {
                        pUp.mostrarError(ex);
                    } catch (NumberFormatException ex) {
                        tableModel.removeRow(row);
                    }
                });

                return EliminarButton; // Devolvemos el botón para que sea el componente en la celda
            }
        });

        // Panel con Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(238, 245, 255));
        addButton = new JButton("Afegir Producte");
        importButton = new JButton("Carregar Producte");
        saveButton = new JButton("Guardar canvis");

        Dimension buttonSize = new Dimension(180, 35);
        addButton.setPreferredSize(buttonSize);
        importButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        // Añadir botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(importButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Acción para el botón "Añadir"
        addButton.addActionListener(e -> addRow());
        importButton.addActionListener(e -> mostrarPopupLista());
        saveButton.addActionListener(e -> {
            try {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing(); // Guardar la celda que está siendo editada
                }
                saveChanges();
            } catch (ArrayIndexOutOfBoundsException ex) {}
        });
    }

    /**
     * Reinicia els components del panell.
     */
    public void def() {
        try {
            tableModel.setRowCount(0);
            // Configurar columna "TIPUS"
            String[] tipusOptions = new String[0];
            List<List<String>> catsPlantilla = cP.getLlistaCategoriesPlantilla();
            categoriesList = new HashSet<>();
            for (List<String> categoria : catsPlantilla) {
                categoriesList.add(categoria.get(0));
            }
            tipusOptions = categoriesList.toArray(new String[0]);
            JComboBox<String> tipusComboBox = new JComboBox<>(tipusOptions);
            table.getColumn("Categoria").setCellEditor(new DefaultCellEditor(tipusComboBox));

            // Afegir PRODUCTES de la PLANTILLA
            List<List<String>> prod = cP.getLlistaProductesPlantilla();
            for (List<String> producte : prod) {
                String id = producte.get(0);
                String nom = producte.get(1);
                String preu = producte.get(3);
                String categoria = producte.get(2);

                if (!categoriesList.contains(categoria)) {
                    JOptionPane.showMessageDialog(this,
                            "La categoria del producte amb id \"" + id + "\" no pertany a aquesta plantilla." +
                                    "Es necessari afegir la categoria amb nom \"" + categoria + "\".",
                            "Error de format",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                // Agregar una nueva fila al modelo de la tabla
                tableModel.addRow(new Object[]{id, nom, preu, categoria, EliminarButton});
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Actualitza la taula amb el producte seleccionat.
     * @param carregarProd ID del producte a carregar
     */
    private void actualitzar(String carregarProd) {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            cP.importarProducte(Integer.parseInt(carregarProd));
            //Añadimos fila
            List<List<String>> prod = cP.getLlistaProductesPlantilla();
            for (List<String> producte : prod) {
                String id = producte.get(0);
                if (id.equals(carregarProd)) {
                    String nom = producte.get(1);
                    String preu = producte.get(3);
                    String categoria = producte.get(2);
                    if (!categoriesList.contains(categoria)) {
                        JOptionPane.showMessageDialog(this,
                                "La categoria del producte amb id \"" + id + "\" no pertany a aquesta plantilla." +
                                        "Es necessari afegir la categoria amb nom \"" + categoria + "\".",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    def();
                    break;
                }
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Afegeix una fila a la taula.
     */
    private void addRow() {
        Object[] rowData = {"", "", "", "", EliminarButton};
        tableModel.addRow(rowData);
    }

    /**
     * Guarda els canvis fets a la taula.
     */
    private void saveChanges() {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            boolean mal = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object value = tableModel.getValueAt(i, j);
                    // Validar columna 0 (ID): Ha de ser un número enter
                    if (j == 0) {
                        if (value == null || value.toString().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this,
                                    "Completa l'ID a la fila " + (i + 1) + ".",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                            mal = true;
                            break;
                        } else {
                            try {
                                Integer.parseInt(value.toString().trim());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this,
                                        "L'ID a la fila " + (i + 1) + " ha de ser un numero enter.",
                                        "Error de format",
                                        JOptionPane.ERROR_MESSAGE);
                                mal = true;
                                break;
                            }
                        }
                    }
                    // Validar columna 1 (Nom) o 3 (Categoria): Ha d'estar completa
                    if (j == 1 || j == 3) {
                        if (value == null || value.toString().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this,
                                    "Completa " + tableModel.getColumnName(j) + " a la fila " + (i + 1) + ".",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                            mal = true;
                            break;
                        }
                    }
                    // Validar columna 2 (Preu): Ha de ser un double
                    if (j == 2) {
                        if (value == null || value.toString().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this,
                                    "Completa el preu a la fila " + (i + 1) + ".",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                            mal = true;
                            break;
                        } else {
                            try {
                                Double.parseDouble(value.toString().trim());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this,
                                        "El preu a la fila " + (i + 1) + " ha de ser un número decimal.",
                                        "Error de format",
                                        JOptionPane.ERROR_MESSAGE);
                                mal = true;
                                break;
                            }
                        }
                    }
                }
            }

            // Si totes estan completes, procedim a guardar-les
            if (!mal) {
                List<Integer> prodsPlantilla = cP.getIdProductesPlantilla();
                // Eliminamos de todas las categorias de la Plantilla si la tabla esta vacia al guardar.
                if (tableModel.getRowCount() == 0) {
                    for (Integer prods : prodsPlantilla) {
                        cP.treureProductePlantilla(prods);
                    }
                }
                // Procedemos a evaluar la tabla.
                else {
                    String id;
                    String nom;
                    String preu;
                    String categoria;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        id = (String) tableModel.getValueAt(i, 0);
                        nom = (String) tableModel.getValueAt(i, 1);
                        preu = (String) tableModel.getValueAt(i, 2);
                        categoria = (String) tableModel.getValueAt(i, 3);

                        //Guardamos en catsIguales las categorias que hay actualmente en la tabla
                        if (modifiedRows.contains(i)) {
                            if(!oldRows.contains(i) && !prodsPlantilla.contains(Integer.parseInt(id))) {
                                cP.crearProducte(Integer.parseInt(id), nom, categoria, Double.parseDouble(preu));
                                oldRows.add(i);
                            } else cP.modificarProducte(Integer.parseInt(id), nom, Double.parseDouble(preu));
                            modifiedRows.remove(i); //La quitamos porque ahora quedará guardada así.
                        }
                    }
                    JOptionPane.showMessageDialog(this,
                            "Canvis guardats correctament",
                            "Informacio",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                def();
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Mostra un popup amb la llista de productes disponibles.
     */
    private void mostrarPopupLista() {
        try {
            List<String> allProds = cP.getIdAllProductes();

            // Crear el JList con todas las categorías
            JList<String> listaProds = new JList<>(allProds.toArray(new String[0]));

            // Hacer que la lista sea desplazable si tiene más elementos de los que caben
            JScrollPane scrollPane = new JScrollPane(listaProds);
            scrollPane.setPreferredSize(new Dimension(200, 100));  // Definir el tamaño del área visible

            final String[] selectedValue = new String[1];
            listaProds.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    selectedValue[0] = listaProds.getSelectedValue();
                }
            });

            // Mostrar el popup con la lista y el scroll
            int option = JOptionPane.showOptionDialog(
                    this,
                    scrollPane,
                    "Selecciona un producte",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new Object[] {"Aceptar", "Cancelar", "Eliminar"},
                    "Aceptar"
            );
            if (option == 0) {
                if (selectedValue[0] != null) {
                    actualitzar(selectedValue[0]);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No s'ha seleccionat un producte a carregar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (option == 2) {
                if (selectedValue[0] != null) {
                    cP.eliminarProducte(Integer.parseInt(selectedValue[0]));
                    def();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No s'ha seleccionat un producte a eliminar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }
}
