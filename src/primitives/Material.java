package primitives;

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
     *
     */
    public Double3 kt=Double3.ZERO;
    /**
     *
     */
    public Double3 kr=Double3.ZERO;
    /**
     * the shininess of the material
     */
    public int nShininess=0;

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

}
