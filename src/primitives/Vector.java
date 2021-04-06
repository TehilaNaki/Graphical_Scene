package primitives;
/**
 * Class Vector is the basic class representing a vector
 * point3D is the head of the vector.
 * @author TehilaNaki and MeravIzhaki
 */

 public class Vector {

    private Point3D head;

     @Override
     public boolean equals(Object obj) {
         if (this == obj) return true;
         if (obj == null) return false;
         if (!(obj instanceof Vector)) return false;
         Vector other = (Vector)obj;
         return head.equals(other.head);
     }

     @Override
     public String toString() {
         return head.toString();
     }

     public Vector(Point3D h) {
         h.IsZero();
         this.head = h;
     }

     public Vector(Coordinate x,Coordinate y, Coordinate z) {
         Point3D newP=new Point3D(x,y,z);
         newP.IsZero();
         head=newP;
     }
     public Vector(double x, double y, double z) {
         Point3D newP=new Point3D(x,y,z);
         newP.IsZero();
         head=new Point3D(x,y,z);
     }
     public Point3D getHead() {
         return head;
     }
     public boolean IsZero(){ return head.IsZero();}
    /**
     *the func return a new vector that add 2 vectors
     */
    public Vector add(Vector v) {
       return new Vector(head.add(v));
    }
    /**
     *the func return a vector that sub 2 vectors
     */
    public Vector subtract(Vector v) {
      return head.subtract(v.head);
    }
    /**
     *the func return the  vector after multiply in the scalar c
     */
     public Vector scale(double c) {
        return new Vector(head.x.coord*c,head.y.coord*c,head.z.coord*c);
     }
    /**
     *the func return the dot product between 2 vectors
     */
     public double dotProduct(Vector v) {
        double sum=0;
        sum+=(v.head.x.coord*head.x.coord);
         sum+=(v.head.y.coord*head.y.coord);
         sum+=(v.head.z.coord*head.z.coord);
         return sum;
     }
    /**
     *the func return the cross product between 2 vectors
     */
     public Vector crossProduct(Vector v) {

         if(parallelVectors(v))
             throw new IllegalArgumentException("the vectors are parallel!!!");
         double x=head.y.coord*v.head.z.coord- head.z.coord*v.head.y.coord;
         double y=head.z.coord*v.head.x.coord- head.x.coord*v.head.z.coord;
         double z=head.x.coord*v.head.y.coord- head.y.coord*v.head.x.coord;
         return new Vector(x,y,z);
     }
    /**
     *the func checks if the 2 vectors are parallel
     */
     public boolean parallelVectors(Vector v)
     {
         double divideX=head.x.coord/v.head.x.coord;
         double divideY=head.y.coord/v.head.y.coord;
         double divideZ=head.z.coord/v.head.z.coord;
         return divideX == divideY && divideY == divideZ;
     }
    /**
     *the func return the length squared of the vector
     */
     public double lengthSquared() {
         return head.distanceSquared(Point3D.ZERO);
     }
    /**
     *the func return the length of the vector
     */
     public double length(){
         return head.distance(Point3D.ZERO);
     }
    /**
     *the func return the normalize vector
     */
     public Vector normalize()
     {
         double length=length();
         head=scale(1/length).head;
         return this;
     }

    /**
     *the func return a new vector normalized
     */
    public Vector normalized()
    {
        Vector newV= new Vector(head.x, head.y, head.z);
        return newV.normalize();
    }
}
