package gameClient;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.Deserializer.ServerAgentJsonDeserailizer;
import gameClient.Deserializer.ServerDeserializer;
import gameClient.Deserializer.ServerGraphJsonDeserializer;
import gameClient.Deserializer.ServerPokemonJsonDeserializer;
import Server.Game_Server_Ex2;
import kotlin.jvm.Synchronized;

import java.util.*;

public class Client implements Runnable {
    private Arena arena;
    private MyGameFrame mygame;
    private game_service game;
    private Server server;
    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
    public Client()
    {

    }

//    public directed_weighted_graph deserializeGraph(game_service game)
//    {
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(DWGraph_DS.class, new ServerGraphJsonDeserializer());
//        Gson gson = builder.create();
//
//
//        directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
//        return graph;
//
//    }
//
//    public ArrayList<Pokemon> deserializePokemon(game_service game)
//    {
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(ArrayList.class, new ServerPokemonJsonDeserializer());
//        Gson gson = builder.create();
//
//
//        ArrayList<Pokemon> pokemons = gson.fromJson(game.getPokemons(), ArrayList.class);
//        return pokemons;
//
//    }
//
//    public Server deserializeServer(game_service game)
//    {
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(Server.class, new ServerDeserializer());
//        Gson gson = builder.create();
//
//
//        Server server = gson.fromJson(game.toString(), Server.class);
//        System.out.println(server.getAgents());
//        return server;
//
//    }
//
//    public ArrayList<Agent> deserializeAgent(game_service game)
//    {
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(ArrayList.class, new ServerAgentJsonDeserailizer());
//        Gson gson = builder.create();
//
//        System.out.println(game.getAgents());
//        ArrayList<Agent> agents = gson.fromJson(game.getAgents(), ArrayList.class);
//        return agents;
//
//    }

