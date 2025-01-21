package presentation.panels;

import exceptions.MyException;
import presentation.CtrlPresentacio;
import presentation.MyFrame;
import presentation.components.popUp;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa el panell de distribució.
 */
public class DistribPanel extends JPanel {

    /**
     * Controlador de presentació
     */
    private CtrlPresentacio cP;

    /**
     * Finestra principal de l'aplicació
     */
    private MyFrame mV;

    /**
     * Indica si s'ha acabat de calcular la distribució
     */
    private boolean done;

    /**
     * PopUp de la finestra
     */
    private popUp pUp;

    /**
     * Constructora de la classe DistribPanel
     * @param cP Controlador de presentació
     * @param mV Finestra principal de l'aplicació
     */
    public DistribPanel(CtrlPresentacio cP, MyFrame mV) {
        this.cP = cP;
        this.mV = mV;
        this.done = false;
        this.pUp = new popUp(mV);
        // Configurar el diseño del panel
        setLayout(new BorderLayout());
        setBackground(new Color(134, 182, 246));

        JLabel titleLabel = new JLabel("Selecciona l'algorisme que es vol utilitzar per calcular la distribucio:");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(134, 182, 246));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Espaciado entre botones

        // Crear los botones
        JButton aprox = new JButton("Distribucio per Aproximacio");
        JButton brute = new JButton("Distribucio per Forca Bruta");
        aprox.setFocusable(false);
        brute.setFocusable(false);

        aprox.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        done = false;
                        int number = showNumberInputDialog();
                        if (number >= 0) {
                            done = false;
                            cP.calcularDistribucioAproximada(number);
                            done = true;
                        }
                        return null;
                    } catch (MyException e) {
                        pUp.mostrarError(e);
                        return null;
                    }
                }

                @Override
                protected void done() {
                    calcAprox();
                }
            };
            worker.execute();
        });

        brute.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        done = false;
                        cP.calcularDistribucioBruta();
                        done = true;
                        return null;
                    } catch (MyException e) {
                        pUp.mostrarError(e);
                        return null;
                    }
                }

                @Override
                protected void done() {
                        calcBrute();
                }
            };
            worker.execute();
        });

        buttonPanel.add(aprox);
        buttonPanel.add(brute);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Canvia la vista per mostrar la distribució aproximada
     */
    private void calcAprox() {
        if(done) mV.changeDefault(true);
    }

    /**
     * Canvia la vista per mostrar la distribució per força bruta
     */
    private void calcBrute() {
        if(done) mV.changeDefault(false);
    }

    /**
     * Mostra un diàleg perquè l'usuari introdueixi un nombre
     * @return Número introduït per l'usuari
     */
    public static int showNumberInputDialog() {
        int number = -1;
        try {
            // Mostrar un cuadro de diálogo para que el usuario introduzca un número
            String input = JOptionPane.showInputDialog(
                    null,
                    "Introdueix el nombre d'iteracions:",
                    "",
                    JOptionPane.QUESTION_MESSAGE
            );

            // Si el usuario cancela o no introduce nada, devolver -1
            if (input == null || input.trim().isEmpty()) {
                return -1; // Número por defecto
            }

            // Intentar convertir el valor introducido a un entero
            number = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // Mostrar un mensaje de error si el valor no es un número válido
            JOptionPane.showMessageDialog(
                    null,
                    "Si us plau, introdueix un nombre valid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return number;
    }
}
