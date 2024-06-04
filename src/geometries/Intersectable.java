package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface is the basic interface for all geometries that can be intersected by a ray
 */
public interface Intersectable {
    /**
     * findIntersections function returns a list of intersection points of a ray with the geometry
     * @param ray the ray that intersects the geometry
     * @return a list of intersection points of the ray with the geometry
     */
    List<Point> findIntersections(Ray ray) ;
}
