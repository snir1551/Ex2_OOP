package gameClient;



public class Server {
    private int pokemons; //pokemon that need to be on the specific lvl in the game
    private boolean is_logged_in; // check logged in to the game
    private int moves; // the moves that do in the game
    private int grade; // the grade of the game
    private int game_level; // the lvl of the game
    private int max_user_level;
    private int id;
    private String graph; // the graph the game lvl
    private int agents; // agents that need to be on the specific lvl in the game


    /**
     * Constructor
     * @param pokemons
     * @param is_logged_in
     * @param moves
     * @param grade
     * @param game_level
     * @param max_user_level
     * @param id
     * @param graph
     * @param agents
     */
    public Server(int pokemons, boolean is_logged_in, int moves, int grade,int game_level, int max_user_level, int id, String graph, int agents)
    {
        this.pokemons = pokemons;
        this.is_logged_in = is_logged_in;
        this.moves = moves;
        this.grade = grade;
        this.game_level = game_level;
        this.max_user_level = max_user_level;
        this.id = id;
        this.graph = graph;
        this.agents = agents;
    }


    /**
     * getter method
     * @return
     */
    public int getPokemons() {
        return pokemons;
    }



    public boolean isIs_logged_in() {
        return is_logged_in;
    }

    /**
     * getter mothod
     * @return moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * getter method
     * @return grade
     */
    public int getGrade() {
        return grade;
    }

    public int getGame_level() {
        return game_level;
    }

    public int getMax_user_level() {
        return max_user_level;
    }

    /**
     * getter method
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter method
     * @return graph - String
     */
    public String getGraph() {
        return graph;
    }

    /**
     * getter method
     * @return agents
     */
    public int getAgents() {
        return agents;
    }








}