package lighting;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SpotLight class represents a spot light in the scene
 */
public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * SpotLight constructor
     *
     * @param intensity the intensity of the light
     * @param position the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double cos = alignZero(direction.dotProduct(getL(p)));
        if (alignZero(cos) <= 0) return Color.BLACK;
        return super.getIntensity().scale(cos);
    }

    /**
     * set the constant attenuation factor
     *
     * @param kc the constant attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKc(double kc) {
        return (SpotLight) super.setKc(kc);
    }

    /**
     * set the linear attenuation factor
     *
     * @param kl the linear attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKl(double kl) {
        return (SpotLight) super.setKl(kl);
    }

    /**
     * set the quadratic attenuation factor
     *
     * @param kq the quadratic attenuation factor
     * @return the SpotLight object
     */
    public SpotLight setKq(double kq) {
        return (SpotLight) super.setKq(kq);
    }


}
