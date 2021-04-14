package UnitTest.geometries;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Unit tests for {@link geometries.Plane} class.
 */
public class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
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
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
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
}