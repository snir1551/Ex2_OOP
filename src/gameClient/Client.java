package gameClient;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;
import Server.Game_Server_Ex2;
import kotlin.Pair;
import kotlin.jvm.Synchronized;

import java.util.*;

public class Client implements Runnable {
    private Arena arena;
    private MyGameFrame mygame;
    private ServerManagement game;
    private Server server;
    private double moves;
    private long sleep = 500;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
    public Client()
    {

    }


    @Override
    public void run() {
        game = new ServerManagement(11);
        arena = new Arena(game, false);
        mygame = new MyGameFrame();
        mygame.setVisible(true);
        System.out.println(game);


        updateLogic();
        locateAgents();
        updateLogic();
        game.startGame(); // after locateAgents

        Map<Integer,AgentPath> mapAgentPath = getInitialAgentPathMap();
        updateLogic();

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
        int ind=0;
        long dt=300;
        while(game.isRunning())
        {
            game.move();
            updateLogic();
            List<Agent> agentsToUpdate = new ArrayList<>();

            for(Agent agent: getArena().getAgents()) {
                if(agent.getDest() == -1) {
                    agentsToUpdate.add(agent);
                }
            }

            updateAgentPaths(agentsToUpdate, mapAgentPath);



            try {
                if(ind%1==0) {}
                Thread.sleep(sleep);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }



        }
    }

    private void locateAgents() {

        List<Pokemon> sortedPokemons = getPokemonsSorted(getArena().getPokemons());
        //List<Agent> sortedAgents = getAgentsSorted(getArena().getAgents());
        for(int i = 0; i < getArena().getServer().getAgents(); i++)
        {
            game.addAgent(keyCloseAgent(sortedPokemons.get(i)));
        }
    }

    private Map<Integer,AgentPath> getInitialAgentPathMap() {
        Map<Integer,AgentPath> map = new HashMap<>();

        for(Agent agent: arena.getAgents()) {
            map.put(agent.getId(), new AgentPath(agent.getId(), new ArrayList<>(), 0));
        }



        return map;
    }

    private synchronized Arena getArena() {
        return arena;
    }

    private synchronized void setArena(Arena arena) {
        this.arena = arena;
    }

    private void updateAgentPaths(List<Agent> agentsToUpdate, Map<Integer,AgentPath> mapAgentPath) {
        List<Pokemon> sortedPokemons = getPokemonsSorted(getArena().getPokemons());
        for(Agent agent: agentsToUpdate) {
            AgentPath agentPath = mapAgentPath.get(agent.getId());

            if(agentPath.getIndex() == agentPath.getPath().size()) {
                // at end of path (or before path was set, at beginning of game)
                List<node_data> path = new ArrayList<>();
                path = shortestpath(getArena().getAgents().get(agentPath.getId()).getSrc(),sortedPokemons.get(agentPath.getId()));
                agentPath.setPath(path);
                agentPath.setIndex(1);
                moves = calculateMoves(agentPath);
                game.chooseNextEdge(agent.getId(), agentPath.getPath().get(0).getKey());
            } else {
                // not at end of path
                if(agentPath.getIndex() == agentPath.getPath().size()-1)
                {
                    sleep = 240;
                }
                else
                    sleep = 400;
                game.chooseNextEdge(agent.getId(), agentPath.getPath().get(agentPath.getIndex()).getKey());
                agentPath.setIndex(agentPath.getIndex() + 1);

            }
        }
    }

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


    public void updateEdge(Pokemon fr, directed_weighted_graph g) {
        //	oop_edge_data ans = null;

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





    private List<node_data> shortestpath(int startNode,Pokemon p)
    {
        List<node_data> listPath = new LinkedList<>();
        dw_graph_algorithms graph_algo = new DWGraph_Algo(getArena().getGraph());
        listPath = graph_algo.shortestPath(startNode,p.get_edge().getSrc());
        node_data getDestinationPokemon = getArena().getGraph().getNode(p.get_edge().getDest());
        listPath.add(getDestinationPokemon);
        return listPath;
    }

    private void updateLogic() {
        //arena = new Arena(deserializeGraph(game), deserializePokemon(game), null);
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


    private void updateGraphics() {
        mygame.update(getArena());
    }


    private int keyCloseAgent(Pokemon p)
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


    private List<Agent> getAgentsSorted(List<Agent> agent) {
        List<Agent> agentList = arena.getAgents();

        Collections.sort(agentList, new Comparator<Agent>() {
            @Override
            public int compare(Agent r1, Agent r2) {
                if(r1.getSpeed() > r2.getSpeed()) {
                    return -1;
                } else if(r1.getSpeed() < r2.getSpeed()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return agentList;
    }

    private double calculateMoves(AgentPath agentPath)
    {
        node_data node1Location = agentPath.getPath().get(agentPath.getIndex());
        node_data node2Location;
        if(agentPath.getPath().get(agentPath.getIndex()).getKey() < agentPath.getPath().size()-1) {
            node2Location = agentPath.getPath().get(agentPath.getIndex() + 1);
        }
        else
            node2Location = agentPath.getPath().get(agentPath.getIndex() + 0);
        double distance = distastance(node1Location,node2Location);

        double speed = getArena().getAgents().get(agentPath.getId()).getSpeed();

        return distance/speed;
    }

    private double distastance(node_data node1, node_data node2)
    {
        double x = Math.pow(node1.getLocation().x()-node2.getLocation().x(),2);
        double y = Math.pow(node1.getLocation().y()-node2.getLocation().y(),2);
        double distance = Math.sqrt(x+y);

        return distance;
    }


}
