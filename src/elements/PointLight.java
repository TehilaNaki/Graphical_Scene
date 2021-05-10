package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private Point3D position;
    private double kC = 1, kL = 0, kQ = 0;


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

    /**
     * create the intensity and position of the light
     *
     * @param intensity of the light
     * @param position  of the light
     * @param kC factor of the light
     * @param kL factor of the light
     * @param kQ factor of the light
     */
    protected PointLight(Color intensity, Point3D position,double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC=kC;
        this.kL=kL;
        this.kQ=kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = position.distance(p);
        return super.getIntensity().scale(1 / (kC + kL * distance + kQ * distance * distance));
    }

    @Override
    public Vector getL(Point3D p) {
        return position.subtract(p);
    }


}
