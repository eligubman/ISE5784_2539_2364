package lighting;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The SpotLight class represents a spot light in the scene.
 * A spot light is a light source that has a specific direction,
 * and its intensity can be controlled by various attenuation factors
 * and a narrow beam factor.
 */
public class SpotLight extends PointLight {
    private final Vector direction;
    private double narrowBeam = 1;

    /**
     * SpotLight constructor.
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Get the intensity of the light at a given point.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light at point p
     */
    @Override
    public Color getIntensity(Point p) {
        double cos = alignZero(direction.dotProduct(getL(p)));
        return narrowBeam != 1
                ? super.getIntensity(p).scale(Math.pow(Math.max(0, direction.dotProduct(getL(p))), narrowBeam))
                : super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
    }

    /**
     * Set the constant attenuation factor.
     *
     * @param kc the constant attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKc(double kc) {
        return (SpotLight) super.setKc(kc);
    }

    /**
     * Set the linear attenuation factor.
     *
     * @param kl the linear attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKl(double kl) {
        return (SpotLight) super.setKl(kl);
    }

    /**
     * Set the quadratic attenuation factor.
     *
     * @param kq the quadratic attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKq(double kq) {
        return (SpotLight) super.setKq(kq);
    }

    /**
     * Set the narrow beam factor.
     * The narrow beam factor adjusts the concentration of the light beam.
     *
     * @param i the narrow beam factor
     * @return the SpotLight object
     */
    public SpotLight setNarrowBeam(double i) {
        this.narrowBeam = i;
        return this;
    }
}
