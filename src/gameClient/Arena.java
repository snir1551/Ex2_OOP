package gameClient;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Arena {

    private directed_weighted_graph graph;
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Agent> agents;


    public Arena(directed_weighted_graph graph, ArrayList<Pokemon> pokemons, ArrayList<Agent> agents) {
        this.graph = graph;
        this.pokemons = pokemons;
        this.agents = agents;
    }

    public void setGraph(directed_weighted_graph graph)
    {
        this.graph = new DWGraph_DS(graph);
    }
    public directed_weighted_graph getGraph()
    {
        return graph;
    }

    public void setPokemons(ArrayList<Pokemon>  p)
    {
        this.pokemons = p;
    }
    public ArrayList<Pokemon> getPokemons()
    {
        return pokemons;
    }

    public void setAgents(ArrayList<Agent>  a)
    {
        this.agents = a;
    }
    public ArrayList<Agent> getAgents()
    {
        return agents;
    }




}
