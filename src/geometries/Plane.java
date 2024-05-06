package geometries;

import primitives.Point;
import primitives.Vector;

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
        this.normal = null;
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
