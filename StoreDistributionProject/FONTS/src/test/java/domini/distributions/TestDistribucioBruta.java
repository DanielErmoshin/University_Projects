package domini.distributions;

import exceptions.MyException;
import domini.models.Categoria;
import domini.models.Producte;
import domini.models.Prestatgeria;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

public class TestDistribucioBruta {

    // Cas normal: Distribució de productes entre prestatgeries amb similitudes
    @Test
    public void testCasNormalCalcDist() throws MyException {
        Categoria cat1 = new Categoria("cat1", "");

        // Crear productes i prestatgeries
        Producte product1 = new Producte(1, "Producto1", cat1, 10.0);
        Producte product2 = new Producte(2, "Producto2", cat1, 20.0);
        Producte product3 = new Producte(3, "Producto3", cat1, 30.0);
        Producte product4 = new Producte(4, "Producto4", cat1, 40.0);
        Producte product5 = new Producte(5, "Producto5", cat1, 50.0);

        product1.afegirSimilitud(2, 0.9);
        product1.afegirSimilitud(3, 0.7);
        product2.afegirSimilitud(3, 0.8);
        product3.afegirSimilitud(4, 0.85);
        product4.afegirSimilitud(5, 0.95);
        product4.afegirSimilitud(2, 0.75);

        Prestatgeria prestatgeria1 = new Prestatgeria(1, 3); // 3 prestatges
        Prestatgeria prestatgeria2 = new Prestatgeria(2, 2); // 2 prestatges

        //Crear llistes amb productes i prestatgeries
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        llistaProductes.put(1, product1);
        llistaProductes.put(2, product2);
        llistaProductes.put(3, product3);
        llistaProductes.put(4, product4);
        llistaProductes.put(5, product5);

        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        llistaPrestatges.put(1, prestatgeria1);
        llistaPrestatges.put(2, prestatgeria2);

        Distribution distribucio = new DistributionFuerzaBruta(llistaProductes, llistaPrestatges);
        distribucio.calcularDist();

        List<List<String>> resultPerID = distribucio.imprimirPerID();
        List<List<String>> resultPerNom = distribucio.imprimirPerNom();

        assertNotNull(resultPerID);
        assertNotNull(resultPerNom);

        List<List<String>> expected = List.of(List.of("3", "2", "1"), List.of("4", "5"));
        List<List<String>> expected2 = List.of(List.of("Producto3", "Producto2", "Producto1"), List.of("Producto4", "Producto5"));

        // Verificar que els productes estan en l'ordre indicat
        assertEquals(expected, resultPerID);
        assertEquals(expected2, resultPerNom);
    }
    @Test
    public void testCalcularDistDuesPrestatgeries() throws MyException {
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        Categoria c = new Categoria("1", "");
        Producte patata = new Producte(1, "Patata", c, 8);
        Producte crispetes = new Producte(2, "Crispetes", c, 8);
        Producte sucre = new Producte(3, "Sucre", c, 8);
        patata.afegirSimilitud(2, 0.9);
        patata.afegirSimilitud(3, 0.8);
        crispetes.afegirSimilitud(1, 0.9);
        crispetes.afegirSimilitud(3, 0.7);
        sucre.afegirSimilitud(1, 0.8);
        sucre.afegirSimilitud(2, 0.7);
        llistaProductes.put(1, patata);
        llistaProductes.put(2, crispetes);
        llistaProductes.put(3, sucre);
        Prestatgeria prestatgeria = new Prestatgeria(1, 1);
        Prestatgeria prestatgeria2 = new Prestatgeria(1, 2);
        llistaPrestatges.put(1, prestatgeria);
        llistaPrestatges.put(2, prestatgeria2);
        DistributionFuerzaBruta distFuerzaBruta = new DistributionFuerzaBruta(llistaProductes, llistaPrestatges);
        distFuerzaBruta.calcularDist();
        List<List<String>> distPerID = distFuerzaBruta.imprimirPerID();

        List<List<String>> expected = List.of(List.of("1"), List.of("3", "2"));
        assertEquals(distPerID,expected);
    }

    @Test
    public void testCalcularDistSenseProductes() throws MyException {
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        int iterations = 100;
        Prestatgeria prest1 = new Prestatgeria(1, 10);
        llistaPrestatges.put(1, prest1);
        DistributionFuerzaBruta distFuerzaBruta = new DistributionFuerzaBruta(llistaProductes, llistaPrestatges);
        distFuerzaBruta.calcularDist();
        List<List<String>> s = distFuerzaBruta.imprimirPerID();
        List<List<String>> expected = List.of(List.of());
        assertEquals(s,expected);
    }

    @Test
    public void testCalcularDistSensePrestatgeries() {
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        int iterations = 100;
        Categoria c = new Categoria("1", "");
        Producte patata = new Producte(1, "Patata", c, 8);
        llistaProductes.put(1, patata);
        DistributionFuerzaBruta distFuerzaBruta = new DistributionFuerzaBruta(llistaProductes, llistaPrestatges);
        distFuerzaBruta.calcularDist();
        List<List<String>> distPerId = distFuerzaBruta.imprimirPerNom();

        List<List<String>> expected = List.of();
        assertEquals(expected,distPerId);
    }

    // Cas extrem: Dos productes amb similitud 0
    @Test
    public void testImprimirCasoExtremoSimilitudCero() throws MyException {
        Categoria cat1 = new Categoria("cat1", "");
        Producte product1 = new Producte(1, "Producto1", cat1, 10.0);
        Producte product2 = new Producte(2, "Producto2", cat1, 20.0);

        Prestatgeria prestatgeria1 = new Prestatgeria(1, 1); // 1 prestatge
        Prestatgeria prestatgeria2 = new Prestatgeria(2, 1); // 1 prestatge

        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        llistaProductes.put(1, product1);
        llistaProductes.put(2, product2);

        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        llistaPrestatges.put(1, prestatgeria1);
        llistaPrestatges.put(2, prestatgeria2);

        Distribution distribucio = new DistributionFuerzaBruta(llistaProductes, llistaPrestatges);

        distribucio.calcularDist();


        List<List<String>> resultPerID = distribucio.imprimirPerID();
        List<List<String>> resultPerNom = distribucio.imprimirPerNom();

        assertNotNull(resultPerID);
        assertNotNull(resultPerNom);

        List<List<String>> expected = List.of(List.of("1"), List.of("2"));
        List<List<String>> expected2 = List.of(List.of("Producto1"), List.of("Producto2"));

        // Verificar que la distribució es correcta encara que no hi hagin similituds
        assertEquals(expected, resultPerID);
        assertEquals(expected2, resultPerNom);
    }
}
