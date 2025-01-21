package domini.distributions;

/**
 * Aquesta classe representa un graph.
 */
public abstract class Graph {
    /**
     * Número de vèrtexs del graph.
     */
    protected int numVertices;

    /**
     * Constructora de la classe Graph.
     * @param numVertices Número de vèrtexs del graph.
     */
    public Graph(int numVertices) {
        this.numVertices = numVertices;
    }

    /**
     * Afegeix una aresta al graph.
     * @param source Vèrtex origen.
     * @param destination Vèrtex destí.
     */
    abstract public void addEdge(int source, int destination, double weight);

    /**
     * Retorna el nombre de vèrtexs del graph.
     * @return Nombre de vèrtexs del graph.
     */
    public int size() {
        return numVertices;
    }
}