package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry{
    /**
     * axis of the tube
     */
    protected final Ray axis;

    /**
     * constructor for Tube class
     * @param radius radius of the tube
     * @param axis axis of the tube
     */
  public   Tube(double radius,Ray axis){
        super(radius);
        this.axis=axis;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
