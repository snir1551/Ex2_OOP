package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;

import java.io.FileNotFoundException;

public class Ex2 {
    public static void main(String[] args) throws FileNotFoundException {
        MyGameFrame game = new MyGameFrame();
        game.setVisible(true);
        game_service game1 = Game_Server_Ex2.getServer(4);
        System.out.println(game1.toString());
    }
}
