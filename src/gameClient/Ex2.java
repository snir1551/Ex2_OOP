package gameClient;

import gameClient.GUI.WelcomeGUI;

public class Ex2 {


    public static void main(String[] args)
    {
        int id = -1;
        int lvl = -1;
        if (args.length == 2) {
            id = Integer.parseInt(args[0]);
            lvl = Integer.parseInt(args[1]);

            Thread client = new Thread(new Client(id,lvl));
            client.start();
        } else {
            WelcomeGUI g = new WelcomeGUI();
        }



    }





}
