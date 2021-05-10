package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class represent a three-dimensional cylinder- inherited from a tube and a height added to it
 * @author TehilaNaki & MeravIzhaki
 */
public class Cylinder extends Tube {

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
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point3D p){
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point3D p0 =axisRay.getPoint();

        //if p=p0, then (p-p0) is zero vector
        //returns the vector of the base as a normal
        if(p.equals(p0)){
            return v.scale(-1);
        }

        double t= v.dotProduct(p.subtract(p0));
        //check if the point on the bottom
        if(isZero(t)){
            return v.scale(-1);
        }
        //check if the point on the top
        if(isZero(t-height)){
            return v;
        }

        Point3D o=p0.add(v.scale(t));
        return p.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return super.findGeoIntersections(ray);
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
