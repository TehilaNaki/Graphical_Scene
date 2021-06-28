package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * Class spot light
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class SpotLight extends PointLight{

    /**
     * direction vector of the spot light
     */
    private Vector direction;

    /**
     * specular component (default=1, no affection on the sharp of the original spot)
     */
    private double specularN=1;

    /**
     * create the intensity, direction and position of the light
     * @param intensity of the light
     * @param direction of the light
     * @param position of the light
     */
    public SpotLight(Color intensity, Vector direction, Point3D position) {
        super(intensity,position);
        this.direction=direction.normalized();
    }


    /**
     * @param specularN specular component
     * @return this spot light
     */
    public SpotLight setSpecularN(double specularN) {
        this.specularN = specularN;
        return this;
    }

    /**
     * @return specularN
     */
    public double getSpecularN() {
        return specularN;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double factor=Math.max(0,direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(factor);
    }
}
