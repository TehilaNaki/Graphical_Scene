package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

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
     List<Point3D> intersections= scene.geometries.findIntersections(ray);
     if(intersections != null){
         Point3D closePoint=ray.findClosestPoint(intersections);
         return calcColor(closePoint);
     }
     //no intersections
     return scene.background;
    }

    private Color calcColor(Point3D point){
       return scene.ambientLight.getIntensity();
    }
}
