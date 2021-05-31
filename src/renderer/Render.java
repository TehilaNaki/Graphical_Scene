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
           for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    int c=0;
                    int k,l;
                    //the left up corner
                    if(i==0&&j==0)
                    {
                        for (k=0; k <2; k++) {
                            for (l = 0; l <=1; l++) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 4;
                        imageWriter.writePixel(j, i, c);
                    }
                    //the right down corner
                    else if(i==nY-1&&j==0)
                    {
                        for (k=nY-1; k > nY-3; k--) {
                            for (l =0; l <2; l++) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 4;
                        imageWriter.writePixel(j, i, c);
                    }
                   //the right up corner
                   else if(i==0&&j==nX-1)
                    {
                        for (k=0; k < 2; k++) {
                            for (l = nX- 1; l > nX-3 ; l--) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 4;
                        imageWriter.writePixel(j, i, c);
                    }
                   //the right down corner
                   else  if(i==nY-1&&j==nX-1)
                    {
                        for (k=nY-1; k > nY-3; k--) {
                            for (l = nX- 1; l > nX-3 ; l--) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 4;
                        imageWriter.writePixel(j, i, c);
                    }
                   //left column
                    else if(i>0&&j==0)
                    {
                        for (k=i-1; k < i + 2; k++) {
                            for (l =0; l <2; l++) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 6;
                        imageWriter.writePixel(j, i, c);
                    }
                    //up row
                    else if(i==0&&j>0)
                    {
                        for (k=0; k < 2; k++) {
                            for (l = j - 1; l < j + 2; l++) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 6;
                        imageWriter.writePixel(j, i, c);
                    }
                    //right column
                    else if(j==nX-1&&i>0)
                    {
                        for (k=i-1; k < i + 2; k++) {
                            for (l = nX- 1; l > nX-3 ; l--) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 6;
                        imageWriter.writePixel(j, i, c);
                    }
                    //down row
                    else if(j>0&&i==nY-1)
                    {
                        for (k=nY-1; k > nY-3; k--) {
                            for (l = j - 1; l < j + 2; l++) {
                                c += imageWriter.getImage().getRGB(k, l);
                            }
                        }
                        c /= 6;
                        imageWriter.writePixel(j, i, c);
                    }
                    //center
                  else if(i>0&&j>0)
                   {
                       for (k=i-1; k < i + 5; k++) {
                            for (l = j - 1; l < j + 5; l++) {
                             c += imageWriter.getImage().getRGB(i,j );
                            }
                        }
                        c /= 36;
                        imageWriter.writePixel(j, i, c);
                    }

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
