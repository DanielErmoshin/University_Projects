package presentation.panels;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;
import presentation.tools.FileChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

/**
 * Classe que representa el panell inmutable de l'aplicació dins de la finestra principal.
 */
public class InmutablePanel extends JPanel {

    /**
     * Boto que configura la plantilla actual.
     */
    private JButton button1;

    /**
     * Boto que gestiona els productes.
     */
    private JButton button2;

    /**
     * Boto que gestiona les categories.
     */
    private JButton button3;

    /**
     * Boto que gestiona les prestatgeries.
     */
    private JButton button4;

    /**
     * Boto que gestiona les similituds.
     */
    private JButton button5;

    /**
     * Boto que calcula les distribucions.
     */
    private JButton button6;

    /**
     * Boto per sortir de l'aplicació o tornar enrere.
     */
    private JButton button7;

    /**
     * Boto per exportar la plantilla.
     */
    private JButton button8;

    /**
     * Boto per guardar la plantilla.
     */
    private JButton button9;

    /**
     * Finestra principal de l'aplicació
     */
    private MyFrame mV;

    /**
     * Controlador de presentació
     */
    private CtrlPresentacio cP;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Constructora de la classe InmutablePanel
     * @param mV Finestra principal de l'aplicació
     * @param cP Controlador de presentació
     */
    public InmutablePanel(MyFrame mV, CtrlPresentacio cP) {
        this.mV = mV;
        this.cP = cP;
        pUp = new popUp(mV);
        setBackground(new Color(238, 245, 255));

        button1 = new JButton("Configuracio de la Plantilla Activa");
        button1.setActionCommand("Config_Plantilla"); // Lo añadimos para tratar la acción.

        button2 = new JButton("Gestio de Productes");
        button2.setActionCommand("ProductPanel");

        button3 = new JButton("Gestio de Categories");
        button3.setActionCommand("CategoriesPanel");

        button4 = new JButton("Gestio de Prestatgeries");
        button4.setActionCommand("PresatgeriesPanel");

        button5 = new JButton("Gestio de Similituds");
        button5.setActionCommand("SimilitudsPanel");

        button6 = new JButton("Calcul de Distribucions");
        button6.setActionCommand("DistribPanel");
    }

    /**
     * Crea els components inicials del panell.
     */
    public void inici() {
        JLabel label = new JLabel("Inici");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 50));
        add(label);
    }

    /**
     * Reinicia el panell.
     */
    public void reset() {
        removeAll();
        revalidate();
        repaint();
        inici();
    }

    /**
     * Reinicia els components del panell.
     */
    public void def() {
        remove(0); // Borramos el JLabel del inicio.

        setLayout(new BorderLayout(10, 10));

        // Panel para los primeros 6 botones con GridLayout
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 filas, 3 columnas
        topPanel.setBackground(new Color(238, 245, 255));

        topPanel.add(button1);
        topPanel.add(button2);
        topPanel.add(button3);
        topPanel.add(button4);
        topPanel.add(button5);
        topPanel.add(button6);

        // Panel para el botón centralizado
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(238, 245, 255));
        bottomPanel.setLayout(new BorderLayout());
        button7 = new JButton("Exit");
        button8 = new JButton("Exportar Plantilla");
        button9 = new JButton("Guardar Plantilla");
        button8.setForeground(new Color(0,150, 0));
        button8.setPreferredSize(new Dimension(150, 40));
        button9.setPreferredSize(new Dimension(150, 40));
        bottomPanel.add(button7, BorderLayout.WEST);
        bottomPanel.add(button9, BorderLayout.CENTER);
        bottomPanel.add(button8, BorderLayout.EAST);

        // Configurar tamaños de botones
        Dimension buttonSize = new Dimension(100, 40);
        JButton[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9};
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            associateAction(button);
            button.setFocusable(false);
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
            button.setBackground(new Color(100, 149, 237)); // Azul claro
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 180), 1), // Azul más oscuro
                    BorderFactory.createEmptyBorder(5, 15, 5, 15))); // Padding interno
        }

        // Añadir los paneles al layout principal
        add(topPanel, BorderLayout.CENTER); // Botones principales
        add(bottomPanel, BorderLayout.SOUTH); // Botón centrado al final

        // Bordes opcionales para estética
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 2), // Borde gris claro
                        "", // Título del panel
                        TitledBorder.CENTER,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14), // Fuente
                        new Color(60, 60, 60) // Texto gris oscuro
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Margen interno
        ));
    }

    /**
     * Assigna una acció a un botó.
     * @param button Botó al qual assignar l'acció
     */
    private void associateAction(JButton button) {
            button.addActionListener(e -> {
                try {
                    if (button == button7) { // Si el botón es el "Exit" (button7)
                        if (Objects.equals(mV.getCurrentPanel(), "Default")) {
                            int result = pUp.showConfirmationDialog(0);
                            if (result != JOptionPane.CANCEL_OPTION) {
                                if (result == JOptionPane.YES_OPTION) {
                                    guardar_canvis();
                                    mV.changeInm(false);
                                }
                                else if (result == JOptionPane.NO_OPTION) {
                                    mV.changeInm(false);
                                }
                                try {
                                    cP.tancarPlantilla();
                                } catch (MyException ex) {
                                    pUp.mostrarError(ex);
                                }
                            }
                        }
                        else {
                            setTextButton();
                            mV.backToDefault();
                        }
                    } else if (button == button8) { // Si el boton es el de "Exportar Plantilla"
                        String filepath = new FileChooser().openSaveFileDialog(true);
                        if (filepath != null) cP.exportarPlantilla(filepath);
                    } else if (button == button9) {
                        guardar_canvis();
                        JOptionPane.showMessageDialog(mV,
                                "Plantilla guardada correctament",
                                "Guardat",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String actionComand = e.getActionCommand();
                        mV.changePanel(actionComand);
                        button7.setText("Enrere");
                    }
                } catch (MyException ex) {
                    pUp.mostrarError(ex);
                }
            });
    }

    /**
     * Guarda els canvis fets a la plantilla.
     */
    private void guardar_canvis() {
        try {
            cP.guardarPlantilla();
        } catch (MyException ex) {
            pUp.mostrarError(ex);
        }
    }

    /**
     * Canvia el text del botó de sortida.
     */
    public void setTextButton() {
        button7.setText("Exit");
    }
}
