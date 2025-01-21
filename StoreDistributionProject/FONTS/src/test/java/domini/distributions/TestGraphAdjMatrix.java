package domini.distributions;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class TestGraphAdjMatrix {

    @Test
    public void testAddEdge() {
        // Crear un graf amb 4 vèrtexs
        Graph graph = new GraphAdjacencyMatrix(4);

        // Afegir arestes
        graph.addEdge(0, 1, 1.0);
        graph.addEdge(1, 2, 2.5);
        graph.addEdge(2, 3, 3.0);
        graph.addEdge(3, 0, 4.0);

        GraphAdjacencyMatrix graphAdjMatrix = (GraphAdjacencyMatrix) graph;

        assertEquals(1.0, graphAdjMatrix.getEdge(0, 1), 0.0001);
        assertEquals(2.5, graphAdjMatrix.getEdge(1, 2), 0.0001);
        assertEquals(3.0, graphAdjMatrix.getEdge(2, 3), 0.0001);
        assertEquals(4.0, graphAdjMatrix.getEdge(3, 0), 0.0001);
    }

    @Test
    public void testCalcularCicloHamiltonianoMaximo() {
        Graph graph = new GraphAdjacencyMatrix(4);

        graph.addEdge(0, 1, 1.0);
        graph.addEdge(1, 2, 2.5);
        graph.addEdge(2, 3, 3.0);
        graph.addEdge(3, 0, 4.0);
        graph.addEdge(1, 3, 1.5);

        // Buscar el cicle hamiltonià màxim
        GraphAdjacencyMatrix graphAdjMatrix = (GraphAdjacencyMatrix) graph;
        List<Integer> ciclo = graphAdjMatrix.calcularCicloHamiltonianoMaximo();

        // Verificar que el ciclo no està buit
        assertNotNull(ciclo);
        assertTrue(ciclo.size() > 0);

        //verificar que conté els vèrtexs correctes
        assertTrue(ciclo.contains(0));
        assertTrue(ciclo.contains(1));
        assertTrue(ciclo.contains(2));
        assertTrue(ciclo.contains(3));

        // El cicle ha de ser una permutació dels vèrtexs + el primer al final (per tancar el cicle)
        assertEquals((Integer) ciclo.get(ciclo.size() - 1), ciclo.get(0));
    }

    @Test
    public void testCalcularPesoCiclo() {
        Graph graph = new GraphAdjacencyMatrix(4);

        graph.addEdge(0, 1, 1.0);
        graph.addEdge(1, 2, 2.5);
        graph.addEdge(2, 3, 3.0);
        graph.addEdge(3, 0, 4.0);

        GraphAdjacencyMatrix graphAdjMatrix = (GraphAdjacencyMatrix) graph;
        List<Integer> ciclo = graphAdjMatrix.calcularCicloHamiltonianoMaximo();

        // Calcular el pes del cicle
        double pesoCiclo = graphAdjMatrix.calcularPesoCiclo(ciclo);

        // Verificar que el peso del cicle es correcte
        assertEquals(10.5, pesoCiclo, 0.0001);
    }

    @Test
    public void testGetMax() {
        Graph graph = new GraphAdjacencyMatrix(4);

        graph.addEdge(0, 1, 1.0);
        graph.addEdge(1, 2, 2.5);
        graph.addEdge(2, 3, 3.0);
        graph.addEdge(3, 0, 4.0);
        graph.addEdge(1, 3, 1.5);

        GraphAdjacencyMatrix graphAdjMatrix = (GraphAdjacencyMatrix) graph;
        graphAdjMatrix.calcularCicloHamiltonianoMaximo();

        // Verificar el valor de max (ha de ser el pes màxim trobat)
        assertEquals(10.5, graphAdjMatrix.getMax(), 0.0001);
    }
}
