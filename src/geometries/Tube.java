package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class represent a three-dimensional tube with Ray and radius
 * @author TehilaNaki & MeravIzhaki
 */

public class Tube extends Geometry{

    protected final Ray axisRay;
    protected final double radius;

    /**
     * Creates a new tube by a given axis ray and radius.
     * @param axis The tube's axis ray.
     * @param r The tube's radius.
     * @exception IllegalArgumentException When the radius is equals or less than 0.
     */
    public Tube(Ray axis, double r) {
        if(r <= 0)
        {
            throw new IllegalArgumentException("The radius should be grater than 0");
        }
        axisRay=axis;
        radius=r;
    }



    /**
     * Returns the tube's radius.
     * @return the radius.
     */
    public double getRadius()
    {
        return radius;
    }

    /**
     * Returns the tube's axis ray.
     * @return A shallow copy of the axis ray.
     */
    public Ray getAxisRay()
    {
        return axisRay;
    }


    @Override
    public Vector getNormal(Point3D p){
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point3D p0 =axisRay.getPoint();

        double t= v.dotProduct(p.subtract(p0));

        //if t=0, then t*v is the zero vector and o=p0.
        Point3D o=p0;

        if(!isZero(t))
        {
            o=p0.add(v.scale(t));
        }

        return p.subtract(o).normalize();
    }

    @Override
    public String toString(){
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }



    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
}
