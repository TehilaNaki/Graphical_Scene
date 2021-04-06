package UnitTest.geometries;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Cylinder} class.
 */
public class CylinderTest
{
    /**
     * Test method for {@link geometries.Cylinder#Cylinder(primitives.Ray, double, double)}.
     */
    @Test
    public void testConstructor()
    {
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
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the height negative, -1
        try {
            new Cylinder(new Ray(new Point3D(1, 2, 3), new Vector(1, 5, 4)), 5,-1);
            fail("Constructed a cylinder while a height can not be negative");
        } catch (IllegalArgumentException ignored) {}


    }
}