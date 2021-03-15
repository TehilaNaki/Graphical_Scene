package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Plane class represents plane with vector and point in 3D Cartesian coordinate
 * system
 *
 * @author TehilaNaki & MeravYzhaki
 */
public class Plane implements Geometry{

    private Point3D q0;
    private Vector normal;

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3)
    {
        q0=p1;
        //Vector v=p1.subtract(p2);
       // Vector u=p2.subtract(p3);
        //normal=v.crossProduct(u);
        normal=null;
    }

    public Plane(Point3D p,Vector n)
    {
      normal=n;
      q0=p;
    }
    @Override
    public Vector getNormal(Point3D p) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }

    public Point3D getQ0() {
        return q0;
    }
}
