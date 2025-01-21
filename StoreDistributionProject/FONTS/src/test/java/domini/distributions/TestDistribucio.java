package domini.distributions;

import exceptions.MyException;
import domini.models.Categoria;
import domini.models.Producte;
import domini.models.Prestatgeria;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

public class TestDistribucio {


    @Test
    public void testImprimirCasoNormal() throws MyException{

        Categoria cat1 = new Categoria("cat1", "");
        Producte product1 = new Producte(1, "Producto1", cat1, 1.0);
        Producte product2 = new Producte(2, "Producto2", cat1, 1.2);
        Producte product3 = new Producte(3, "Producto3", cat1, 1.4);
        Producte product4 = new Producte(4, "Producto4", cat1, 2.4);
        Producte product5 = new Producte(5, "Producto5", cat1, 1.1);

        // Crear prestatgerías
        Prestatgeria prestatgeria1 = new Prestatgeria(1, 2);
        Prestatgeria prestatgeria2 = new Prestatgeria(2, 2);
        Prestatgeria prestatgeria3 = new Prestatgeria(3, 1);

        // Crear listas de productos y prestatgerías
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        llistaProductes.put(1, product1);
        llistaProductes.put(2, product2);
        llistaProductes.put(3, product3);
        llistaProductes.put(4, product4);
        llistaProductes.put(5, product5);

        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        llistaPrestatges.put(1, prestatgeria1);
        llistaPrestatges.put(2, prestatgeria2);
        llistaPrestatges.put(3, prestatgeria3);

        Distribution distribucio = new Distribution(llistaProductes, llistaPrestatges) {
            @Override
            public void calcularDist() {
                dist.addAll(llistaProductes.values());
            }
        };

        distribucio.calcularDist();

        List<List<String>> resultPerID = distribucio.imprimirPerID();
        List<List<String>> resultPerNom = distribucio.imprimirPerNom();

        assertNotNull(resultPerID);
        assertNotNull(resultPerNom);

        List<List<String>> expected = List.of(List.of("1", "2"), List.of("4", "3"), List.of("5"));

        assertEquals(expected, resultPerID);
    }
}