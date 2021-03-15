package primitives;
/**
 * Class Point3D is the basic class representing a point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author TehilaNaki and MeravYzhaki
 */

public class Point3D {

     Coordinate x;
     Coordinate y;
     Coordinate z;
    public static Point3D ZERO=new Point3D(0,0,0);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D)obj;
        return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
    }

    @Override
    public String toString() {
        return "(" +
                 x +
                "," + y +
                "," + z +
                ')';
    }
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y, double z) {
        this.x=new Coordinate(x);
        this.y = new Coordinate(y);
        this.z =new Coordinate(z);
    }

    /**
      *the func check if the point is zero
     */
    public boolean IsZero() {
        if(this.equals(Point3D.ZERO))
            throw new IllegalArgumentException("the vector is the zero vector!!!");
        else return false;
    }

    public Point3D add(Vector v) {
        return new Point3D(x.coord+v.getHead().x.coord,y.coord+v.getHead().y.coord,z.coord+v.getHead().z.coord);
    }

   public Vector subtract(Point3D p0) {
       return new Vector(x.coord-p0.x.coord,y.coord-p0.y.coord,z.coord-p0.z.coord);
   }
   public double distanceSquared(Point3D p)
   {
       double sum=0;
       double temp;
       temp=p.x.coord- x.coord;
       temp*=temp;
       sum+=temp;
       temp=p.y.coord- y.coord;
       temp*=temp;
       sum+=temp;
       temp=p.z.coord- z.coord;
       temp*=temp;
       sum+=temp;
       return sum;
   }
   public double distance(Point3D p)
   {
       return (Math.sqrt(distanceSquared(p)));
   }
}
