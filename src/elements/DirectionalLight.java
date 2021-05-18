package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class of directional light (like sun)
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * vector of direction of light
     */
    private Vector direction;



    /**
     * create the intensity and direction of the light
     * @param intensity of the light
     * @param direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
       return direction;
    }
}
