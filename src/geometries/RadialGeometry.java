package geometries;
/**
 * RadialGeometry is an abstract class representing a radial geometry shape
 */
public abstract class RadialGeometry implements Geometry{
    /**
     * radius of the geometry shape
     */
    protected final double radius;

    /**
     * constructor for RadialGeometry class
     * @param radius the radius of the geometry shape
     */
    public RadialGeometry(double radius){
        this.radius=radius;
    }
}
