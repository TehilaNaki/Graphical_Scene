package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class represent a three-dimensional Sphere with a center point and radius
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Sphere extends Geometry {

    /**
     * Center point on the sphere
     */
    protected final Point3D center;
    /**
     * Radius of the sphere
     */
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
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = ray.getPoint();
        Vector v = ray.getDir();

        if (p0.equals(center)) {
            return List.of(new GeoPoint(this,ray.getPointBy(radius)));
        }

        Vector u = center.subtract(p0);
        double tm =v.dotProduct(u);
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPointBy(t1)),new GeoPoint(this,ray.getPointBy(t2)));
        }

        if (t1 > 0) {
            return List.of(new GeoPoint(this,ray.getPointBy(t1)));
        }

        if (t2 > 0) {
            return List.of(new GeoPoint(this,ray.getPointBy(t2)));
        }

        return null;
    }

}
