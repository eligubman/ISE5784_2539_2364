package primitives;

/**
 * Point class represents a point in 3D Cartesian coordinate system
 */
public class Point {
    /**
     * Point in 3D space
     */
    protected final Double3 xyz;
    /**
     * Origin point (0,0,0)
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * Constructor for Point
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor for Point
     *
     * @param xyz the coordinates of the point
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * add a vector to the point
     *
     * @param v the vector to add
     * @return the new point after adding the vector
     */
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }

    /**
     * subtract a point from the point
     *
     * @param p the point to subtract
     * @return the new vector after subtracting the point
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * calculate the squared distance between two points
     *
     * @param p the point to calculate the distance to
     * @return the squared distance between the two points
     */
    public double distanceSquared(Point p) {
        return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2) + (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);

    }

    /**
     * calculate the distance between two points
     *
     * @param p the point to calculate the distance to
     * @return the distance between the two points
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "" + xyz;
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }


}

