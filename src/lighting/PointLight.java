package lighting;
import primitives.*;

/**
 * PointLight class represents a point light in the scene
 */
public class PointLight extends Light implements LightSource{
    private Point position;
    private double kc=1;
    private double kl=0;
    private double kq=0;

    /**
     * PointLight constructor
     * @param intensity the intensity of the light
     * @param position the position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1/(kC+kL*d+kQ*d*d));
    }

    @Override
    public Vector getL(Point p) {
        // if the point is the same as the light source, return null
        if (p.equals(position))
           return null;
        // otherwise, return the normalized vector from the light source to the point
        return p.subtract(position).normalize();
    }

    //----------------- setters-----------------

    /**
     * Set the constant attenuation factor
     * @param kc the constant attenuation factor
     */
    public PointLight setKc(double kc) {
        this.kc = kc;
        return this;
    }

    /**
     * Set the linear attenuation factor
     * @param kl the linear attenuation factor
     */
    public PointLight setKl(double kl) {
        this.kl = kl;
        return this;
    }

    /**
     * Set the quadratic attenuation factor
     * @param kq the quadratic attenuation factor
     */
    public PointLight setKq(double kq) {
        this.kq = kq;
        return this;
    }
}
