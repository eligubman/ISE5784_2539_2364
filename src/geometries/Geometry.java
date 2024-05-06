package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is the basic interface for all geometric objects
 */
public interface Geometry {
    /**
     * getNormal function returns the normal to the geometry at the point
     * @param p  the point on the geometry surface for which the normal is required
     * @return the normal to the geometry at the point
     */
    Vector getNormal(Point p);


}
