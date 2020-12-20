package gameClient;

import api.geo_location;
import gameClient.util.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Agent {

    private int id; //id of agent
    private double value; // value of agent
    private int src; // the src node of the agent
    private int dest; // the dest node of the agent
    private double speed; // speed of the agent
    private Point3D location; // the location of the agent
    private String idPokemon; // idPokemon
    private String image; // the path image
    private double gradeCatchPokemon;

    /**
     *
     * @param id
     * @param value
     * @param src
     * @param dest
     * @param speed
     * @param location
     */
    public Agent(int id, double value, int src, int dest, double speed, Point3D location)
    {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.location = new Point3D(location);
        idPokemon = null;
        this.image = "src\\gameClient\\resources\\Player\\ash.png";
        gradeCatchPokemon = 0;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public double getSpeed() {
        return speed;
    }

    public geo_location getLocation() {
        return location;
    }

    public String getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(String idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String toString()
    {
        return "id = " + getId() + " value = " + getValue() + " src = " + getSrc() + " dest = " + getDest() + " speed = " + getSpeed() + " location" + getLocation();
    }

    public double getGradeCatchPokemon() {
        return gradeCatchPokemon;
    }

    public void setGradeCatchPokemon(double gradeCatchPokemon) {
        this.gradeCatchPokemon = gradeCatchPokemon;
    }

    /**
     *
     * @return the image of the agent
     */
    public BufferedImage getImg()
    {
        BufferedImage agentAsh = null;
        File player = new File(this.image);
        try {
            agentAsh = ImageIO.read(player);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return agentAsh;
    }
}
