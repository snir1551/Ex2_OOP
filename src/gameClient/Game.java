package gameClient;

import gameClient.GUI.MyGameFrame;

public class Game implements Runnable {
    ServerManagement game;
    Arena arena;
    MyGameFrame mygame;

    @Override
    public void run() {
        game = new ServerManagement(1);
        arena = new Arena(game, false);
        mygame = new MyGameFrame();
        mygame.setVisible(true);
        Thread agent1 = new Thread(new AgentController(game,arena));
        agent1.start();
        System.out.println(agent1.isAlive());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(100);
                        updateGraphics();
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();

    }

    private void updateGraphics() {
        mygame.update(arena);
    }

    //Thread agentpath = new Thread(new AgentPath());
}
