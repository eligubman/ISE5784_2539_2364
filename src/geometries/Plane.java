package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Plane is the basic class representing a plane in 3D Cartesian coordinate
 * system
 */
public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * Plane constructor receiving 3 points
     *
     * @param p1 first point on the plane
     * @param p2 second point on the plane
     * @param p3 third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        //the normal calculation is done by the cross product of two vectors on the plane
        this.normal = ((p2.subtract(p1)).crossProduct(p3.subtract(p1))).normalize();
        this.q = p1;
    }

    /**
     * Plane constructor receiving a point and a normal vector
     * @param p=point on the plane
     * @param normal=normal vector to the plane
     */

    public Plane(Point p, Vector normal) {
        this.normal = normal.normalize();
        this.q = p;
    }

    public List<Point> findIntsersections(Ray ray){
        if(ray.getHead().equals(q)){
            return null;
        }
        double t = alignZero( normal.dotProduct(q.subtract(ray.getHead()))/normal.dotProduct(ray.getDirection()));
        if(t>0){
            return List.of(ray.getHead().add(ray.getDirection().scale(t)));
        }
        return null;
    }

    /**
     * getNormal function returns the normal to the plane
     * @return the normal to the plane
     */
    public Vector getNormal() {
        return normal;
    }
    @Override
    public Vector getNormal(Point p) {
        return normal;
    }
}
