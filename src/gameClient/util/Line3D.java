
package gameClient.util;

/**
 * This class represents the line between two points
 */
public class Line3D {
	private Point3D a, b;
	/**
	 * vector (m, n, p) is the direction vector of the line
	 */
	private double m, n, p;
	
	public Line3D(Point3D a, Point3D b) {
		this.a = new Point3D(a);
		this.b = new Point3D(b);
		double dist = a.distance(b);
		this.m = (b.x() - a.x())/dist;
		this.n = (b.y() - a.y())/dist;
		this.p = (b.z() - a.z())/dist;
	}
	public Line3D(Line3D line) {
		this(line.getA(), line.getB());
	}
	public Point3D getA() {return a;}
	public Point3D getB() {return b;}
	public double getM() {return m;}
	public double getN() {return n;}
	public double getP() {return p;}
	
	public Point3D getPointOnLine(double t) {
		double x = a.x() + t*this.m;
		double y = a.y() + t*this.n;
		double z = a.z() + t*this.p;
		return new Point3D(x,y,z);
	}
}
