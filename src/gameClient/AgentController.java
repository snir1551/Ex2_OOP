package gameClient;

public class AgentController implements Runnable {
    Arena arena;
    ServerManagement game;
    Agent agent;
    AgentController(ServerManagement game, Arena arena)
    {
        this.game = game;
        this.arena = arena;
    }


    @Override
    public void run() {
        game.addAgent(0);
        updateLogic();
        agent = arena.getAgents().get(0);
        System.out.println(arena.getAgents());
        if(agent.getDest() == -1)
        {
            game.chooseNextEdge(agent.getId(),8);
        }
        while(game.isRunning())
        {
            updateLogic();
            game.move();
        }
    }

    private void updateLogic() {
        //arena = new Arena(deserializeGraph(game), deserializePokemon(game), null);
        Arena arena;

        if(game.getCounterAgents() == 0)
        {
            arena = new Arena(game, true);
        }
        else
        {
            arena = new Arena(game, true);
        }

        setArena(arena);


    }


    private synchronized Arena getArena() {
        return arena;
    }

    private synchronized void setArena(Arena arena) {
        this.arena = arena;
    }
}
