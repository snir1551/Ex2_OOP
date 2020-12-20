package gameClient;


import api.directed_weighted_graph;
import api.game_service;
import Server.Game_Server_Ex2;

public class ServerManagement implements game_service {

    private game_service game; // service game
    private int lvl; // lvl of the game
    private static int counterAgents; // counter agents that we added to game

    /**
     * Constructor
     * @param lvl - lvl of the game
     */
    public ServerManagement(int lvl) {
        this.lvl = lvl;
        game = Game_Server_Ex2.getServer(lvl);
        counterAgents = 0;
    }

    /**
     * Returns a JSON representation of graph as a JSON String.
     * @return
     */
    @Override
    public String getGraph() {
        return game.getGraph();
    }

    /**
     * Returns an interface to the graph (should NOT be used) for final version - for testing only.
     * @return
     */
    @Override
    public directed_weighted_graph getJava_Graph_Not_to_be_used() {
        return game.getJava_Graph_Not_to_be_used();
    }

    /**
     * Returns a JSON string, representing all Pokemons (fixed bonus coin).
     * @return
     */
    @Override
    public String getPokemons() {
        return game.getPokemons();
    }

    /**
     * Returns a JSON string, representing all the Agents.
     * @return
     */
    @Override
    public String getAgents() {
        return game.getAgents();
    }

    /**
     * This method allows the user to add & locate the agents,
     * all should be located in order to start a game.
     *
     * @param start_node - the vertex in the graph from which the agent will start.
     * @return
     */
    @Override
    public boolean addAgent(int start_node) {
        ++counterAgents;
        return game.addAgent(start_node);
    }

    /**
     * Start a new game
     * @return the time (new Date().getTime()) if a new game was started, else -1.
     */
    @Override
    public long startGame() {
        return game.startGame();
    }

    /**
     * Returns the current status of the game (true: is running, false: NOT running).
     * @return
     */
    @Override
    public boolean isRunning() {
        return game.isRunning();
    }

    /**
     * Stops the game, after this method the isRunning() will return false
     * @return
     */
    @Override
    public long stopGame() {
        return game.stopGame();
    }

    /**
     * This method is the main logical functionality, allows the client algorithm
     * to direct each agent to the "next" edge.
     * @param id - the agent id, as received from the the JSON String
     * @param next_node - the next edge defined as (src,next_node)
     * @return the time the action was performed (-1 if not performed).
     */
    @Override
    public long chooseNextEdge(int id, int next_node) {
        if(id > counterAgents)
            throw new RuntimeException("Agent not exist");

        return game.chooseNextEdge(id,next_node);
    }

    /**
     * return the number of mili-seconds till the game is over
     *
     * @return
     */
    @Override
    public long timeToEnd() {
        return game.timeToEnd();
    }

    /**
     * moves all the agents along each edge,
     * if the agent is on the node
     * (nothing is done - requires to chooseNextEdge(int id, int next_node)
     * @return a JSON like String - representing status of all the agents.
     */
    @Override
    public String move() {
        return game.move();
    }

    /**
     * Performs a login - so the results of the game will be stored in the data-base after the game,
     * requires Internet connection. The following data is stored: id, level, number of moves, grade & time.
     * @param id
     * @return: true iff the user was successfully logged-in to the server.
     */
    @Override
    public boolean login(long id) {
        return game.login(id);
    }

    /**
     * toString of the service_game
     * @return
     */
    @Override
    public String toString()
    {
        return game.toString();
    }

    /**
     *
     * @return counterAgents - number of agents that added to the game
     */
    public int getCounterAgents()
    {
        return counterAgents;
    }


}
