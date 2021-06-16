package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
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
        Vector v = ray.getDir();
        Vector v0 = axisRay.getDir();

        // Calculating temp1 = v - v0 * (v,v0)
        Vector temp1 = v;
        double vv0 = v.dotProduct(v0);
        if (!isZero(vv0)) {
            Vector v0vv0 = v0.scale(vv0);
            if (v.equals(v0vv0)) {
                return null;
            }
            temp1 = v.subtract(v0vv0);
        }

        // Calculating temp2 = dp - v0 * (dp,v0) where dp = p0 - p
        double temp1DotTemp2 = 0;
        double squaredTemp2 = 0;
        if (!ray.getPoint().equals(axisRay.getPoint())) {
            Vector dp = ray.getPoint().subtract(axisRay.getPoint());
            Vector temp2 = dp;
            double dpv0 = dp.dotProduct(v0);
            if (isZero(dpv0)) {
                temp1DotTemp2 = temp1.dotProduct(temp2);
                squaredTemp2 = temp2.lengthSquared();
            }
            else {
                Vector v0dpv0 = v0.scale(dpv0);
                if (!dp.equals(v0dpv0)) {
                    temp2 = dp.subtract(v0dpv0);
                    temp1DotTemp2 = temp1.dotProduct(temp2);
                    squaredTemp2 = temp2.lengthSquared();
                }
            }
        }

        // Getting the quadratic equation: at^2 +bt + c = 0
        double a = temp1.lengthSquared();
        double b = 2 * temp1DotTemp2;
        double c = alignZero(squaredTemp2 - radius * radius);

        double squaredDelta = alignZero(b * b - 4 * a * c);
        if (squaredDelta <= 0) {
            return null;
        }

        double delta = Math.sqrt(squaredDelta);
        double t1 = alignZero((-b + delta) / (2 * a));
        double t2 = alignZero((-b - delta) / (2 * a));

        if (t1 > 0 && t2 > 0 ) {
            return List.of(
                    new GeoPoint(this, ray.getPointBy(t1)),
                    new GeoPoint(this, ray.getPointBy(t2))
            );
        }
        if (t1 > 0 ) {
            return List.of(new GeoPoint(this, ray.getPointBy(t1)));
        }
        if (t2 > 0 ) {
            return List.of(new GeoPoint(this, ray.getPointBy(t2)));
        }

        return null;
    }
}
