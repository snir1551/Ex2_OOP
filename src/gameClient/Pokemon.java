package gameClient;

import api.geo_location;
import gameClient.util.Point3D;

public class Pokemon {
    private double value;
    private int type;
    private Point3D location;

    public Pokemon(double value, int type, Point3D location)
    {
        this.value = value;
        this.type = type;
        this.location = new Point3D(location);
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

    public String toString()
    {
        return "value = " + value + " Type = " + type + " location[" + location.x() + "," + location.y() + "," + location.z() + "]";
    }



}
