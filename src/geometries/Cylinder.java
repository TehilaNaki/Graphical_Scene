package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Cylinder class represent a three-dimensional cylinder- inherited from a tube and a height added to it
 * @author TehilaNaki & MeravIzhaki
 */
public class Cylinder extends Tube{

    protected final double height;

    /**
     * Creates a new cylinder by a given axis ray, radius and height.
     * @param axisRay The cylinder's axis ray.
     * @param radius The cylinder's radius.
     * @param h The cylinder's height.
     * @exception IllegalArgumentException When the radius or the height are less than 0 or equals 0.
     */
    public Cylinder(Ray axisRay, double radius, double h) {
        super(axisRay, radius);

        if (h <= 0)
        {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        height = h;
    }

    /**
     * Returns the cylinder's height
     * @return the cylinder's height
     */
    public double getHeight()
    {
        return height;
    }

    @Override
    public Vector getNormal(Point3D p) {
       return null;
    }


    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

}
