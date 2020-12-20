package gameClient;

import api.edge_data;
import api.geo_location;
import gameClient.util.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pokemon {
    private double value; //value of the pokemon
    private int type; // type of the pokemon
    private Point3D location; // location of the pokemon
    private edge_data _edge; // the edge that pokemon sit on him
    private String image; // path image of the pokemon
    private String id; // id of the pokemon

    /**
     *
     * @param value
     * @param type
     * @param location
     */
    public Pokemon(double value, int type, Point3D location)
    {
        this.value = value;
        this.type = type;
        this.location = new Point3D(location);
        id = location.toString();
        if(type == -1)
        {
            this.image = "src\\gameClient\\resources\\Pokemons\\pokemon1.png";
        }
        else
        {
            this.image = "src\\gameClient\\resources\\Pokemons\\pokemon2.png";
        }
    }


    /**
     * getter method
     * @return location of the pokemon
     */
    public geo_location getLocation() {
        return location;
    }

    /**
     * getter method
     * @return value of the pokemon
     */
    public double getValue()
    {
        return value;
    }

    /**
     * getter method
     * @return the type of the pokemon
     */
    public int getType()
    {
        return type;
    }

    /**
     * setter method
     * @param e - edge_data
     */
    public void set_edge(edge_data e)
    {
        this._edge = e;
    }

    /**
     * getter method
     * @return edge_data
     */
    public edge_data get_edge()
    {
        return _edge;
    }

    /**
     *
     * @return toString
     */
    @Override
    public String toString()
    {
        return "value = " + value + " Type = " + type + " location[" + location.x() + "," + location.y() + "," + location.z() + "]";
    }

    /**
     * getter method
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * setter method
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     *
     * @return the image of the pokemon
     */
    public BufferedImage getImg()
    {
        BufferedImage PokemonImg = null;
        File pokemonFile = new File(this.image);
        try {
            PokemonImg = ImageIO.read(pokemonFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return PokemonImg;
    }
}
