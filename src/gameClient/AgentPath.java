package gameClient;

import api.node_data;

import java.util.List;

public class AgentPath {
    private int id;
    private List<node_data> path;
    private int index;
    private double timeToSleep;

    public AgentPath(int id, List<node_data> path, int index, double timeToSleep) {
        this.id = id;
        this.path = path;
        this.index = index;
        this.timeToSleep = timeToSleep;
    }



    public AgentPath(AgentPath agentPath)
    {
        this.id = agentPath.id;
        this.path = agentPath.path;
        this.index = agentPath.index;
        this.timeToSleep = agentPath.timeToSleep;
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

    public double getTimeToSleep() {
        return timeToSleep;
    }

    public void setTimeToSleep(double timeToSleep) {
        this.timeToSleep = timeToSleep;
    }


}
