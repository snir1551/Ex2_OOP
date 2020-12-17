package gameClient;

import api.*;
import gameClient.GUI.MyGameFrame;
import kotlin.Pair;

import java.util.*;

public class Client implements Runnable {
    private Arena arena;
    private MyGameFrame mygame;
    private ServerManagement game;
    private double distanceNodeToPokemon = 100;
    private double distancePokemonToNode = 100;
    private Pair<List<node_data>,Integer> path;
    private Pair<Double,Double> d;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
    public Client()
    {

    }

    //Constructor start game from GUI
    public Client(int id,int lvl)
    {
        game = new ServerManagement(lvl);
        boolean isLogin = game.isLogin(id);
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
        //long dt=300;
        while(game.isRunning())
        {

            updateLogic();
            List<Agent> agentsToUpdate = new ArrayList<>();

            for(Agent agent: getArena().getAgents()) {
                if(agent.getDest() == -1) {
                    agentsToUpdate.add(agent);
                }
            }


            List<AgentPath> listSortedAgentPath = updateAgentPaths(agentsToUpdate, mapAgentPath);
            if(listSortedAgentPath.size() != 0)
            {
                try {
                    //Thread.sleep((long)listSortedAgentPath.get(0).getTimeToSleep());
                    //Thread.sleep((long)((distanceNodeToPokemon+distancePokemonToNode)/10));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    //Thread.sleep((long)((distanceNodeToPokemon+distancePokemonToNode)/10));
                    //Thread.sleep((long)((dt+dtt+300)/10));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    private List<AgentPath> updateAgentPaths(List<Agent> agentsToUpdate, Map<Integer,AgentPath> mapAgentPath) {
        List<Pokemon> sortedPokemons = getPokemonsSorted(getArena().getPokemons());
        List<Agent> sortedAgents = getAgentsSorted(getArena().getAgents());
        List<AgentPath> sortAgentpath = new ArrayList<>();
        for(Agent agent: agentsToUpdate) {
            AgentPath agentPath = mapAgentPath.get(agent.getId());

            if(agentPath.getIndex() == agentPath.getPath().size()) {
                // at end of path (or before path was set, at beginning of game)
//                if(agent.getId() == 0)
//                {
                    path = AgentClosetPokemon(agentPath,sortedPokemons,sortedAgents);
                    agentPath.setPath(path.getFirst());
                    agentPath.setIndex(1);
                    System.out.println(agent.getId());
                    System.out.println(agent.getIdPokemon());
                    System.out.println(sortedPokemons.get(path.getSecond()));
                    //d = set_SDT(100,agentPath,sortedPokemons.get(path.getSecond()));
//                }
//                else
//                {
//                    List<node_data> path = new ArrayList<>();
//                    path = shortestpath(getArena().getAgents().get(agentPath.getId()).getSrc(),sortedPokemons.get(agentPath.getId()));
//                    agentPath.setPath(path);
//                    agentPath.setIndex(1);
//                    d = set_SDT(100,agentPath,sortedPokemons.get(agentPath.getId()));
//
//                }




                //game.chooseNextEdge(agent.getId(), agentPath.getPath().get(agentPath.getIndex()).getKey());



//                distanceNodeToPokemon = d.getFirst();
//                distancePokemonToNode = d.getSecond();
//                if(agentPath.getIndex() == agentPath.getPath().size()-1)
//                {
//                    try {
//                        agentPath.setTimeToSleep(distanceNodeToPokemon);
//                    }
//                    catch(Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // }
//                else
//                {
//                    //moves = calculateMoves(agentPath);
//                    try {
//                        agentPath.setTimeToSleep(distanceNodeToPokemon);
//                    }
//                    catch(Exception e) {
//                        e.printStackTrace();
//                    }
//                }




            }
            //else {
                // not at end of path


                game.chooseNextEdge(agent.getId(), agentPath.getPath().get(agentPath.getIndex()).getKey());
                //if(agentPath.getIndex() == agentPath.getPath().size()-1)
                //{
//                if(agent.getId() == 0)
//                {
//                    d = set_SDT(100, agentPath, sortedPokemons.get(path.getSecond()));
//                }
//                else
//                {
//                    d = set_SDT(100, agentPath, sortedPokemons.get(agent.getId()));
//                }


//                d = set_SDT(100, agentPath, sortedPokemons.get(path.getSecond()));
//                distanceNodeToPokemon = d.getFirst();
//                distancePokemonToNode = d.getSecond();
//                if(agentPath.getIndex() == agentPath.getPath().size()-1)
//                {
//                    agentPath.setTimeToSleep(distanceNodeToPokemon);
//                }

                // }
//                else
//                {
//                    //moves = calculateMoves(agentPath);
//                    agentPath.setTimeToSleep(distanceNodeToPokemon);
//                }
                //agentPath.setTimeToSleep(distanceNodeToPokemon);

                agentPath.setIndex(agentPath.getIndex() + 1);



            //}
            sortAgentpath.add(agentPath);
        }

        //Collections.sort(sortAgentpath);

        return sortAgentpath;
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


    private void updateEdge(Pokemon fr, directed_weighted_graph g) {
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
        //arena.setServerManagement(game);
        for(Pokemon p : getArena().getPokemons())
        {
            updateEdge(p,getArena().getGraph());
        }
    }


    private void updateGraphics()
    {
        mygame.update(getArena());
    }


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



    public Pair<Double,Double> set_SDT(double ddtt,AgentPath agentPath,Pokemon p) {
        double ddt = ddtt;
        double ddttt = 0;
        node_data node1Location = agentPath.getPath().get(agentPath.getIndex()-1);
        node_data node2Location;
        node2Location = agentPath.getPath().get(agentPath.getIndex());
        edge_data _curr_edge = getArena().getGraph().getEdge(node1Location.getKey(),node2Location.getKey());

        if(_curr_edge!=null) {
            double w = _curr_edge.getWeight();
            geo_location dest = getArena().getGraph().getNode(_curr_edge.getDest()).getLocation();
            geo_location src = getArena().getGraph().getNode(_curr_edge.getSrc()).getLocation();
            double de = src.distance(dest);

            geo_location _pos = getArena().getAgents().get(agentPath.getId()).getLocation();

            double dist = _pos.distance(dest);
            if(p.get_edge()==_curr_edge) {
                dist = p.getLocation().distance(_pos);
            }
            double norm = dist/de;
            double speed = getArena().getAgents().get(agentPath.getId()).getSpeed();
            double dt = w*norm / speed;
            double dtd = Math.abs(w-w*norm);
            ddt = (double)(1000.0*dt);
            ddttt = (double)(1000.0*dtd);
        }
        return new Pair<Double, Double>(ddt,ddttt);
    }


    private Pair<List<node_data>,Integer> AgentClosetPokemon(AgentPath agentPath, List<Pokemon> pokemons,List<Agent> agentList)
    {
        int min = Integer.MAX_VALUE;
        List<node_data> path = null;
        int location = -1;
        int i = 0;
        Pokemon pokemon = null;
//        System.out.println(pokemons.toString());
//        System.out.println(pokemons.size());
        for(Pokemon p : pokemons)
        {
            if(shortestpath(getArena().getAgents().get(agentPath.getId()).getSrc(),p).size() < min && !isPokemonTaken(agentList,p.getId()))
            {
                path = shortestpath(getArena().getAgents().get(agentPath.getId()).getSrc(),p);
                min = path.size();
                location = i;
                pokemon = p;
            }
            i++;
        }
//        System.out.println(agentList.size());
//        System.out.println(agentPath.getId());
//        System.out.println(pokemon.getId());
        agentList.get(agentPath.getId()).setIdPokemon(pokemon.getId());//list agent 0 -> n
        return new Pair<List<node_data>,Integer>(path,location);
    }


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

//    private double calculateMoves(AgentPath agentPath)
//    {
//
//        node_data node1Location = agentPath.getPath().get(agentPath.getIndex()-1);
//        node_data node2Location = agentPath.getPath().get(agentPath.getIndex());
//        edge_data _curr_edge = getArena().getGraph().getEdge(node1Location.getKey(),node2Location.getKey());
//        //double distance = distastance(node1Location,node2Location);
//
//        double speed = getArena().getAgents().get(agentPath.getId()).getSpeed();
//
//        return _curr_edge.getWeight()/speed;
//    }


}