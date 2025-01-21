package domini.distributions;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Aquesta classe representa un graph amb llista d'adjacència.
 */
public class GraphAdjacencyList extends Graph {

    /**
     * Llista d'adjacència del graph.
     */
    private final List<List<Integer>> adjacencyList;

    /**
     * Número d'arestes del graph.
     */
    private int numEdges;

    /**
     * Constructora de la classe GraphAdjacencyList.
     * @param numVertices Número de vèrtexs del graph.
     */
    public GraphAdjacencyList(int numVertices) {
        super(numVertices);
        adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        numEdges = 0;
    }

    /**
     * Afegeix una aresta al graph.
     * @param source Vèrtex origen.
     * @param destination Vèrtex destí.
     */
    @Override
    public void addEdge(int source, int destination, double weight) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
        ++numEdges;
    }

    /**
     * Retorna el nombre d'arestes del graph.
     * @return Nombre d'arestes del graph.
     */
    public int getNumEdges() {
        return numEdges;
    }

    /**
     * Troba un cicle eulerià en el graph.
     * @return Cicle eulerià.
     */
    public Cycle findEulerianCycle() {
        int[] outDegree = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            outDegree[i] = adjacencyList.get(i).size();
        }
        Deque<Integer> stack = new ArrayDeque<>();
        Cycle eulerianCycle = new Cycle();
        if (numVertices > 0) stack.push(0);
        int current = 0;
        List<List<Integer>> clonedList = new ArrayList<>();
        for (List<Integer> innerList : adjacencyList) {
            clonedList.add(new ArrayList<>(innerList));
        }
        while (!stack.isEmpty()) {
            if (outDegree[current] > 0) {
                stack.push(current);
                int next_v = clonedList.get(current).get(clonedList.get(current).size() - 1);
                --outDegree[current];
                clonedList.get(current).remove(clonedList.get(current).size() - 1);
                current = next_v;
            }
            else {
                eulerianCycle.addEdge(current);
                current = stack.pop();
            }
        }
        eulerianCycle.removeLastElement();
        return eulerianCycle;
    }
}