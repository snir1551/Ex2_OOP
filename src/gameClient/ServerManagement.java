package gameClient;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;

import java.util.ArrayList;
import java.util.List;

public class ServerManagement {

    private game_service game;
    private int lvl;
    private static int counterAgents;
    private directed_weighted_graph graph;
    private List<Pokemon> arrayPokemons;
    private Server server;
    private List<Agent> arrayAgents;

    public ServerManagement(int lvl) {
        this.lvl = lvl;
        game = Game_Server_Ex2.getServer(lvl);
        counterAgents = 0;
    }


    public void addAgent(int nodePosition)
    {
        game.addAgent(nodePosition);
        ++counterAgents;
    }

    public void chooseNextEdge(int id,int destinationNode)
    {
        if(id > counterAgents)
            throw new RuntimeException("Agent not exist");

        game.chooseNextEdge(id,destinationNode);
    }

    public boolean gameIsRunning()
    {
        if(game.isRunning())
            return true;
        else
            return false;
    }

    public void move()
    {
        game.move();
    }

    public String getPokemons()
    {
        return game.getPokemons();
    }

    public String getGraph()
    {
        return game.getGraph();
    }

    public String getAgents()
    {
        return game.getAgents();
    }

    public String toString()
    {
        return game.toString();
    }

    public void startGame()
    {
        game.startGame();
    }

    public boolean isRunning()
    {
        return game.isRunning();
    }

    public int getCounterAgents()
    {
        return counterAgents;
    }
}
