package lighting;
import primitives.*;
/**
 * AmbientLight class represents an ambient light in the scene
 */
public class AmbientLight {
    private final Color intensity;
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    /**
     * AmbientLight constructor
     * @param iA intensity of the light
     * @param kA the light intensity scaling factor
     */
    public AmbientLight(Color iA,Double3 kA){
        intensity=iA.scale(kA);
    }
    /**
     * AmbientLight constructor
     * @param iA intensity of the light
     * @param kA the light intensity scaling factor
     */
    public AmbientLight(Color iA, double kA){
        intensity=iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
