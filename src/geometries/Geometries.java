package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 * Geometries class is a collection of intersectables and can calculates their intersections.
 * It is using Composite design.
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Geometries implements Intersectable {

    /**
     * List of intersectables
     */
    private final List<Intersectable> intersectables;


    /**
     * Default constructor.
     * Creates an empty list of intersectables.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * Creates a list of given intersectables.
     *
     * @param intersectables List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        this.intersectables = new LinkedList<>(Arrays.asList(intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     *
     * @param intersectables List of intersectables to add
     */
    public void add(Intersectable... intersectables) {
        this.intersectables.addAll(Arrays.asList(intersectables));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;

        for (Intersectable geo : intersectables) {
            List<Point3D> points = geo.findIntersections(ray);

            if (points != null) {

                if (result == null) {
                    result = new LinkedList<>();
                }

                result.addAll(points);
            }
        }

        return result;

    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        List<GeoPoint> intersections = null;

        for (Intersectable geometry : intersectables) {

            // if there are elements in geoIntersections – add them to intersections
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray);

            if (geoIntersections != null) {

                   if (intersections == null) {
                       intersections = new LinkedList<>();
                   }

                   intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }

}
