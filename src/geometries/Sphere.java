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
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    public List<Point> findIntsersections(Ray ray) {
        // if the ray starts at the center of the sphere
        if (ray.getHead().equals(center)) {
            return List.of(ray.getHead().add(ray.getDirection().scale(radius)));
        }
        //check if there is intsersection between them
        Vector v = center.subtract(ray.getHead());

        double tm = alignZero(ray.getDirection().dotProduct(v));
        // if the sphere is behind the ray there is no intserection
        if (tm < 0 || isZero(tm)) return null;
        //check if the ray is tangent to the sphere
        double d = alignZero(Math.sqrt(v.lengthSquared() - tm * tm));
        if (d >= radius) return null;
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)), ray.getHead().add(ray.getDirection().scale(t2)));
        }
        if (t1 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)));
        }
        if (t2 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t2)));
        }
        return null;


    }
}

