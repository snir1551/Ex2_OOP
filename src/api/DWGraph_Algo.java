package api;

import JsonWrapper.DirectedWeightedGraphJsonWrapper;
import com.google.gson.*;
import gameClient.util.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms{

    directed_weighted_graph graph;

    public DWGraph_Algo(directed_weighted_graph graph)
    {
        init(graph);
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copyGraph = new DWGraph_DS(graph);
        return copyGraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * @return
     */
    @Override
    public boolean isConnected() {
        if(graph.nodeSize() <= 1)
            return true;
        List<List<node_data>> listComponents = tarjan();
        return listComponents.size() == 1;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(graph.getNode(src) == null || graph.getNode(dest) == null) //if one of the nodes not exist return -1
            return -1;
        if(src == dest)
            return 0;
        dijkstra(graph.getNode(src)); //start dijkstra algorithm on the (src node)
        if(graph.getNode(dest).getWeight() >= Double.MAX_VALUE)
            return -1;
        return graph.getNode(dest).getWeight(); // return the shortest path between them by the tag that contain the distance
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> list = new LinkedList<>(); //list of the path from src to dest
        if(graph.getNode(src) == null || graph.getNode(dest) == null) // if src or dest not exist return null
        {
            return null;
        }
        if(src == dest) //if src equal to dest
        {
            list.add(graph.getNode(src)); //add src to list
            return list; //return list
        }
        HashMap<Integer,node_data> pv = dijkstra(this.graph.getNode(src));//start dijkstra on src and return hashmap that contain the path fathers
        if(graph.getNode(dest).getInfo().equals("WHITE")) //if the dest is WHITE so we didnt move on him so return null
        {
            return null;
        }

        list.addFirst(graph.getNode(dest)); //add to list dest
        node_data t = pv.get(dest); // t = next node

        while(t != null)
        {
            list.addFirst(graph.getNode(t.getKey())); // add the t to list
            t = pv.get(t.getKey()); // t = next node
        }

        return list;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(new DirectedWeightedGraphJsonWrapper(this.graph));

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
                DirectedWeightedGraphJsonWrapper graphJsonWrapper = new Gson().fromJson(new FileReader(new File(file)), DirectedWeightedGraphJsonWrapper.class);
                graph = new DWGraph_DS(graphJsonWrapper);
                return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }





    public HashMap<Integer, node_data> dijkstra(node_data node)
    {
        PriorityQueue<node_data> queue = new PriorityQueue<>(); // init PriorityQueue of node_info
        HashMap<Integer,node_data> mapPath = new HashMap<>(); //init HashMap of key: Integer , value: node_info
        for(node_data ni : graph.getV()) //We go through all the vertices
        {
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
            for(edge_data edge : graph.getE(n.getKey())) //We go through all his neighbors
            {
                node_data ni = graph.getNode(edge.getDest());
                if(ni.getInfo().equals("WHITE")) //if he is WHITE We never went through it
                {
                    if(n.getWeight() < Double.MAX_VALUE) { //if tag smallest than MAX_VALUE
                        double t = n.getWeight() + edge.getWeight();
                        if (ni.getWeight() > t) { //if the tag of the neighbor bigger than new path tag so update the neighbor tag
                            ni.setWeight(t); //neighbor tag to be t
                            mapPath.put(ni.getKey(), n); //update the father path
                            queue.remove(ni);//decreaseKey - we're removing the node and add him back
                            queue.add(ni);
                        }
                    }
                }
            }
            n.setInfo("BLACK"); //we finish with the node set info to BLACK
        }
        return mapPath; //return the father path

    }




    /**
     * Tarjan's strongly connected components algorithm
     * finding the strongly connected components (SCCs) of a directed graph.
     * @return
     */
    private List<List<node_data>> tarjan() {
        List<List<node_data>> components = new ArrayList<>();

        Stack<node_data> stack = new Stack<>();
        int time = 0;
        for(node_data nodeData : graph.getV()) {
            nodeData.setTag(0); // lowlink
            nodeData.setInfo("white"); // set all to not-visited
        }

        for(node_data nodeData : graph.getV()) {
            if(nodeData.getInfo().equals("white")) { // not visited
                dfs(nodeData, time, stack, components);
            }
        }

        return components;
    }

    /**
     * DFS is an algorithm for traversing or searching tree or graph data structures.
     * The algorithm starts at the root node (selecting some arbitrary node as the root node in the case of a graph)
     * and explores as far as possible along each branch before backtracking.
     * @param nodeData
     * @param time
     * @param stack
     * @param components
     */
    private void dfs(node_data nodeData, int time, Stack<node_data> stack, List<List<node_data>> components ) {
        nodeData.setTag(time);
        time++;
        nodeData.setInfo("black");
        stack.add(nodeData);
        boolean componentRoot = true;

        for(edge_data edge: graph.getE(nodeData.getKey())) {
            node_data neighbor = graph.getNode(edge.getDest());

            if(neighbor.getInfo().equals("white")) { // not visited
                dfs(neighbor, time, stack, components);
            }
            if(nodeData.getTag() > neighbor.getTag()) {
                nodeData.setTag(neighbor.getTag());
                componentRoot = false;
            }
        }

        if(componentRoot) {
            List<node_data> component = new ArrayList<>();

            while(true) {
                node_data nd = stack.pop();
                component.add(nd);
                nd.setTag(Integer.MAX_VALUE);

                if(nd.getKey() == nodeData.getKey()) {
                    break;
                }
            }

            components.add(component);
        }
    }



    public List<Integer> componnt(int at) {
        List<Integer> path = new ArrayList<Integer>();
        if (this.graph == null||this.graph.getNode(at)==null){
            return path;
        }

        int id = 0;
        Stack<Integer> stack =new Stack<Integer>();
        HashMap<Integer,Integer> lows = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> ids = new HashMap<Integer,Integer>();
        HashMap<Integer,Boolean> onStack = new HashMap<Integer,Boolean>();



        for (node_data i:this.graph.getV()) {
            lows.put(i.getKey(), 0);
            ids.put(i.getKey(), 0);
            onStack.put(i.getKey(),false);
        }
        int v=at;
        Stack<Pair> work=new Stack<Pair>();
        work.add(new Pair(v,0));
        while (!work.isEmpty()){
            Pair p= work.pop();
            v=p.v;
            int i=p.i;
            if (i==0){
                stack.add(v);
                id++;
                lows.put(v, id);
                ids.put(v, id);
                onStack.put(v, true);
            }
            boolean  recurse = false;
            int j=0;
            for(edge_data edge:this.graph.getE(v)) {
                int w=edge.getDest();
                if (ids.get(w)==0) {
                    work.add(new Pair(v,j+1));
                    work.add(new Pair(w,0));
                    recurse =true;
                    j++;
                    break;
                }else if(onStack.get(w)) {
                    j++;
                    lows.put(v, Math.min(lows.get(v), lows.get(w)));
                }
            }

            if (recurse) continue;
            if(ids.get(v) == lows.get(v)) {
                path.clear();
                while(!stack.isEmpty()) {
                    int node = stack.pop();
                    path.add(node);
                    onStack.put(node, false);
                    lows.put(node, ids.get(v));
                    if(node==v) break;
                }

            }
            if (!work.isEmpty()) {
                int w = v;
                Pair pe = work.peek();
                v=pe.v;
                lows.put(v, Math.min(lows.get(v), lows.get(w)));
            }
        }

        return path;
    }


    public List<String> componnts() {
        List<String> path_lists=new ArrayList<String>();
        if (this.graph == null){
            return path_lists;
        }

        int id = 0;
        Stack<Integer> stack =new Stack<Integer>();
        HashMap<Integer,Integer> lows = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> ids = new HashMap<Integer,Integer>();
        HashMap<Integer,Boolean> onStack = new HashMap<Integer,Boolean>();

        List<Integer> path = new ArrayList<Integer>();

        for (node_data i:this.graph.getV()) {
            lows.put(i.getKey(), 0);
            ids.put(i.getKey(), 0);
            onStack.put(i.getKey(),false);
        }

        for (node_data no :this.graph.getV()) {
            if (ids.get(no.getKey()) == 0){
                int v=no.getKey();
                Stack<Pair> work=new Stack<Pair>();
                work.add(new Pair(v,0));
                while (!work.isEmpty()){
                    Pair p= work.pop();
                    v=p.v;
                    int i=p.i;
                    if (i==0){
                        stack.add(v);
                        id++;
                        lows.put(v, id);
                        ids.put(v, id);
                        onStack.put(v, true);
                    }
                    boolean  recurse = false;
                    int j=0;
                    for(edge_data edge:this.graph.getE(v)) {
                        int w=edge.getDest();
                        if (ids.get(w)==0) {
                            work.add(new Pair(v,j+1));
                            work.add(new Pair(w,0));
                            recurse =true;
                            j++;
                            break;
                        }else if(onStack.get(w)) {
                            j++;
                            lows.put(v, Math.min(lows.get(v), lows.get(w)));
                        }
                    }

                    if (recurse) continue;
                    if(ids.get(v) == lows.get(v)) {
                        path.clear();
                        while(!stack.isEmpty()) {
                            int node = stack.pop();
                            path.add(node);
                            onStack.put(node, false);
                            lows.put(node, ids.get(v));
                            if(node==v) break;
                        }
                        path_lists.add(Arrays.deepToString(path.toArray()));
                    }
                    if (!work.isEmpty()) {
                        int w = v;
                        Pair pe = work.peek();
                        v=pe.v;
                        lows.put(v, Math.min(lows.get(v), lows.get(w)));
                    }
                }

            }

        }

        return path_lists;
    }

    public boolean loadNx(String file) {
        try {

            directed_weighted_graph newGraph = new DWGraph_DS();
            JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            JsonArray E = json.getAsJsonArray("Edges");
            JsonArray V = json.getAsJsonArray("Nodes");
            //run by json and convert it to Nodes
            for (JsonElement node: V){
                int id = node.getAsJsonObject().get("id").getAsInt();
                String pos = node.getAsJsonObject().get("pos").getAsString();
                String[] splitPos = pos.split(",");
                double posX = Double.parseDouble(splitPos[0]);
                double posY = Double.parseDouble(splitPos[1]);
                double posZ = Double.parseDouble(splitPos[2]);
                node_data new_node = new NodeData(id,new Point3D(posX,posY,posZ));
                newGraph.addNode(new_node);
            }
            //run by json and convert it to Edges
            for (JsonElement edge : E){
                int src = edge.getAsJsonObject().get("src").getAsInt();
                int dest = edge.getAsJsonObject().get("dest").getAsInt();
                double w = edge.getAsJsonObject().get("w").getAsDouble();
                newGraph.connect(src,dest,w);
            }
            this.graph = newGraph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
