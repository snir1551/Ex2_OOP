import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

class DWGraph_AlgoTest {

    //pictures/graph1
    /*
        our graph:
                [1]->[7]->[5]
                [2]
                [5]->[7]->[1]
     */
    private directed_weighted_graph buildFirstGraph()
    {
        directed_weighted_graph graph = new DWGraph_DS();
        for(int i = 0; i < 8; i++)
        {
            node_data node = new NodeData(i);
            graph.addNode(node);
        }
        graph.connect(0,1,10);
        graph.connect(1,2,20);
        graph.connect(2,3,30);
        graph.connect(3,2,10);
        graph.connect(3,4,21);
        graph.connect(4,3,5);
        graph.connect(4,5,7);
        graph.connect(5,6,9);
        graph.connect(6,5,30);
        graph.connect(7,6,40);
        graph.connect(7,0,1);
        graph.connect(1,7,2);
        graph.connect(1,6,9);
        graph.connect(2,5,3);
        return graph;
    }

    @Test
    void init() {
        dw_graph_algorithms graph_algo = new DWGraph_Algo(null);
        directed_weighted_graph graph = buildFirstGraph();
        graph_algo.init(graph);
        assertEquals(graph,graph_algo.getGraph());
        graph.addNode(new NodeData(1000));
        assertEquals(graph,graph_algo.getGraph());
        directed_weighted_graph newGraph = buildFirstGraph();
        assertNotEquals(graph,newGraph);
    }

    @Test
    void getGraph() {
        dw_graph_algorithms graph_algo = new DWGraph_Algo(buildFirstGraph());
        directed_weighted_graph graph = graph_algo.getGraph();
    }

    @Test
    void copy() {
        dw_graph_algorithms graph_algo = new DWGraph_Algo(buildFirstGraph());
        directed_weighted_graph graph1 = graph_algo.copy();
        directed_weighted_graph graph2 = graph_algo.copy();
        assertEquals(graph1,graph2);
        graph1.connect(6,7,3);
        assertNotEquals(graph1,graph2);
        assertNotNull(graph1.getEdge(6,7));
        assertNull(graph2.getEdge(6,7));
    }

    @Test
    void isConnected() {
        dw_graph_algorithms graphAlgo = new DWGraph_Algo(buildFirstGraph());
        assertFalse(graphAlgo.isConnected());
        directed_weighted_graph graph = graphAlgo.getGraph();
        graph.connect(6,7,2);
        assertTrue(graphAlgo.isConnected());
        System.out.println(graph);
    }

    @Test
    void shortestPathDist() {
        double shotestpath = 0;
        dw_graph_algorithms graphAlgo = new DWGraph_Algo(buildFirstGraph());
        shotestpath = graphAlgo.shortestPathDist(0,1);
        assertTrue(shotestpath == 10);
        shotestpath = graphAlgo.shortestPathDist(0,2);
        assertTrue(shotestpath == 30);
        shotestpath = graphAlgo.shortestPathDist(0,3);
        assertTrue(shotestpath == 60);
        shotestpath = graphAlgo.shortestPathDist(0,4);
        assertTrue(shotestpath == 81);
        shotestpath = graphAlgo.shortestPathDist(0,5);
        assertTrue(shotestpath == 33);
        shotestpath = graphAlgo.shortestPathDist(0,6);
        assertTrue(shotestpath == 19);
        shotestpath = graphAlgo.shortestPathDist(0,7);
        assertTrue(shotestpath == 12);
        shotestpath = graphAlgo.shortestPathDist(0,0);
        assertTrue(shotestpath == 0);
        shotestpath = graphAlgo.shortestPathDist(1,0);
        assertTrue(shotestpath == 3);
        shotestpath = graphAlgo.shortestPathDist(6,7);
        assertTrue(shotestpath == -1);
        shotestpath = graphAlgo.shortestPathDist(6,1);
        assertTrue(shotestpath == -1);
        shotestpath = graphAlgo.shortestPathDist(6,4);
        assertTrue(shotestpath == -1);
        shotestpath = graphAlgo.shortestPathDist(6,5);
        assertTrue(shotestpath == 30);



    }

    @Test
    void shortestPath() {

    	directed_weighted_graph graph = new DWGraph_DS();
        node_data node1 = new NodeData(1);
        node_data node2 = new NodeData(2);
        node_data node3 = new NodeData(3);
        node_data node4 = new NodeData(4);
        node_data node5 = new NodeData(5);
        node_data node6 = new NodeData(6);
        node_data node7 = new NodeData(7);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.connect(1,2,1);
        graph.connect(2,3,2);
        graph.connect(3,4,3);
        graph.connect(3,5,4);
        graph.connect(4,1,4);
        graph.connect(4,6,4);
        graph.connect(1,6,5);
        graph.connect(5,6,5);
        graph.connect(6,5,6);
        graph.connect(6,7,7);
        
        assertTrue(graph.nodeSize() == 7);
        assertTrue(graph.edgeSize() == 10);
        assertTrue(graph.getMC() == 17);
    
        
        dw_graph_algorithms g=new DWGraph_Algo(graph);
        List<node_data> list=g.shortestPath(node1.getKey(),node5.getKey());
        

        assertTrue(list.size() == 4);

        
    }

    @Test
    void save_load() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node = new NodeData(1);
        node_data node1 = new NodeData(2);
        graph.addNode(node);
        graph.addNode(node1);
        graph.connect(node1.getKey(),node.getKey(),20);
        dw_graph_algorithms graphAlgo = new DWGraph_Algo(graph);
        graphAlgo.save("file1");

        dw_graph_algorithms graphAlgo1 = new DWGraph_Algo(null);
        graphAlgo1.load("file1");
        directed_weighted_graph g = graphAlgo1.copy();
        System.out.println(g.edgeSize());
    }

}