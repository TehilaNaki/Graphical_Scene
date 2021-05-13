package primitives;

public class Material {

    public double kD=0,kS=0;
    public int nShininess=0;

    /**
     *
     * @param kD factor of the diffuse affect of the material
     * @return this Material
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     *
     * @param kS factor of the specular affect of the material
     * @return this Material
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     *
     * @param nShininess factor of the reflection of the material
     * @return this Material
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
