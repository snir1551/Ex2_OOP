import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    void getNode() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node = new NodeData(2);
        node_data node1 = new NodeData(10);
        node_data node2 = new NodeData(20);
        node_data node3 = new NodeData(22);
        node_data node4 = new NodeData(3);
        assertNull(graph.getNode(node.getKey()));
        graph.addNode(node);
        assertEquals(graph.getNode(node.getKey()),node);
        assertNull(graph.getNode(node1.getKey()));
        graph.addNode(node1);
        assertEquals(graph.getNode(node1.getKey()),node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        assertEquals(graph.getNode(node.getKey()),node);
        assertEquals(graph.getNode(node1.getKey()),node1);
        assertEquals(graph.getNode(node2.getKey()),node2);
        assertEquals(graph.getNode(node3.getKey()),node3);
        assertEquals(graph.getNode(node4.getKey()),node4);


    }

    @Test
    void getEdge() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node0 = new NodeData(0);
        node_data node1 = new NodeData(1);
        node_data node2 = new NodeData(2);
        node_data node3 = new NodeData(3);
        node_data node4 = new NodeData(4);
        node_data node5 = new NodeData(5);
        assertNull(graph.getEdge(node0.getKey(),node1.getKey()));
        graph.addNode(node0);
        graph.addNode(node1);
        graph.connect(node0.getKey(),node1.getKey(),20);
        edge_data edge = graph.getEdge(node0.getKey(),node1.getKey());
        assertTrue(edge.getSrc() == node0.getKey());
        assertTrue(edge.getDest() == node1.getKey());
        assertTrue(edge.getSrc() == node0.getKey());

    }

    @Test
    void addNode() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node;
        int countnodes = 10000;
        for(int i = 0; i < countnodes; i++)
        {
            node = new NodeData(i);
            graph.addNode(node);
        }
        assertTrue(graph.nodeSize() == countnodes);
        node = new NodeData(20000000);
        graph.addNode(node);
        assertTrue(graph.nodeSize() == (countnodes+1));
    }

    @Test
    void connect() {
        directed_weighted_graph graph = new DWGraph_DS();
        node_data node1 = new NodeData(1);
        node_data node2 = new NodeData(2);
        node_data node3 = new NodeData(3);
        node_data node4 = new NodeData(4);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.connect(node1.getKey(),node2.getKey(),50);
        edge_data edge = graph.getEdge(node1.getKey(),node2.getKey());
        assertTrue(edge.getSrc() == node1.getKey());
        assertTrue(edge.getDest() == node2.getKey());
        assertTrue(edge.getWeight() == 50);
        edge = graph.getEdge(node4.getKey(),node1.getKey());
        assertNull(edge);
        edge = graph.getEdge(node1.getKey(),node4.getKey());
        assertNull(edge);
        graph.connect(node4.getKey(),node1.getKey(),10);
        edge = graph.getEdge(node4.getKey(),node1.getKey());
        assertTrue(edge.getSrc() == node4.getKey());
        assertTrue(edge.getDest() == node1.getKey());
        assertTrue(edge.getWeight() == 10);
        edge = graph.getEdge(node1.getKey(),node4.getKey());
        assertNull(edge);



    }

    @Test
    void getV() {

        directed_weighted_graph graph = new DWGraph_DS();
        for(int i = 10; i < 20; i++)
        {
            node_data node = new NodeData(i);
            graph.addNode(node);
        }
        int j = 0;
        for(node_data n : graph.getV())
        {
            assertEquals(graph.getNode(n.getKey()), n);
            ++j;
        }
        assertTrue(graph.nodeSize() == j);
    }

    @Test
    void getE() {
        directed_weighted_graph graph = new DWGraph_DS();
        for(int i = 10; i < 20; i++)
        {
            node_data node = new NodeData(i);
            graph.addNode(node);
        }
        for(int i = 10; i < 20; i++)
        {
            graph.connect(10,i,30+i);
        }
        int i = 10;
        for(edge_data ed : graph.getE(10))
        {
//            assertEquals(graph.getEdge(10,i),ed);
        }
//        assertTrue(graph.edgeSize() == 11);


    }

    @Test
    void removeNode() {
        directed_weighted_graph graph = new DWGraph_DS();
        for(int i = 0; i < 5; i++)
        {
            node_data node = new NodeData(i);
            graph.addNode(node);
        }
        graph.connect(0,1,0);
        graph.connect(1,0,1);
        graph.connect(0,2,2);
        graph.connect(3,0,4);
        graph.connect(4,3,5);
        graph.connect(3,4,6);
        assertTrue(graph.nodeSize() == 5);
        assertTrue(graph.edgeSize() == 6);
        graph.removeNode(0);
        assertTrue(graph.nodeSize() == 4);
        assertTrue(graph.edgeSize() == 2);
        assertNull(graph.getEdge(0,1));
        assertNull(graph.getEdge(1,0));
        assertNull(graph.getEdge(0,2));
        assertNull(graph.getEdge(3,0));
        assertNotNull(graph.getEdge(4,3));
        assertNotNull(graph.getEdge(3,4));


    }

    @Test
    void removeEdge() {
        directed_weighted_graph graph = new DWGraph_DS();
        for(int i = 0; i < 5; i++)
        {
            node_data node = new NodeData(i);
            graph.addNode(node);
        }
        assertTrue(graph.edgeSize() == 0);
        graph.connect(0,1,0);
        assertTrue(graph.edgeSize() == 1);

    }

    @Test
    void nodeSize() {
    	
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
        graph.connect(5,6,5);
        graph.connect(6,5,6);
        graph.connect(6,7,7);
        assertTrue(graph.nodeSize() == 7);
        graph.removeNode(7);
        assertTrue(graph.nodeSize() == 6);
        graph.addNode(node7);
        graph.connect(6,7,7);
        assertTrue(graph.nodeSize() == 7);
          	
    }
    

    @Test
    void edgeSize() {
    	
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
        graph.connect(5,6,5);
        graph.connect(6,5,6);
        graph.connect(6,7,7);
        
        assertTrue(graph.nodeSize() == 7);
        assertTrue(graph.edgeSize() == 7);
        graph.removeNode(7);
        graph.removeNode(6);
        graph.removeNode(5);
        graph.removeNode(3);
       
        assertTrue(graph.nodeSize() == 3);
        assertTrue(graph.edgeSize() == 1);


    	
    }

    @Test
    void getMC() {
    	
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
        graph.connect(5,6,5);
        graph.connect(6,5,6);
        graph.connect(6,7,7);
        
        assertTrue(graph.nodeSize() == 7);
        assertTrue(graph.edgeSize() == 7);
        assertTrue(graph.getMC() == 14);
        assertTrue(graph.edgeSize() == 7);
        
        graph.removeNode(6);
        System.out.println(graph.getMC());
        assertTrue(graph.getMC() == 18);
      //  System.out.println(graph.getMC());
        
        
    	directed_weighted_graph graph2 = new DWGraph_DS();
    	node_data nod1 = new NodeData(1);
        node_data nod2 = new NodeData(2);
        node_data nod3 = new NodeData(3);
        node_data nod4 = new NodeData(4);    
        graph2.addNode(nod1);
        graph2.addNode(nod2);
        graph2.addNode(nod3);
        graph2.addNode(nod4);
        assertTrue(graph2.nodeSize() == 4);
        assertTrue(graph2.getMC() == 4);
        graph2.removeNode(1);
        assertTrue(graph2.getMC() == 5);

        
        
        
        


        
        
    	
    }
}