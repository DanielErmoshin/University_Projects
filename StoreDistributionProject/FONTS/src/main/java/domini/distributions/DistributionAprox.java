package domini.distributions;

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import domini.models.Prestatgeria;
import domini.models.Producte;

/**
 * Aquesta classe representa una distribució de productes en una prestatgeria calculada de forma aproximada.
 */
public class DistributionAprox  extends Distribution {

    /**
     * HashMap que mapeja l'identificador del producte amb la seva posició en l'estructura de dades.
     */
    private HashMap<Integer, Integer> productIndexMap;

    /**
     * Número d'iteracions per a l'optimització.
     */
    private int iterations;

    /**
     * Graf de similituds entre productes.
     */
    GraphAdjacencyMatrix G;

    /**
     * Aquesta classe representa una aresta del graf.
     */
    private static class Edge {

        /**
         * Valor de l'aresta.
         */
        double value;

        /**
         * Identificador del producte a.
         */
        int a;

        /**
         * Identificador del producte b.
         */
        int b;

        /**
         * Constructora de la classe Edge.
         * @param value Valor de l'aresta.
         * @param a Identificador del producte a.
         * @param b Identificador del producte b.
         */
        public Edge(double value, int a, int b) {
            this.value = value;
            this.a = a;
            this.b = b;
        }
    }

    /**
     * Constructora de la classe DistributionAprox.
     * @param llistaProductes Llista de productes.
     * @param llistaPrestatges Llista de prestatgeries.
     * @param iterations Número d'iteracions per a l'optimització.
     */
    public DistributionAprox(HashMap<Integer, Producte> llistaProductes, HashMap<Integer, Prestatgeria> llistaPrestatges, int iterations) {
        super(llistaProductes, llistaPrestatges);
        productIndexMap = new HashMap<>();
        this.iterations = iterations;
    }

    /**
     * Genera un arbre de recubriment màxim a partir de les similituds entre productes.
    * @return Arbre de recubriment màxim.
    */
    private GraphAdjacencyList Kruskal() {
        DisjointSet mfs = new DisjointSet();
        productIndexMap =  mfs.makeSet(llistaProductes); //makeSet with //HashMap for product mapping
        int size = llistaProductes.size();
        G = new GraphAdjacencyMatrix(size);
        int aid = 0;
        for (Map.Entry<Integer, Producte> entry1: llistaProductes.entrySet()) {
            for (Map.Entry<Integer, Double> entry2: entry1.getValue().getSimilituds().entrySet()) {
                int bid = productIndexMap.get(entry2.getKey());
                if (aid < bid) G.addEdge(aid,bid,entry2.getValue());
            }
            ++aid;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Double.compare(b.value, a.value)); //max heap
        for (int i = 0; i < size; ++i)
        {
            for (int j = i+1; j < size; ++j)
            {
                Edge n = new Edge(G.getEdge(i,j),i,j);
                pq.add(n);
            }
        }
        GraphAdjacencyList T = new GraphAdjacencyList(size);
        while (T.getNumEdges() < size - 1) {
            Edge edge = pq.poll();
            if (mfs.merge(edge.a, edge.b))
                T.addEdge(edge.a,edge.b,edge.value);
        }
        return T;
    }

