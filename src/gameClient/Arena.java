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

    public Arena()
    {
        graph = new DWGraph_DS();
        pokemons = new ArrayList<>();
    }


    public double minXPokemon()
    {
        double minX = Double.MAX_VALUE;
        for(Pokemon p : pokemons)
        {
            if(p.getLocation().x() < minX)
            {
                minX = p.getLocation().x();
            }
        }
        return minX;
    }

    public double maxXPokemon()
    {
        double maxX = Double.MIN_VALUE;
        for(Pokemon p : pokemons)
        {
            if(p.getLocation().x() > maxX)
            {
                maxX = p.getLocation().x();
            }
        }
        return maxX;
    }

    public double minYPokemon()
    {
        double minY = Double.MAX_VALUE;
        for(Pokemon p : pokemons)
        {
            if(p.getLocation().y() < minY)
            {
                minY = p.getLocation().y();
            }
        }
        return minY;
    }

    public double maxYPokemon()
    {
        double maxY = Double.MIN_VALUE;
        for(Pokemon p : pokemons)
        {
            if(p.getLocation().y() > maxY)
            {
                maxY = p.getLocation().y();
            }
        }
        return maxY;
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
        this.pokemons = new ArrayList<>(p);
    }
    public ArrayList<Pokemon> getPokemons()
    {
        return pokemons;
    }




}
