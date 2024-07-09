package primitives;
/**
 * Represents the material properties of a geometric object in a 3D scene.
 * This class encapsulates the various coefficients that define how the material interacts with light,
 * including diffuse and specular reflections, transparency (kt), and reflectivity (kr).
 * These properties influence the appearance of the object when rendered in a ray-traced scene.
 * <p>
 * The diffuse reflection coefficient (kd) determines how much of the incident light is scattered in all directions.
 * The specular reflection coefficient (ks) affects the intensity of the specular highlights (shiny spots).
 * The transparency coefficient (kt) defines how much light can pass through the material, enabling the creation of transparent objects.
 * The reflectivity coefficient (kr) controls how much light is reflected by the surface, useful for creating mirrors or shiny surfaces.
 * The shininess of the material (nShininess) influences the size and intensity of the specular highlights.
 * <p>
 * This class provides setter methods for each property, allowing for fluent configuration of material properties.
 */
public class Material {
    /**
     * the diffuse reflection coefficient of the material
     */
    public Double3 kd=Double3.ZERO;
    /**
     * the specular reflection coefficient of the material
     */
    public Double3 ks=Double3.ZERO;
    /**
     * the transparency coefficient of the material
     */
    public Double3 kt=Double3.ZERO;
    /**
     * the reflectivity coefficient of the material
     */
    public Double3 kr=Double3.ZERO;
    /**
     * the shininess of the material
     */
    public int nShininess=0;

    // Parameters for blur glass
    public int numOfRays = 1;
    public double blurGlassDistance = 1, blurGlassRadius = 1;

    /**
     * setter for kd
     * @param kd the kd to set
     */
    public Material setKd(Double3 kd) {
        this.kd = kd;
        return this;
    }
    /**
     * setter for kd with regular double
     *  @param kd the kd to set
     */
    public Material setKd(double kd) {
        this.kd = new Double3(kd);
        return this;
    }


    /**
     * setter for ks
     * @param ks the ks to set
     */
    public Material setKs(Double3 ks) {
        this.ks = ks;
        return this;
    }
    /**
     * setter for ks with regular double
     *  @param ks the ks to set
     */
    public Material setKs(double ks) {
        this.ks = new Double3(ks);
        return this;
    }

    public Material setKr(Double3 kr) {
        this.kr=kr;
        return this;
    }

    public Material setKr(Double kr) {
        this.kr = new Double3(kr);
        return this;
    }

    public Material setKt(Double3 kt) {
        this.kt=kt;
        return this;
    }

    public Material setKt(Double kt) {
        this.kt = new Double3(kt);
        return this;
    }

    /**
     * setter for nShininess
     * @param nShininess the nShininess to set
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }



    /**
     * Sets the number of rays for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @return This Material object.
     * @throws IllegalArgumentException if numOfRays is less than 1.
     */
    public Material setNumOfRays(int numOfRays) {
        if (numOfRays < 1)
            throw new IllegalArgumentException("Illegal argument in setNumOfRays");
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * Sets the distance for blur glass rendering.
     *
     * @param blurGlassDistance The distance to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassDistance is less than or equal
     *                                  to 0.
     */
    public Material setBlurGlassDistance(double blurGlassDistance) {
        if (blurGlassDistance <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassDistance");
        this.blurGlassDistance = blurGlassDistance;
        return this;
    }

    /**
     * Sets the radius for blur glass rendering.
     *
     * @param blurGlassRadius The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassRadius is less than or equal to
     *                                  0.
     */
    public Material setBlurGlassRadius(double blurGlassRadius) {
        if (blurGlassRadius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassRadius");
        this.blurGlassRadius = blurGlassRadius;
        return this;
    }

    /**
     * Sets the parameters for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @param distance  The distance to set.
     * @param radius    The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if any of the parameters is invalid.
     */
    public Material setBlurGlass(int numOfRays, double distance, double radius) {
        if (numOfRays < 1 || distance <= 0 || radius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlass");

        this.numOfRays = numOfRays;
        this.blurGlassDistance = distance;
        this.blurGlassRadius = radius;

        return this;
    }

}
