package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Plane is the basic class representing a plane in 3D Cartesian coordinate
 * system
 */
public class Plane extends Geometry {
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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        //if the ray starts at the plane
        if(ray.getHead().equals(q)){
            return null;
        }

      double t=alignZero(normal.dotProduct(ray.getDirection()));
        //if the ray is parallel to the plane
        if(isZero(t)){
            return null;
        }

        double t1=alignZero(normal.dotProduct(q.subtract(ray.getHead()))/t);
        //if the ray is in the opposite direction of the normal
        if(t1<=0){
            return null;
        }
        //if the ray intersects the plane
        return List.of(new GeoPoint(this,ray.getPoint(t1)));
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
