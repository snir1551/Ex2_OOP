import api.DWGraph_Algo;
import api.DWGraph_DS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;


public class TestTimeAlgorithms {
    @Test
    void TimeShortestPath() {
        DWGraph_Algo graph_algo = new DWGraph_Algo(null);
        int[] nodes = {10, 100, 1000, 10000, 20000, 30000};
        int[] edges = {80, 800, 8000, 80000, 160000, 240000};
        long start,end;
        double dt;
        for(int i = 0; i < nodes.length; i++)
        {
            graph_algo.loadNx("data//Graphs_on_circle//G_" + nodes[i] + "_"+edges[i]+"_1.json");
            start = new Date().getTime();
            graph_algo.shortestPath(1,3);
            end = new Date().getTime();
            dt = (end-start)/1000.0;
            System.out.println("time Shortest path MyGraph: " + "{" + nodes[i] + "_" + edges[i]+ "}" + ": " + dt);
            start = new Date().getTime();
            graph_algo.componnts();
            end = new Date().getTime();
            dt = (end-start)/1000.0;
            System.out.println("time components MyGraph: " + "{" + nodes[i] + "_" + edges[i]+ "}" + ": " + dt);
            start = new Date().getTime();
            graph_algo.componnt(1);
            end = new Date().getTime();
            dt = (end-start)/1000.0;
            System.out.println("time component MyGraph: " + "{" + nodes[i] + "_" + edges[i]+ "}" + ": " + dt);
        }


    }
}
