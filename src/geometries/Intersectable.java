package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Gives interface for an object that is instersectable.
 * @author TehilaNaki & MeravIzhaki
 */
public interface Intersectable {

    List<Point3D> findIntersections(Ray ray);
}
