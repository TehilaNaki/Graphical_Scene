package UnitTest.elements;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;


/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTest {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Scene scene3=new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.5));

    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000).setNumOfRays(81);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000).setNumOfRays(81);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry triangle3 =  new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
            .setEmission(new Color(java.awt.Color.GREEN));
    private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setkL(0.00001).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Vector(1, 1, -2), new Point3D(-50, -50, 50)) //
                .setkL(0.00001).setkQ(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene1));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setkL(0.0005).setkQ(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene2));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Vector(-2, -2, -1), new Point3D(10, -10, -130)).setSpecularN(1) //
                .setkL(0.0001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a multiple lights
     */
    @Test
    public void sphereMultiLights() {
        scene1.geometries.add(sphere);
        //direction light
       scene1.lights.add(new DirectionalLight(new Color(600, 350, 0), new Vector(30, 30, -1)));
        //point light
        scene1.lights.add(new PointLight(new Color(500, 0, 0), new Point3D(900, 100, -500))
               .setkL(0.00001).setkQ(0.000001));
        //spot light
        scene1.lights.add(new SpotLight(new Color(600, 0, 0), new Vector(1, 1, -2), new Point3D(-200, 100, 50)) //
                .setkL(0.00000005).setkQ(0.000000005));

        ImageWriter imageWriter = new ImageWriter("MultiLightSphere", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }
    /**
     * Produce a picture of a two triangles lighted by a multiple lights
     */
    @Test
    public void TrianglesMultiLight() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
        //direction light
        scene2.lights.add(new DirectionalLight(new Color(400, 30, 0), new Vector(0.65, 0, -1)));
        //spot light
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Vector(-150100, -1000, 1), new Point3D(10, -10, -130)) //
               .setkL(0.0001).setkQ(0.000005));
        //point light
       scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(110, -10, -130)) //
              .setkL(0.0005).setkQ(0.0005));


        ImageWriter imageWriter = new ImageWriter("MultiLightTriangles", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a improve spot light
     */
    @Test
    public void sphereImproveSpotLights() {
        scene1.geometries.add(sphere);
        //spot light
        scene1.lights.add(new SpotLight(new Color(600, 0, 0), new Vector(1, 1, -2), new Point3D(-200, 100, 50)).setSpecularN(30) //
                .setkL(0.00000005).setkQ(0.000000005));

        ImageWriter imageWriter = new ImageWriter("ImproveSpotLightSphere", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene1));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ImproveTrianglesLight() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.6).setnShininess(10)));

        scene2.lights.add(new SpotLight(new Color(600, 0, 250), new Vector(-2, -2, -1), new Point3D(10, -12, -130)).setSpecularN(100) //
                .setkL(0.0001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("ImproveLightTriangles", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene2));

        render.renderImage();
        render.writeToImage();
    }


}
