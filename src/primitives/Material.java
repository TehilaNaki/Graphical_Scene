package primitives;

/**
 * The material kind of the object
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Material {


    /**
     * factors
     */
    public double
            kD=0, //Diffuse
            kS=0, //Specular
            kT=0, //Transparency
            kR=0, //Reflection
            kG=1; //Glossy
    /**
     * factor of the shininess
     */
    public int nShininess=0;

    /**
     * @param kD factor of the diffuse affect of the material
     * @return this Material
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * @param kS factor of the specular affect of the material
     * @return this Material
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * @param nShininess factor of the shine of the material
     * @return this Material
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
     * @param kT factor of the Transparency of the material
     * @return this Material
     */
    public Material setkT(double kT) {
        this.kT = kT;
        return this;
    }
    /**
     * @param kR factor of the reflection of the material
     * @return this Material
     */
    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }
    /**
     * Chaining method for setting the glossiness of the material.
     * @param kG the glossiness to set, value in range [0,1]
     * @return the current material
     */
    public Material setkG(double kG) {
        this.kG = Math.pow(kG, 0.5);
        return this;
    }

    public int getnShininess(){
        return nShininess;
    }
}
