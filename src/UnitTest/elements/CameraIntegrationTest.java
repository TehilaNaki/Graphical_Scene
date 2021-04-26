package UnitTest.elements;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Ray;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Integration tests for {@link Camera} class.
 */
public class CameraIntegrationTest {


    /**
     * Integration tests of a camera and a sphere.
     */
    @Test
    public void testSphereAndCamera() {
       Sphere[] spheres=new Sphere[]{
               //TC01: the sphere is in front of the view plane(2).
               new Sphere(new Point3D(0, 0, -2.5), 1),
               //TC02: the view plane is inside the sphere, all rays should intersect twice(18).
               new Sphere(new Point3D(0, 0, -2.5), 2.5),
               //TC03: the view plane cross the sphere (10).
               new Sphere(new Point3D(0, 0, -2), 2),
               //TC04: the camera is inside the sphere,all rays should intersect only once(9).
               new Sphere(new Point3D(0, 0, -1), 4),
               //TC05: the sphere is behind the camera , no ray should intersect(0).
               new Sphere(new Point3D(0, 0, 1), 0.5)
       };
       int[] expected=new int[]{2,18,10,9,0};

        testIntersectsAndCamera(spheres,expected);
    }

    /**
     * Integration tests of a camera and a plane.
     */
    @Test
    public void testPlaneAndCamera() {
        Plane[] planes=new Plane[]{
                //TC01: the plane is parallel with the view plane, all rays should intersect(9).
                new Plane(new Point3D(0,0,-2),new Vector(0,0,1)),
                //TC02: the plane is in front of the view plane and cross, all rays should intersect(9).
                new Plane(new Point3D(0,0,-1.5),new Vector(0,-0.5,1)),
                //TC03: the plane is above the view plane's third row (6).
                new Plane(new Point3D(0,0,-3),new Vector(0,-1,1))
        };
        int[] expected=new int[]{9,9,6};

        testIntersectsAndCamera(planes,expected);
    }

    /**
     * Integration tests of a camera and a triangle.
     */
    @Test
    public void testTriangleAndCamera() {
        Triangle[] triangles= new Triangle[]{
                //TC01:only the center ray should intersect(1).
                new Triangle(new Point3D(0,1,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2)),
                //TC02: only the center ray and the top-middle ray should intersect(2).
                new Triangle(new Point3D(0,20,-2), new Point3D(1,-1,-2), new Point3D(-1,-1,-2))
        };

        int[] expected=new int[]{1,2};

        testIntersectsAndCamera(triangles,expected);
    }


    private void testIntersectsAndCamera(Intersectable[] intersectables, int[] expectedIntersections) {
        int nX = 3, nY = 3;
        Camera cam = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);
        int sum =0;



            for (int i = 0; i < 3; ++i) {//foreach pixel in row
                for (int j = 0; j < 3; ++j) {//foreach pixel in column

                    Ray pixelRay = cam.constructRayThroughPixel(3, 3, j, i);
                    for (Intersectable geo:intersectables) {//foreach geometries in intersectables

                    var intersect = geo.findIntersections(pixelRay);
                    sum += intersect == null ? 0 : intersect.size();//sum the intersections points from all the geometries
                }
            }
        }
        assertEquals("Wrong number of intersections points", Arrays.stream(expectedIntersections).sum(), sum);
        }

}