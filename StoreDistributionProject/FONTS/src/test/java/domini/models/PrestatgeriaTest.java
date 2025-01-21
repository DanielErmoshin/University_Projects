package domini.models;

import exceptions.MyException;
import org.junit.Test;

import static org.junit.Assert.*;


public class PrestatgeriaTest {

    @Test
    public void testGetNumPrestatges_positiveNumber() throws MyException {
        Prestatgeria prestatgeria = new Prestatgeria(1, 3);
        int actual = prestatgeria.getNumPrestatges();
        assertEquals(3, actual);
    }

    @Test(expected = MyException.class)
    public void testGetNumPrestatges_zero() throws MyException {
        Prestatgeria prestatgeria = new Prestatgeria(2, 0);
    }

    @Test(expected = MyException.class)
    public void testGetNumPrestatges_negativeNumber() throws MyException {
        Prestatgeria prestatgeria = new Prestatgeria(2, -1);
    }

}