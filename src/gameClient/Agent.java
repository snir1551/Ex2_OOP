package gameClient;

import api.geo_location;
import gameClient.util.Point3D;

public class Agent {
    private int id;
    private double value;
    private int src;
    private int dest;
    private double speed;
    private Point3D location;
    private static int counter;
    private String idPokemon;
    public Agent(int id, double value, int src, int dest, double speed, Point3D location)
    {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.location = new Point3D(location);
        ++counter;
        idPokemon = null;
    }

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
}
