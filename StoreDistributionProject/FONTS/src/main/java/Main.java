import exceptions.MyException;
import presentation.CtrlPresentacio;

public class Main {

    public static void main(String[] args) {
        try {
            CtrlPresentacio.getInstance().iniciarVista();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }
}
