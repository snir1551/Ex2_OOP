package api;

import JsonWrapper.NodeDataJsonWrapper;
import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NodeData implements node_data, Comparable<node_data>{

    private int key;
    private Point3D location;
    private double weight;
    private String info;
    private int tag;

    public NodeData(int key)
    {
        this.key = key;
        location = new Point3D(Point3D.ORIGIN);
        weight = 0;
        info = null;
        tag = 0;
    }
    public NodeData(int key,Point3D location,double weight,String info, int tag)
    {
        this.key = key;
        this.location = new Point3D(location);
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }
    public NodeData(int key,Point3D location)
    {
        this.key = key;
        this.location = new Point3D(location);
    }

    public NodeData(node_data node)
    {
        this.key = node.getKey();
        this.location = new Point3D(node.getLocation().x(),node.getLocation().y(),node.getLocation().z());
        this.weight = node.getWeight();
        this.info = node.getInfo();
        this.tag = node.getTag();
    }

    public NodeData(NodeDataJsonWrapper nodeJsonWrapper)
    {
        this.key = nodeJsonWrapper.getKey();
        this.location = new Point3D(nodeJsonWrapper.getLocation().getX(),nodeJsonWrapper.getLocation().getY(),nodeJsonWrapper.getLocation().getZ());
        this.weight = nodeJsonWrapper.getWeight();
        this.info = nodeJsonWrapper.getInfo();
        this.tag = nodeJsonWrapper.getTag();
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
        return this.weight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return key == nodeData.key &&
                Double.compare(nodeData.weight, weight) == 0 &&
                tag == nodeData.tag &&
                Objects.equals(location, nodeData.location) &&
                Objects.equals(info, nodeData.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, location, weight, info, tag);
    }

    @Override
    public int compareTo(@NotNull node_data o) {
        if(this.getWeight() > o.getWeight())
            return 1;
        else if(this.getWeight() == o.getWeight())
            return 0;
        else
            return -1;
    }

    @Override
    public String toString()
    {
        String ans = "key = " + getKey() + ", " + "location = [" + getLocation().x() + "," + getLocation().y() + "," + getLocation().z() + "] ";
        ans += ", weight = " + getWeight() + ", info = " + getInfo() + ", tag = " + getTag();
        return ans;
    }
}
