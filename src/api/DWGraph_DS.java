package api;

import JsonWrapper.DirectedWeightedGraphJsonWrapper;
import JsonWrapper.EdgeDataJsonWrapper;
import JsonWrapper.NodeDataJsonWrapper;

import java.util.*;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> mapNode; //nodes
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdgeOut; //edges that out from node
    private HashMap<Integer,HashMap<Integer,edge_data>> mapEdgeIn; //edges that into node
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
                edge_data edge = new EdgeData(n); //used copy constructor
                connect(edge); // used the private connect that get edge_data
            }
        }
        this.MC = graph.getMC(); //count of changes in the graph need to be like wgraph
    }

    public DWGraph_DS(DirectedWeightedGraphJsonWrapper graphJsonWrapper)
    {
        this();
        for(NodeDataJsonWrapper nodeDataJsonWrapper : graphJsonWrapper.getNodes()) {
            addNode(new NodeData(nodeDataJsonWrapper));
        }
        for (EdgeDataJsonWrapper edgeDataJsonWrapper : graphJsonWrapper.getEdges()) {
            edge_data edge = new EdgeData(edgeDataJsonWrapper);
            connect(edge);
        }
        MC = graphJsonWrapper.getMc();
    }

    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        return mapNode.get(key); //return node_data by his key
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(existNode(src) && existNode(dest) && hasEdge(src,dest))
            return mapEdgeOut.get(src).get(dest);
        return null;

    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(!existNode(n.getKey()))
        {
            mapNode.put(n.getKey(),n); //put new node in the dwgraph
            ++MC;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {

        connect(new EdgeData(src,dest,w));

    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return mapNode.values(); //
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if(existNode(node_id))
        {
            if(mapEdgeOut.containsKey(node_id))
                return mapEdgeOut.get(node_id).values();
            else
                return new HashSet<edge_data>();
        }
        return null;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        if(existNode(key))
        {
            List<Integer> list = new ArrayList<>(mapEdgeIn.get(key).keySet());
            for(Integer i : list)
            {
                removeEdge(i,key); //O(1)
            }
            list = new ArrayList<>(mapEdgeOut.get(key).keySet());
            for(Integer i : list)
            {
                removeEdge(key,i); // O(1)
            }
            mapEdgeIn.remove(key);
            mapEdgeOut.remove(key);
        }
        return mapNode.remove(key);
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
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

    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return mapNode.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return MC == that.MC &&
                edgeSize == that.edgeSize &&
                Objects.equals(mapNode, that.mapNode) &&
                Objects.equals(mapEdgeOut, that.mapEdgeOut) &&
                Objects.equals(mapEdgeIn, that.mapEdgeIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapNode, mapEdgeOut, mapEdgeIn, MC, edgeSize);
    }

    @Override
    public String toString()
    {
        String ans = "Nodes: [\n";

        for(node_data n : getV())
        {
            ans += n.toString() + "]\n";
        }
        ans += "]\n";
        ans += "Edge: [\n";
        for(node_data n : getV())
        {
            for(edge_data e : getE(n.getKey()))
            {
                ans += e.toString() + "]\n";
            }

        }

        return ans;
    }

    private boolean hasEdge(int node1, int node2)
    {
        if(!mapEdgeOut.containsKey(node1))
            return false;
        if(mapEdgeOut.get(node1).containsKey(node2))
            return true;
        else
            return false;
    }

    private boolean existNode(int node)
    {
        return mapNode.containsKey(node);
    }

    private void connect(edge_data edge)
    {
        if(existNode(edge.getSrc()) && existNode(edge.getDest()) && edge.getWeight() >= 0)
        {
            if(!mapEdgeOut.containsKey(edge.getSrc()))
                mapEdgeOut.put(edge.getSrc(),new HashMap<>());

            if(!mapEdgeIn.containsKey(edge.getDest()))
                mapEdgeIn.put(edge.getDest(),new HashMap<>());

            if(!hasEdge(edge.getSrc(),edge.getDest()))
            {
                ++edgeSize;
            }
            mapEdgeOut.get(edge.getSrc()).put(edge.getDest(),edge);
            mapEdgeIn.get(edge.getDest()).put(edge.getSrc(),edge);
            ++MC;
        }
    }





}
