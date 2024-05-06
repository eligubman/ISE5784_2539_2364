package primitives;

public class Point {
   protected final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }
    Point(Double3 xyz) {
        this.xyz = xyz;
    }
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }
    public double distanceSquared(Point p) {
        return(this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) +
                (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2) +
                (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);

    }
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }
    @Override
    public String toString() { return "" + xyz; }
}

