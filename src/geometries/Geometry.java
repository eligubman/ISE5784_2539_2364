package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry is the basic interface for all geometric objects
 */
public abstract class Geometry extends Intersectable{
    protected Color emission = Color.BLACK;

    /**
     * getEmission function returns the emission color of the geometry
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }
    /**
     * setEmission function sets the emission color of the geometry
     * @param emission the emission color of the geometry
     * @return the geometry itself (for chaining calls)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * getNormal function returns the normal to the geometry at the point
     * @param p  the point on the geometry surface for which the normal is required
     * @return the normal to the geometry at the point
     */
   public abstract Vector getNormal(Point p);


}
