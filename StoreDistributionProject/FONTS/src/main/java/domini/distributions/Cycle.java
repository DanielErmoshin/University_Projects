package domini.distributions;

import java.util.*;


/**
 * Aquesta classe representa un doubly linked list que modela el cicle eulerià generat pel mètode findEulerianCycle de la classe GraphAdjacencyList.
 */
public class Cycle {

    /**
     * Graf associat que s'utilitza per calcular pesos entre vèrtexs
     */
    private GraphAdjacencyMatrix G;

    /**
     * Referència al primer node del cicle.
     */
    private Vertex head;

    /**
     * Referència al darrer node del cicle.
     */
    private Vertex tail;

    /**
     * Aquesta classe representa un vertex del cicle eulerià.
     */
    private class Vertex {
        /**
         * Identificador del vertex.
         */
        int index;

        /**
         * Referència al vertex anterior.
         */
        Vertex prev;

        /**
         * Referència al vertex següent.
         */
        Vertex next;

        /**
         * Constructora de la classe Vertex.
         * @param index Identificador del vertex.
         */
        Vertex(int index) {
            this.index = index;
            this.prev = null;
            this.next = null;
        }
    }

    /**
     * Afegeix un vertex repetit al cicle.
     */
    private static class repeatedVertex {

        /**
         * Increment de pes resultant de l'eliminació del vèrtex.
         */
        public double value;

        /**
         * Referencia al vertex
         */
        public Vertex vertex;

        /**
         * Identificador del producte a l'esquerra del vèrtex.
         */
        public int left;

        /**
         * Identificador del producte a la dreta del vèrtex.
         */
        public int right;

        /**
         * Constructora de la classe repeatedVertex.
         * @param value Increment de pes resultant de l'eliminació del vèrtex.
         * @param left Identificador del producte a l'esquerra del vèrtex.
         * @param right Identificador del producte a la dreta del vèrtex.
         * @param vertex Referència al vertex.
         */
        public repeatedVertex(double value, int left, int right, Vertex vertex) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.vertex = vertex;
        }
    }

    /**
     * Afegeix un vertex al cicle.0
     * @param vertex
     */
    public void addEdge(int vertex) {
        Vertex newVertex = new Vertex(vertex);
        if (head == null) {
            head = newVertex;
            tail = newVertex;
            head.next = head;
            head.prev = head;
            return;
        }
        tail.next = newVertex;
        newVertex.prev = tail;
        newVertex.next = head;
        head.prev = newVertex;
        tail = newVertex;
    }

    /**
     * Retorna el cicle eulerià.
     * @return Llista d'enters que representa el cicle eulerià.
     */
    public List<Integer> getCycle() {
        List<Integer> result = new ArrayList<>();
        Vertex it = head;
        do {
            result.add(it.index);
            it = it.next;
        }
        while (it != head);
        return result;
    }

    /**
     * Elimina l'últim element del cicle.
     */
    public void removeLastElement() {
        if (tail == null || head == tail) { // Handle empty or single-element list
            head = null;
            tail = null;
            return;
        }
        tail = tail.prev;
        tail.next = head;
        head.prev = tail;
    }

    /**
     * Elimina un element del cicle.
     * @param v Vertex a eliminar.
     */
    private void removeElement(Vertex v) {
        if (tail == null || head == tail) { // Handle empty or single-element list
            head = null;
            tail = null;
            return;
        }
        v.next.prev = v.prev;
        v.prev.next = v.next;
        if (v == head) head = v.next;
        else if (v == tail) tail = v.prev;
    }

    /**
     * Elimina els vèrtexs repetits del cicle.
     * @param G Graf associat.
     * @return Llista d'enters que representa el cicle sense vèrtexs repetits.
     */
    public List<Integer> deleteRepeatedVertex(GraphAdjacencyMatrix G)  {
        if (head == null) { return new ArrayList<Integer> (); }
        this.G = G;
        int[] visited = new int[G.size()];
        int numRepeatedVertex = 0;
        PriorityQueue<repeatedVertex> extract_vertex = new PriorityQueue<>(Comparator.comparingDouble(a -> a.value)); //max heap
        Vertex it = head;
        do {
            if (visited[it.index] > 0) ++numRepeatedVertex;
            ++visited[it.index];
            it = it.next;
        }
        while (it != head);
        do {
            if (visited[it.index] > 1) {
                int l = it.prev.index;
                int r = it.next.index;
                double value = G.getEdge(l, it.index) + G.getEdge(it.index, r) - G.getEdge(l, r);
                extract_vertex.add(new repeatedVertex(value, l, r, it));
            }
            it = it.next;
        }
        while (it != head);
        while (numRepeatedVertex > 0) {
            repeatedVertex node = extract_vertex.poll();
            Vertex v = node.vertex;
            int l = v.prev.index;
            int r = v.next.index;
            if (l == node.left && r == node.right && visited[v.index] > 1) {
                --visited[v.index];
                --numRepeatedVertex;
                removeElement(v);
                if (visited[l] > 1) {
                    int left = v.prev.prev.index;
                    int right = v.next.index;
                    double value = G.getEdge(left, l) + G.getEdge(l, right) - G.getEdge(left, right);
                    extract_vertex.add(new repeatedVertex(value, left, right, v.prev));
                }
                if (visited[r] > 1) {
                    int left = v.prev.index;
                    int right = v.next.next.index;
                    double value = G.getEdge(left, r) + G.getEdge(r, right) - G.getEdge(left, right);
                    extract_vertex.add(new repeatedVertex(value, left, right, v.next));
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        it = head;
        do {
            result.add(it.index);
            it = it.next;
        }
        while (it != head);
        return result;
    }
}







