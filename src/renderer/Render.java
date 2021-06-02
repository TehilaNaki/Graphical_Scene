package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.LinkedList;
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
     * The camera in the scene
     */
    Camera camera;
    /**
     * The rays from the camera
     */
    RayTracerBasic rayTracerBasic;

    /**
     * @param imageWriter of the scene
     * @return this render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return  this;
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
     * @param rayTracerBasic from the camera
     * @return this render
     */
    public Render setRayTracer(RayTracerBasic rayTracerBasic) {
        this.rayTracerBasic = rayTracerBasic;
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
            if (camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (rayTracerBasic == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //Rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            LinkedList<Ray> rays;
            // pass through each pixel and calculate the color
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    rays=camera.constructRayPixel(nX,nY,j,i);
                    imageWriter.writePixel(j,i,rayTracerBasic.AverageColor(rays));
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
