package gameClient;

import api.node_data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentPath implements Comparable<AgentPath> {
    private int id; //id of agent
    private List<node_data> path; //path of the agent
    private int index; //index on the path
    private double timeToSleep; // time that we need to sleep

    /**
     * Constructor
     * @param id - id of agent
     * @param path - path of the agent to pokemon
     * @param index - index on the path
     * @param timeToSleep - time that the agent need to sleep
     */
    public AgentPath(int id, List<node_data> path, int index,double timeToSleep) {
        this.id = id;
        this.path = path;
        this.index = index;
        this.timeToSleep = timeToSleep;
    }


    /**
     * getter method
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter method
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * setter method
     * @param path - List<node_data>
     */
    public void setPath(List<node_data> path) {
        this.path = path;
    }

    /**
     * setter method
     * @param index - index on the path
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * getter method
     * @return path - List<node_data>
     */
    public List<node_data> getPath() {
        return path;
    }

    /**
     * getter method
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * getter method
     * @return timeToSleep
     */
    public double getTimeToSleep() {
        return timeToSleep;
    }

    /**
     * setter method
     * @param timeToSleep
     */
    public void setTimeToSleep(double timeToSleep) {
        this.timeToSleep = timeToSleep;
    }


    @Override
    public int compareTo(@NotNull AgentPath o) {
        if(this.getTimeToSleep() > o.getTimeToSleep())
            return 1;
        else if(this.getTimeToSleep() < o.getTimeToSleep())
            return -1;
        else
            return 0;
    }

}
