package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Sphere class represent a three-dimensional Sphere with a center point and radius
 * @author TehilaNaki & MeravIzhaki
 */
public class Sphere implements Geometry{

    protected final Point3D center;
    protected final double radius;

    /**
     * creates a new sphere by a given center point and radius
     * @param c The center point.
     * @param r The sphere's radius
     *@exception IllegalArgumentException When the radius smaller or equals 0..
     */
    public Sphere(Point3D c, double r)
    {
        if(r <= 0)
        {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }
        center=c;
        radius=r;
    }

    /**
     * Returns the sphere's center point.
     * @return A shallow copy of the center point.
     */
    public Point3D getCenter()
    {
        return center;
    }

    /**
     * Returns the sphere's radius.
     * @return return radius.
     */
    public double getRadius()
    {
        return radius;
    }

    @Override
    public Vector getNormal(Point3D p)
    {
             return p.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }


}
