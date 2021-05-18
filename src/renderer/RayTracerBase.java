package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**
 * RayTracerBase abstract class that use as an interface for RayTracerBasic
 *
 * @author TehilaNaki & MeravIzhaki
 */
public abstract class RayTracerBase {

    /**
     * The scene of the image
     */
    protected Scene scene;

    /**
     * A builder function that get a scene
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * An abstract function that get a ray and return the color of the point that cross the ray
     * @param ray ray that intersect the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray);
}
