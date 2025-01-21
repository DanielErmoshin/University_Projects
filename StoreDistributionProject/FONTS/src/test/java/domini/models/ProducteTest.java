package domini.models;

import exceptions.MyException;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Map;


public class ProducteTest {
    private Categoria categoria = new Categoria("Categoria", "");
    private Producte producte = new Producte(1, "Nevera", categoria, 10.5);



    @Test
    public void testAfegirSimilitud() throws MyException {
        producte.afegirSimilitud(2, 0.75);
        Map<Integer, Double> similituds = producte.getSimilituds();

        assertTrue(similituds.containsKey(2));
        assertEquals(0.75, similituds.get(2), 0.01);
    }

    @Test(expected = MyException.class)
    public void testAfegirSimilitudThrowsException() throws MyException {
        producte.afegirSimilitud(3, 1.5);
    }

    @Test
    public void testEliminarSimilitud() throws MyException {
        producte.afegirSimilitud(2, 0.75);
        producte.eliminarSimilitud(2);

        assertFalse(producte.getSimilituds().containsKey(2));
    }

    @Test(expected = MyException.class)
    public void testEliminarSimilitudThrowsException() throws MyException {
        producte.eliminarSimilitud(4);
    }

    @Test
    public void testExisteixSimilitud() throws MyException {
        producte.afegirSimilitud(5, 0.5);

        assertTrue(producte.existeixSimilitud(5));
        assertFalse(producte.existeixSimilitud(6));
    }
}