    /**
     * Calcula la diferència de pes entre els enllaços existents i els proposats per un moviment de 3-Opt
     * @param x1 Identificador del producte x1.
     * @param x2 Identificador del producte x2.
     * @param y1 Identificador del producte y1.
     * @param y2 Identificador del producte y2.
     * @param z1 Identificador del producte z1.
     * @param z2 Identificador del producte z2.
     * @param move Moviment de 3-Opt.
     * @return  Diferència de pes.
     */
    private double differencia(int x1, int x2, int y1, int y2, int z1, int z2, int move) {
        double negative_weight = 0;
        double positive_weight = 0;
        switch (move) {
            case 0:
                return 0;
            // casos en el que es modifiquen dos fragments del cicle
            case 1:
                positive_weight = G.getEdge(x1, z1) + G.getEdge(x2, z2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(z1, z2);
                break;
            case 2:
                positive_weight = G.getEdge(y1, z1) + G.getEdge(y2, z2);
                negative_weight = G.getEdge(y1, y2) + G.getEdge(z1, z2);
                break;
            case 3:
                positive_weight = G.getEdge(x1, y1) + G.getEdge(x2, y2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(y1, y2);
                break;

            // casos en el que es modifiquen tres fragments del cicle
            case 4:
                positive_weight = G.getEdge(x1, y1) + G.getEdge(x2, z1) + G.getEdge(y2, z2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(y1, y2) + G.getEdge(z1, z2);
                break;
            case 5:
                positive_weight = G.getEdge(x1, z1) + G.getEdge(y2, x2) + G.getEdge(y1, z2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(y1, y2) + G.getEdge(z1, z2);
                break;
            case 6:
                positive_weight = G.getEdge(x1, y2) + G.getEdge(z1, y1) + G.getEdge(x2, z2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(y1, y2) + G.getEdge(z1, z2);
                break;
            case 7:
                positive_weight = G.getEdge(x1, y2) + G.getEdge(z1, x2) + G.getEdge(y1, z2);
                negative_weight = G.getEdge(x1, x2) + G.getEdge(y1, y2) + G.getEdge(z1, z2);
                break;
        }
        return negative_weight - positive_weight;
    }

    /**
     * Inverteix un segment del cicle.
     * @param cycle Cicle.
     * @param start Inici del segment.
     * @param end Final del segment.
     */
    private void reverseSegment(List<Integer> cycle, int start, int end) {
        while (start < end) {
            int temp = cycle.get(start);
            cycle.set(start, cycle.get(end));
            cycle.set(end, temp);
            start++;
            end--;
        }
    }

    /**
     * Realitza un moviment de 3-Opt.
     * @param tour Cicle.
     * @param i Inici del primer segment.
     * @param j Inici del segon segment.
     * @param k Inici del tercer segment.
     * @param move Moviment de 3-Opt.
     */
    private void threeOpt(List<Integer> tour, int i, int j, int k, int move) {
        int n = tour.size();

        switch (move) {
            case 0:
                //No es canvia res, per a representar el no canviar res
                break;
            case 1:
                reverseSegment(tour, (k + 1) % n, i);
                break;
            case 2:
                reverseSegment(tour, (j + 1) % n, k);
                break;
            case 3:
                reverseSegment(tour, (i + 1) % n, j);
                break;
            case 4:
                reverseSegment(tour, (j + 1) % n, k);
                reverseSegment(tour, (i + 1) % n, j);
                break;
            case 5:
                reverseSegment(tour, (k + 1) % n, i);
                reverseSegment(tour, (i + 1) % n, j);
                break;
            case 6:
                reverseSegment(tour, (k + 1) % n, i);
                reverseSegment(tour, (j + 1) % n, k);
                break;
            case 7:
                reverseSegment(tour, (k + 1) % n, i);
                reverseSegment(tour, (i + 1) % n, j);
                reverseSegment(tour, (j + 1) % n, k);
                break;
        }
    }

    /**
     * Calcula la distància total d'un cicle.
     * @param cycle Cicle.
     * @return
     */
    private double calculateTourDistance(List<Integer> cycle) {
        double totalDistance = 0.0;
        int n = cycle.size();
        if (n != 0) {
            for (int i = 0; i < n - 1; i++) {
                int currentNode = cycle.get(i);
                int nextNode = cycle.get(i + 1);
                totalDistance += G.getEdge(currentNode, nextNode);
            }
            int lastNode = cycle.get(n - 1);
            int firstNode = cycle.get(0);
            totalDistance += G.getEdge(lastNode, firstNode);
        }
        return totalDistance;
    }

    /**
     * Realitza una perturbació de doble pont.
     * @param cycle Cicle.
     */
    private void doubleBridgePerturbation(List<Integer> cycle) {
        int n = cycle.size();
        if (n < 8) return;
        int p1 = (int) (Math.random() * (n / 4));
        int p2 = p1 + 1 + (int) (Math.random() * (n / 4));
        int p3 = p2 + 1 + (int) (Math.random() * (n / 4));
        int p4 = p3 + 1 + (int) (Math.random() * (n / 4));
        List<Integer> newCycle = new ArrayList<>();
        newCycle.addAll(cycle.subList(0, p1 + 1));
        newCycle.addAll(cycle.subList(p3 + 1, p4 + 1));
        newCycle.addAll(cycle.subList(p2 + 1, p3 + 1));
        newCycle.addAll(cycle.subList(p1 + 1, p2 + 1));
        newCycle.addAll(cycle.subList(p4 + 1, n));
        for (int i = 0; i < n; i++) {
            cycle.set(i, newCycle.get(i));
        }
    }

    /**
     * Optimitza un cicle amb 3-Opt.
     * @param cycle Cicle.
     * @param iterations Número d'iteracions.
     */
    private void optimize(List<Integer> cycle, int iterations) {
        double[] totalDistance = {calculateTourDistance(cycle)};
        for (int iter = 0; iter < iterations; iter++) {
            boolean improved = false;
            for (int i = 0; i < cycle.size() - 2; i++) {
                for (int j = i + 1; j < cycle.size() - 1; j++) {
                    for (int k = j + 1; k < cycle.size(); k++) {
                        int bestCase = 0; //7 cases
                        double maxGain = 0;
                        for (int move = 0; move < 8; ++move) {
                            double gain = differencia(cycle.get(i), cycle.get(i + 1), cycle.get(j), cycle.get(j + 1), cycle.get(k), cycle.get((k + 1) % cycle.size()), move);
                            if (gain < maxGain) {
                                maxGain = gain;
                                bestCase = move;
                            }
                        }
                        if (maxGain < 0) {
                            threeOpt(cycle, i, j, k, bestCase);
                            totalDistance[0] += maxGain;
                            improved = true;
                        }
                    }
                }
            }
            if (!improved) {
                doubleBridgePerturbation(cycle);
                totalDistance[0] = calculateTourDistance(cycle);
            }
        }
    }

    /**
     * Calcula la distribució de productes de forma aproximada.
     */
    public void calcularDist() {
        GraphAdjacencyList T = Kruskal();
        Cycle C = T.findEulerianCycle();
        List<Integer> result = C.deleteRepeatedVertex(G);
        optimize(result, iterations);
        dist.clear();
        List<Producte> valueList = new ArrayList<>(llistaProductes.values());
        for (Integer i : result) {
            dist.add(valueList.get(i));
        }
    }
 }


