package primitives;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * Vector class represents a vector in 3D Cartesian coordinate
 * system
 */
public class Vector extends  Point {

    /**
     * y vector
     */
    public static final Vector Y=new Vector(0,1,0);
    /**
     * z vector
     */
    public static final Vector Z=new Vector(0,0,1);
    /**

    /**
     * Constructor for Vector class receiving 3 coordinates
     * @param x coordinate on the x-axis
     * @param y coordinate on the y-axis
     * @param z coordinate on the z-axis
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(x==0 && y==0 && z==0)
            throw new IllegalArgumentException("Vector 0 is illegal");
    }
    /**
     * Constructor for Vector class receiving a Double3
     * @param xyz the coordinates of the vector
     */
   public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 is illegal");
    }

    /**
     * add a vector to the vector
     * @param v the vector to add
     * @return the new vector after adding the vector
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * scale the vector by a factor
     * @param scalingFactor the factor to scale the vector by
     * @return the new vector after scaling
     */
    public Vector scale(double scalingFactor) {
        return new Vector(xyz.scale(scalingFactor));
    }

    /**
     * dot product of two vectors
     * @param v the vector to dot product with
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    /**
     * cross product of two vectors
     * @param v the vector to cross product with
     * @return the cross product of the two vectors
     */
    public Vector crossProduct(Vector v) {
        return new Vector(
                new Double3(
                        xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2,
                        xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
                        xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1));
    }

    /**
     * calculate the squared length of the vector
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * calculate the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector
     * @return the normalized vector
     */
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


    /**
     * create vector normal to this vector
     *
     * @return
     */
    public Vector createNormal() {
        if (Util.isZero(this.xyz.d1))
            return new Vector(1, 0, 0);

        return new Vector(this.xyz.d2, -this.xyz.d1, 0).normalize();
    }


    /**
     * Given a vector and an angle, rotate the vector about the given axis by the given angle
     *
     * @param axis  The axis of rotation.
     * @param theta the angle of rotation in degrees
     * @return A rotated new vector.
     */
    public Vector rotateVector(Vector axis, double theta) {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        double u = axis.getX();
        double v = axis.getY();
        double w = axis.getZ();
        double v1 = u * x + v * y + w * z;
        double thetaRad = toRadians(theta);
        double thetaCos = cos(thetaRad);
        double thetaSin = sin(thetaRad);
        double diff = 1d - thetaCos;
        double xPrime = u * v1 * diff + x * thetaCos + (-w * y + v * z) * thetaSin;
        double yPrime = v * v1 * diff + y * thetaCos + (w * x - u * z) * thetaSin;
        double zPrime = w * v1 * diff + z * thetaCos + (-v * x + u * y) * thetaSin;

        return new Vector(alignZero(xPrime), alignZero(yPrime), alignZero(zPrime));
    }
}
