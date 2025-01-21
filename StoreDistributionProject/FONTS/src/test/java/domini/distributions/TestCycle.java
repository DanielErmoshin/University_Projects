package domini.distributions;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class TestCycle {

    @Test
    public void testAddEdgesNoRepeatedVertices() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(2);
        cycle.addEdge(3);
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                G.addEdge(i, j, 1.0);
            }
        }
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteRepeatedVertices() {
        Cycle cycle = new Cycle();
        for (int i = 0; i < 5; i++) {
            cycle.addEdge(i);
        }
        int numVertices = 5;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 2, 1.0);
        G.addEdge(2, 3, 1.0);
        G.addEdge(3, 1, 1.0);
        G.addEdge(1, 4, 1.0);
        G.addEdge(0, 2, 0.5);
        G.addEdge(2, 4, 0.5);
        G.addEdge(3, 4, 0.0);
        G.addEdge(0, 3, 0.5);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            expected.add(i);
        }
        assertEquals(expected, result);
    }

    @Test
    public void testMultipleRepeatedVertices() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(2);
        cycle.addEdge(1);
        cycle.addEdge(3);
        cycle.addEdge(2);
        cycle.addEdge(4);
        int numVertices = 5;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 2, 1.0);
        G.addEdge(1, 3, 2.0);
        G.addEdge(2, 3, 2.0);
        G.addEdge(2, 4, 1.0);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        assertEquals(result.size(), result.stream().distinct().count());
    }

    //Consecutive nodes are not tested because nodes don't have edges to themselves

    @Test
    public void testRepeatedVerticesAtFirstAndThirdPositions() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(0);
        cycle.addEdge(2);
        cycle.addEdge(3);
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 0, 1.0);
        G.addEdge(0, 2, 0.5);
        G.addEdge(2, 3, 1.0);
        G.addEdge(3, 0, 1.0);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, result);
    }

    @Test
    public void testRepeatedVerticesAtFirstAndSecondLastPositions() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(2);
        cycle.addEdge(0);
        cycle.addEdge(3);
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 2, 1.0);
        G.addEdge(2, 0, 0.5);
        G.addEdge(0, 3, 1.0);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, result);
    }

    @Test
    public void testRepeatedVerticesAtLastAndSecondPositions() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(2);
        cycle.addEdge(3);
        cycle.addEdge(1);
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 2, 1.0);
        G.addEdge(2, 3, 1.0);
        G.addEdge(3, 1, 0.5);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, result);
    }

    @Test
    public void testRepeatedVerticesAtLastAndThirdLastPositions() {
        Cycle cycle = new Cycle();
        cycle.addEdge(0);
        cycle.addEdge(1);
        cycle.addEdge(2);
        cycle.addEdge(3);
        cycle.addEdge(2);
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        G.addEdge(0, 1, 1.0);
        G.addEdge(1, 2, 1.0);
        G.addEdge(2, 3, 1.0);
        G.addEdge(3, 2, 1.0);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveLastElement() {
        int numVertices = 4;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        Cycle cycle = new Cycle();
        for (int i = 0; i < 4; ++i) {
            cycle.addEdge(i);
        }
        cycle.removeLastElement();
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            expected.add(i);
        }
        assertEquals(expected, result);
    }

    @Test
    public void testEmptyCycle() {
        Cycle cycle = new Cycle();
        int numVertices = 0;
        GraphAdjacencyMatrix G = new GraphAdjacencyMatrix(numVertices);
        List<Integer> result = cycle.deleteRepeatedVertex(G);
        assertTrue(result.isEmpty());
    }

}