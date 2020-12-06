package JsonWrapper;


import api.geo_location;

public class GeoLocationJsonWrapper {
    private double x;
    private double y;
    private double z;

    public GeoLocationJsonWrapper(geo_location location) {
        this.x = location.x();
        this.y = location.y();
        this.z = location.z();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}


