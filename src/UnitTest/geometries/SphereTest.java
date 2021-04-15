package UnitTest.geometries;


import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
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
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */

    @Test
    public void findIntersections() {
        Sphere sphere = new Sphere(new Point3D(-2, 0, 0),Math.sqrt(21));
        List<Point3D> result;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(5,-6,0), new Vector(3, 6, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(-4.89,0,3.55);
        Point3D p2 = new Point3D(-3.1,0,4.44);
        result = sphere.findIntersections(new Ray(new Point3D(-12,0,0), new Vector(12,0,6)));

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), List.of(result.get(0).cutTwoNumbers(),result.get(1).cutTwoNumbers()), "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point3D(-0.9d, 1.45d, 4.2d), new Vector(3.4d, 4.05d, -4.2d)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(0.3d, 2.88d, 2.70d), result.get(0).cutTwoNumbers(), "Ray starts inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-2, -6, 0), new Vector(2, -3, 0))), "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line goes through the center
        // TC11: Ray starts before the sphere (2 points)
        result=sphere.findIntersections(new Ray(new Point3D(0,-8,0),new Vector(-3.35d,13.51d,0)));

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(new Point3D(-3.08d,4.45d,0), new Point3D(-0.88d,-4.44d,0)), List.of(result.get(0).cutTwoNumbers(),result.get(1).cutTwoNumbers()), "Ray starts before the sphere. The ray's line goes through the center");

        // TC12: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(-3.17d,4.42d,0), new Vector(3.18d,-12.43d,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-0.90d,-4.44d,0), result.get(0).cutTwoNumbers(),"Ray starts at sphere and goes inside. The ray's line goes through the center");

        // TC13: Ray starts inside (1 points)
        result= sphere.findIntersections(new Ray(new Point3D(-4,0,0) ,new Vector(0,1,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-4d,4.12,0),result.get(0).cutTwoNumbers(),"Ray starts inside sphere and it's line goes through the center");

        // TC14: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-8,0,0) ,new Vector(0,1,0))),"Ray starts after sphere and goes outside. The ray's line goes through the center");

        // TC15: Ray starts at sphere and goes outside (0 points)
        Sphere s = new Sphere(new Point3D(1,0,0),1);
        assertNull(s.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))),"Ray starts at sphere and goes outside. The ray's line goes through the center");

        // TC16: Ray starts at the center (1 points)
        result=sphere.findIntersections(new Ray(new Point3D(-2,0,0),new Vector(-5,-5,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-5.24d,-3.24d,0), result.get(0).cutTwoNumbers(),"Ray starts at the sphere's center");

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC17: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntersections(new Ray(new Point3D(2.34d,0.88d,1.17d),new Vector(-7.34d,-9.88d,-1.17d)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-1.68d,-4.54d,0.52d), result.get(0).cutTwoNumbers(),"Ray starts at sphere and goes inside");

        // TC18: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-6.1d,0,2.05d),new Vector(-3.9d,0,-2.05))),"Ray starts at sphere and goes outside");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(-3.15d,-6.31d,0.26d),new Vector(0.52d,-8.31d,-7.35d))),"Ray starts before the sphere's tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(0,0,4.58d),new Vector(2,0,0))),"Ray starts at the sphere's tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(-3.46d,-1.35d,4.65d),new Vector(-0.21d,3.35d,2.96d))),"Ray starts after the sphere's tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point3D(-2,0,6.28),new Vector(2,0,-0.01))),"Ray's line is outside the sphere. The ray is orthogonal to the ray that goes through the center line");
    }
}