package UnitTest.geometries;

import geometries.Plane;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link geometries.Sphere} class.
 */
public class SphereTest
{
    /**
     * Test method for {@link geometries.Sphere#Sphere(primitives.Point3D, double)}.
     */
   @Test
   public void testConstructor()
   {
       // ============ Equivalence Partitions Tests ==============
       // TC01: Test for a proper result.
       try{
           new Sphere ( new Point3D(1,2,3), 5);
       }
       catch (IllegalArgumentException error)
       {
           fail("Failed constructor of the correct sphere");
       }

       // ============ Boundary Values Tests =============
       // TC02: Test when the radius is 0.
       try{
           new Sphere ( new Point3D(1,2,3), 0);
           fail("Constructed a sphere while the radius is 0");
       }
       catch (IllegalArgumentException ignored){}
       // TC03: Test when the radius is negative,-1.
       try{
           new Sphere ( new Point3D(1,2,3), -1);
           fail("Constructed a sphere while the radius is negative");
       }
       catch (IllegalArgumentException ignored){}
   }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Sphere s= new Sphere( new Point3D(1,0,1),2);

        assertEquals(new Vector(0,0,1),s.getNormal(new Point3D(1,0,2)),"Bad normal to sphere");
    }
}