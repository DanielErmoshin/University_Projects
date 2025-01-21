package presentation.components;

import javax.swing.*;
import exceptions.MyException;
import presentation.MyFrame;

/**
 * Classe que representa un pop-up.
 */
public class popUp {
    /**
     * Finestra principal de l'aplicació.
     */
    private static MyFrame mV;

    /**
     * Constructora de la classe popUp.
     * @param mV Finestra principal de l'aplicació.
     */
    public popUp(MyFrame mV) {
        this.mV = mV;
    }

    /**
     * Array de strings amb els missatges dels pop-ups.
     */
    private String[] myArray = {"Desitja guardar els canvis realitzats?",
            "Totes les dades creades o importades fins ara desapareixeran. " +
                    "\nDesitja continuar amb el RESET?",
            "Eliminaras la plantilla del sistema." +
                    "\nContinuar amb la eliminació?",
            "Eliminaras la distribucio actual." +
                    "\nContinuar amb la eliminacio?"};

    /**
     * Mostra un pop-up de confirmació amb el missatge corresponent al número passat per paràmetre.
     * @param number Número del missatge a mostrar.
     * @return Retorna un enter que indica la opció escollida per l'usuari.
     */
    public int showConfirmationDialog(int number) {
        return JOptionPane.showConfirmDialog(
                mV,
                myArray[number],
                "Confirmacio",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Mostra un pop-up d'error amb el missatge de l'excepció passada per paràmetre.
     * @param ex Excepció que es vol mostrar.
     */
    public static void mostrarError(MyException ex) {
        JOptionPane.showMessageDialog(
                mV,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
