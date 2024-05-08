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
        Point p0=axis.head;
        Vector v=axis.direction;
        Vector delta=p.subtract(p0);
        double t=v.dotProduct(delta);
        Point o;
        // if t=0, the point is on the axis
        if(t==0){
            o=p0;
        }
        else{
            o=p0.add(v.scale(t));
        }
        return p.subtract(o).normalize();
    }
}
