package gameClient;

import api.*;
import gameClient.GUI.MyGameFrame;
import kotlin.Pair;

import java.util.*;

public class Client implements Runnable {
    private Arena arena;
    private MyGameFrame mygame;
    private ServerManagement game;
    private Pair<List<node_data>,Integer> path;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;


    /**
     * Constructor
     * @param id - your id
     * @param lvl - the lvl of the game
     */
    public Client(int id,int lvl)
    {
        game = new ServerManagement(lvl);
        boolean isLogin = game.login(id);
        if(!isLogin)
            System.exit(0);
    }


    @Override
    public void run() {

        arena = new Arena(game, false);
        mygame = new MyGameFrame();
        mygame.setVisible(true);
        System.out.println(game);


        updateLogic();
        locateAgents();
        updateLogic();
        Map<Integer,AgentPath> mapAgentPath = getInitialAgentPathMap();


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

        game.startGame(); // after locateAgents

        System.out.println(game.getAgents());
        System.out.println(game.getPokemons());
        while(game.isRunning())
        {
            updateLogic();
            List<Agent> agentsToUpdate = new ArrayList<>();

            for(Agent agent: getArena().getAgents()) {
                if(agent.getDest() == -1) {
                    agentsToUpdate.add(agent); // all agents that we need to update their path
                }
            }

            long timeToSleep = 200; // 200 sleep

            if(agentsToUpdate.size() != 0) {
                List<AgentPath> listSortedAgentPath = updateAgentPaths(agentsToUpdate, mapAgentPath);
                timeToSleep = Math.min(timeToSleep, (long)(listSortedAgentPath.get(0).getTimeToSleep()*1000.0)); // minimum sleep between 200 to minimum agents sleep
            } else {
                for(Agent agent: getArena().getAgents()) { //go through all agents
                    AgentPath agentPath = mapAgentPath.get(agent.getId());
                    if(agent.getDest() == agentPath.getPath().get(agentPath.getPath().size() - 1).getKey()) { //before pokemon

                        double distanceAgentToNode = distanceAgentToNode(agent, agentPath.getPath().get(agentPath.getPath().size() - 1)); // distance agent to node
                        double distanceNodeToNode = distanceNodeToNode(agentPath.getPath().get(agentPath.getPath().size() - 2), agentPath.getPath().get(agentPath.getPath().size() - 1)); //distance node to node
                        double w = getArena().getGraph().getEdge(agentPath.getPath().get(agentPath.getPath().size() - 2).getKey(), agentPath.getPath().get(agentPath.getPath().size() - 1).getKey()).getWeight(); // the weight of the edge of the pokemon sit
                        double speed = agent.getSpeed(); // speed of agent
                        timeToSleep = Math.min(timeToSleep, (long)(((w / speed) * (distanceAgentToNode / distanceNodeToNode)) * 1000)); // take the minimum sleep between

                        for(Pokemon p : arena.getPokemons()) {
                            double distToPokemon = distanceAgentToPokemon(agent, p);

                            if(distToPokemon < distanceAgentToNode) {
                                double pkTimeSleep = ((w / speed) * (distToPokemon / distanceNodeToNode))  * 0.8;
                                timeToSleep = Math.min(timeToSleep, (long)(pkTimeSleep * 1000));
                            }
                        }
                    }
                }
            }

            try {
                Thread.sleep(timeToSleep);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }


            game.move();

        }
    }

    private void locateAgents() {
        List<Pokemon> sortedPokemons = getPokemonsSorted(getArena().getPokemons());
        for(int i = 0; i < getArena().getServer().getAgents(); i++)
        {
            game.addAgent(keyDirectionClosePokemon(sortedPokemons.get(i)));
        }
    }

    private Map<Integer,AgentPath> getInitialAgentPathMap() {
        Map<Integer,AgentPath> map = new HashMap<>();

        for(Agent agent: arena.getAgents()) { //all agents
            map.put(agent.getId(), new AgentPath(agent.getId(), new ArrayList<>(), 0,0));
        }

        return map;
    }

