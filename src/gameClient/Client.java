package gameClient;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;
import Server.Game_Server_Ex2;
import java.util.ArrayList;

public class Client implements Runnable {
    private Arena arena;
    private MyGameFrame mygame;
    private game_service game;
    private Server server;
    public Client()
    {

    }

    public directed_weighted_graph deserializeGraph(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new ServerGraphJsonDeserializer());
        Gson gson = builder.create();


        directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
        return graph;

    }

    public ArrayList<Pokemon> deserializePokemon(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerPokemonJsonDeserializer());
        Gson gson = builder.create();


        ArrayList<Pokemon> pokemons = gson.fromJson(game.getPokemons(), ArrayList.class);
        return pokemons;

    }

    public Server deserializeServer(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Server.class, new ServerDeserializer());
        Gson gson = builder.create();


        Server server = gson.fromJson(game.toString(), Server.class);
        return server;

    }

    public ArrayList<Agent> deserializeAgent(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerAgentJsonDeserailizer());
        Gson gson = builder.create();


        ArrayList<Agent> agents = gson.fromJson(game.getAgents(), ArrayList.class);
        return agents;

    }

    @Override
    public void run() {
        game = Game_Server_Ex2.getServer(0);
        mygame = new MyGameFrame();
        mygame.setVisible(true);
        server = deserializeServer(game);
        for(int i = 0; i < server.getAgents(); i++)
        {
            game.addAgent(9);
        }
        game.startGame();
        update();
        game.chooseNextEdge(0, arena.getGraph().getE(4).iterator().next().getDest());
        int ind=0;
        long dt=100;
        while(game.isRunning()) {
            game.move();
            update();

            if(arena.getAgents().get(0).getDest() == -1) {
                game.chooseNextEdge(0, arena.getGraph().getE(arena.getAgents().get(0).getSrc()).iterator().next().getDest());
            }

            try {
                if(ind%1==0) {mygame.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        arena = new Arena(deserializeGraph(game), deserializePokemon(game), deserializeAgent(game));
        mygame.update(arena);
    }
}
