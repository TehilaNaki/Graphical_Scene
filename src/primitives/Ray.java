package primitives;

import java.util.Objects;
/**
 * Class Ray is the basic class representing a ray
 * point3D is the start point, Vector is the direction.
 * @author TehilaNaki and MeravIzhaki
 */
public class Ray
{
    private final Point3D p0;
    private final Vector dir;

    /**
     * Creates a new ray by point and vector.
     * @param p A point of the ray.
     * @param v A vector of the ray.
     */
    public Ray(Point3D p,Vector v)
    {
        p0=p;
        dir=v.normalized();
    }

    /**
     * Returns a point of the ray.
     * @return A shallow copy of the point.
     */
    public Point3D getPoint() {
        return p0;
    }
    /**
     * Returns a vector of the ray.
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

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", direction=" + dir +
                '}';
    }




}


