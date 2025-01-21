package presentation.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe personalitzada que s'utilitza per crear un editor de cel·les en una taula
 */
public class ButtonEditor extends DefaultCellEditor {

    /**
     * Botó que es mostra a la cel·la
     */
    private JButton button;

    /**
     * Index de la fila seleccionada
     */
    private int selectedRow;

    /**
     * Model de la taula
     */
    private DefaultTableModel tableModel;

    /**
     * Constructora de la classe ButtonEditor
     * @param checkBox CheckBox que es mostra a la cel·la
     * @param model Model de la taula
     */
    public ButtonEditor(JCheckBox checkBox, DefaultTableModel model) {
        super(checkBox);
        this.tableModel = model;
        button = new JButton("Eliminar");
        button.setForeground(Color.WHITE);
        button.setBackground(Color.RED);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });

    }

    /**
     * Assigna la fila seleccionada i retorna el botó com a component d'edició de la cel·la.
     * @param table Taula
     * @param value Valor de la cel·la
     * @param isSelected Indica si la cel·la està seleccionada
     * @param row Fila
     * @param column Columna
     * @return Component d'edició de la cel·la
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row; // Actualizar la fila seleccionada
        button.setText((value == null) ? "" : value.toString());
        return button;
    }

    /**
     * Retorna el valor del botó
     * @return Valor del botó
     */
    @Override
    public Object getCellEditorValue() {
        return "Eliminar";
    }
}
