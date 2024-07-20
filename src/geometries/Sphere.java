package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.List;

/**
 * Sphere class represents a sphere in 3D Cartesian coordinate system
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor for Sphere class
     *
     * @param radius radius of the sphere
     * @param center center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
        this.boundary=calcBoundary();
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // if the ray starts at the center of the sphere
        if (ray.getHead().equals(center)) {
            return alignZero(this.radius - maxDistance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(this.radius)));
        }
        //check if there is intersection between them
        Vector v = center.subtract(ray.getHead());

        double tm = alignZero(ray.getDirection().dotProduct(v));

        //check if the ray is tangent to the sphere
        double d = alignZero(Math.sqrt(v.lengthSquared() - tm * tm));
        if (d >= radius) return null;
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if(t2<=0){
            return null;
        }
        if (t1 <= 0) {
            // One intersection point is behind the ray, the other is in front
            //We will check that our point is at the appropriate distance
            if (alignZero(maxDistance - t2) > 0){
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
            }
        }
        else {
            // Both intersection points are in front of the ray
            //We will check that our point is at the appropriate distance
            if (alignZero(maxDistance - t1) > 0 && alignZero(maxDistance - t2) > 0){
                return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
            }
            else if (alignZero(maxDistance - t1) > 0){
               return List.of(new GeoPoint(this,ray.getPoint(t1)));
            }
            else {
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
            }
        }

        return null;
    }

    @Override
    public int[][] calcBoundary() {
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();

        return new int[][]{{(int) Math.floor(x - radius), (int) Math.ceil(x + radius)},
                {(int) Math.floor(y - radius), (int) Math.ceil(y + radius)},
                {(int) Math.floor(z - radius), (int) Math.ceil(z + radius)}};
    }
}

