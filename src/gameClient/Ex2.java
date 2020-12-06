package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Ex2 {
    public static void main(String[] args) {
        //liron check
        MyGameFrame game = new MyGameFrame();
        game.setVisible(true);
        game_service game1 = Game_Server_Ex2.getServer(4);
        System.out.println(game1);
        deserialize(game1);
    }

    private static void deserialize(game_service game)
    {

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new ServerJsonDeserializer());
            Gson gson = builder.create();
            //continue as usual..


            directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
            System.out.println(graph);

    }
}
