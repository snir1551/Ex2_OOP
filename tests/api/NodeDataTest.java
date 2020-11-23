package api;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getKey() {
        Random rand = new Random();
        int key = 0;
        for(int i = 0; i<10000000; i++)
        {
            key = rand.nextInt(1000);
            node_data node = new NodeData(key);
            assertTrue(node.getKey() == key);
        }


    }

    @Test
    void getLocation() {
        Point3D g = new Point3D(500,10,50);
        node_data n = new NodeData(3,g);
        Random rand = new Random();
        double x = 0;
        double y = 0;
        double z = 0;
        for(int i = 0; i<10000000; i++)
        {
            x = rand.nextInt(1000);
            y = rand.nextInt(1000);
            z = rand.nextInt(1000);
            node_data node = new NodeData(i,new Point3D(x,y,z));
            assertTrue(node.getLocation().x() == x);
            assertTrue(node.getLocation().y() == y);
            assertTrue(node.getLocation().z() == z);
        }
        node_data node = new NodeData(33,new Point3D(1,5,1000));
        assertTrue(node.getLocation().x() == 1);
        assertTrue(node.getLocation().y() == 5);
        assertTrue(node.getLocation().z() == 1000);
        assertFalse(node.getLocation().x() == 5);
        assertFalse(node.getLocation().x() == 2);
        assertFalse(node.getLocation().y() == 1);
    }

    @Test
    void setLocation() {
        Point3D g = new Point3D(500,10,50);
        node_data n = new NodeData(3,g);
        Random rand = new Random();
        double x = 0;
        double y = 0;
        double z = 0;
        for(int i = 0; i<10000000; i++)
        {
            x = rand.nextInt(1000);
            y = rand.nextInt(1000);
            z = rand.nextInt(1000);
            node_data node = new NodeData(i);
            node.setLocation(new Point3D(x,y,z));
            assertTrue(node.getLocation().x() == x);
            assertTrue(node.getLocation().y() == y);
            assertTrue(node.getLocation().z() == z);
        }
    }

    @Test
    void getWeight() {

    }

    @Test
    void setWeight() {
    }

    @Test
    void getInfo() {
    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }
}