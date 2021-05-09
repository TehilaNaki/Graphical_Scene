package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface for geometrics object
 * @author TehilaNaki & MeravIzhaki
 */
public abstract class Geometry implements Intersectable{

    /**
     * The emission light at first is black
     */
    protected Color emission =Color.BLACK;

    /**
    * @param point on geometrics object
    * @return normal vector on this point
    */
  public abstract Vector getNormal(Point3D point);

    /**
     * Returns the emission
     * @return return A shallow copy of the emission
     */
    public Color getEmission() {
        return emission;
    }


    /**
     * @param emission, set the emission
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
  }

}
