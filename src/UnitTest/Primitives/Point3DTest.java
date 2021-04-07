package UnitTest.Primitives;

import org.testng.annotations.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link primitives.Point3D} class.
 */
public class Point3DTest {

    /**
     * Test method for {@link primitives.Point3D#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,1,1);
        Vector v=new Vector(3,2,4);

        assertEquals("Failed adding", new Point3D(4,3,5), p0.add(v));
    }

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);

        assertEquals("Subtract() wrong result of sub",new Vector(5,1,5),p0.subtract(p1));

        // =============== Boundary Values Tests ==================
        //sub point by it self
        try {
            p0.subtract(p0);
            fail("wrong result of sub point by it self");
        } catch (Exception ignored) {}
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)}.
     */
    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);

        assertEquals("distanceSquared() wrong result",p0.distanceSquared(p1),51,0.00001);

        // =============== Boundary Values Tests ==================
        //distance of point from it self
        assertEquals( "the distance of point from herself is not 0",0,p0.distanceSquared(p0),0.00001);

    }

    /**
     * Test method for {@link primitives.Point3D#distance(Point3D)}.
     */
    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p0=new Point3D(1,3,-2);
        Point3D p1=new Point3D(-4,2,-7);

        assertEquals("distance() wrong result",p0.distance(p1),7.14142,0.0001);
    }
}