package domini.distributions;

import exceptions.MyException;
import domini.models.Categoria;
import domini.models.Producte;
import domini.models.Prestatgeria;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;


public class TestDistributionAprox {

    @Test
    public void testCalcularDistID() throws MyException {
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
        Prestatgeria prestatgeria = new Prestatgeria(1, 3);
        llistaPrestatges.put(1, prestatgeria);
        int iterations = 100;
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> distPerID = distAprox.imprimirPerID();

        List<List<String>> expected = List.of(List.of("2", "1", "3"));
        assertEquals(distPerID,expected);
    }

    @Test
    public void testCalcularDistNom() throws MyException {
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
        Prestatgeria prestatgeria = new Prestatgeria(1, 3);
        llistaPrestatges.put(1, prestatgeria);
        int iterations = 100;
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> distPerNom = distAprox.imprimirPerNom();

        List<List<String>> expected = List.of(List.of("Crispetes", "Patata", "Sucre"));
        assertEquals(distPerNom,expected);
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
        int iterations = 100;
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> distPerID = distAprox.imprimirPerID();

        List<List<String>> expected = List.of(List.of("2"), List.of("3", "1"));
        assertEquals(distPerID,expected);
    }

    @Test
    public void testCalcularDistSenseProductes() throws MyException {
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        int iterations = 100;
        Prestatgeria prest1 = new Prestatgeria(1, 10);
        llistaPrestatges.put(1, prest1);
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> s = distAprox.imprimirPerID();

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
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> distPerId = distAprox.imprimirPerNom();

        List<List<String>> expected = List.of();
        assertEquals(expected,distPerId);
    }

    @Test
    public void testCalcularDistSenseSimilituds() throws MyException { //si no hi ha similituds, similitud = 0
        HashMap<Integer, Producte> llistaProductes = new HashMap<>();
        HashMap<Integer, Prestatgeria> llistaPrestatges = new HashMap<>();
        Categoria c = new Categoria("1", "");
        Producte patata = new Producte(1, "Patata", c, 8);
        Producte crispetes = new Producte(2, "Crispetes", c, 8);
        Producte sucre = new Producte(3, "Sucre", c, 8);
        llistaProductes.put(1, patata);
        llistaProductes.put(2, crispetes);
        llistaProductes.put(3, sucre);
        Prestatgeria prestatgeria = new Prestatgeria(1, 10);
        llistaPrestatges.put(1, prestatgeria);
        int iterations = 100;
        DistributionAprox distAprox = new DistributionAprox(llistaProductes, llistaPrestatges, iterations);
        distAprox.calcularDist();
        List<List<String>> distPerID = distAprox.imprimirPerID();

        List<List<String>> expected = List.of(List.of("1", "3", "2"));
        assertEquals(distPerID,expected);
    }
}