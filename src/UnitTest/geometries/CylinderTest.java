package UnitTest.geometries;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for {@link geometries.Cylinder} class.
 */
public class CylinderTest
{
    /**
     * Test method for {@link geometries.Cylinder#Cylinder(primitives.Ray, double, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 2, 3);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 0,5);
            fail("Constructed a cylinder while a radius can not be 0");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the radius negative, -1
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), -1,5);
            fail("Constructed a cylinder while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {}
        //TC04: Test when the height 0
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 5,0);
            fail("Constructed a cylinder while a height can not be 0");
        } catch (IllegalArgumentException ignored) { }
        //TC03:Test when the height negative, -1
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 5,-1);
            fail("Constructed a cylinder while a height can not be negative");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal(){
        Cylinder c = new Cylinder(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)), 1d, 3d);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Test with point on the top of the cylinder
        assertEquals(new Vector(0, 0, 1), c.getNormal(new Point3D(1, 1, 3)), "Bad normal to the top of the cylinder");

        //TC02: Test with point on the bottom of the cylinder
        assertEquals(new Vector(0, 0, -1), c.getNormal(new Point3D(1, 1, 0)), "Bad normal to the bottom of the cylinder");

        //TC03: Test with point on the side of the cylinder
        assertEquals(new Vector(0, -1, 0), c.getNormal(new Point3D(1, 0, 1)), "Bad normal to the side of the cylinder");

        // =============== Boundary Values Tests ==================
        //TC04: Test with point on the top edge of the cylinder
        assertEquals(new Vector(0, 0, 1), c.getNormal(new Point3D(1, 0, 3)), "Bad normal to the top-edge of the cylinder");

        //TC05: Test with point on the bottom edge of the cylinder
        assertEquals(new Vector(0, 0, -1), c.getNormal(new Point3D(0, 1, 0)), "Bad normal to the bottom-edge of the cylinder");
    }
}