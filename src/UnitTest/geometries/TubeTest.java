package UnitTest.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Tube} class.
 */
public class TubeTest
{
    /**
     * Test method for {@link geometries.Tube#Tube(primitives.Ray,double)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Tube(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 1);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct Tube");
        }

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        try {
            new Tube(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 0);
            fail("Constructed a Tube while a radius can not be 0");
        } catch (IllegalArgumentException ignored) { }
        //TC03:Test when the radius negative, -1
        try {
            new Tube(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), -1);
            fail("Constructed a Tube while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link Tube#getNormal(Point3D)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube= new Tube( new Ray(new Point3D(1,1,0),new Vector(0,0,1)),1d);

        assertEquals(new Vector(0,-1,0), tube.getNormal(new Point3D(1,0,2)), "Bad normal to tube");

    }
    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {

        Tube tube =new Tube(new Ray(new Point3D(0,1,0),new Vector(0,0,2)),3);
        Ray r=null;
        List<Point3D> result;
        // ============ Equivalence Partitions Tests ==============
        //TC01:Ray intersect the tube (2 points)
        result=tube.findIntersections(new Ray(new Point3D(5,0,3),new Vector(-10,0,0)));
        assertEquals(2 ,result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(new Point3D(2.83d,0,3), new Point3D(-2.83d,0,3)), List.of(result.get(0).cutTwoNumbers(),result.get(1).cutTwoNumbers()),"Wrong points");

        //TC02:Ray outside the tube (0 points)
        result=tube.findIntersections(new Ray(new Point3D(-5,0,3),new Vector(0,0,5)));
        assertNull(result,"Wrong points");

        //TC03:Ray inside the tube (0 points)
        result=tube.findIntersections(new Ray(new Point3D(-2,0,3),new Vector(0,0,5)));
        assertNull(result,"Wrong points");

        //TC04:Ray start on the axis ray(1 points)
        result=tube.findIntersections(new Ray(new Point3D(0,1,8),new Vector(5,-1,-5)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(2.94d, 0.41d, 5.06d), result.get(0).cutTwoNumbers(), "Wrong points");

        // =============== Boundary Values Tests ==================
        //TC05:Ray pass 0n tangent point(1 point)
        result=tube.findIntersections(new Ray(new Point3D(-10.85d,0.48d,0),new Vector(10.41d,-2.45d,4.14d)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(0.44d, -1.97d, 4.14d), result.get(0).cutTwoNumbers(), "Wrong tangent point");

        //TC06:Ray start on the tube(0 point)
        result=tube.findIntersections(new Ray(new Point3D(-0.44d,-1.97d,4.14d),new Vector(-3.66d,-12.92d,-4.14d)));
        assertNull(result,"Wrong point");

    }
}