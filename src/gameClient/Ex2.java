package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;

import java.util.ArrayList;

public class Ex2 {


    public static void main(String[] args)
    {
        game_service game = Game_Server_Ex2.getServer(1);
        directed_weighted_graph graph = deserializeGraph(game);
        ArrayList<Pokemon>  p = deserializePokemon(game);
        Arena arena = new Arena();
        arena.setGraph(graph);
        arena.setPokemons(p);
        MyGameFrame mygame = new MyGameFrame(arena);
        mygame.setVisible(true);
        System.out.println(game.getPokemons());
        System.out.println(game.getGraph());

        for(Pokemon d : p)
        {
            System.out.println(d);
        }
    }
    public static directed_weighted_graph deserializeGraph(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new ServerGraphJsonDeserializer());
        Gson gson = builder.create();


        directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
        return graph;

    }

    public static ArrayList<Pokemon> deserializePokemon(game_service game)
    {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ArrayList.class, new ServerPokemonJsonDeserializer());
        Gson gson = builder.create();


        ArrayList<Pokemon> pokemons = gson.fromJson(game.getPokemons(), ArrayList.class);
        return pokemons;

    }




}
