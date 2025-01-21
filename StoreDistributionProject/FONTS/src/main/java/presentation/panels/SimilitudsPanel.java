package presentation.panels;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Classe que representa el panell de similituds.
 */
public class SimilitudsPanel extends JPanel {

    /**
     * Taula on es mostren les similituds entre productes.
     */
    private JTable table;

    /**
     * Model de la taula.
     */
    private DefaultTableModel tableModel;

    /**
     * Controlador de presentació.
     */
    private CtrlPresentacio cP;

    /**
     * PopUp de la finestra.
     */
    private popUp pUp;

    /**
     * Matriu de similituds.
     */
    private double[][] matrix;

    /**
     * Constructora de la classe SimilitudsPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     */
    public SimilitudsPanel(CtrlPresentacio cP, MyFrame mV) {
        this.cP = cP;
        this.pUp = new popUp(mV);
        setLayout(new BorderLayout());
        setBackground(new Color(134, 182, 246));
    }

    /**
     * Inicialitza el panell de similituds.
     */
    public void initialize() {
        removeAll(); // Eliminar cualquier componente previo en el panel
        try {
            matrix = cP.imprimirSimilituds();
            if (matrix.length == 1) {
                // Si la matriz está vacía, añadir un fondo vacío
                JLabel emptyLabel = new JLabel("No hi ha productes a la plantilla.", SwingConstants.CENTER);
                emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
                emptyLabel.setForeground(Color.BLACK);
                add(emptyLabel, BorderLayout.CENTER);

            } else {
                // Crear el modelo de la tabla
                tableModel = createTableModel(matrix);

                // Crear la tabla
                table = new JTable(tableModel);

                // Personalizar renderers
                customizeTable();

                // Configurar el editor de celdas para aceptar solo Double
                setDoubleEditor();

                // Ajustar tamaño de las celdas
                adjustCellSizes();

                // Añadir la tabla a un JScrollPane con scroll horizontal y vertical
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                add(scrollPane, BorderLayout.CENTER);

                // Agregar un TableModelListener para detectar modificaciones en las celdas
                tableModel.addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        if (e.getType() == TableModelEvent.UPDATE) {
                            onCellModified(e.getFirstRow(), e.getColumn());
                        }
                    }
                });
            }
        } catch (MyException e) {
            pUp.mostrarError(e);
        }

        revalidate(); // Actualizar el diseño del panel
        repaint();    // Repintar el panel
    }


    /**
     * Crea el model de la taula a partir de la matriu de similituds.
     * @param matrix Matriu de similituds
     * @return Model de la taula
     */
    private DefaultTableModel createTableModel(double[][] matrix) {
        int size = matrix.length - 1; // Excluir la fila/columna de IDs de la matriz

        // Crear nombres de columna (sin incluir el índice 0 de la matriz)
        String[] columnNames = new String[size + 1];
        columnNames[0] = "ID Producte"; // Encabezado de IDs
        for (int i = 1; i <= size; i++) {
            columnNames[i] = String.valueOf((int) matrix[0][i]); // Nombres de columna a partir del índice 1
        }

        // Crear datos de la tabla
        Object[][] data = new Object[size][size + 1];
        for (int i = 1; i <= size; i++) {
            data[i - 1][0] = String.valueOf((int) matrix[i][0]); // IDs de la fila desde el índice 1
            for (int j = 1; j <= size; j++) {
                if (i == j) {
                    data[i - 1][j] = "-"; // Diagonal con "-"
                } else if (i > j) {
                    data[i - 1][j] = matrix[i][j]; // Parte inferior de la diagonal
                } else {
                    data[i - 1][j] = null; // Las celdas superiores serán sincronizadas
                }
            }
        }

        // Retornar el modelo de la tabla
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Celdas inmutables: diagonal, diagonal superior, y primera columna
                if (row == column - 1 || row < column - 1 || column == 0) return false;
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return String.class; // Primera columna (IDs) como String
                }
                return Object.class; // Resto de columnas como Object
            }
        };
    }


    /**
     * Acció a realitzar quan una cel·la de la taula és modificada.
     * @param row Fila
     * @param column Columna
     */
    private void onCellModified(int row, int column) {
        // Verificar si la celda modificada es editable
        if (tableModel.isCellEditable(row, column)) {
            double oldDouble = matrix[row + 1][column];
            Object newValue = tableModel.getValueAt(row, column);      // Nou valor


            try {
                double newDouble = newValue == null || newValue.toString().trim().isEmpty() ? 0.0
                        : Double.parseDouble(newValue.toString().trim());

                if (newDouble > 1 || newDouble < 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "El valor ha d'estar entre 0 i 1.",
                            "Error de entrada",
                            JOptionPane.ERROR_MESSAGE);
                    tableModel.setValueAt(String.valueOf(oldDouble), row, column);
                }
                else if (oldDouble > 0 && newDouble == 0) {
                    // Si el valor cambia de un número positiu a 0
                    cP.eliminarSimilitud((int) matrix[row + 1][0], (int) matrix[0][column]);
                }
                else if (oldDouble != 0 || newDouble != 0){
                    // Si el valor cambia de un número > 0
                    cP.modificarSimilitud((int) matrix[row + 1][0], (int) matrix[0][column], newDouble);
                }
            } catch (MyException ex) {
                pUp.mostrarError(ex);
                tableModel.setValueAt(String.valueOf(oldDouble), row, column);
            } catch (NumberFormatException ex) {
                pUp.mostrarError(new MyException("Introdueixi un valor numeric valid entre 0 i 1 en la casella"));
                tableModel.setValueAt(String.valueOf(oldDouble), row, column);
            }
        }
    }

    /**
     * Personaliza la apariencia de la tabla.
     */
    private void customizeTable() {
        // Renderer para la primera columna (IDs)
        DefaultTableCellRenderer idRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setFont(getFont().deriveFont(Font.BOLD));
                setBackground(new Color(173, 173, 173)); // Azul claro
                setHorizontalAlignment(SwingConstants.CENTER); // Centrado
                return this;
            }
        };
        table.getColumnModel().getColumn(0).setCellRenderer(idRenderer);

        // Renderer para la diagonal y diagonal superior
        DefaultTableCellRenderer diagonalRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == column - 1) {
                    setBackground(Color.LIGHT_GRAY); // Diagonal con fondo gris claro
                } else if (row < column - 1) {
                    setBackground(Color.DARK_GRAY); // Diagonal superior con fondo gris oscuro
                } else {
                    setBackground(Color.WHITE); // Resto de celdas con fondo normal
                    setForeground(Color.BLACK);
                }
                setHorizontalAlignment(SwingConstants.CENTER); // Centrado
                return this;
            }
        };

        for (int i = 1; i <= table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(diagonalRenderer);
        }
    }

    /**
     * Configura el editor de celdas para aceptar solo valores Double entre 0 y 1.
     */
    private void setDoubleEditor() {
        JTextField textField = new JTextField();
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField) input).getText();
                try {
                    Double value = Double.parseDouble(text); // Validar si es un double
                    if (value >= 0 && value <= 1) { // Validar que esté entre 0 y 1
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "El valor ha d'estar entre 0 y 1.",
                                "Error de entrada",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    pUp.mostrarError(new MyException ("Introdueixi un valor numeric valid."));
                    return false;
                }
            }
        });

        DefaultCellEditor editor = new DefaultCellEditor(textField);
        table.setDefaultEditor(Object.class, editor);
    }

    /**
     * Modifica la mida de les cel·les de la taula.
     */
    private void adjustCellSizes() {
        table.setRowHeight(30); // Altura de filas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(80); // Ancho mínimo de columnas
        }
    }
}
