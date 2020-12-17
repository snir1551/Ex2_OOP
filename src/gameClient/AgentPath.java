package gameClient;

import api.node_data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentPath {
    private int id; //id of agent
    private List<node_data> path; //path of the agent
    private int index; //index on the path

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
