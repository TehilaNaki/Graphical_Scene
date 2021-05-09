package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import java.util.List;


/**
 * Class Ray is the basic class representing a ray
 * point3D is the start point, Vector is the direction.
 *
 * @author TehilaNaki and MeravIzhaki
 */
public class Ray {
    private final Point3D p0;
    private final Vector dir;

    /**
     * Creates a new ray by point and vector.
     *
     * @param p A point of the ray.
     * @param v A vector of the ray.
     */
    public Ray(Point3D p, Vector v) {
        p0 = p;
        dir = v.normalized();
    }

    /**
     * Returns a point of the ray.
     *
     * @return A shallow copy of the point.
     */
    public Point3D getPoint() {
        return p0;
    }

    /**
     * Returns a vector of the ray.
     *
     * @return A shallow copy of the vector.
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Gets a point on the ray by calculating p0 + t*v.
     *
     * @param t A scalar to calculate the point.
     * @return A point on the ray.
     */
    public Point3D getPointBy(double t) {
        return p0.add(dir.scale(t));
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", direction=" + dir +
                '}';
    }



    /**
     * The function find the closest points to P0 of the ray
     * @param points
     * @return Point3D the closes point
     */

    public Point3D findClosestPoint(List<Point3D> points) {

        double minDistance = Double.MAX_VALUE;
        double d;
        Point3D closePoint = null;

        if(points==null){
            return null;
        }

        for (Point3D p : points) {
            
             d = p.distance(p0);
             //check if the distance of p is smaller then minDistance
            if (d < minDistance) {
                minDistance = d;
                closePoint = p;
            }
        }
        return closePoint;
    }

    /**
     *
     * @param intersections
     * @return
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
        double minDistance = Double.MAX_VALUE;
        double d;
        GeoPoint closePoint = null;

        if(intersections==null){
            return null;
        }

        for (GeoPoint geoP : intersections) {

            d = geoP.point.distance(p0);
            //check if the distance of p is smaller then minDistance
            if (d < minDistance) {
                minDistance = d;
                closePoint = geoP;
            }
        }
        return closePoint;
    }
}


