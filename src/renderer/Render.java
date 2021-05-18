package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 * Render class make from the scene the color matrix
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Render {

    /**
     * Image writer of the scene
     */
    ImageWriter imageWriter;
    /**
     * The scene of the image
     */
    Scene scene;
    /**
     * The camera in the scene
     */
    Camera camera;
    /**
     * The rays from the camera
     */
    RayTracerBase rayTracerBase;

    /**
     * @param imageWriter of the scene
     * @return this render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return  this;
    }

    /**
     * @param scene of the image
     * @return this render
     */
    public Render setScene(Scene scene){
        this.scene=scene;
        return this;
    }

    /**
     * @param camera of the scene
     * @return this render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * @param rayTracerBase from the camera
     * @return this render
     */
    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * Make the image from the elements
     */
    public void renderImage(){

        //check that all the parameters OK
        try {

            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (scene == null) {
                throw new MissingResourceException("missing resource", Scene.class.getName(), "");
            }
            if (camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //Rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            // pass through each pixel and calculate the color
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {

                    Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = rayTracerBase.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        }
        catch (MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
        }

    }

    /**
     * The function make the grid lines
     * @param interval between the lines
     * @param color of the lines
     */
    public void printGrid(int interval, Color color){
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i+=interval) {
            for (int j = 0; j < nX; j++) {

                imageWriter.writePixel(j,i,color);
            }
        }
        for (int i = 0; i < nX; i+=interval) {
            for (int j = 0; j < nY; j++) {

                imageWriter.writePixel(i,j,color);
            }
        }

    }

    /**
     * Turn on the function of the imageWriter to writeToImage
     */
   public void writeToImage(){

       if(imageWriter==null) {
           throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
       }
       imageWriter.writeToImage();

   }

}
