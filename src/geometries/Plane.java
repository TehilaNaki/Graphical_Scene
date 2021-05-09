package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents plane with vector and point in 3D Cartesian coordinate
 * @author TehilaNaki & MeravIzhaki
 */
public class Plane extends Geometry{

    protected final Point3D q0;
    protected final Vector normal;



    /**
     * Creates a new plane by a point on the plane and the plane's normal.
     * @param p A point on the plane.
     * @param _normal The plane's normal.
     */
    public Plane(Point3D p,Vector _normal) {
        q0=p;
        normal=_normal;
    }


    /**
     * Creates a new plane by three different points on the plane.
     * @param p1 A point on the plane.
     * @param p2 A point on the plane.
     * @param p3 A point on the plane.
     *@exception IllegalArgumentException When at least two of the given points are equals.
     */

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        if(p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("All points should be different");
        }

        q0=p1;

        Vector v=p1.subtract(p2);
        Vector u=p2.subtract(p3);
        normal=v.crossProduct(u).normalize();
    }

    /**
     * Returns a point on the plane.
     * @return A shallow copy of the point.
     */
    public Point3D getPoint()
    {
        return q0;
    }

    /**
     * Returns the plane's normal.
     * @return A shallow copy of the plane's normal.
     */
    public Vector getNormal()
    {
        return normal;
    }

    @Override
    public Vector getNormal(Point3D p)
    {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //t=n*(q0-Po)/n*v
        Vector v= ray.getDir();
        Point3D p0=ray.getPoint();

        //Ray on the plane
        if(q0.equals(p0)){
            return null;
        }

        double nqp=normal.dotProduct(q0.subtract(p0));
        //Ray on the plane
        if(isZero(nqp)){
            return null;
        }

        double nv= normal.dotProduct(v);

        if(isZero(nv)){
            return null;
        }

        double t=nqp/nv;

        //Ray after the plane
        if(t<0){
            return null;
        }

        //Ray crosses the plane
        return List.of(ray.getPointBy(t));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        //t=n*(q0-Po)/n*v
        Vector v= ray.getDir();
        Point3D p0=ray.getPoint();

        //Ray on the plane
        if(q0.equals(p0)){
            return null;
        }

        double nqp=normal.dotProduct(q0.subtract(p0));
        //Ray on the plane
        if(isZero(nqp)){
            return null;
        }

        double nv= normal.dotProduct(v);

        if(isZero(nv)){
            return null;
        }

        double t=nqp/nv;

        //Ray after the plane
        if(t<0){
            return null;
        }

        Point3D P=ray.getPointBy(t);
        //Ray crosses the plane
        return List.of(new GeoPoint(this,P));
    }
}
