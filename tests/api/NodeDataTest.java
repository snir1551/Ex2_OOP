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

        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        double weight = 0;
        for(int i = 0; i<10000000; i++)
        {
            weight = rand.nextInt(1000);
            node_data node = new NodeData(i,location,weight,"",0);
            assertTrue(node.getWeight() == weight);
        }
        node_data node = new NodeData(3,location,50,"",0);
        assertTrue(node.getWeight() == 50);

    }

    @Test
    void setWeight() {
        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        double weight = 0;
        for(int i = 0; i<10000000; i++)
        {
            weight = rand.nextInt(1000);
            node_data node = new NodeData(i);
            node.setWeight(weight);
            assertTrue(node.getWeight() == weight);
        }
        node_data node = new NodeData(3);
        node.setWeight(100);
        assertTrue(node.getWeight() == 100);
    }

    @Test
    void getInfo() {
        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        String info = "";
        for(int i = 0; i<10; i++)
        {
            info += "a";
            node_data node = new NodeData(i,location,0,info,0);
            assertTrue(node.getInfo().equals(info));
        }
        node_data node = new NodeData(3,location,0,"OOP",0);
        assertTrue(node.getInfo().equals("OOP"));
    }

    @Test
    void setInfo() {
        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        String info = "";
        for(int i = 0; i<10; i++)
        {
            info += "a";
            node_data node = new NodeData(i);
            node.setInfo(info);
            assertTrue(node.getInfo().equals(info));
        }
        node_data node = new NodeData(3);
        node.setInfo("OOP");
        assertTrue(node.getInfo().equals("OOP"));
    }

    @Test
    void getTag() {
        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        int tag = 0;
        for(int i = 0; i<10000000; i++)
        {
            tag = rand.nextInt(1000);
            node_data node = new NodeData(i,location,0,"",tag);
            assertTrue(node.getTag() == tag);
        }
        node_data node = new NodeData(3,location,50,"",1000);
        assertTrue(node.getTag() == 1000);
    }

    @Test
    void setTag() {
        Point3D location = new Point3D(1,2,3);
        Random rand = new Random();
        int tag = 0;
        for(int i = 0; i<10000000; i++)
        {
            tag = rand.nextInt(1000);
            node_data node = new NodeData(i);
            node.setTag(tag);
            assertTrue(node.getTag() == tag);
        }
        node_data node = new NodeData(3);
        node.setTag(100);
        assertTrue(node.getTag() == 100);
    }
}