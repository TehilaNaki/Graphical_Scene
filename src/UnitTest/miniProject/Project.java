package UnitTest.miniProject;
import elements.*;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;


import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class Project {
    /*
    private Scene scene = new Scene("project scene");
    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200).setDistance(900).setNumOfRays(50);


    @Test
    public void project3() {
        scene.geometries.add(
                new Polygon(new Point3D(-100.38423,60.40576,-30),new Point3D(-70.39239,100.02373,-30),new Point3D(-10.43208,30.47956,-30),new Point3D(-30.0967,21.44841,-30)) .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
                new Polygon(new Point3D(-60.38423,100.02373,0),new Point3D(-10.39239,105.02373,0),new Point3D(20.43208,31.47956,0),new Point3D(-8,31.47956,0)) .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
                new Polygon(new Point3D(0,100.02373,-70),new Point3D(35.39239,95.02373,-70),new Point3D(45,22,-70),new Point3D(25.43208,30.47956,-70)) .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkT(0.5)),
                new Sphere( new Point3D(-5, -5, 30), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)),
                new Polygon(new Point3D(0,0.02373,-70),new Point3D(35.39239,-5.02373,-70),new Point3D(45,-80,-70),new Point3D(25.43208,-70.47956,-70)) .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkT(0.5)),
                new Polygon(new Point3D(1,0,0),new Point3D(4,0,0),new Point3D(-3.82224,2.51229,0)) .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkT(0.5))
               );

       // new Polygon(new Point3D(40.0967,-40.44841,40),new Point3D(40.0967,-20.44841,60),new Point3D(40.0967,0.44841,40),new Point3D(40.0967,-20.44841,70),new Point3D(40.0967,-40.44841,80)));

       // scene.lights.add( //
         //       new SpotLight(new Color(400, 240, 0), new Vector(1, 1, -3), new Point3D(-40.39239,20.02373,100)) //
                   //     .setkL(1E-5).setkQ(1.5E-7));

       // scene.lights.add(new SpotLight(new Color(400, 240, 0), new Vector(1, 1, -3), new Point3D(-60.39239,20.02373,100)) //
         //               .setkL(1E-5).setkQ(1.5E-7));
        scene.lights.add( new PointLight(new Color(400, 240, 0), new Point3D(50.39239,-35,100)) //
                        .setkL(1E-5).setkQ(1.5E-7));
        scene.ambientLight =new AmbientLight(new Color(java.awt.Color.green), 0.15);
        scene.background=new Color(java.awt.Color.PINK);

        Render render = new Render(). //
                setImageWriter(new ImageWriter("project", 400, 400)) //
                .setCamera(camera)
                .setMultithreading(5)
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }*/
    @Test
    public void project() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setViewPlaneSize(200, 125)
                .setDistance(800)
                .setNumOfRays(20);


               // .setFocus(new Point3D(0,0,0),300);

        Scene scene = new Scene("Test Scene");
        setLights(scene);
        setGeometries(scene);

        Render render = new Render() //
                .setCamera(camera) //
                .setMultithreading(3)
                .setRayTracer(new RayTracerBasic(scene).setGlossinessRays(20));

        int frames = 1;
        double angle = 360d / frames;
        double angleRadians = 2 * Math.PI / frames;

        double radius = camera.getP0().subtract(Point3D.ZERO).length();

        for (int i = 0; i < frames; i++) {
            System.out.println("Start frame " + (i + 1));

            camera.rotate(0, angle, 0);
            camera.setP0(
                    Math.sin(angleRadians * (i + 1)) * radius,
                    0,
                    Math.cos(angleRadians * (i + 1)) * radius
            );

            ImageWriter imageWriter = new ImageWriter("project/Project" + (i + 1), 600, 450);
            render.setImageWriter(imageWriter);
            render.renderImage();
            render.writeToImage();
        }
    }


    private void setLights(Scene scene){
        scene.lights.add(
                new SpotLight(
                        new Color(500, 500, 500),
                        new Vector(-0.5, -1, -0.5),
                        new Point3D(-50, 100, 100))
                        .setkL(0.004)
                        .setkQ(0.000006));
    }


    private void setGeometries(Scene scene) {
        //triangles
                Point3D h=new Point3D(60,-50,30);
                Point3D g=new Point3D(45,-30,0);
                Point3D a=new Point3D(30,-50,30);
                Point3D b=new Point3D(40,0,15);



        scene.geometries.add(
                //sphere

                new Sphere(new Point3D(80, -28, 0), 22)
                        .setEmission(new Color(30,40,50))
                       .setMaterial(new Material()
                                .setkR(0.8)),

                new Sphere(new Point3D(-45, -45, -5), 5)
                        .setEmission(new Color(60,0,0))
                        .setMaterial(new Material()
                                .setkR(0.8).setkG(0.95)),

                //triangles

                new Triangle(a,g,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(80)),
                new Triangle(a,b,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(80)),
                new Triangle(a,b,g)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(80)),
                new Triangle(g,b,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                           .setnShininess(80)),

                //cylinder
                new Cylinder(new Ray(
                        new Point3D(-80, -45, 0),
                        new Vector(60, 85, 0)),
                        13, 50)
                        .setEmission(new Color(0,100,70))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),


                //square
              //1
                new Polygon(new Point3D(-25,-50,-30),
                        new Point3D(-25,-50,30),
                        new Point3D(15,-50,30),
                        new Point3D(15,-50,-30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8).setkT(0.8)
                                .setnShininess(50)),
                new Polygon(new Point3D(-25,-25,-30),
                        new Point3D(-25,-25,30),
                        new Point3D(15,-25,30),
                        new Point3D(15,-25,-30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                new Polygon(new Point3D(-25,-50,-30),
                        new Point3D(-25,-50,30),
                        new Point3D(-25,-25,30),
                        new Point3D(-25,-25,-30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                new Polygon(new Point3D(15,-50,-30),
                        new Point3D(15,-50,30),
                        new Point3D(15,-25,30),
                        new Point3D(15,-25,-30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                new Polygon(new Point3D(-25,-50,30),
                        new Point3D(15,-50,30),
                        new Point3D(15,-25,30),
                        new Point3D(-25,-25,30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                new Polygon(new Point3D(-25,-50,-30),
                        new Point3D(15,-50,-30),
                        new Point3D(15,-25,-30),
                        new Point3D(-25,-25,-30))
                        .setEmission(new Color(java.awt.Color.magenta))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                //2
                new Polygon(new Point3D(-15,-25,-20),
                        new Point3D(-15,-25,20),
                        new Point3D(5,-25,20),
                        new Point3D(5,-25,-20))
                       .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(new Point3D(-15,-15,-20),
                        new Point3D(-15,-15,20),
                        new Point3D(5,-15,20),
                        new Point3D(5,-15,-20))
                        .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(new Point3D(-15,-25,-20),
                        new Point3D(-15,-25,20),
                        new Point3D(-15,-15,20),
                        new Point3D(-15,-15,-20))
                       .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(new Point3D(5,-25,-20),
                        new Point3D(5,-25,20),
                        new Point3D(5,-15,20),
                        new Point3D(5,-15,-20))
                       .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(new Point3D(-15,-25,20),
                        new Point3D(5,-25,20),
                        new Point3D(5,-15,20),
                        new Point3D(-15,-15,20))
                       .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(new Point3D(-15,-25,-20),
                        new Point3D(5,-25,-20),
                        new Point3D(5,-15,-20),
                        new Point3D(-15,-15,-20))
                       .setEmission(new Color(167,167,167))
                        .setMaterial(new Material()
                                .setkR(0.1).setkD(0.5).setkS(0.5).setkT(0.2)
                                .setnShininess(50)),


                // surface
                new Polygon(
                        new Point3D(-100, -50, -150),
                        new Point3D(-100, -50, 150),
                        new Point3D(100, -50, 150),
                        new Point3D(100, -50, -150))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50)),
                //front block
                new Polygon(
                        new Point3D(40, -50, 85),
                        new Point3D(40, 30, 85),
                        new Point3D(70, 30, 85),
                        new Point3D(70, -50, 85))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setkT(1.0).setkG(0.8)),
                new Polygon(
                        new Point3D(60, -50, 75),
                        new Point3D(60, 40, 75),
                        new Point3D(75 ,40, 75),
                        new Point3D(75, -50, 75))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setkT(1.0).setkG(0.8))



                /*
                new Polygon(
                        new Point3D(-100, -50, -150),
                        new Point3D(-100, 75, -150),
                        new Point3D(100, 75, -150),
                        new Point3D(100, -50, -150))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setkT(0.8)
                                .setnShininess(50))
                                */

        );
    }




}


