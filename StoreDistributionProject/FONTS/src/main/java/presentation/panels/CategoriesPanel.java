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
 * Classe que representa el panell de categories de la finestra principal de l'aplicació.
 */
public class CategoriesPanel extends JPanel {

    /**
     * Taula de categories
     */
    private JTable table;

    /**
     * Model de la taula
     */
    private DefaultTableModel tableModel;

    /**
     * Botó per afegir una nova fila a la taula
     */
    private JButton addButton;

    /**
     * Botó per importar categories del sistema
     */
    private JButton importButton;

    /**
     * Botó per guardar els canvis realitzats a la taula
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
     * Controlador de presentació
     */
    private CtrlPresentacio cP;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Constructora de la classe CategoriesPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     */
    public CategoriesPanel(CtrlPresentacio cP, MyFrame mV) {
        this.cP = cP;
        pUp = new popUp(mV);
        this.oldRows = new HashSet<>();
        this.modifiedRows = new HashSet<>();
        setLayout(new BorderLayout(15, 15)); // Layout principal
        setBackground(new Color(238, 245, 255));

        // Crear modelo de tabla
        String[] columnNames = {"Categoria", "Num Productes", "Descripcio", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Evitar que las celdas sean editables directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) { // Columna "ID"
                    Object value = getValueAt(row, column);
                    //String stringValue = value != null ? value.toString() : "";
                    return value == null || value.toString().isEmpty(); //&& !categoriesEnPlantilla.contains(stringValue); // Editable solo si está vacío
                } else if (column == 1) {
                    return false;
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
                        String categoriaId = (String) table.getValueAt(row, 0);
                        if (categoriaId != null && !categoriaId.isEmpty() && cP.getIdCategoriesPlantilla().contains(categoriaId)) {
                                cP.treureCategoriaPlantilla(categoriaId);
                                // Eliminar la fila de la tabla
                                tableModel.removeRow(row);
                        } else {
                            tableModel.removeRow(row);
                        }
                        oldRows.remove(row);
                    } catch (MyException ex) {
                        pUp.mostrarError(ex);
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
        addButton = new JButton("Afegir categoria");
        importButton = new JButton("Carregar categories");
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
        importButton.addActionListener(e -> {
            mostrarPopupLista();
        });
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
     * Actualitza la taula amb les categories de la plantilla
     */
    public void def() {
        try {
            tableModel.setRowCount(0); // Borramos todas las filas

            //Actualizamos
            List<List<String>> cat = cP.getLlistaCategoriesPlantilla();

            // Recorrer cada categoría y agregarla a la tabla
            for (List<String> categoria : cat) {
                String nom = categoria.get(0); // Nombre de la categoría
                String descripcio = categoria.get(1); // Descripción de la categoría
                String numProductes = categoria.get(2); // Número de productos en la categoría

                Object[] rowData = {nom, numProductes, descripcio, EliminarButton};
                tableModel.addRow(rowData);
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }

    /**
     * Carrega una categoria del sistema a la taula
     * @param carregarCat Nom de la categoria a carregar
     */
    private void actualitzar(String carregarCat) {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            cP.importarCategoria(carregarCat);
            //Añadimos fila
            List<List<String>> cat = cP.getLlistaCategoriesPlantilla();
            for (List<String> categoria : cat) {
                String nom = categoria.get(0);
                if (nom.equals(carregarCat)) {
                    def();
                    break;
                }
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }

    /**
     * Afegeix una nova fila a la taula
     */
    private void addRow() {
        Object[] rowData = {"", "0", "", EliminarButton};
        tableModel.addRow(rowData);
    }

    /**
     * Guarda els canvis realitzats a la taula
     */
    private void saveChanges() {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            boolean mal = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0) == null || tableModel.getValueAt(i, 0).toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Completa el nom a la fila " + (i + 1) + ".",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    mal = true;
                    break;
                }
            }
            // Si totes estan completes, procedim a guardar-les
            if (!mal) {
                // Eliminamos de todas las categorias de la Plantilla si la tabla esta vacia al guardar.
                List<String> categoriesPlantilla = cP.getIdCategoriesPlantilla();
                if (tableModel.getRowCount() == 0) {
                    for (String nomCats : categoriesPlantilla) {
                        cP.treureCategoriaPlantilla(nomCats);
                    }
                }
                // Procedemos a evaluar la tabla.
                else {
                    String nom;
                    String descripcio;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        nom = (String) tableModel.getValueAt(i, 0);
                        descripcio = (String) tableModel.getValueAt(i, 2);

                        //Guardamos en catsIguales las categorias que hay actualmente en la tabla
                        if (modifiedRows.contains(i)) {
                            if (!oldRows.contains(i) && !categoriesPlantilla.contains(nom)) {
                                if (descripcio == null || descripcio.trim().isEmpty()) {
                                    cP.crearCategoria(nom);
                                } else {
                                    cP.crearCategoria(nom, descripcio);
                                }
                                oldRows.add(i);
                            } else cP.modificarCategoria(nom, descripcio);
                            modifiedRows.remove(i);
                        }
                    }
                    JOptionPane.showMessageDialog(this,
                            "Canvis guardats correctament",
                            "Informacio",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                def();
            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }

    /**
     * Mostra un popup amb una llista de totes les categories del sistema
     */
    private void mostrarPopupLista() {
        try {
            List<String> allCats = cP.getIdAllCategories();

            // Crear el JList con todas las categorías
            JList<String> listaCats = new JList<>(allCats.toArray(new String[0]));

            // Hacer que la lista sea desplazable si tiene más elementos de los que caben
            JScrollPane scrollPane = new JScrollPane(listaCats);
            scrollPane.setPreferredSize(new Dimension(200, 100));  // Definir el tamaño del área visible

            final String[] selectedValue = new String[1];
            // Añadir ListSelectionListener para saber qué elemento fue seleccionado
            listaCats.addListSelectionListener(e -> {
                // Comprobar que la selección es válida y no es una selección múltiple
                if (!e.getValueIsAdjusting()) {
                    selectedValue[0] = listaCats.getSelectedValue();
                }
            });

            // Mostrar el popup con la lista y el scroll
            int option = JOptionPane.showOptionDialog(
                    this,                  // Componente padre
                    scrollPane,            // El contenido que va dentro del popup (en este caso, el JScrollPane con la lista)
                    "Selecciona una categoria",  // Título del popup
                    JOptionPane.DEFAULT_OPTION,  // Tipo de opción (sin botones estándar)
                    JOptionPane.PLAIN_MESSAGE,  // Tipo de mensaje (sin íconos)
                    null,                   // Icono opcional
                    new Object[]{"Aceptar", "Cancelar", "Eliminar"},  // Los botones personalizados
                    "Aceptar"              // Valor inicial (botón predeterminado)
            );
            if (option == 0) {
                if (selectedValue[0] != null) {
                    actualitzar(selectedValue[0]);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No s'ha seleccionat una categoria.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else if (option == 2) {
                if(selectedValue[0] != null) {
                    cP.eliminarCategoria(selectedValue[0]);
                    def();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No s'ha seleccionat una categoria.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }
}