package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class represent a three-dimensional cylinder- inherited from a tube and a height added to it
 * @author TehilaNaki & MeravIzhaki
 */
public class Cylinder extends Tube {

    protected final double height;
    protected final Plane bottomCap, topCap;

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
        Point3D p0 = axisRay.getPoint();
        Point3D p1 = axisRay.getPointBy(height);
        bottomCap = new Plane(p0, axisRay.getDir().scale(-1) /* Sets the normal directed outside of the cylinder */);
        topCap = new Plane(p1, axisRay.getDir());
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
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = axisRay.getPoint();
        Point3D p1 = axisRay.getPointBy(height);
        List<GeoPoint> result = null;

        // Find the tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersections(ray);
        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(q0);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(q1);
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(gp);
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < radius * radius) {
                if (result == null) {
                    return List.of(gp);
                }

                result.add(gp);
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point3D p) {
        Vector v0 = axisRay.getDir();
        Point3D p0 = axisRay.getPoint();
        Point3D p1 = axisRay.getPointBy(height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }

 

}
