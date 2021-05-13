package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

public class SpotLight extends PointLight{

    private Vector direction;

    /**
     * create the intensity, direction and position of the light
     * @param intensity of the light
     * @param direction of the light
     * @param position of the light
     */
    public SpotLight(Color intensity, Vector direction, Point3D position) {
        super(intensity,position);
        this.direction=direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double factor=Math.max(0,direction.dotProduct(getL(p)));
        if(!isZero(factor)){
            return super.getIntensity(p).scale(factor);
        }
        throw new IllegalArgumentException("The factor is zero");
    }

    @Override
    public Vector getL(Point3D p) {
      return direction;
    }
}
