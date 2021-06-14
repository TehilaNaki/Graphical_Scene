package UnitTest.elements;
import geometries.Polygon;
import org.junit.Test;
import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere( new Point3D(0, 0, -50),50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere( new Point3D(0, 0, -50),25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0),new Vector(-1, -1, -2), new Point3D(-100, -100, 500) ) //
                        .setkQ(0.0004).setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000)
               .setNumOfRays(81); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere( new Point3D(-950, -900, -1000),400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
                new Sphere( new Point3D(-950, -900, -1000),200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1).setkG(0.99)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(0.5).setkG(0.99)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point3D(-750, -750, -150)) //
                .setkQ(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setMultithreading(10)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000).setNumOfRays(50);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere( new Point3D(60, 50, -50),30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, -1), new Point3D(60, 50, 0)) //
                .setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void TransparentReflectionSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000).setNumOfRays(50);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //

                new Sphere( new Point3D(60, 50, -50),25) //
                        .setEmission(new Color(java.awt.Color.green)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
                new Sphere( new Point3D(75, 75, 50),30) //
                        .setEmission(new Color(java.awt.Color.red)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(20).setkT(0.6)),
                new Polygon(new Point3D(100,100,0),new Point3D(-70, 70, -140),new Point3D(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6))
        );

        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, -1), new Point3D(60, 50, 0)) //
                .setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionReflectionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void Bonus() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(600, 600).setDistance(1000).setNumOfRays(81);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
                new Triangle(new Point3D(-130, -130, -130), new Point3D(140, -140, -135), new Point3D(65, 65, -140)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)).setEmission(new Color(java.awt.Color.cyan)), //
                new Triangle(new Point3D(150, -150, -150), new Point3D(-150, 1500, -150),
                        new Point3D(67, 67, 300)) //
                        .setEmission(new Color(java.awt.Color.ORANGE)) //
                        .setMaterial(new Material().setkR(1).setkT(0.5)),
                new Triangle(new Point3D(150, -150, -150), new Point3D(-1500, 1500, -1500),
                        new Point3D(-150, -150, -200)) //
                        .setEmission(new Color(0, 120, 220)) //
                        .setMaterial(new Material().setkR(1).setkT(0.5)),
                new Sphere( new Point3D(140, -150, -100),25) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                new Sphere( new Point3D(140, -60, -100),15) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                new Sphere( new Point3D(140, 10, -100),25) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                new Sphere( new Point3D(140, 80, -100),15) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                new Sphere( new Point3D(-100, 150, 50),35) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkR(1)),
                new Sphere( new Point3D(-75, 100, 50),45) //
                        .setEmission(new Color(java.awt.Color.magenta)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
                new Sphere( new Point3D(-55, 100, 50),35) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.3)),
                new Sphere( new Point3D(-30, 100, 50),25) //
                        .setEmission(new Color(java.awt.Color.magenta)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
                new Sphere( new Point3D(-10, 100, 50),20) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.5)),
                new Sphere( new Point3D(15, 100, 50),15) //
                        .setEmission(new Color(java.awt.Color.magenta)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
                new Sphere( new Point3D(40, 100, 50),10) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.6)),
                new Sphere( new Point3D(50, 100, 50),5) //
                        .setEmission(new Color(java.awt.Color.magenta)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),
                new Sphere( new Point3D(70, -10, -100),10) //
                        .setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                new Sphere( new Point3D(75, 75, 50),30) //
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkT(0.6)),
                new Sphere( new Point3D(-350, -300, -400),400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
                new Sphere( new Point3D(-350, -300, -400),200) //
                        .setEmission(new Color(100, 120, 120)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Polygon(new Point3D(100,100,0),new Point3D(-70, 70, -140),new Point3D(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6).setkG(0.8))

        );

        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, -1), new Point3D(60, 50, 0)) //
                .setkQ(2E-7)); //.setkQ(0.000005));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point3D(-750, -750, -150)));
        ImageWriter imageWriter = new ImageWriter("bonus", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setMultithreading(5)
                .setPrintPercent(true)
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void miniProject1() {
        for (int i = 0; i < 11; i++) {
            Scene scene = new Scene("Test scene");

            Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                    .setViewPlaneSize(500, 500).setDistance(1000).rotate(0, 0, 36 * i).setNumOfRays(80);

            scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.17));

            scene.geometries.add( //
                    new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
                    new Sphere(new Point3D(140, 30, -100), 40) //
                            .setEmission(new Color(java.awt.Color.cyan)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),

                    new Sphere(new Point3D(-100, 170, 50), 45) //
                            .setEmission(new Color(java.awt.Color.BLACK)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkR(1)),
                    new Sphere(new Point3D(-105, 120, 80), 55) //
                            .setEmission(new Color(java.awt.Color.RED)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkT(0.5)),
                    new Sphere(new Point3D(-40, 120, 50), 35) //
                            .setEmission(new Color(java.awt.Color.pink)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                    new Sphere(new Point3D(15, 120, 70), 25) //
                            .setEmission(new Color(java.awt.Color.RED)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4).setkT(0.4)),
                    new Sphere(new Point3D(50, 140, -50), 15) //
                            .setEmission(new Color(java.awt.Color.pink)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkR(0.4)),
                    new Sphere(new Point3D(70, -10, -100), 20) //
                            .setEmission(new Color(java.awt.Color.cyan)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(10).setkR(0.4)),
                    new Sphere(new Point3D(75, 75, -100), 40) //
                            .setMaterial(new Material().setkD(0.5).setkS(0.5).setkT(0.5).setnShininess(30)).setEmission(new Color(java.awt.Color.magenta)),
                    new Sphere(new Point3D(-350, -300, -400), 500) //
                            .setEmission(new Color(0, 0, 150)) //
                            .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(60).setkT(0.3)),
                    new Sphere(new Point3D(-350, -300, -300), 300) //
                            .setEmission(new Color(200, 120, 120)) //
                            .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(60).setkT(0.3)),
                    new Sphere(new Point3D(-350, -300, -300), 150) //
                            .setEmission(new Color(java.awt.Color.red)) //
                            .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(60).setkT(0.3)),
                    new Polygon(new Point3D(200, 100, 0), new Point3D(-70, 70, -140), new Point3D(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));


            scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1), new Point3D(60, 50, 0)) //
                    .setkQ(2E-7)); //.setkQ(0.000005));
            scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point3D(-750, -750, -150)));
            scene.lights.add(new DirectionalLight(new Color(400, 30, 0), new Vector(0.65, 0, -1)));
            ImageWriter imageWriter = new ImageWriter("miniProject1 " + i, 500, 500);
            Render render = new Render() //
                    .setImageWriter(imageWriter) //
                    .setCamera(camera) //
                    .setMultithreading(5)
                    .setPrintPercent(true)
                    .setRayTracer(new RayTracerBasic(scene));

            render.renderImage();
            render.writeToImage();
        }
    }

    @Test
    public void trianglesTransparentSphereRotated() {
        for (int i = 0; i < 11; i++) {
            Scene scene = new Scene("Test scene");

            Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                    .setViewPlaneSize(200, 200).setDistance(1000)
                    .rotate(0, 0, 36 * i).setNumOfRays(50);

            scene.ambientLight =new AmbientLight(new Color(java.awt.Color.WHITE), 0.15);

            scene.geometries.add( //
                    new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                    new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                    new Sphere( new Point3D(60, 50, -50),30) //
                            .setEmission(new Color(java.awt.Color.BLUE)) //
                            .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

            scene.lights.add(new SpotLight(new Color(700, 400, 400), new Vector(0, 0, -1),new Point3D(60, 50, 0)) //
                    .setkL(4E-5).setkQ(2E-7));

            ImageWriter imageWriter = new ImageWriter("refractionShadowRotated" + i, 500, 500);
            Render render = new Render() //
                    .setImageWriter(imageWriter) //
                    .setCamera(camera) //
                    .setMultithreading(5)
                    .setPrintPercent(true)
                    .setRayTracer(new RayTracerBasic(scene));

            render.renderImage();
            render.writeToImage();
        }
    }
}
