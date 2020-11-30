package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node = new NodeData(1);
        node_data node1 = new NodeData(2);
        graph.addNode(node);
        graph.addNode(node1);
        graph.connect(node1.getKey(),node.getKey(),20);
        dw_graph_algorithms graphAlgo = new DWGraph_Algo(graph);
        graphAlgo.save("file1");
    }

    @Test
    void load() {
        dw_graph_algorithms graphAlgo = new DWGraph_Algo(null);
        graphAlgo.load("D:\\אוניברסיטת אריאל\\תואר\\מונחה עצמים\\חדש\\מטלה 3\\Ariel_OOP_2020\\Ex2\\file1");
    }
}