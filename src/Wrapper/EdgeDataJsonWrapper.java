package Wrapper;

import api.edge_data;

public class EdgeDataJsonWrapper {
    private final int src;
    private final int dest;
    private final double weight;
    private final String info;
    private final int tag;

    public EdgeDataJsonWrapper(edge_data edgeData) {
        this.src = edgeData.getSrc();
        this.dest = edgeData.getDest();
        this.weight = edgeData.getWeight();
        this.info = edgeData.getInfo();
        this.tag = edgeData.getTag();
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public double getWeight() {
        return weight;
    }

    public String getInfo() {
        return info;
    }

    public int getTag() {
        return tag;
    }
}
