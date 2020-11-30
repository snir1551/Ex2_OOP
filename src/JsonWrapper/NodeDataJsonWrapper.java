package JsonWrapper;

import api.node_data;

public class NodeDataJsonWrapper {
    private final int key;
    private final GeoLocationJsonWrapper location;
    private final double weight;
    private final String info;
    private final int tag;

    public NodeDataJsonWrapper(node_data nodeData) {
        this.key = nodeData.getKey();
        this.location = new GeoLocationJsonWrapper(nodeData.getLocation());
        this.weight = nodeData.getWeight();
        this.info = nodeData.getInfo();
        this.tag = nodeData.getTag();
    }

    public int getKey() {
        return key;
    }

    public GeoLocationJsonWrapper getLocation() {
        return location;
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
