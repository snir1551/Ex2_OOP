package gameClient;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;

import java.util.ArrayList;

public class Arena {

    private directed_weighted_graph graph; //graph of our game
    private ArrayList<Pokemon> pokemons; // the list of the pokemons in the game
    private ArrayList<Agent> agents; // the list of the agents in the game
    private Server server; // server
    private ServerManagement serverManagement; // the service_game


    /**
     * Constructor that init the all data from service_game
     * @param game ServerManagement - service_game
     * @param withAgents - boolean withAgents
     */
    public Arena(ServerManagement game, boolean withAgents) {
        this.serverManagement = game;
        this.graph = deserializeGraph(game);
        this.pokemons = deserializePokemon(game);
        this.server = deserializeServer(game);
        if (withAgents) {
            this.agents = deserializeAgent(game);
        }
    }

    /**
     * This method is the setter graph
     * @param graph directed_weighted_graph
     */
    public void setGraph(directed_weighted_graph graph) {
        this.graph = new DWGraph_DS(graph);
    }

    /**
     * This method is the getter graph
     * @return graph
     */
    public directed_weighted_graph getGraph() {
        return graph;
    }

    /**
     * This method is the getter list of pokemons
     * @return ArrayList<Pokemon>
     */
    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    /**
     * This method is the getter list of agents
     * @return ArrayList<Agent>
     */
    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public Server getServer() {
        return server;
    }

    /**
     * This method create directed graph from the data that we're getting from the service_game
     * @param game
     * @return
     */
    public directed_weighted_graph deserializeGraph(ServerManagement game) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new ServerGraphJsonDeserializer());
        Gson gson = builder.create();


        directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
        return graph;

    }

    /**
     * This method create ArrayList<Pokemon> from the data that we're getting from the service_game
     * @param game ServerManagement - service_game
     * @return ArrayList<Pokemon>
     */
    public ArrayList<Pokemon> deserializePokemon(ServerManagement game) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerPokemonJsonDeserializer());
        Gson gson = builder.create();


        ArrayList<Pokemon> pokemons = gson.fromJson(game.getPokemons(), ArrayList.class);
        return pokemons;

    }

    /**
     * This method create Server from the data that we're getting from the service_game
     * @param game ServerManagement - service_game
     * @return Server
     */
    public Server deserializeServer(ServerManagement game) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Server.class, new ServerDeserializer());
        Gson gson = builder.create();


        Server server = gson.fromJson(game.toString(), Server.class);

        return server;

    }

    /**
     * This method create ArrayList<Agent> from the data that we're getting from the service_game
     * @param game ServerManagement - service_game
     * @return ArrayList<Pokemon>
     */
    public ArrayList<Agent> deserializeAgent(ServerManagement game) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerAgentJsonDeserailizer());
        Gson gson = builder.create();


        ArrayList<Agent> agents = gson.fromJson(game.getAgents(), ArrayList.class);
        return agents;

    }


    /**
     * this method is getter the ServerManagement
     * @return ServerManagement
     */
    public ServerManagement getServerManagement()
    {
        return serverManagement;
    }




}