package gameClient;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import kotlin.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Arena {

    private directed_weighted_graph graph;
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Agent> agents;
    private Server server;
    public Arena()
    {

    }


    public Arena(ServerManagement game, boolean withAgents) {
        this.graph = deserializeGraph(game);
        this.pokemons = deserializePokemon(game);
        this.server = deserializeServer(game);

        if(withAgents) {
            this.agents = deserializeAgent(game);
        }
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

    public Server getServer()
    {
        return server;
    }

    public directed_weighted_graph deserializeGraph(ServerManagement game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new ServerGraphJsonDeserializer());
        Gson gson = builder.create();


        directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
        return graph;

    }

    public ArrayList<Pokemon> deserializePokemon(ServerManagement game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerPokemonJsonDeserializer());
        Gson gson = builder.create();


        ArrayList<Pokemon> pokemons = gson.fromJson(game.getPokemons(), ArrayList.class);
        return pokemons;

    }

    public Server deserializeServer(ServerManagement game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Server.class, new ServerDeserializer());
        Gson gson = builder.create();


        Server server = gson.fromJson(game.toString(), Server.class);

        return server;

    }

    public ArrayList<Agent> deserializeAgent(ServerManagement game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerAgentJsonDeserailizer());
        Gson gson = builder.create();


        ArrayList<Agent> agents = gson.fromJson(game.getAgents(), ArrayList.class);
        return agents;

    }

//    public Pair<Double,Double> WorldToFrame()
//    {
//        node_location location = new NodeLocation(graph);
//        double maxX = location.getMaxXNodeData().getLocation().x();
//        double minX = location.getMinXNodeData().getLocation().x();
//        double maxY = location.getMaxYNodeData().getLocation().y();
//        double minY = location.getMinYNodeData().getLocation().y();
//        double x;
//        double y;
//        for(node_data nd : graph.getV())
//        {
//
//            x = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
//            y = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
//
//        }
//        Pair<Double,Double> arrayLocation = new Pair<Double,Double>(x,y);
//        return arrayLocation;
//    }

    public double scale(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }


}