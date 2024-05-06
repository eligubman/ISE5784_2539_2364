package geometries;

import primitives.Point;
import primitives.Vector;
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
        return null;
    }
}
