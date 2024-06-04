package lighting;
import primitives.*;
/**
 * AmbientLight class represents an ambient light in the scene
 */
public class AmbientLight extends Light {

    public final static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    /**
     * AmbientLight constructor
     * @param iA intensity of the light
     * @param kA the light intensity scaling factor
     */
    public AmbientLight(Color iA,Double3 kA){
       super(iA.scale(kA));
    }
    /**
     * AmbientLight constructor
     * @param iA intensity of the light
     * @param kA the light intensity scaling factor
     */
    public AmbientLight(Color iA, double kA){
       super(iA.scale(kA));
    }


}
