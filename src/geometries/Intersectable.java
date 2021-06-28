package geometries;

import primitives.Coordinate;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static primitives.Util.isZero;

/**
 * Gives interface for an object that is instersectable.
 *
 * @author TehilaNaki & MeravIzhaki
 */
public interface Intersectable {


    /**
     * class of geometry's points
     */
    public static class GeoPoint {
        /**
         * Geometry on the scene
         */
        public Geometry geometry;
        /**
         * Point on the geometry
         */
        public Point3D point;

        /**
         * Constructor with two parameters
         * @param geometry
         * @param point on the geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {

            this.geometry = geometry;
            this.point=point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }
    }

    /**
     * @param ray intersection in geometries
     * @return list of intersectables the the ray intersecte in geometries
     */
    default List<Point3D> findIntersections(Ray ray) {

        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * @param ray from the camera
     * @return list of geometry's points
     */
    List<GeoPoint> findGeoIntersections (Ray ray);


}