    /**
     * This method update agent path that need to move
     * @param agentsToUpdate agents that need to update their path
     * @param mapAgentPath put path for all agents that need to update
     * @return
     */
    private List<AgentPath> updateAgentPaths(List<Agent> agentsToUpdate, Map<Integer,AgentPath> mapAgentPath) {
        List<Pokemon> sortedPokemons = getPokemonsSorted(getArena().getPokemons());
        List<Agent> sortedAgents = getAgentsSorted(getArena().getAgents());
        List<AgentPath> sortAgentpath = new ArrayList<>();
        for(Agent agent: agentsToUpdate) {
            AgentPath agentPath = mapAgentPath.get(agent.getId());

            if(agentPath.getIndex() == agentPath.getPath().size()) {

                path = AgentClosetPokemon(agentPath,sortedPokemons,sortedAgents);
                agentPath.setPath(path.getFirst());
                agentPath.setIndex(1);

                agent.setGradeCatchPokemon(sortedPokemons.get(path.getSecond()).getValue());

            }


            if(agentPath.getIndex() == agentPath.getPath().size()-1)
            {
                try {
                    double distanceNodeToPokemon = DistanceNodeToPokemon(agentPath.getPath().get(agentPath.getIndex() - 1), sortedPokemons.get(path.getSecond()));
                    double distanceNodeToNode = distanceNodeToNode(agentPath.getPath().get(agentPath.getIndex() - 1), agentPath.getPath().get(agentPath.getIndex()));
                    double w = getArena().getGraph().getEdge(agentPath.getPath().get(agentPath.getIndex() - 1).getKey(), agentPath.getPath().get(agentPath.getIndex()).getKey()).getWeight();
                    double speed = agent.getSpeed();
                    agentPath.setTimeToSleep(((distanceNodeToPokemon / distanceNodeToNode) * (w / speed)) * 0.8);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                double w = getArena().getGraph().getEdge(agentPath.getPath().get(agentPath.getIndex() - 1).getKey(), agentPath.getPath().get(agentPath.getIndex()).getKey()).getWeight();
                double speed = agent.getSpeed();
                agentPath.setTimeToSleep((w / speed));
            }

            game.chooseNextEdge(agent.getId(), agentPath.getPath().get(agentPath.getIndex()).getKey());



            agentPath.setIndex(agentPath.getIndex() + 1);


            sortAgentpath.add(agentPath);
        }

        Collections.sort(sortAgentpath);

        return sortAgentpath;
    }

    /**
     * checks if an agent is on an edge
     * @param p
     * @param src
     * @param dest
     * @return boolean if its on an edge
     */
    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if(dist>d1-EPS2) {ans = true;}
        return ans;
    }
    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p,src,dest);
    }
    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if(type<0 && dest>src) {return false;}
        if(type>0 && src>dest) {return false;}
        return isOnEdge(p,src, dest, g);
    }


    private void updateEdge(Pokemon fr, directed_weighted_graph g) {


        for(node_data n : getArena().getGraph().getV())
        {

            for(edge_data e : getArena().getGraph().getE(n.getKey()))
            {
                boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
                if(f) {
                    fr.set_edge(e);
                }


            }
        }
    }


    /**
     * this method that let us shortest path between agent to pokemon
     * @param startNode node that our agent start
     * @param p pokemon target
     * @return
     */
    private List<node_data> shortestpath(int startNode,Pokemon p)
    {
        List<node_data> listPath = new LinkedList<>();
        dw_graph_algorithms graph_algo = new DWGraph_Algo(getArena().getGraph());
        listPath = graph_algo.shortestPath(startNode,p.get_edge().getSrc());
        node_data getDestinationPokemon = getArena().getGraph().getNode(p.get_edge().getDest());
        listPath.add(getDestinationPokemon);
        return listPath;
    }


    /**
     * this method let us the direction that pokemon sit in the edge
     * @param p
     * @return
     */
    private int keyDirectionClosePokemon(Pokemon p)
    {
        int key = -1;
        if(p.getType() == 1)
        {
            if(p.get_edge().getSrc() < p.get_edge().getDest()) {
                key = p.get_edge().getSrc();
            }
        }
        else
        {
            if(p.get_edge().getSrc() > p.get_edge().getDest())
            {
                key = p.get_edge().getSrc();
            }

        }
        return key;
    }


    /**
     * this method sort the pokemons from high value to low value
     * @param pokemon
     * @return
     */
    private List<Pokemon> getPokemonsSorted(List<Pokemon> pokemon) {
        List<Pokemon> pokemonList = pokemon;

        Collections.sort(pokemonList, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon f1, Pokemon f2) {
                if(f1.getValue() > f2.getValue()) {
                    return -1;
                } else if(f1.getValue() < f2.getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return pokemonList;
    }

    /**
     *
     * @param node node_data
     * @param p Pokemon
     * @return the distance between node to pokemon
     */
    private double DistanceNodeToPokemon(node_data node, Pokemon p)
    {
        return node.getLocation().distance(p.getLocation());
    }

    /**
     *
     * @param node1 node_data
     * @param node2 node_data
     * @return the distance between node1 to node2
     */
    private double distanceNodeToNode(node_data node1,node_data node2)
    {

        return node1.getLocation().distance(node2.getLocation());
    }

    /**
     *
     * @param agent Agent
     * @param node node_data
     * @return the distance between agent to node
     */
    private double distanceAgentToNode(Agent agent, node_data node) {

        return agent.getLocation().distance(node.getLocation());
    }

    /**
     *
     * @param agent Agent
     * @param pokemon Pokemon
     * @return the distance between agent to pokemon
     */
    private double distanceAgentToPokemon(Agent agent, Pokemon pokemon) {
        return agent.getLocation().distance(pokemon.getLocation());
    }



//    public Pair<Double,Double> set_SDT(double ddtt,AgentPath agentPath,Pokemon p) {
//        double ddt = ddtt;
//        double ddttt = 0;
//        node_data node1Location = agentPath.getPath().get(agentPath.getIndex()-1);
//        node_data node2Location;
//        node2Location = agentPath.getPath().get(agentPath.getIndex());
//        edge_data _curr_edge = getArena().getGraph().getEdge(node1Location.getKey(),node2Location.getKey());
//
//        if(_curr_edge!=null) {
//            double w = _curr_edge.getWeight();
//            geo_location dest = getArena().getGraph().getNode(_curr_edge.getDest()).getLocation();
//            geo_location src = getArena().getGraph().getNode(_curr_edge.getSrc()).getLocation();
//            double de = src.distance(dest);
//
//            geo_location _pos = getArena().getAgents().get(agentPath.getId()).getLocation();
//
//            double dist = _pos.distance(dest);
//            if(p.get_edge()==_curr_edge) {
//                dist = p.getLocation().distance(_pos);
//            }
//            double norm = dist/de;
//            double speed = getArena().getAgents().get(agentPath.getId()).getSpeed();
//            double dt = w*norm / speed;
//            double dtd = Math.abs(w-w*norm);
//            ddt = (double)(1000.0*dt);
//            ddttt = (double)(1000.0*dtd);
//        }
//        return new Pair<Double, Double>(ddt,ddttt);
//    }


    /**
     *
     * @param agentPath AgentPath
     * @param pokemons List<pokemon>
     * @param agentList List<Agent>
     * @return Pair<List<node_data>,Integer> - the path of the agent and the key of the pokemon
     */
    private Pair<List<node_data>,Integer> AgentClosetPokemon(AgentPath agentPath, List<Pokemon> pokemons,List<Agent> agentList)
    {
        int min = Integer.MAX_VALUE;
        List<node_data> path = null;
        int location = -1;
        int i = 0;
        Pokemon pokemon = null;

        DWGraph_Algo graph_algo = new DWGraph_Algo(getArena().getGraph());
        graph_algo.dijkstra(getArena().getGraph().getNode(getArena().getAgents().get(agentPath.getId()).getSrc()));

        Pokemon minPoke = null;
        double minDist = -1;
        int minPokIndex = -1;

        for(int j = 0; j< pokemons.size(); j++) {

            Pokemon p = pokemons.get(j);

            if(!isPokemonTaken(agentList,p.getId())) {
                double dist = getArena().getGraph().getNode(p.get_edge().getSrc()).getWeight()
                        + p.get_edge().getWeight();
                if(minPoke == null || dist < minDist) {
                    minPoke = p;
                    minDist = dist;
                    minPokIndex = j;
                }
            }
        }

        path = shortestpath(getArena().getAgents().get(agentPath.getId()).getSrc(), minPoke);
        min = path.size();
        location = minPokIndex;
        pokemon = minPoke;


        agentList.get(agentPath.getId()).setIdPokemon(pokemon.getId());//list agent 0 -> n
        return new Pair<List<node_data>,Integer>(path,location);
    }

    /**
     * This method check if the pokemon taken by agent
     * @param agentList - List<Agent>
     * @param pokemonId - String pokemonId
     * @return
     */
    private boolean isPokemonTaken(List<Agent> agentList, String pokemonId)
    {
        for(Agent agent : agentList)
        {
            if(agent.getIdPokemon() != null && agent.getIdPokemon().equals(pokemonId))
            {
                return true;
            }
        }
        return false;
    }


    private List<Agent> getAgentsSorted(List<Agent> agent) {
        List<Agent> agentList = arena.getAgents();

        Collections.sort(agentList, new Comparator<Agent>() {
            @Override
            public int compare(Agent r1, Agent r2) {
                if(r1.getId() < r2.getId()) {
                    return -1;
                } else if(r1.getId() > r2.getId()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return agentList;
    }

    /**
     * sychronized getter Arena
     * @return arena
     */
    private synchronized Arena getArena() {
        return arena;
    }

    /**
     * sychronized setter Arena
     * @param arena
     */
    private synchronized void setArena(Arena arena) {
        this.arena = arena;
    }

    /**
     * This method update the logic of the game
     */
    private void updateLogic() {

        Arena arena;

        if(game.getCounterAgents() == 0)
        {
            arena = new Arena(game, false);
        }
        else
        {
            arena = new Arena(game, true);
        }
        setArena(arena);

        for(Pokemon p : getArena().getPokemons())
        {
            updateEdge(p,getArena().getGraph());
        }
    }


    /**
     * This method update the graphics of the game
     */
    private void updateGraphics()
    {
        mygame.update(getArena());
    }




}