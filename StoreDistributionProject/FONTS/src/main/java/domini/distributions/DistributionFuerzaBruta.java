package domini.distributions;

import java.util.ArrayList;
import java.util.List;
import domini.models.Prestatgeria;
import domini.models.Producte;
import java.util.Map;
import java.util.HashMap;

/**
 * Aquesta classe representa una distribució de productes en una prestatgeria calculada amb força bruta.
 */
public class DistributionFuerzaBruta extends Distribution {

    /**
     * Valor màxim de la distribució.
     */
    private double max;

    /**
     * Constructora de la classe DistributionFuerzaBruta.
     * @param llistaProductes Llista de productes.
     * @param llistaPrestatges Llista de prestatgeries.
     */
    public DistributionFuerzaBruta(HashMap<Integer, Producte> llistaProductes, HashMap<Integer, Prestatgeria> llistaPrestatges) {
        super(llistaProductes, llistaPrestatges); // Llama al constructor de la superclase
        this.max = 0.0;
    }

    /**
     * Calcula la distribució de productes en les prestatgeries amb força bruta.
     */
    @Override
    public void calcularDist() {
        Map<Integer, Integer> indexToId = new HashMap<>();

        int n = llistaProductes.size();
        int index = 0;
        for (Integer id : llistaProductes.keySet()) {
            indexToId.put(index, id);
            index++;
        }

        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(n);
        // Llenar la matriz de adyacencia de g
        for (int i = 0; i < n; i++) {
            int id1 = indexToId.get(i);
            Producte product1 = llistaProductes.get(id1);
            Map<Integer, Double> similitudes = product1.getSimilituds();
            for (int j = i + 1; j < n; j++) {
                int id2 = indexToId.get(j);
                if (similitudes.containsKey(id2)) {
                    G.addEdge(i, j, similitudes.get(id2));
                }
            }
        }

        List <Integer> result = G.calcularCicloHamiltonianoMaximo();

        List<Producte> distribFinal = new ArrayList<>();

        for (int id : result) {
            int aux = indexToId.get(id);
            distribFinal.add(llistaProductes.get(aux));
        }
        this.dist = distribFinal;
        this.max = G.getMax();
    }
}