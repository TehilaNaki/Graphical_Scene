package UnitTest.Primitives;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link primitives.Ray} class.
 */
public class RayTest {

    /**
     * Test method for {@link Ray#findClosestPoint(List)} .
     */
    @Test
    public void testFindClosestPoint() {
        Ray ray=new Ray(new Point3D(0,0,10), new Vector(1,10,-100));
        List<Point3D> lst;
        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the ray.
        lst=new LinkedList<Point3D>();
        lst.add(new Point3D(1,1,-100));
        lst.add(new Point3D(-1,1,-99));
        lst.add(new Point3D(0,2,-10));
        lst.add(new Point3D(0.5,0,-100));

        assertEquals(lst.get(2),ray.findClosestPoint(lst),"");

        // =============== Boundary Values Tests ==================
        //TC01: the list is empty
        lst=null;

       assertNull(ray.findClosestPoint(lst),"The list is empty");

       //TC02: The first point is closest to the beginning of the ray
        lst=new LinkedList<Point3D>();
        lst.add(new Point3D(0,2,-10));
        lst.add(new Point3D(1,1,-100));
        lst.add(new Point3D(-1,1,-99));
        lst.add(new Point3D(0.5,0,-100));

        assertEquals(lst.get(0),ray.findClosestPoint(lst),"The first point isn't te closest to the beginning of the ray");

        // TC03: The first point is closest to the beginning of the ray
        lst=new LinkedList<Point3D>();
        lst.add(new Point3D(1,1,-100));
        lst.add(new Point3D(-1,1,-99));
        lst.add(new Point3D(0.5,0,-100));
        lst.add(new Point3D(0,2,-10));

        assertEquals(lst.get(3),ray.findClosestPoint(lst),"The last point isn't te closest to the beginning of the ray");

    }
}