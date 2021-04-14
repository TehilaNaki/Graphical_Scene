package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class represent a three-dimensional Sphere with a center point and radius
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Sphere implements Geometry {

    protected final Point3D center;
    protected final double radius;

    /**
     * creates a new sphere by a given center point and radius
     *
     * @param c The center point.
     * @param r The sphere's radius
     * @throws IllegalArgumentException When the radius smaller or equals 0..
     */
    public Sphere(Point3D c, double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("The radius should be greater then 0");
        }
        center = c;
        radius = r;
    }

    /**
     * Returns the sphere's center point.
     *
     * @return A shallow copy of the center point.
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * Returns the sphere's radius.
     *
     * @return return radius.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //|P-O|^2-R^2=0
        //u=O-P0
        //tm=v*u
        // d=sqrt(|u|^2-tm^2)
        //th=sqrt(r^2-d^2)
        //t1=tm-th, t2=tm+th
        //P1=P0+t1*v  P2=P0+t2*v

        Vector u = center.subtract(ray.getPoint());
        double tm = u.dotProduct(ray.getDir());
        double d = Math.sqrt(u.length() * u.length() - tm * tm);

        if (d >= radius) {
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPointBy(t1), ray.getPointBy(t2));
        }

        if (t1 > 0) {
            return List.of(ray.getPointBy(t1));
        }

        if (t2 > 0) {
            return List.of(ray.getPointBy(t2));
        }

        return null;
    }
}
