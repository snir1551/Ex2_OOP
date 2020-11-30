package api;

import Wrapper.DirectedWeightedGraphJsonWrapper;
import Wrapper.EdgeDataJsonWrapper;
import Wrapper.NodeDataJsonWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.util.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms{

    directed_weighted_graph graph;

    public DWGraph_Algo(directed_weighted_graph graph)
    {
        init(graph);
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copyGraph = new DWGraph_DS(graph);
        return copyGraph;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src) == null || graph.getNode(dest) == null) //if one of the nodes not exist return -1
            return -1;
        dijkstra(graph.getNode(src)); //start dijkstra algorithm on the (src node)
        if(graph.getNode(dest).getWeight() >= Double.MAX_VALUE)
            return -1;
        return graph.getNode(dest).getWeight(); // return the shortest path between them by the tag that contain the distance
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> list = new LinkedList<>(); //list of the path from src to dest
        if(graph.getNode(src) == null || graph.getNode(dest) == null) // if src or dest not exist return null
        {
            return null;
        }
        if(src == dest) //if src equal to dest
        {
            list.add(graph.getNode(src)); //add src to list
            return list; //return list
        }
        HashMap<Integer,node_data> pv = dijkstra(this.graph.getNode(src));//start dijkstra on src and return hashmap that contain the path fathers
        if(graph.getNode(dest).getInfo().equals("WHITE")) //if the dest is WHITE so we didnt move on him so return null
        {
            return null;
        }

        list.addFirst(graph.getNode(dest)); //add to list dest
        node_data t = pv.get(dest); // t = next node

        while(t != null)
        {
            list.addFirst(graph.getNode(t.getKey())); // add the t to list
            t = pv.get(t.getKey()); // t = next node
        }

        return list;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(new DirectedWeightedGraphJsonWrapper(this.graph));

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            DirectedWeightedGraphJsonWrapper graphJsonWrapper = new Gson().fromJson(new FileReader(new File(file)), DirectedWeightedGraphJsonWrapper.class);
            this.graph = new DWGraph_DS();

            for(NodeDataJsonWrapper nodeDataJsonWrapper : graphJsonWrapper.getNodes()) {
                this.graph.addNode(new NodeData(nodeDataJsonWrapper.getKey(),
                        new Point3D(nodeDataJsonWrapper.getLocation().getX(), nodeDataJsonWrapper.getLocation().getY(),
                                    nodeDataJsonWrapper.getLocation().getZ()),
                        nodeDataJsonWrapper.getWeight(), nodeDataJsonWrapper.getInfo(), nodeDataJsonWrapper.getTag()));
            }

            for (EdgeDataJsonWrapper edgeDataJsonWrapper : graphJsonWrapper.getEdges()) {
                this.graph.connect(edgeDataJsonWrapper.getSrc(), edgeDataJsonWrapper.getDest(),
                        edgeDataJsonWrapper.getWeight());
            }
            //private mc
            return true;
        } catch(IllegalStateException | FileNotFoundException e) {
            return false;
        }

    }




    private HashMap<Integer, node_data> dijkstra(node_data node)
    {
        PriorityQueue<node_data> queue = new PriorityQueue<>(); // init PriorityQueue of node_info
        HashMap<Integer,node_data> mapPath = new HashMap<>(); //init HashMap of key: Integer , value: node_info
        for(node_data ni : graph.getV()) //We go through all the vertices
        {
            ni.setWeight(Double.MAX_VALUE); //set their tag to Max_Value
            ni.setInfo("WHITE"); //  set their info to WHITE
            mapPath.put(ni.getKey(),null); //put in our HashMap (father path)  - key: key of the node , value: null
            queue.add(ni); //add to our PriorityQueue the node
        }
        node.setWeight(0); //set tag of our start node to be 0
        queue.remove(node);//decreaseKey - we're removing the node and add him back
        queue.add(node);
        while(!queue.isEmpty()) // while our PriorityQueue is not empty
        {
            node_data n = queue.remove(); //remove our node that we're working on him
            for(edge_data edge : graph.getE(n.getKey())) //We go through all his neighbors
            {
                node_data ni = graph.getNode(edge.getDest());
                if(ni.getInfo().equals("WHITE")) //if he is WHITE We never went through it
                {
                    if(n.getWeight() < Double.MAX_VALUE) { //if tag smallest than MAX_VALUE
                        double t = n.getWeight() + edge.getWeight();
                        if (ni.getWeight() > t) { //if the tag of the neighbor bigger than new path tag so update the neighbor tag
                            ni.setWeight(t); //neighbor tag to be t
                            mapPath.put(ni.getKey(), n); //update the father path
                            queue.remove(ni);//decreaseKey - we're removing the node and add him back
                            queue.add(ni);
                        }
                    }
                }
            }
            n.setInfo("BLACK"); //we finish with the node set info to BLACK
        }
        return mapPath; //return the father path

    }


}
