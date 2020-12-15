package gameClient;

import api.node_data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentPath implements Comparable<AgentPath> {
    private int id;
    private List<node_data> path;
    private int index;
    private double timeToSleep;
    private Pokemon pokemon;
    public AgentPath(int id, List<node_data> path, int index, double timeToSleep,Pokemon pokemon) {
        this.id = id;
        this.path = path;
        this.index = index;
        this.timeToSleep = timeToSleep;
        this.pokemon = pokemon;
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

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public int compareTo(@NotNull AgentPath o) {
        if(this.getTimeToSleep() > o.getTimeToSleep())
            return 1;
        else if(this.getTimeToSleep() == o.getTimeToSleep())
            return 0;
        else
            return -1;
    }
}
