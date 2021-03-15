package primitives;

import java.util.Objects;
/**
 * Class Ray is the basic class representing a ray
 * point3D is the start point, Vector is the direction.
 * @author TehilaNaki and MeravYzhaki
 */
public class Ray
{
    private Point3D p0;
    private Vector dir;

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

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }


}


