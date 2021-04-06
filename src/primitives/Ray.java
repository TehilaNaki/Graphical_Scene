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
                ", diruction=" + dir +
                '}';
    }

    public Ray(Point3D p,Vector v)
    {
        p0=p;
        dir=v.normalized();
    }

    public Point3D getPoint() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }



}


