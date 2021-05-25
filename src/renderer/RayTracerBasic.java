package renderer;

import elements.LightSource;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constant number for size moving first rays for shading rays
     */
    private static final double DELTA = 0.1;
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * A builder
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);

        if (points != null) {
            GeoPoint closePoint = ray.findClosestGeoPoint(points);
            return calcColor(closePoint, ray);
        }
        //no points
        return Color.BLACK;
    }



    /**
     * Calculate the local component of the scene
     * @param point on the geometry
     * @param ray from the camera
     * @return the color intensity
     */
    private Color calcLocalEffects(GeoPoint point, Ray ray) {

        Vector v = ray.getDir();
        Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = point.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            double specularN=1;
            if(lightSource instanceof SpotLight)
            {
                specularN=((SpotLight) lightSource).getSpecularN();
            }
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(lightSource,l, n,point)) {
                    Color lightIntensity = lightSource.getIntensity(point.point);
                    color = color.add(calcDiffusive(material.kD, l, n, lightIntensity),
                            calcSpecular(material.kS, l, n, nl, v,  material.nShininess, lightIntensity, specularN));
                }
            }
        }
        return color;
    }

    /**
     *
     * @param ks factor of the specular
     * @param l vector of the
     * @param n normal vector of the geometry point
     * @param nl vector n dot product vector l
     * @param v vector of ray direction
     * @param nShininess factor of the shining
     * @param lightIntensity color of the intensity
     * @param specularN specular component
     * @return calculate color
     */
    private Color calcSpecular(double ks, Vector l, Vector n,double nl, Vector v, int nShininess, Color lightIntensity,double specularN) {
        Vector r=l.subtract(n.scale(nl*2));
        double vr=Math.pow(v.scale(-1).dotProduct(r),nShininess);
        return lightIntensity.scale(ks*Math.pow(vr,specularN));
    }

    /**
     * @param kd diffuse factor
     * @param l vector of the
     * @param n normal vector of the geometry point
     * @param lightIntensity color of the intensity
     * @return calculate color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln=Math.abs(n.dotProduct(l));
        return lightIntensity.scale(kd*ln);
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Ray lightRay = new Ray(geopoint.point.add(delta), lightDirection);

        //only geometry with Transparency!= make shadow
        if(geopoint.geometry.getMaterial().kT==0) return true;

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) return true;

        double lightDistance = light.getDistance(geopoint.point);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 )
                return false;
        }
        return true;
    }

    /**
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }
    /**
     * Calculate the color intensity on the point
     * @param gPoint on the geometry
     * @param ray from the camera
     * @return the color intensity
     */
    private Color calcColor(GeoPoint gPoint, Ray ray) {
        return calcColor(gPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


    /*private Color calcGlobalEffects(GeoPoint gp, Ray r, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkR = k * material.kR;
        if (kkR > MIN_CALC_COLOR_K) {
            Ray reflectedRay=constructReflectedRay(gp,r);
            GeoPoint reflectedPoint=reflectedRay.findClosestGeoPoint(scene.geometries.findGeoIntersections(r));
            color = calcGlobalEffect(constructReflectedRay(gp, r), level-1, material.kR, kkR);
        }
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay=constructRefractedRay(gp,r);
            GeoPoint refractedPoint=refractedRay.findClosestGeoPoint(scene.geometries.findGeoIntersections(r));
            color = color.add(calcGlobalEffect(constructRefractedRay(gp, r), level-1, material.kT, kkt));

        }
        return color;
    }*/

    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        double kr =material.kR, kkr=k*kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay=constructReflectedRay(geoPoint,ray);
            List<GeoPoint> geoPointList =scene.geometries.findGeoIntersections(reflectedRay);
            GeoPoint reflectedPoint=reflectedRay.findClosestGeoPoint(geoPointList);
            if(reflectedPoint!=null){
                color = color.add(calcGlobalEffect(reflectedRay, level, kr, kkr));
            }
        }
        double kt =material.kT, kkt=k*kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay=constructRefractedRay(geoPoint,ray);
            List<GeoPoint> geoPointList =scene.geometries.findGeoIntersections(refractedRay);
            GeoPoint refractedPoint=refractedRay.findClosestGeoPoint(geoPointList);
            if(refractedPoint!=null){
                color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
            }
        }
        return color;
    }


    private Ray constructRefractedRay(GeoPoint geoPoint, Ray r) {
       return new Ray(geoPoint.point,r.getDir());
    }

    private Ray constructReflectedRay(GeoPoint geoPoint,Ray ray){

        Vector n=geoPoint.geometry.getNormal(geoPoint.point);
        Vector v=ray.getDir();
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙n
        Vector r=v.subtract(n.scale(2*v.dotProduct(n)));

        return new Ray(geoPoint.point,r);
    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx);
    }

    /**
     *
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections=scene.geometries.findGeoIntersections(ray);
        if(intersections==null)
            return null;
       return ray.findClosestGeoPoint(intersections);
    }
}
