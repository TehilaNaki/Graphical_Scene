package geometries;

import primitives.Color;
import primitives.Material;
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

    private Material material=new Material();



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
     * @return the material of the Geometry
     */
    public Material getMaterial() {
        return material;
    }


    /**
     * @param material of the Geometry
     * @return this Geometry
     */

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }


    /**
     *
     * @param emission set the emission
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
  }

}
