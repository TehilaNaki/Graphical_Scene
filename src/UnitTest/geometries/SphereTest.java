package UnitTest.geometries;

import geometries.Plane;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Sphere} class.
 */
public class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#Sphere(primitives.Point3D, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(new Point3D(1, 2, 3), 5);
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct sphere");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when the radius is 0.
        try {
            new Sphere(new Point3D(1, 2, 3), 0);
            fail("Constructed a sphere while the radius is 0");
        } catch (IllegalArgumentException ignored) {
        }
        // TC03: Test when the radius is negative,-1.
        try {
            new Sphere(new Point3D(1, 2, 3), -1);
            fail("Constructed a sphere while the radius is negative");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(new Point3D(1, 0, 1), 2);

        assertEquals(new Vector(0, 0, 1), s.getNormal(new Point3D(1, 0, 2)), "Bad normal to sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     */
    /**
     *  p1,p2,p3 intersect point with the sphere
     *  p0 starting point of the ray
     */
    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(new Point3D(-2, 0, 0), Math.sqrt(21));
        List<Point3D> result = null;
        Ray r=null;
        Point3D p1=null;
        Point3D p2=null;
        Point3D p3=null;
        Point3D p0=null;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        p1= new Point3D(5,-6,0);
        assertNull(sphere.findIntersections(new Ray(p1, new Vector(3, 6, 0))), "Ray's line out of sphere");
        assertTrue( p1.distance(sphere.getCenter()) > sphere.getRadius(),"Ray starts outside the sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
         p1 = new Point3D(-6.45d, 1.09d, 0);
         p2 = new Point3D(1.07d, 3.41d, 0);

        result = sphere.findIntersections(new Ray(new Point3D(3, 4, 0), new Vector(-13, -4, 0)));

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        assertTrue( p1.distance(sphere.getCenter()) < sphere.getRadius() && p1.distance(sphere.getCenter())>0,"Ray starts outside the sphere or on the center");

        //check the opposite vector doesn't across the sphere

        assertNull(sphere.findIntersections(new Ray(new Point3D(3, 4, 0), new Vector(13, 4, 0))),"Opposite ray crosses the sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p3 = new Point3D(0.3d, 2.88d, 2.72d);

        result = sphere.findIntersections(new Ray(new Point3D(-0.9d, 1.45d, 4.2d), new Vector(3.4d, 4.05d, -4.2d)));

        assertTrue( p3.distance(sphere.getCenter()) < sphere.getRadius(),"Ray starts outside the sphere");
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts inside and crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-2, -6, 0), new Vector(2, -3, 0))), "Ray's line starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1= new Point3D(-0.88d,-4.44d,0);
        p2= new Point3D(-3.09d,4.45d,0);

        r= new Ray(new Point3D(0,-8,0),new Vector(-3.35d,13.51d,0));

        result=sphere.findIntersections(r);

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        assertTrue(r.pointOnRay(sphere.getCenter()),"Ray doesn't cross the center point");

        // TC14: Ray starts at sphere and goes inside (1 points)
        p1 = new Point3D(-0.91d,-4.45d,0);
        p0= new Point3D(-3.18d,4.43d,0);
        r=new Ray(p0, new Vector(3.18d,-12.43d,0));
        result = sphere.findIntersections(r);

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result,"Ray starts inside and crosses sphere");
        assertTrue(r.pointOnRay(sphere.getCenter()),"Ray doesn't cross the center point");
        assertEquals(p1.distance(p0),2* sphere.getRadius(),"Ray doesn't cross the center point");

        // TC15: Ray starts inside (1 points)
        p1= new Point3D(-6.58d,0,0);
        p0=new Point3D(-4,0,0);
        r=new Ray(p0 ,new Vector(0,1,0));

        result= sphere.findIntersections(r);

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result,"Ray starts inside and crosses sphere");
        assertTrue( p0.distance(sphere.getCenter()) < sphere.getRadius() && p1.distance(sphere.getCenter())>0,"Ray starts outside the sphere or on the center");

        // check if the opposite ray crosses the center
        r=new Ray(new Point3D(-4,0,0), new Vector(0,-1,0));
        assertTrue(r.pointOnRay(sphere.getCenter()),"Ray doesn't cross the center point");

        // TC16: Ray starts at the center (1 points)


        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)





        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)



        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line


    }
}