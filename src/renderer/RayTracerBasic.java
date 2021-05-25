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

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constant number for size moving first rays for shading rays
     */

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
        return scene.background;
    }


    /**
     *calculate the local effects of the scene
     * @param intersection geo point intersection with the ray
     * @param ray
     * @param k max level of color
     * @return the color of the local effect
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;
        //for each light source calculate the diffuse and specular
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            double specularN=1;
            if(lightSource instanceof SpotLight)
            {
                specularN=((SpotLight) lightSource).getSpecularN();
            }
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                //adding shadow or not according to the answer from transparency function
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd,l,n, lightIntensity), calcSpecular( ks,l,n, nl, v, nShininess, lightIntensity,specularN));
                }
            }
        }
        return color;
    }

    /**
     *calculate the specular light of the scene
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
     * calculate the diffusive light of the scene
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
     * calculate the transparency of the geometry
     * @param ls light source of the scene
     * @param l vector l
     * @param n vector n
     * @param geoPoint geo point
     * @return double transparency factor of the geometry
     */
   private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDirection,n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) return 1.0;
        double ktr = 1.0;

        double lightDistance = ls.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point)-lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }



    /**
     *calculate the color of the scene
     * @param intersection geo point intersections with the ray
     * @param ray
     * @param level of the recursion
     * @param k factor of the max value of the level
     * @return color of the geometry
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        if(intersection==null)
            return scene.background;
        Color color =intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray,k));
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

    /**
     * construct a reflected ray from the geometry
     * @param n normal vector of the point on the geometry
     * @param point on the geometry
     * @param ray from the geometry
     * @return new reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) {
        Vector v = ray.getDir();
        double vn = v.dotProduct(n);
        Vector vnn = n.scale(-2 * vn);
        Vector r = v.add(vnn);
        // use the constructor with 3 arguments to move the head if needed
        return new Ray(point, r, n);
    }


    /**
     * calculate the global effects of the scene
     * @param geoPoint point on the geometry
     * @param ray from the geometry
     * @param level of recursion
     * @param k max value level
     * @return color
     */
   private Color calcGlobalEffects(GeoPoint geoPoint,Ray ray, int level, double k) {
       Color color = Color.BLACK;

       Material material = geoPoint.geometry.getMaterial();

       double KKr = k * material.kR;

       // vector normal
       Vector n = geoPoint.geometry.getNormal(geoPoint.point);

       if (KKr > MIN_CALC_COLOR_K) {
           Ray reflectedRay = constructReflectedRay(n, geoPoint.point, ray);
           GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
           color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, KKr).scale(material.kR));
       }

       double KKt = k * material.kT;

       if (KKt > MIN_CALC_COLOR_K) {
           Ray refractedRay = constructRefractedRay(n, geoPoint.point, ray);
           GeoPoint refractedPoint =findClosestIntersection(refractedRay);
           color = color.add(calcColor(refractedPoint, refractedRay, level - 1, KKt).scale(material.kT));
       }
       return color;
    }
    /**
     *help function to the recursion
     * @param ray from the geometry
     * @param level of recursion
     * @param kx parameter of the recursion
     * @param kkx parameter of the recursion
     * @return the calculate color
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * construct the refracted ray of the point on the geometry
     * @param n normal vector
     * @param point on the geometry
     * @param ray from the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir(), n);
    }


    /**
     *find the closest intersection point of the ray with the geometry
     * @param ray on the geometry
     * @return the closest geo point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestGeoPoint(intersections);
        }
    }



}
