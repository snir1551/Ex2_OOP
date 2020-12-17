package gameClient;

import api.edge_data;
import api.geo_location;
import gameClient.util.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pokemon {
    private double value;
    private int type;
    private Point3D location;
    private edge_data _edge;
    private String image;
    private String id;

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



    public geo_location getLocation() {
        return location;
    }

    public double getValue()
    {
        return value;
    }

    public int getType()
    {
        return type;
    }

    public void set_edge(edge_data e)
    {
        this._edge = e;
    }

    public edge_data get_edge()
    {
        return _edge;
    }

    public String toString()
    {
        return "value = " + value + " Type = " + type + " location[" + location.x() + "," + location.y() + "," + location.z() + "]";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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