    @Override
    public void run() {
        server = new Server();
        game = server.Game(0);
        arena = new Arena(game);
        mygame = new MyGameFrame();
        mygame.setVisible(true);


        for(Pokemon p : arena.getPokemons())
        {
            updateEdge(p,arena.getGraph());
        }

        Pokemon p = arena.getPokemons().get(0);
        int key = keyCloseAgent(p);
        server.addAgent(key);
        List<node_data> list = shortestPath(key,p.get_edge().getDest());
        game.startGame();
        update();
        int size = list.size();
        int f = 0;
        int i = 0;
        int j = 0;
        while(game.isRunning())
        {
            game.move();
            update();
            int ind=0;
            long dt=100;
            if(arena.getAgents().get(0).getDest() == -1) {
                if(i<size)
                {
                    game.chooseNextEdge(0, list.get(i).getKey());
                    j = list.get(i).getKey();
                    i++;
                }
                else
                {
                    //System.out.println(p);
                    update();
                    for(Pokemon d : arena.getPokemons())
                    {
                        updateEdge(d,arena.getGraph());
                    }
                    p = arena.getPokemons().get(0);
                    //System.out.println(p);
                    //System.out.println(p.get_edge().getSrc());
                    list = shortestPath(j,p.get_edge().getDest());
                    System.out.println(++f);
                    size = list.size();
                    i=1;
                }

            }

            try {
                if(ind%1==0) {mygame.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
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

       for(node_data n : arena.getGraph().getV())
       {

            for(edge_data e : arena.getGraph().getE(n.getKey()))
            {
                boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g);
                if(f) {
                    fr.set_edge(e);
                    if(fr.getType() == 1)
                    {
                        if(e.getSrc() < e.getDest()) {
                            arena.getGraph().getNode(e.getSrc()).setTag(1);
                            arena.getGraph().getNode(e.getDest()).setTag(-1);
                        }
                    }
                    else
                    {
                        if(e.getSrc() > e.getDest())
                            arena.getGraph().getNode(e.getSrc()).setTag(1);
                            arena.getGraph().getNode(e.getDest()).setTag(-1);
                    }
                }
//                else
//                {
//                    arena.getGraph().getNode(e.getSrc()).setTag(0);
//                    arena.getGraph().getNode(e.getDest()).setTag(0);
//                }

            }
        }
    }



//    private int keyCloseAgent()
//    {
//
//        Pokemon p = arena.getPokemons().get(0);
//        node_location location = new NodeLocation(arena.getGraph());
//        double maxX = location.getMaxXNodeData().getLocation().x();
//        double minX = location.getMinXNodeData().getLocation().x();
//        double maxY = location.getMaxYNodeData().getLocation().y();
//        double minY = location.getMinYNodeData().getLocation().y();
//        double px = arena.scale(p.getLocation().x(),minX,maxX,20,mygame.getWidth()-20);
//        double py = arena.scale(p.getLocation().y(),minY,maxY,mygame.getHeight()-10,150);
//        double min = Double.MAX_VALUE;
//        int key = -1;
//        for(node_data node : arena.getGraph().getV())
//        {
//            double x = arena.scale(node.getLocation().x(),minX,maxX,20,mygame.getWidth()-20);
//            double y = arena.scale(node.getLocation().y(),minY,maxY,mygame.getHeight()-10,150);
//            double s = Math.sqrt(Math.pow(x-px,2) + Math.pow(y-py,2));
//            if((s) < min) {
//                min = s;
//                key = node.getKey();
//            }
//        }
//
//        return key;
//    }






    private void update() {
        //arena = new Arena(deserializeGraph(game), deserializePokemon(game), null);
        if(server.getCounter() == 0)
        {
            arena.updateWithoutAgent(game);
        }
        else
        {
            arena.updateAll(game);
        }
        mygame.update(arena);
    }


    private HashMap<Integer, node_data> dijkstra(node_data node)
    {
        PriorityQueue<node_data> queue = new PriorityQueue<>(); // init PriorityQueue of node_info
        HashMap<Integer,node_data> mapPath = new HashMap<>(); //init HashMap of key: Integer , value: node_info
        for(node_data ni : arena.getGraph().getV()) //We go through all the vertices
        {
            ni.setTag(0);
            ni.setWeight(Double.MAX_VALUE); //set their tag to Max_Value
            ni.setInfo("WHITE"); //  set their info to WHITE
            mapPath.put(ni.getKey(),null); //put in our HashMap (father path)  - key: key of the node , value: null
            queue.add(ni); //add to our PriorityQueue the node
        }
        node.setWeight(0); //set tag of our start node to be 0
        queue.remove(node);//decreaseKey - we're removing the node and add him back
        queue.add(node);
        while(!queue.isEmpty()) // while our PriorityQueue is not empty
        {
            node_data n = queue.remove(); //remove our node that we're working on him
            for(edge_data edge : arena.getGraph().getE(n.getKey())) //We go through all his neighbors
            {
                node_data ni = arena.getGraph().getNode(edge.getDest());
                //if(node.getTag() != -1) {
                    if (ni.getInfo().equals("WHITE")) //if he is WHITE We never went through it
                    {
                        if (n.getWeight() < Double.MAX_VALUE) { //if tag smallest than MAX_VALUE
                            double t = n.getWeight() + edge.getWeight();
                            if (ni.getWeight() > t) { //if the tag of the neighbor bigger than new path tag so update the neighbor tag
                                ni.setWeight(t); //neighbor tag to be t
                                mapPath.put(ni.getKey(), n); //update the father path
                                queue.remove(ni);//decreaseKey - we're removing the node and add him back
                                queue.add(ni);
                            }
                        }
                    }
                //}
            }
            n.setInfo("BLACK"); //we finish with the node set info to BLACK
        }
        return mapPath; //return the father path

    }

    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> list = new LinkedList<>(); //list of the path from src to dest
        if(arena.getGraph().getNode(src) == null || arena.getGraph().getNode(dest) == null) // if src or dest not exist return null
        {
            return null;
        }
        if(src == dest) //if src equal to dest
        {
            list.add(arena.getGraph().getNode(src)); //add src to list
            return list; //return list
        }
        HashMap<Integer,node_data> pv = dijkstra(this.arena.getGraph().getNode(src));//start dijkstra on src and return hashmap that contain the path fathers
        if(arena.getGraph().getNode(dest).getInfo().equals("WHITE")) //if the dest is WHITE so we didnt move on him so return null
        {
            return null;
        }

        list.addFirst(arena.getGraph().getNode(dest)); //add to list dest
        node_data t = pv.get(dest); // t = next node

        while(t != null)
        {
            list.addFirst(arena.getGraph().getNode(t.getKey())); // add the t to list
            t = pv.get(t.getKey()); // t = next node
        }
        node_data n = list.get(list.size()-1);
        for(edge_data e : arena.getGraph().getE(n.getKey()))
        {
            node_data nodeDest = arena.getGraph().getNode(e.getDest());
            node_data nodeSrc = arena.getGraph().getNode(e.getSrc());
            if(nodeDest.getTag() == -1)
            {
                list.add(arena.getGraph().getNode(e.getSrc()));
                nodeDest.setTag(0);
                nodeSrc.setTag(0);
                break;
            }
        }
        return list;
    }

//    public HashMap<Integer,node_data> BFS(node_data n) // O(|V|+|E|)
//    {
//        HashMap<Integer,node_data> bfsMap = new HashMap<>();
//        for(node_data node : arena.getGraph().getV())
//        {
//            node.setInfo("WHITE"); // all nodes WHITE
//            node.setWeight(Integer.MAX_VALUE); // all nodes MAX_VALUE
//            bfsMap.put(node.getKey(),null);
//        }
//        n.setInfo("Gray");
//        n.setWeight(0);
//        Queue<node_data> q = new LinkedList<>();
//        q.add(n);
//
//        while(!q.isEmpty())
//        {
//            node_data newNode = q.remove();
//            for(edge_data edge : arena.getGraph().getE(newNode.getKey()))
//            {
//                node_data node = arena.getGraph().getNode(edge.getDest());
//                if(node.getTag() != -1)
//                {
//                    if(node.getInfo().equals("WHITE"))
//                    {
//                        node.setInfo("GRAY");
//                        node.setWeight(newNode.getWeight() + 1);
//                        q.add(node);
//                        bfsMap.put(node.getKey(),newNode);
//                    }
//                }
//            }
//            newNode.setInfo("BLACK");
//        }
//        return bfsMap;
//    }

//    public List<node_data> shortestPath(int src, int dest) {
//        LinkedList<node_data> list = new LinkedList<>();
//        if(arena.getGraph().getNode(src) == null || arena.getGraph().getNode(dest) == null)
//        {
//            return null;
//        }
//        HashMap<Integer,node_data> pv = BFS(this.arena.getGraph().getNode(src));
//        if(arena.getGraph().getNode(dest).getWeight() == Integer.MAX_VALUE)
//        {
//            return null;
//        }
//
//        list.addFirst(arena.getGraph().getNode(dest));
//        node_data t = pv.get(dest);
//
//        while(t != null)
//        {
//            list.addFirst(arena.getGraph().getNode(t.getKey()));
//            t = pv.get(t.getKey());
//        }
//
//        node_data n = list.get(list.size()-1);
//        for(edge_data e : arena.getGraph().getE(n.getKey()))
//        {
//            node_data nodeDest = arena.getGraph().getNode(e.getDest());
//            node_data nodeSrc = arena.getGraph().getNode(e.getSrc());
//            if(nodeDest.getTag() == -1)
//            {
//                list.add(arena.getGraph().getNode(e.getDest()));
//                nodeDest.setTag(0);
//                nodeSrc.setTag(0);
//                break;
//            }
//        }
//
//        return list;
//    }

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

}
