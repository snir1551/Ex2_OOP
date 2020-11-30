package api;

import JsonWrapper.DirectedWeightedGraphJsonWrapper;
import JsonWrapper.EdgeDataJsonWrapper;
import JsonWrapper.NodeDataJsonWrapper;
import com.google.gson.Gson;
import gameClient.util.Point3D;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> mapNode; //nodes
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdgeOut; //edges "neighbors"
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdgeIn; //edges "neighbors"
    private int MC; //num of changes in the graph
    private int edgeSize; // num of edge in the graph

    public DWGraph_DS()
    {
        mapNode = new HashMap<>();
        mapEdgeOut = new HashMap<>();
        mapEdgeIn = new HashMap<>();
        edgeSize = 0;
        MC = 0;
    }

    public DWGraph_DS(directed_weighted_graph graph)
    {
        this();
        for(node_data n : graph.getV()) //go through all the vertices of the wgraph
        {
            addNode(new NodeData(n)); //add them to this graph
        }
        for(node_data nd : graph.getV()) // go through all the vertices of the wgraph
        {
            for(edge_data n : graph.getE(nd.getKey())) // Go through all the vertices of the neighbors of the wgraph
            {
                this.connect(n.getSrc(),n.getDest(),n.getWeight()); //add for this graph edge like in wgraph
            }
        }
        this.MC = graph.getMC(); //count of changes in the graph need to be like wgraph
    }

    public DWGraph_DS(DirectedWeightedGraphJsonWrapper graphJsonWrapper)
    {
        for(NodeDataJsonWrapper nodeDataJsonWrapper : graphJsonWrapper.getNodes()) {
            addNode(new NodeData(nodeDataJsonWrapper));
        }
        for (EdgeDataJsonWrapper edgeDataJsonWrapper : graphJsonWrapper.getEdges()) {
            connect(edgeDataJsonWrapper.getSrc(), edgeDataJsonWrapper.getDest(),
                    edgeDataJsonWrapper.getWeight());
        }
        MC = graphJsonWrapper.getMc();
    }

    @Override
    public node_data getNode(int key) {
        return mapNode.get(key); //return node_data by his key
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(existNode(src) && existNode(dest) && hasEdge(src,dest))
            return mapEdgeOut.get(src).get(dest);
        return null;

    }

    @Override
    public void addNode(node_data n) {
        if(!existNode(n.getKey()))
        {
            mapNode.put(n.getKey(),n); //put new node in the dwgraph
            mapEdgeOut.put(n.getKey(),new HashMap<>());
            mapEdgeIn.put(n.getKey(),new HashMap<>());
            ++MC;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(existNode(src) && existNode(dest) && w >= 0)
        {
            if(!hasEdge(src,dest))
            {
                ++edgeSize;
            }
            mapEdgeOut.get(src).put(dest,new EdgeData(src,dest,w));
            mapEdgeIn.get(dest).put(src,new EdgeData(src,dest,w));
            ++MC;
        }

    }

    @Override
    public Collection<node_data> getV() {
        return mapNode.values(); //
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(existNode(node_id))
            return mapEdgeOut.get(node_id).values();
        return null;
    }

    //O(k)
    @Override
    public node_data removeNode(int key) {
        if(existNode(key))
        {
            for(Integer i : mapEdgeIn.get(key).keySet())
            {
                removeEdge(i,key); //O(1)
            }
            for(Integer i : mapEdgeOut.get(key).keySet())
            {
                removeEdge(key,i); // O(1)
            }
            mapEdgeIn.remove(key);
            mapEdgeOut.remove(key);
        }
        return mapNode.remove(key);
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(existNode(src) && hasEdge(src,dest))
        {
            --edgeSize;
            ++MC;
            mapEdgeIn.get(dest).remove(src);
            return mapEdgeOut.get(src).remove(dest);
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return mapNode.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return MC;
    }

    private boolean hasEdge(int node1,int node2)
    {
        if(mapEdgeOut.get(node1).containsKey(node2))
            return true;
        else
            return false;
    }

    private boolean existNode(int node)
    {
        return mapNode.containsKey(node);
    }


}
