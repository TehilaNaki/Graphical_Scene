package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Sphere class represent a three-dimensional Sphere with a center point and radius
 * @author TehilaNaki & MeravYzhaki
 */
public class Sphere implements Geometry{

    private Point3D center;
    private double radius;

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
}
