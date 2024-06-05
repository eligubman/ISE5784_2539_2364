package lighting;
import primitives.*;
/**
 * abstract class Light represents a light in the scene
 */
abstract class Light {
    /**
     * the intensity of the light
     */
    protected Color intensity;

    /**
     * Light constructor
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Get the intensity of the light
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
