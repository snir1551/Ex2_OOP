package api;

import gameClient.util.Point3D;
import gameClient.util.Range;

public class NodeData implements node_data{

    private int key;
    private Point3D location;
    private double weight;
    private String info;
    private int tag;
    public NodeData(int key,Point3D location)
    {
        this.key = key;
        this.location = new Point3D(location);
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return location;
    }

    @Override
    public void setLocation(geo_location p) {
        location = new Point3D(p.x(),p.y(),p.z());
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
