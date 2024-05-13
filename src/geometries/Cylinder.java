package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate
 * system
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * constructor for Cylinder class
     *
     * @param radius radius of the cylinder
     * @param axis axis of the cylinder
     * @param height height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        // the formula to calculate the normal of a point on the cylinder is:
        // if the point is on the bottom or top of the cylinder,
        // the normal is the vector from the point to the center of the cylinder
        // if the point is on the side of the cylinder,
        // the normal is the vector from the point to the axis
        Point p0 = axis.head;
        Vector v = axis.direction;
        // if the point is the head of the axis
        if(p.equals(p0)){
            return v.scale(-1);
        }
        Vector delta = p.subtract(p0);
        double t = v.dotProduct(delta);
        Point o;

        // if t=0, the point is on the axis
        if (t == 0) {
            o = p0;
        } else {
            o = p0.add(v.scale(t));
        }
        // if the point is on the bottom or top of the cylinder
        if(p.equals(o)){
            return p.subtract(p0).normalize();
        }
        // if the point is on the bottom or top of the cylinder
        if (p.subtract(o).length() == radius) {
            return p.subtract(o).normalize();
        }
        // if the point is on the side of the cylinder
        return super.getNormal(p);

    }
}
