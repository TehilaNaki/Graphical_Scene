package renderer;

import elements.LightSource;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.random;
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

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private int glossinessRays = 10;

    /**
     * A builder
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    public RayTracerBasic setGlossinessRays(int glossinessRays) {
        if (glossinessRays <= 0) {
            throw new IllegalArgumentException("number of glossiness rays should be greater than 0");
        }

        this.glossinessRays = glossinessRays;
        return this;
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

        if (intersections == null)
            return 1.0;

        double ktr = 1.0;
        double lightDistance = ls.getDistance(geoPoint.point);

        for (GeoPoint gp : intersections) {

            if (alignZero(gp.point.distance(geoPoint.point)-lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
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
        return calcColor(gPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
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
        Vector vn = n.scale(-2 *  v.dotProduct(n));
        Vector r = v.add(vn);
        // use the constructor with 3 arguments to move the head
        return new Ray(point, r, n);
    }


    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that caused the intersection
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();

        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        if (v.dotProduct(n) > 0) {
            n = n.scale(-1);
        }

        // adds the reflection effect
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray[] reflectedRays = constructReflectedRays(gp.point, v, n, material.kG, glossinessRays);
            for (Ray reflectedRay : reflectedRays) {
                color = color.add(calcGlobalEffect(reflectedRay, level, material.kR, kkr)
                        .scale(1d / reflectedRays.length));
            }
        }

        // adds the refraction effect
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray[] refractedRays = constructRefractedRays(gp.point, v, n.scale(-1), material.kG, glossinessRays);
            for (Ray refractedRay : refractedRays) {
                color = color.add(calcGlobalEffect(refractedRay, level, material.kT, kkt)
                        .scale(1d / refractedRays.length));
            }
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

    @Override
    public Color averageColor(LinkedList<Ray> rays){
        Color color=Color.BLACK;
        for( Ray ray:rays){
            color=color.add(traceRay(ray));
        }
        return color.reduce(Double.valueOf(rays.size()));
    }
    /**
     * Constructs randomized reflection rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the specular vector
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized reflection rays
     */
    private Ray[] constructReflectedRays(Point3D point, Vector v, Vector n, double kG, int numOfRays) {
        Vector n2vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(n2vn);

        // If kG is equals to 1 then return only 1 ray, the specular ray (r)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, r, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(r.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    /**
     * Constructs randomized refraction rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the vector v (which is the specular vector).
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized refraction rays
     */
    private Ray[] constructRefractedRays(Point3D point, Vector v, Vector n, double kG, int numOfRays) {
        // If kG is equals to 1 then return only 1 ray, the specular ray (v)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, v, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(v.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    /**
     * Creates random vectors on the unit hemisphere with a given normal on the hemisphere's bottom.<br>
     * source: https://my.eng.utah.edu/~cs6958/slides/pathtrace.pdf#page=18
     *
     * @param n normal to the hemisphere's bottom
     * @return the randomized vectors
     */
    private Vector[] createRandomVectorsOnSphere(Vector n, int numOfVectors) {
        // pick axis with smallest component in normal
        // in order to prevent picking an axis parallel
        // to the normal and eventually creating zero vector
        Vector axis;
        if (Math.abs(n.getX()) < Math.abs(n.getY()) && Math.abs(n.getX()) < Math.abs(n.getZ())) {
            axis = new Vector(1, 0, 0);
        } else if (Math.abs(n.getY()) < Math.abs(n.getZ())) {
            axis = new Vector(0, 1, 0);
        } else {
            axis = new Vector(0, 0, 1);
        }

        // find two vectors orthogonal to the normal
        Vector x = n.crossProduct(axis);
        Vector z = n.crossProduct(x);

        Vector[] randomVectors = new Vector[numOfVectors];
        for (int i = 0; i < numOfVectors; i++) {
            // pick a point on the hemisphere bottom
            double u, v, u2, v2;
            do {
                u = random() * 2 - 1;
                v = random() * 2 - 1;
                u2 = u * u;
                v2 = v * v;
            } while (u2 + v2 >= 1);

            // calculate the height of the point
            double w = Math.sqrt(1 - u2 - v2);

            // create the new vector according to the base (x, n, z) and the coordinates (u, w, v)
            randomVectors[i] = x.scale(u)
                    .add(z.scale(v))
                    .add(n.scale(w));
        }

        return randomVectors;
    }
}
