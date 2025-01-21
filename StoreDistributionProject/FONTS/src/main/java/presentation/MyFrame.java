package presentation;

import exceptions.MyException;
import presentation.panels.*;
import presentation.components.popUp;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Aquesta classe representa la finestra principal de l'aplicació que gestiona la configuració i canvi entre diferents panells
 */
public class MyFrame extends JFrame{

    /**
     * Nom de la finestra
     */
    public String FRAME_NAME = "Grup 13.2";

    /**
     * Panell superior de la finestra inmutable
     */
    private JPanel inm;

    /**
     * HashMap amb els panells de la finestra
     */
    private HashMap<String, JPanel> mutable = new HashMap<>();

    /**
     * Nom del panell actual
     */
    private String currentPanel;

    /**
     * Controlador de presentació
     */
    private final CtrlPresentacio cP;

    /**
     * PopUp de la finestra
     */
    private final popUp pUp;

    /**
     * Constructora de la classe MyFrame
     * @param cP Controlador de presentació
     */
    public MyFrame(CtrlPresentacio cP) {
        this.cP = cP;
        this.pUp = new popUp(this);
        frameConfiguration();
        setVisible(true);
    }

    /**
     * Configura la finestra (títol, mida, tancament, etc.), inicialitza els panells i els afegeix al disseny de la finestra.
     */
    private void frameConfiguration() {
        new JFrame();
        setTitle(FRAME_NAME);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(134, 182, 246));
        setLayout(new BorderLayout());

        //Inicializamos el panel inmutable
        inm = new InmutablePanel(this, cP);
        ((InmutablePanel) inm).inici();

        //Inicializamos el panel mutable
        initialize_mutable();

        add(inm, BorderLayout.NORTH);
        add(mutable.get(currentPanel), BorderLayout.CENTER);

        pack();
        Dimension minimumSize = getSize();
        setMinimumSize(minimumSize);
        setSize(800, 600);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (Objects.equals(currentPanel, "Inicial")) {
                    System.exit(0);
                } else {
                    int result = pUp.showConfirmationDialog(0);
                    if (result == JOptionPane.YES_OPTION) {
                        // Guardar canvis
                        try {
                            cP.guardarPlantilla();
                        } catch (MyException ex) {
                            pUp.mostrarError(ex);
                        }
                        System.exit(0);
                    } else if (result == JOptionPane.NO_OPTION) {
                        try {
                            if (cP.existeixDistribucioAproximada()) cP.eliminarDistribucioAproximada();
                            if (cP.existeixDistribucioBruta()) cP.eliminarDistribucioBruta();
                        } catch (MyException ex) {
                            pUp.mostrarError(ex);
                        }
                        System.exit(0);
                    }
                }
            }
        });

    }

    /**
     * Inicialitza els panells mutables i els afegeix a la col·lecció mutable.
     */
    private void initialize_mutable() {
        mutable.put("Inicial", new InitialPanel(this, cP));
        mutable.put("Default", new DefaultPanel());
        mutable.put("Config_Plantilla", new ConfigPlantillaPanel(this, cP));
        mutable.put("CategoriesPanel", new CategoriesPanel(cP, this));
        mutable.put("PresatgeriesPanel", new PrestatgeriesPanel(cP, this));
        mutable.put("ProductPanel", new ProductPanel(cP, this));
        mutable.put("SimilitudsPanel", new SimilitudsPanel(cP, this));
        mutable.put("DistribPanel", new DistribPanel(cP, this));

        currentPanel = "Inicial";
    }

    /**
     * Torna al panell "Default" (per defecte) després d'un canvi de panell.
     */
    public void backToDefault() {
        changePanel("Default");
    }

    /**
     * Fa que el panell “DistribPanel” sigui interpretat per MyFrame com a Default.
     * @param aprox Indica si la distribució a mostrar és aproximada o per força bruta.
     */
    public void changeDefault(boolean aprox) {
        mutable.remove("Default");
        mutable.put("Default", new ShowDistribPanel(cP, this, aprox));
        ((ShowDistribPanel)mutable.get("Default")).initializePanel();
        ((InmutablePanel) inm).setTextButton();
        backToDefault();
    }

    /**
     *  Restableix el panell "Default" com a la configuració inicial.
     */
    public void resetDefault() {
        mutable.remove("Default");
        mutable.put("Default", new DefaultPanel());
        backToDefault();
    }

    /**
     * Canvia el panell actual per un altre panell.
     * @param name_panel Nom del panell al qual es vol canviar.
     */
    public void changePanel(String name_panel) {
        remove(mutable.get(currentPanel));
        add(mutable.get(name_panel), BorderLayout.CENTER);

        if (Objects.equals(name_panel, "Config_Plantilla")) {
            ((ConfigPlantillaPanel)mutable.get(name_panel)).def();
        }
        else if(Objects.equals(name_panel, "SimilitudsPanel")) {
            ((SimilitudsPanel)mutable.get(name_panel)).initialize();
        }
        else if(Objects.equals(name_panel, "CategoriesPanel")) {
            ((CategoriesPanel)mutable.get(name_panel)).def();
        }
        else if (Objects.equals(name_panel, "ProductPanel")) {
            ((ProductPanel)mutable.get(name_panel)).def();
        }
        else if (Objects.equals(name_panel, "PresatgeriesPanel")) {
            ((PrestatgeriesPanel)mutable.get(name_panel)).def();
        }

        setMinimumSize(getSize());
        revalidate();
        repaint();
        currentPanel = name_panel;
    }

    /**
     * Canvia el panell actual per un altre panell.
     * @param inici Indica si es vol canviar a la configuració inicial.
     */
    public void changeInm(boolean inici) {
        if (inici) {
            ((InmutablePanel) inm).def();
            backToDefault();
        }
        else {
            ((InmutablePanel) inm).reset();
            if (cP.existeixDistribucioBruta() || cP.existeixDistribucioAproximada()) {
                changePanel("DistribPanel");
                resetDefault();
            }
            changePanel("Inicial");
            setTitle(FRAME_NAME);
        }
    }

    /**
     * Retorna la finestra.
     * @return Finestra.
     */
    public JFrame getFrame() {
        return this;
    }

    /**
     * Retorna el nom del panell actual.
     * @return Nom del panell actual.
     */
    public String getCurrentPanel() {
        return this.currentPanel;
    }

    /**
     * Afegeix una plantilla a la finestra.
     */
    public void addPlantilla() {
        try {
            setTitle(cP.getNomPlantilla());
        } catch (MyException e) {
            popUp.mostrarError(e);
        }
    }
}
