package gameClient;

import api.node_data;

import java.util.List;

public class AgentPath {
    private int id;
    private List<node_data> path;
    private int index;

    public AgentPath(int id, List<node_data> path, int index) {
        this.id = id;
        this.path = path;
        this.index = index;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPath(List<node_data> path) {
        this.path = path;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<node_data> getPath() {
        return path;
    }

    public int getIndex() {
        return index;
    }
}
