package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate
 * system
 */
public class Cylinder extends Tube{
   private final double height;

    /**
     * constructor for Cylinder class
     * @param radius radius of the cylinder
     * @param axis axis of the cylinder
     * @param height height of the cylinder
     */
  public Cylinder(double radius, Ray axis, double height){
        super(radius,axis);
        this.height=height;
    }

    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}
