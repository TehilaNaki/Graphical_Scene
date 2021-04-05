package UnitTest;

import org.testng.annotations.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class Point3DTest {

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,1,1);
        Vector v=new Vector(3,2,4);
        Point3D p1=p0.add(v);
        Point3D p2=new Point3D(4,3,5);
        assertEquals("add() wrong result of adding",p1,p2);
    }

    @org.junit.Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);
        Vector v0=new Vector(5,1,5);
        Vector v1=p0.subtract(p1);
        assertEquals("Subtract() wrong result of sub",v0,v1);

    }


    @org.junit.Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);
        double d=p0.distanceSquared(p1);
        assertEquals("distanceSquared() wrong result",d,51,0.0001);
    }

    @org.junit.Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);
        double d=p0.distance(p1);
        assertEquals("distance() wrong result",d,7.14142,0.0001);
    }
}