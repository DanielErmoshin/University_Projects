package domini.distributions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Aquesta classe representa un graph amb matriu d'adjacència.
 */
public class GraphAdjacencyMatrix extends Graph {

    /**
     * Matriu d'adjacència del graph.
     */
    private double[][] adjacencyMatrix;

    /**
     * Pes màxim del cicle hamiltonià.
     */
    private double max;

    /**
     * Constructora de la classe GraphAdjacencyMatrix.
     * @param numVertices Número de vèrtexs del graph.
     */
    public GraphAdjacencyMatrix(int numVertices) {
        super(numVertices);
        adjacencyMatrix = new double[numVertices][numVertices];

        // Crear una fila inicializada a 1
        double[] row = new double[numVertices];
        Arrays.fill(row, 0);
        // Copiar esa fila a todas las filas de la matriz de adyacencia.
        //Asi, inicializamos a 1 la matriz.
        for (int i = 0; i < numVertices; i++) {
            adjacencyMatrix[i] = row.clone();
        }
        this.max = -1.0;
    }

    /**
     * Afegeix una aresta al graph.
     * @param source Vèrtex origen.
     * @param destination Vèrtex destí.
     * @param weight Pes de l'aresta.
     */
    @Override
    public void addEdge(int source, int destination, double weight) {
        adjacencyMatrix[source][destination] = weight;
        adjacencyMatrix[destination][source] = weight;
    }

    /**
     * Retorna el pes de l'aresta.
     * @param source Vèrtex origen.
     * @param destination Vèrtex destí.
     * @return Pes de l'aresta.
     */
    public double getEdge(int source, int destination) {
        return adjacencyMatrix[source][destination];
    }

    /**
     * Calcula el cicle hamiltonià màxim.
     * @return Llista d'enters que representa el cicle hamiltonià màxim.
     */
    public List<Integer> calcularCicloHamiltonianoMaximo() {
        int n = numVertices;
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vertices.add(i);
        }

        List<Integer> mejorCiclo = new ArrayList<>();
        double mayorPeso = -1.0;

        List<List<Integer>> permutaciones = permutar(vertices);
        for (List<Integer> ciclo : permutaciones) {
            double pesoActual = calcularPesoCiclo(ciclo);
            if (pesoActual > mayorPeso) {
                mayorPeso = pesoActual;
                mejorCiclo = ciclo;
            }
        }

        this.max = Math.round(this.max * 1000.0) / 1000.0;
        return mejorCiclo;
    }

    /**
     * Genera totes les permutacions possibles dels vèrtexs.
     * @param vertices Llista de vèrtexs.
     * @return Llista de permutacions.
     */
    private List<List<Integer>> permutar(List<Integer> vertices) {
        List<List<Integer>> permutaciones = new ArrayList<>();
        permutar(vertices, 0, permutaciones);
        return permutaciones;
    }

    /**
     * Funció recursiva per generar les permutacions a partir d'una llista de vèrtexs.
     * @param vertices Llista de vèrtexs.
     * @param start Índex de l'element actual.
     * @param permutaciones Llista de permutacions.
     */
    private void permutar(List<Integer> vertices, int start, List<List<Integer>> permutaciones) {
        if (start == vertices.size() - 1) {
            List<Integer> ciclo = new ArrayList<>(vertices);
            ciclo.add(vertices.get(0)); // para formar un ciclo
            permutaciones.add(ciclo);
            return;
        }
        for (int i = start; i < vertices.size(); i++) {
            swap(vertices, start, i);
            permutar(vertices, start + 1, permutaciones);
            swap(vertices, start, i); // backtrack
        }
    }

    /**
     * Intercanvia dos vertexs en una llista.
     * @param vertices Llista de vèrtexs.
     * @param i Índex del primer vèrtex.
     * @param j Índex del segon vèrtex.
     */
    private void swap(List<Integer> vertices, int i, int j) {
        int temp = vertices.get(i);
        vertices.set(i, vertices.get(j));
        vertices.set(j, temp);
    }

    /**
     * Calcula el pes d'un cicle.
     * @param ciclo Llista d'enters que representa el cicle.
     * @return Pes del cicle.
     */
    public double calcularPesoCiclo(List<Integer> ciclo) {
        double peso = 0.0;
        for (int i = 0; i < ciclo.size() - 1; i++) {
            int u = ciclo.get(i);
            int v = ciclo.get(i + 1);
            peso += adjacencyMatrix[u][v];
        }
        if (peso > this.max) this.max = peso;
        return peso;
    }

    /**
     * Retorna el pes màxim del cicle hamiltonià.
     * @return Pes màxim del cicle hamiltonià.
     */
    public double getMax() {
        return this.max;
    }
}