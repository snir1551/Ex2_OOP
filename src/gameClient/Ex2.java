package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;

import java.util.ArrayList;

public class Ex2 {


    public static void main(String[] args)
    {
        Thread client = new Thread(new Client());
        client.start();

    }





}
