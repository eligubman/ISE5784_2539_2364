package lighting;
import primitives.*;
/**
 * LightSource interface is the basic interface for all light sources in the scene
 */
public interface LightSource {
    /**
     * Get the intensity of the light at a point
     * @param p the point
     * @return the intensity of the light at the point
     */
    public Color getIntensity(Point p);
    /**
     * Get the vector from the light source to a point
     * @param p the point
     * @return the vector from the light source to the point
     */
    public Vector getL(Point p);

    /**
     *
     * @param point
     * @return
     */
    double getDistance(Point point);
}
