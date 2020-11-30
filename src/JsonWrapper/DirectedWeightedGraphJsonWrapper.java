package JsonWrapper;


import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;

import java.util.ArrayList;

public class DirectedWeightedGraphJsonWrapper {
    private final ArrayList<NodeDataJsonWrapper> nodes;
    private final ArrayList<EdgeDataJsonWrapper> edges;
    private final int mc;

    public DirectedWeightedGraphJsonWrapper(directed_weighted_graph graph) {
        nodes = new ArrayList<>();

        for(node_data nodeData : graph.getV()) {
            nodes.add(new NodeDataJsonWrapper(nodeData));
        }

        edges = new ArrayList<>();

        for(node_data nodeData : graph.getV()) {
            for(edge_data edgeData : graph.getE(nodeData.getKey())) {
                edges.add(new EdgeDataJsonWrapper(edgeData));
            }
        }

        this.mc = graph.getMC();
    }

    public ArrayList<NodeDataJsonWrapper> getNodes() {
        return nodes;
    }

    public ArrayList<EdgeDataJsonWrapper> getEdges() {
        return edges;
    }

    public int getMc() {
        return mc;
    }
}
