package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {
    /**
     * axis of the tube
     */
    protected final Ray axis;

    /**
     * constructor for Tube class
     *
     * @param radius radius of the tube
     * @param axis axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        // the formula to calculate the normal of a point on the tube is:
        // p-o where o is the point on the axis that is closest to p
        //now let's find o
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        // if the point is the head of the axis
        if (p.equals(p0)) {
            return v.scale(-1);
        }
        Vector delta = p.subtract(p0);
        double t = v.dotProduct(delta);
        Point o;

        // if delta is orthogonal to the axis
        if (t == 0) {
            o = p0;
        }
        // if t>0, the point o is on the axis but not at the head
        else {
            o = p0.add(v.scale(t));
        }
        return p.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }
}
