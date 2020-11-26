package api;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    @Test
    void getSrc() {

        Random random = new Random();
        int src = 0;
        for(int i = 0; i<10000000; i++)
        {
            src = random.nextInt(1000);
            edge_data edge = new EdgeData(src,3,5,"",0);
            assertTrue(edge.getSrc() == src);
        }
        edge_data edge = new EdgeData(5,3,5,"",0);
        assertTrue(edge.getSrc() == 5);

    }

    @Test
    void getDest() {
        Random random = new Random();
        int dest = 0;
        for(int i = 0; i<10000000; i++)
        {
            dest = random.nextInt(1000);
            edge_data edge = new EdgeData(1,dest,5,"",0);
            assertTrue(edge.getDest() == dest);
        }
        edge_data edge = new EdgeData(5,3,5,"",0);
        assertTrue(edge.getDest() == 3);
    }

    @Test
    void getWeight() {
        Random random = new Random();
        int weight = 0;
        for(int i = 0; i<10000000; i++)
        {
            weight = random.nextInt(1000);
            edge_data edge = new EdgeData(1,23,weight,"",0);
            assertTrue(edge.getWeight() == weight);
        }
        edge_data edge = new EdgeData(5,3,15,"",0);
        assertTrue(edge.getWeight() == 15);
    }

    @Test
    void getInfo() {

        String info = "";
        for(int i = 0; i<10; i++)
        {
            info += "a";
            edge_data edge = new EdgeData(1,23,3,info,0);
            assertTrue(edge.getInfo().equals(info));
        }
        edge_data edge = new EdgeData(5,3,15,"OOP",0);
        assertTrue(edge.getInfo().equals("OOP"));

    }

    @Test
    void setInfo() {
        String info = "";
        for(int i = 0; i<10; i++)
        {
            info += "a";
            edge_data edge = new EdgeData(i,i+1,0,"",0);
            edge.setInfo(info);
            assertTrue(edge.getInfo().equals(info));
        }
        edge_data edge = new EdgeData(0,1,0,"",0);
        edge.setInfo("OOP");
        assertTrue(edge.getInfo().equals("OOP"));
    }

    @Test
    void getTag() {
        Random random = new Random();
        int tag = 0;
        for(int i = 0; i<10000000; i++)
        {
            tag = random.nextInt(1000);
            edge_data edge = new EdgeData(1,23,0,"",tag);
            assertTrue(edge.getTag() == tag);
        }
        edge_data edge = new EdgeData(5,3,15,"",10);
        assertTrue(edge.getTag() == 10);
    }

    @Test
    void setTag() {
        Random random = new Random();
        int tag = 0;
        for(int i = 0; i<10000000; i++)
        {
            tag = random.nextInt(1000);
            edge_data edge = new EdgeData(i,i+1,0,"",0);
            edge.setTag(tag);
            assertTrue(edge.getTag() == tag);
        }
        edge_data edge = new EdgeData(0,1,0,"",0);
        edge.setTag(10);
        assertTrue(edge.getTag() == 10);
    }
}