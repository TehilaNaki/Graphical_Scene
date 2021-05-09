package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Gives interface for an object that is instersectable.
 * @author TehilaNaki & MeravIzhaki
 */
public interface Intersectable {


    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {

            this.geometry = geometry;
            this.point=point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }


    }

    /**
     * @param ray intersection in geometries
     * @return list of intersectables the the ray intersecte in geometries
     */
    List<Point3D> findIntersections(Ray ray);


    /**
     * @param ray
     * @return
     */
    List<GeoPoint> findGeoIntersections (Ray ray);

}
