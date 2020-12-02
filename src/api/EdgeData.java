package api;

import JsonWrapper.EdgeDataJsonWrapper;
import JsonWrapper.NodeDataJsonWrapper;

import java.util.Objects;

public class EdgeData implements edge_data {

    private int src; //The id of the source node of this edge
    private int dest; //The id of the destination node of this edge
    private double weight; // weight of the edge
    private String info;
    private int tag;


    public EdgeData(int src, int dest,double weight)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }


    public EdgeData(int src, int dest, double weight, String info, int tag)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public EdgeData(edge_data edge)
    {
        this.src = edge.getSrc();
        this.dest = edge.getDest();
        this.weight = edge.getWeight();
        this.info = edge.getInfo();
        this.tag = edge.getTag();
    }

    /**
     * The id of the source node of this edge.
     * @return src
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     * @return id
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     * @return info
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return src == edgeData.src &&
                dest == edgeData.dest &&
                Double.compare(edgeData.weight, weight) == 0 &&
                tag == edgeData.tag &&
                Objects.equals(info, edgeData.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight, info, tag);
    }
}
