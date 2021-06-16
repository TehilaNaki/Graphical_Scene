package UnitTest.improve;

import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class DepthOfField {

    private Scene scene = new Scene("Test scene");

    @Test
    public void depth() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000).setNumOfRays(10).setFocus(new Point3D(0,0,0),200);

        scene.geometries.add( //
                new Sphere( new Point3D(-20, -10, -50),50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere( new Point3D(-20, -10, -50),30) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
                new Sphere( new Point3D(30, 70, -200),30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere( new Point3D(30, 70, -200),10) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0),new Vector(-1, -1, -2), new Point3D(-100, -100, 550) ) //
                        .setkQ(0.0004).setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("depth", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));//.setMultithreading(5);
        render.renderImage();
        render.writeToImage();
    }

}
