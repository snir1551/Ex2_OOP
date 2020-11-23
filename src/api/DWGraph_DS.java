package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> mapNode; //nodes
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdge; //edges "neighbors"
    private int MC;
    private int edgeSize;

    public DWGraph_DS()
    {
        mapNode = new HashMap<>();
        mapEdge = new HashMap<>();
        edgeSize = 0;
        MC = 0;
    }

    @Override
    public node_data getNode(int key) {
        return mapNode.get(key); //return node_data by his key
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return mapEdge.get(src).get(dest); //return edge_data by src and dest
    }

    @Override
    public void addNode(node_data n) {
        mapNode.put(n.getKey(),n); //put new node in the dwgraph
    }

    @Override
    public void connect(int src, int dest, double w) {
        mapEdge.get(src).put(dest,new EdgeData(w)); //add edge between src to dest
        mapEdge.get(dest).put(src,new EdgeData(w)); //add edge between dest to src
    }

    @Override
    public Collection<node_data> getV() {
        return mapNode.values(); //
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        LinkedList<edge_data> listNeighbors = new LinkedList<edge_data>();
        for(Integer n : mapEdge.get(node_id).keySet())
        {
            listNeighbors.addLast(mapEdge.get(node_id).get(n));
        }
        return listNeighbors;
    }

    @Override
    public node_data removeNode(int key) {
        return mapNode.remove(key);
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return mapEdge.get(src).remove(dest);
    }

    @Override
    public int nodeSize() {
        return mapNode.size();
    }

    @Override
    public int edgeSize() {
        return mapEdge.size();
    }

    @Override
    public int getMC() {
        return 0;
    }
}
