package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> mapNode; //nodes
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdge; //edges "neighbors"
    private int MC;

    public DWGraph_DS()
    {
        mapNode = new HashMap<>();
        MC = 0;
    }

    @Override
    public node_data getNode(int key) {
        return mapNode.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return mapEdge.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        mapNode.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        mapEdge.get(src).put(dest,new EdgeData(w));
    }

    @Override
    public Collection<node_data> getV() {
        return mapNode.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return mapEdge.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        mapNode.remove(key);
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
