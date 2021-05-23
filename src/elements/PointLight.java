package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *Class of point light (like lantern)
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class PointLight extends Light implements LightSource {

    /**
     * point of the position of the light
     */
    private Point3D position;

    /**
     * the factors of the light
     */
    private double kC = 1, kL = 0, kQ = 0;


    /**
     * create the intensity and position of the light
     *
     * @param intensity of the light
     * @param position  of the light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
    }


    /**
     * @param kC factor of the light
     * @return this point light.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * @param kL factor of the light
     * @return this point light.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * @param kQ factor of the light
     * @return this point light.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }



    @Override
    public Color getIntensity(Point3D p) {
        double distance = position.distance(p);
        return super.getIntensity().scale(1d / (kC + kL * distance + kQ * distance * distance));
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();
    }


    @Override
    public double getDistance(Point3D point) {
        return position.distance(point);
    }

}
