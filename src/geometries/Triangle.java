package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class represent a three-dimensional triangle- inherited from Polygon
 * @author TehilaNaki & MeravIzhaki
 */

public class Triangle extends Polygon {


    /**
     * Creates a new triangle from a given vertices of the triangle.
     * @param p1 A point on the plane.
     * @param p2 A point on the plane.
     * @param p3 A point on the plane.
     * @exception IllegalArgumentException When two of the given vertices are equals.
     */
    public Triangle(Point3D p1,Point3D p2, Point3D p3)
    {
        super(p1,p2,p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + vertices.get(0) +
                ", b=" + vertices.get(1) +
                ", c=" + vertices.get(2) +
                '}';
    }


}
