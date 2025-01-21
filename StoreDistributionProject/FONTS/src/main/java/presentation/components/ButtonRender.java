package presentation.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

/**
 * Classe personalitzada que s'utilitza per crear un render de cel·les en una taula
 */
public class ButtonRender extends JButton implements TableCellRenderer {

    /**
     * Constructora de la classe ButtonRender
     */
    public ButtonRender() {
        setText("Eliminar");
        setForeground(Color.WHITE);
        setBackground(Color.RED);
        setOpaque(true);
        setFocusPainted(false);
    }

    /**
     * Retorna el botó com a component de render de la cel·la.
     * @param table Taula
     * @param value Valor de la cel·la
     * @param isSelected Indica si la cel·la està seleccionada
     * @param hasFocus Indica si la cel·la té el focus
     * @param row Fila
     * @param column Columna
     * @return Component de render de la cel·la
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
