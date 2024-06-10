package lighting;
import primitives.*;
/**
 * DirectionalLight class represents a directional light in the scene
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * DirectionalLight constructor
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
