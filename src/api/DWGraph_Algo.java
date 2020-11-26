package api;

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
        if(graph.nodeSize() == 0)
            return true;

        BFS(graph.getV().iterator().next());

        for(node_data node : graph.getV())
        {
            if(node.getInfo().equals("WHITE"))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

//    private HashMap<Integer, node_data> dijkstra(node_data node)
//    {
//        PriorityQueue<node_data> queue = new PriorityQueue<>(); // init PriorityQueue of node_info
//        HashMap<Integer,node_data> mapPath = new HashMap<>(); //init HashMap of key: Integer , value: node_info
//        for(node_data ni : graph.getV()) //We go through all the vertices
//        {
//            ni.setTag(Integer.MAX_VALUE); //set their tag to Max_Value
//            ni.setInfo("WHITE"); //  set their info to WHITE
//            mapPath.put(ni.getKey(),null); //put in our HashMap (father path)  - key: key of the node , value: null
//            queue.add(ni); //add to our PriorityQueue the node
//        }
//        node.setTag(0); //set tag of our start node to be 0
//        queue.remove(node);//decreaseKey - we're removing the node and add him back
//        queue.add(node);
//        while(!queue.isEmpty()) // while our PriorityQueue is not empty
//        {
//            node_data n = queue.remove(); //remove our node that we're working on him
//            for(edge_data edge : graph.getE(n.getKey())) //We go through all his neighbors
//            {
//                node_data ni = graph.getNode(edge.getDest());
//                if(ni.getInfo().equals("WHITE")) //if he is WHITE We never went through it
//                {
//                    if(n.getTag() < Double.MAX_VALUE) { //if tag smallest than MAX_VALUE
//                        double t = n.getTag() + edge.getWeight();
//                        if (ni.getTag() > t) { //if the tag of the neighbor bigger than new path tag so update the neighbor tag
//                            ni.setTag((int)t); //neighbor tag to be t
//                            mapPath.put(ni.getKey(), n); //update the father path
//                            queue.remove(ni);//decreaseKey - we're removing the node and add him back
//                            queue.add(ni);
//                        }
//                    }
//                }
//            }
//            n.setInfo("BLACK"); //we finish with the node set info to BLACK
//        }
//        return mapPath; //return the father path
//
//    }


    public HashMap<Integer,node_data> BFS(node_data n) // O(|V|+|E|)
    {
        HashMap<Integer,node_data> bfsMap = new HashMap<>();
        for(node_data node : graph.getV())
        {
            node.setInfo("WHITE"); // all nodes WHITE
            node.setTag(Integer.MAX_VALUE); // all nodes MAX_VALUE
            bfsMap.put(node.getKey(),null);
        }
        n.setInfo("Gray");
        n.setTag(0);
        Queue<node_data> q = new LinkedList<>();
        q.add(n);

        while(!q.isEmpty())
        {
            node_data newNode = q.remove();
            for(edge_data edge : graph.getE(newNode.getKey()))
            {
                node_data node = graph.getNode(edge.getDest());
                if(node.getInfo().equals("WHITE"))
                {
                    node.setInfo("GRAY");
                    node.setTag(newNode.getTag() + 1);
                    q.add(node);
                    bfsMap.put(node.getKey(),newNode);
                }
            }
            newNode.setInfo("BLACK");
        }
        return bfsMap;
    }
}
