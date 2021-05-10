package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * A builder
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
     List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);

     if(intersections != null){
         GeoPoint closePoint=ray.findClosestGeoPoint(intersections);
         return calcColor(closePoint);
     }
     //no intersections
     return scene.background;
    }

    private Color calcColor(GeoPoint point){

        return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }
}
