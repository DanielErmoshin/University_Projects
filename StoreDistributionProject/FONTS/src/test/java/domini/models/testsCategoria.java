package domini.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class testsCategoria {

    @Test
    public void testIncrementarNumProducte_DefaultConstructer() {
        Categoria categoria = new Categoria("Electronics", "All electronics");
        assertEquals(0, categoria.getNumProducte());
        categoria.incrementarNumProducte();
        assertEquals(1, categoria.getNumProducte());
    }

    @Test
    public void testIncrementarNumProducte_ParameterizedConstructer() {
        Categoria categoria = new Categoria("Clothes", "Summer collection");
        assertEquals(0, categoria.getNumProducte());
        categoria.incrementarNumProducte();
        assertEquals(1, categoria.getNumProducte());
    }

    @Test
    public void testIncrementarNumProducte_MultipleIncrement() {
        Categoria categoria = new Categoria("Books", "Fiction");
        assertEquals(0, categoria.getNumProducte());
        categoria.incrementarNumProducte();
        categoria.incrementarNumProducte();
        categoria.incrementarNumProducte();
        assertEquals(3, categoria.getNumProducte());
    }

    // Tests per probar decrementarNumProducte en la classe Categoria
    @Test
    public void testDecrementarNumProducte_FromNonZeroNumber() {
        Categoria categoria = new Categoria("Test", "Test description");
        categoria.decrementarNumProducte();
        assertEquals(0, categoria.getNumProducte());
    }

    @Test
    public void testDecrementarNumProducte_FromZero() {
        Categoria categoria = new Categoria("Test", "Test description");
        categoria.decrementarNumProducte();
        assertEquals(0, categoria.getNumProducte());
    }

    @Test
    public void testDecrementarNumProducte_FromOne() {
        Categoria categoria = new Categoria("Test", "Test description");
        categoria.decrementarNumProducte();
        assertEquals(0, categoria.getNumProducte());
    }

    //Excepcion per posar un numero de productes negatiu
}

