package primitives;
/**
 * Class Vector is the basic class representing a vector
 * point3D is the head of the vector.
 * @author TehilaNaki and MeravIzhaki
 */

 public class Vector {

    private Point3D head;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;



    /**
     * Creates a new point3d by a given 3 coordinate and put it on the head of the vector.
     * @param x Coordinate.
     * @param y Coordinate.
     * @param z Coordinate.
     */
     public Vector(Coordinate x,Coordinate y, Coordinate z) {
         Point3D newP=new Point3D(x,y,z);
         newP.IsZero();
         head=newP;
     }

    /**
     * Creates a new point3d by a given 3 double and put it on the head of the vector.
     * @param x Coordinate.
     * @param y Coordinate.
     * @param z Coordinate.
     */
     public Vector(double x, double y, double z) {
         Point3D newP=new Point3D(x,y,z);
         newP.IsZero();
         head=new Point3D(x,y,z);
     }
    /**
     * Returns the vector's x coordinate
     */
    public double getX() {
        return head.getX();
    }

    /**
     * Returns the vector's y coordinate
     */
    public double getY() {
        return head.getY();
    }

    /**
     * Returns the vector's z coordinate
     */
    public double getZ() {
        return head.getZ();
    }
    /**
     * Returns the vector's head.
     * @return A shallow copy of the point3d head.
     */
     public Point3D getHead() { return head; }

    /**
     *return true if zero and false if not.
     */
     public boolean IsZero(){ return head.IsZero();}

    /**
     *return a new vector that add 2 vectors
     */
    public Vector add(Vector v) {
       return new Vector(head.add(v));
    }

    /**
     *return a vector that sub 2 vectors
     */
    public Vector subtract(Vector v) {
      return head.subtract(v.head);
    }

    /**
     *return the  vector after multiply in the scalar c
     */
     public Vector scale(double c) {
        return new Vector(head.x.coord*c,head.y.coord*c,head.z.coord*c);
     }

    /**
     *return the dot product between 2 vectors
     */
     public double dotProduct(Vector v) {
        double sum=0;
        sum+=(v.head.x.coord*head.x.coord);
         sum+=(v.head.y.coord*head.y.coord);
         sum+=(v.head.z.coord*head.z.coord);
         return sum;
     }

    /**
     * Vector multiplier
     * @param v A vector.
     * @return the cross product between 2 vectors
     * @exception IllegalArgumentException When parallel vectors.
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
     *checks if the 2 vectors are parallel
     */
     public boolean parallelVectors(Vector v) {
         double divideX=head.x.coord/v.head.x.coord;
         double divideY=head.y.coord/v.head.y.coord;
         double divideZ=head.z.coord/v.head.z.coord;
         return divideX == divideY && divideY == divideZ;
     }

    /**
     *return the length squared of the vector
     */
     public double lengthSquared() {
         return head.distanceSquared(Point3D.ZERO);
     }

    /**
     *return the length of the vector
     */
     public double length(){
         return head.distance(Point3D.ZERO);
     }

    /**
     *return the normalize vector
     */
     public Vector normalize() {
         double length=length();
         head=scale(1/length).head;
         return this;
     }

    /**
     *return a new vector normalized
     */
    public Vector normalized() {
        Vector newV= new Vector(head.x, head.y, head.z);
        return newV.normalize();
    }

    /**
     * Rotates the vector around the x axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateX(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = head.getX();
        double y = head.getY() * Math.cos(radianAlpha) - head.getZ() * Math.sin(radianAlpha);
        double z = head.getY() * Math.sin(radianAlpha) + head.getZ() * Math.cos(radianAlpha);

        head = new Point3D(x, y, z);
        return this;
    }


    /**
     * Rotates the vector around the y axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateY(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = head.getX() * Math.cos(radianAlpha) + head.getZ() * Math.sin(radianAlpha);
        double y = head.getY();
        double z = -head.getX() * Math.sin(radianAlpha) + head.getZ() * Math.cos(radianAlpha);

        head = new Point3D(x, y, z);
        return this;
    }


    /**
     * Rotates the vector around the z axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateZ(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = head.getX() * Math.cos(radianAlpha) - head.getY() * Math.sin(radianAlpha);
        double y = head.getX() * Math.sin(radianAlpha) + head.getY() * Math.cos(radianAlpha);
        double z = head.getZ();

        head = new Point3D(x, y, z);
        return this;
    }

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
}
