package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface of all the light
 *
 * @author TehilaNaki & MeravIzhaki
 */
public interface LightSource {

    /**
     * @param p point on the geometry
     * @return intensity color on that point
     */
    public Color getIntensity(Point3D p);

    /**
     * @param p point on the geometry
     * @return the vector between p and position point
     */
    public Vector getL(Point3D p);


    /**
     * @param point on the geometry
     * @return the distance between two points.
     */
    public double getDistance(Point3D point);



}
