package UnitTest.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Triangle} class.
 */
public class TriangleTest
{

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try{
            new Triangle
                    (
                            new Point3D(1,0,0),
                            new Point3D(0,1,0),
                            new Point3D(0,0,1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct triangle");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        try{
            new Triangle
                    (
                            new Point3D(1,0,0),
                            new Point3D(1,0,0),
                            new Point3D(0,0,1)
                    );
            fail("Constructed a triangle while a point equal to b point");
        } catch (IllegalArgumentException ignored){}
        //TC03: Test when a point equal to c point.
        try{
            new Triangle
                    (
                            new Point3D(1,0,0),
                            new Point3D(0,0,1),
                            new Point3D(1,0,0)
                    );
            fail("Constructed a triangle while a point equal to c point");
        } catch (IllegalArgumentException ignored){}
        //TC04: Test when b point equal to c point.
        try{
            new Triangle
                    (
                            new Point3D(1,0,0),
                            new Point3D(0,0,1),
                            new Point3D(0,0,1)
                    );
            fail("Constructed a triangle while b point equal to c point");
        } catch (IllegalArgumentException ignored){}
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Triangle triangle= new Triangle
                (
                        new Point3D(1,0,0),
                        new Point3D(0,1,0),
                        new Point3D(0,0,1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n), triangle.getNormal(new Point3D(0, 0, 1)), "Bad normal to triangle");

    }


}