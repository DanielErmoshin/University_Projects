package domini.distributions;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


public class TestGraphAdjacencyList {
    @Test
    public void testAddEdgeAndGetNumEdges() {
        int numVertices = 3;
        GraphAdjacencyList graph = new GraphAdjacencyList(numVertices);
        assertEquals(0, graph.getNumEdges());
        graph.addEdge(0, 1, 1.0);
        assertEquals(1, graph.getNumEdges());
        graph.addEdge(1, 2, 1.0);
        assertEquals(2, graph.getNumEdges());
        graph.addEdge(2, 0, 1.0);
        assertEquals(3, graph.getNumEdges());
    }
    @Test
    public void testFindEulerianCycle() {
        int numVertices = 4;
        GraphAdjacencyList graph = new GraphAdjacencyList(numVertices);
        graph.addEdge(0, 1, 1.0);
        graph.addEdge(0, 2, 1.0);
        graph.addEdge(0, 3, 1.0);
        graph.addEdge(1, 2, 1.0);
        graph.addEdge(2, 3, 1.0);
        graph.addEdge(1, 3, 1.0);
        Cycle eulerianCycle = graph.findEulerianCycle();
        Cycle expected = new Cycle();
        expected.addEdge(0);
        expected.addEdge(1);
        expected.addEdge(0);
        expected.addEdge(2);
        expected.addEdge(1);
        expected.addEdge(2);
        expected.addEdge(0);
        expected.addEdge(3);
        expected.addEdge(2);
        expected.addEdge(3);
        expected.addEdge(1);
        expected.addEdge(3);
        assertEquals(expected.getCycle(), eulerianCycle.getCycle());
    }
}