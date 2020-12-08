package api;

import kotlin.Pair;

import java.util.Collections;
import java.util.Comparator;

public class NodeLocation implements node_location {
    directed_weighted_graph graph;
    public NodeLocation(directed_weighted_graph graph)
    {
        this.graph = graph;
    }
    public node_data getMinXNodeData() {
        return Collections.min(this.graph.getV(), new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
                double res = n1.getLocation().x() - n2.getLocation().x();

                if(res < 0) {
                    return - 1;
                } else if(res > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }
    public node_data getMaxXNodeData() {
        return Collections.min(this.graph.getV(), new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
                double res = -1 * (n1.getLocation().x() - n2.getLocation().x());

                if(res < 0) {
                    return - 1;
                } else if(res > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }

    public node_data getMinYNodeData() {
        return Collections.min(this.graph.getV(), new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
                double res = n1.getLocation().y() - n2.getLocation().y();

                if(res < 0) {
                    return - 1;
                } else if(res > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }

    public node_data getMaxYNodeData() {
        return Collections.min(this.graph.getV(), new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
                double res = -1 * (n1.getLocation().y() - n2.getLocation().y());

                if(res < 0) {
                    return - 1;
                } else if(res > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }

}
