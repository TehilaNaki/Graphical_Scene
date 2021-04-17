package UnitTest.geometries;

import geometries.Intersectable;
import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Unit tests for {@link Plane} class.
 */
public class PlaneTest {

    /**
     * Test method for {@link Plane#Plane(Point3D, Point3D,Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Plane
                    (
                            new Point3D(1, 0, 0),
                            new Point3D(0, 1, 0),
                            new Point3D(0, 0, 1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct plane");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        try {
            new Plane
                    (
                            new Point3D(1, 0, 0),
                            new Point3D(1, 0, 0),
                            new Point3D(0, 0, 1)
                    );
            fail("Constructed a plane while a point equal to b point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03: Test when a point equal to c point.
        try {
            new Plane
                    (
                            new Point3D(1, 0, 0),
                            new Point3D(0, 0, 1),
                            new Point3D(1, 0, 0)
                    );
            fail("Constructed a plane while a point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC04: Test when b point equal to c point.
        try {
            new Plane
                    (
                            new Point3D(1, 0, 0),
                            new Point3D(0, 0, 1),
                            new Point3D(0, 0, 1)
                    );
            fail("Constructed a plane while b point equal to c point");
        } catch (IllegalArgumentException ignored) {
        }
        //TC05: Test when all 3 points are on the same line.
        try {
            new Plane
                    (
                            new Point3D(1, 2, 3),
                            new Point3D(2, 3, 4),
                            new Point3D(3, 4, 5)
                    );
            fail("Constructed a plane while all 3 point on the same plane");
        } catch (IllegalArgumentException ignored) {}
    }
    /**
     * Test method for {@link Plane#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Plane plane = new Plane
                (
                        new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0),
                        new Point3D(0, 0, 1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n), plane.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    /**
     *Test method for {@link Plane#findIntersections(Ray)}.
     */
    @Test
    public void findIntersections() {
        Plane plane = new Plane
                (
                        new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0),
                        new Point3D(0, 0, 1)
                );
        List<Point3D> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 points)
        result=plane.findIntersections(new Ray(new Point3D(0,1,1),new Vector(0,0,-1)));
        assertEquals(result.size(),1,"Wrong number of points");
        assertEquals(new Point3D(0,1,0),result.get(0).cutTwoNumbers(),"Ray intersects the plane");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0,1,1),new Vector(0,0,1))),"Ray doesn't intersect the plane");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC11: Ray is included in the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0,0,1), new Vector(1,-1,0))),"Ray is included in the plane. Ray is parallel to the plane");

        //TC12: Ray isn't included in the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0,0,2), new Vector(1,-1,0))),"Ray isn't included in the plane. Ray is parallel to the plane");

        //**** Group: Ray is orthogonal to the plane
        //TC13: Ray starts before the plane (1 points)
        result=plane.findIntersections(new Ray(new Point3D(-1,-1,-1),new Vector(1,1,1)));
        double n = 0.33;    //(1/3)

        assertEquals(result.size(),1,"Wrong number of points");
        assertEquals(new Point3D(n,n,n), result.get(0).cutTwoNumbers()," Ray starts before the plane. Ray is orthogonal to the plane");

        //TC14: Ray starts inside the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0,0,1), new Vector(1,1,1))),"Ray starts inside the plane. Ray is orthogonal to the plane");

        //TC15: Ray starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(2,2,2),new Vector(1,1,1))),"Ray starts after the plane. Ray is orthogonal to the plane");

        //**** Group: Special case
        //TC16: Ray begins at the plane (p0 is in the plane, but not the ray)
        assertNull(plane.findIntersections(new Ray(new Point3D(1,0,0),new Vector(0,0,-1))),"Ray begins at the plane (p0 is in the plane, but not the ray)");

        //TC17: Ray begins in the plane's reference point
        assertNull(plane.findIntersections(new Ray(plane.getPoint(),new Vector(1,0,0))),"Ray begins in the plane's reference point");
    }
}