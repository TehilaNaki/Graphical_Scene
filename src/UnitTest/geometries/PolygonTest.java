package UnitTest.geometries;

import geometries.Polygon;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException ignored) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException ignored) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException ignored) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException ignored) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException ignored) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException ignored) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to polygon");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {

        Polygon polygon = new Polygon(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(-2, 0, 0),
                new Point3D(0,-1,0)
        );
        List<Point3D> result;
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the polygon
        result = polygon.findIntersections(new Ray(new Point3D(-0.5, -0.5, -1), new Vector(0.5, 0.5, 3)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-0.33d, -0.33d, 0d), result.get(0).cutTwoNumbers(), "Ray doesn't intersect the polygon");

        //TC02:Ray outside against vertex
        assertNull(polygon.findIntersections(new Ray(new Point3D(0, -2, 0), new Vector(0, 0, 4))), "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(polygon.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(0, 0, 3))), "Ray isn't outside against edge");

        //TC04:Ray inside the polygon
        assertNull(polygon.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0))), "Ray  isn't inside the polygon");

        // ============ Boundary Values Tests =============
        //TC11: Ray On edge
        result = polygon.findIntersections(new Ray(new Point3D(-2, 0, 3), new Vector(1.03d, 0.51d, -3)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point3D(-0.96d, 0.51d, 0d), result.get(0).cutTwoNumbers(), "Ray  isn't on edge of the polygon");

         ///TC12: Ray in vertex
        assertNull(polygon.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(-2d, -1d, 3))),  "Ray  isn't on vertex of the polygon");

        //TC13: Ray On edge's continuation
        assertNull(polygon.findIntersections(new Ray(new Point3D(-1, 2, 0), new Vector(-1d, -2d, 3))), "Ray  isn't On edge's continuation");


    }
}
