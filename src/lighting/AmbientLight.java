package lighting;
import primitives.*;
public class AmbientLight {
    private final Color intensity;
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    public AmbientLight(Color iA,Double3 kA){
        intensity=iA.scale(kA);
    }

    public AmbientLight(Color iA, double kA){
        intensity=iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
