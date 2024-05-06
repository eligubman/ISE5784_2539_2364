package primitives;

public class Vector extends  Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(x==0 && y==0 && z==0)
            throw new IllegalArgumentException("Vector 0 is illegal");
    }

    Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 is illegal");
    }
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }
    public Vector scale(double scalingFactor) {
        return new Vector(xyz.scale(scalingFactor));
    }
    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }
    public Vector crossProduct(Vector v) {
        return new Vector(
                new Double3(
                        xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2,
                        xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
                        xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1));
    }
    public double lengthSquared() {
        return dotProduct(this);
    }
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize() {
        return scale(1 / length());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }
    @Override
    public String toString() { return "->" + super.toString(); }
}
