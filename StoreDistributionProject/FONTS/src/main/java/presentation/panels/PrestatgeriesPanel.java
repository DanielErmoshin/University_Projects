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
 * Classe que representa el panell de prestatgeries.
 */
public class PrestatgeriesPanel extends JPanel {
    /**
     * Taula on es mostren les prestatgeries
     */
    private JTable table;

    /**
     * Model de la taula
     */
    private DefaultTableModel tableModel;

    /**
     * Botó per afegir una fila a la taula
     */
    private JButton addButton;

    /**
     * Botó per importar una prestatgeria
     */
    private JButton importButton;

    /**
     * Botó per guardar els canvis
     */
    private JButton saveButton;

    /**
     * Botó per eliminar una fila de la taula
     */
    private JButton Eliminar_button;

    /**
     * Conjunt de files antigues
     */
    private Set<Integer> oldRows;

    /**
     * Conjunt de files modificades
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
     * Constructora de la classe PrestatgeriesPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     */
    public PrestatgeriesPanel(CtrlPresentacio cP, MyFrame mV) {
        this.cP = cP;
        pUp = new popUp(mV);
        this.oldRows = new HashSet<>();
        this.modifiedRows = new HashSet<>();
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(238, 245, 255));

        String[] columnNames = {"ID", "NumPrestatges", "Accions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) { // Columna "ID"
                    Object value = getValueAt(row, column);
                    //String stringValue = value != null ? value.toString() : "";
                    return value == null || value.toString().isEmpty(); //&& !prestatgeriesEnPlantilla.contains(stringValue); // Editable solo si está vacío
                }
                return true;
            }
        };

        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                // Agregar el índice de la fila modificada al conjunto
                int row = e.getFirstRow();
                modifiedRows.add(row);
            }
        });

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setBackground(new Color(200, 220, 255));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumn("Accions").setCellRenderer(new ButtonRender());
        table.getColumn("Accions").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                Eliminar_button = new JButton("Eliminar");
                Eliminar_button.setBackground(new Color(255, 92, 92)); // Color de fondo
                Eliminar_button.setForeground(Color.WHITE); // Color del texto

                Eliminar_button.addActionListener(e -> {
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    try {
                        String prestatgeriaId = (String) table.getValueAt(row, 0);
                        if (prestatgeriaId != null && !prestatgeriaId.isEmpty() && cP.getIdPrestatgeriesPlantilla().contains(Integer.parseInt(prestatgeriaId))) {
                                cP.treurePrestatgeriaPlantilla(Integer.parseInt(prestatgeriaId));
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

                return Eliminar_button; // Devolvemos el botón para que sea el componente en la celda
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(238, 245, 255));
        addButton = new JButton("Afegir Prestatgeria");
        importButton = new JButton("Carregar Prestatgeria");
        saveButton = new JButton("Guardar canvis");

        Dimension buttonSize = new Dimension(180, 35);
        addButton.setPreferredSize(buttonSize);
        importButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(importButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addRow());
        importButton.addActionListener(e -> {
            try {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing(); // Guardar la celda que está siendo editada
                }
                mostrarPopupLista();
            } catch (ArrayIndexOutOfBoundsException ex) {}
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
     * Reinicia els components del panell
     */
    public void def() {
        try {
            try {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing(); // Guardar la celda que está siendo editada
                }
            } catch (ArrayIndexOutOfBoundsException ex) {}

            tableModel.setRowCount(0); // Borramos todas las filas

            //Actualizamos
            List<List<String>> prest = cP.getLlistaPrestatgeriesPlantilla();

            // Recorrer cada categoría y agregarla a la tabla
            for (List<String> prestageries : prest) {
                String id = prestageries.get(0); // Nombre de la categoría
                String num_p = prestageries.get(1); // Descripción de la categoría

                tableModel.addRow(new Object[]{id, num_p, Eliminar_button});
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Afegeix una fila a la taula
     */
    private void addRow() {
        Object[] rowData = new Object[]{"", "", Eliminar_button};
        tableModel.addRow(rowData);
    }

    /**
     * Carrega una prestatgeria a la taula
     * @param carregarPrest ID de la prestatgeria a carregar
     */
    private void actualitzar(String carregarPrest) {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            cP.importarPrestatgeria(Integer.parseInt(carregarPrest));
            //Añadimos fila
            List<List<String>> prest = cP.getLlistaPrestatgeriesPlantilla();
            for (List<String> prestatgeria : prest) {
                String id = prestatgeria.get(0);
                if (id.equals(carregarPrest)) {
                    def();
                    break;
                }
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Guarda els canvis fets a la taula
     */
    private void saveChanges() {
        try {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            boolean mal = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount() - 1; j++) {
                    Object value = tableModel.getValueAt(i, j);
                    if (value == null || value.toString().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Completa " + tableModel.getColumnName(j) + " a la fila " + (i + 1) + ".",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE);
                        mal = true;
                        break;
                    }  else {
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
            }
            // Si totes estan completes, procedim a guardar-les
            if (!mal) {
                List<Integer> prestatgeriesPlantilla = cP.getIdPrestatgeriesPlantilla();
                // Eliminamos de todas las prestatgeries de la Plantilla si la tabla esta vacia al guardar.
                if (tableModel.getRowCount() == 0) {
                    for (Integer idPrest : prestatgeriesPlantilla) {
                        cP.treurePrestatgeriaPlantilla(idPrest);
                    }
                }
                // Procedemos a evaluar la tabla.
                else {
                    String id;
                    String num_p;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        id = (String) tableModel.getValueAt(i, 0);
                        num_p = (String) tableModel.getValueAt(i, 1);

                        //Guardamos en catsIguales las prestatgeries que hay actualmente en la tabla
                        if (modifiedRows.contains(i)) {
                            // Si la fila se ha modificado, llamar a modificarPrestatgeria
                            if (!oldRows.contains(i) && !prestatgeriesPlantilla.contains(Integer.parseInt(id))) {
                                cP.crearPrestatgeria(Integer.parseInt(id), Integer.parseInt(num_p));
                                oldRows.add(i); //La quitamos porque ahora quedará guardada así.
                            } else cP.modificarPrestatgeria(Integer.parseInt(id), Integer.parseInt(num_p));
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
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }

    /**
     * Mostra un popup amb una llista de prestatgeries
     */
    private void mostrarPopupLista() {
        try {
            List<String> allPrests = cP.getIdAllPrestatgeries();

            // Crear el JList con todas las categorías
            JList<String> listaPrests = new JList<>(allPrests.toArray(new String[0]));

            // Hacer que la lista sea desplazable si tiene más elementos de los que caben
            JScrollPane scrollPane = new JScrollPane(listaPrests);
            scrollPane.setPreferredSize(new Dimension(200, 100));  // Definir el tamaño del área visible

            final String[] selectedValue = new String[1];
            listaPrests.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    selectedValue[0] = listaPrests.getSelectedValue();
                }
            });

            // Mostrar el popup con la lista y el scroll
            int option = JOptionPane.showOptionDialog(
                    this,
                    scrollPane,
                    "Selecciona una prestatgeria",
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
                            "No s'ha seleccionat una prestatgeria a carregar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (option == 2) {
                if (selectedValue[0] != null) {
                    cP.eliminarPrestatgeria(Integer.parseInt(selectedValue[0]));
                    def();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No s'ha seleccionat una prestatgeria a eliminar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }
    }
}
