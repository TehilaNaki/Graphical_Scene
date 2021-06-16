package renderer;

import elements.Camera;
import multithreading.ThreadPool;
import primitives.Color;
import primitives.Ray;

import java.util.LinkedList;
import java.util.MissingResourceException;

/**
 * Class for rendering a scene with ray tracing.
 */
public class Render {

    /**
     * Image writer of the scene
     */
    ImageWriter imageWriter = null;

    /**
     * The camera in the scene
     */
    Camera camera = null;
    /**
     * The rays from the camera
     */
    RayTracerBase rayTracerBase = null;

    /**
     *
     */
    private ThreadPool<Pixel> threadPool = null;
    /**
     *
     */
    private Pixel nextPixel = null;
    /**
     *
     */

    public static int lastPercent = -1;
    /**
     * Chaining method for setting the image writer
     *
     * @param imageWriter the image writer to set
     * @return the current render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Chaining method for setting the camera
     *
     * @param camera the camera to set
     * @return the current render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Chaining method for setting the ray tracer
     *
     * @param rayTracer the ray tracer to set
     * @return the current render
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracerBase = rayTracer;
        return this;
    }

    /**
     * Chaining method for setting number of threads.
     * If set to 1, the render won't use the thread pool.
     * If set to greater than 1, the render will use the thread pool with the given threads.
     * If set to 0, the thread pool will pick the number of threads.
     *
     * @param threads number of threads to use
     * @return the current render
     * @throws IllegalArgumentException when threads is less than 0
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("threads can be equals or greater to 0");
        }

        // run as single threaded without the thread pool
        if (threads == 1) {
            threadPool = null;
            return this;
        }

        threadPool = new ThreadPool<Pixel>() // the thread pool choose the number of threads (in0 case threads is 0)
                .setParamGetter(this::getNextPixel)
                .setTarget(this::renderImageMultithreaded);
        if (threads > 0) {
            threadPool.setNumThreads(threads);
        }

        return this;
    }



    /**
     * Renders the image
     *
     * @throws UnsupportedOperationException when the render didn't receive all the arguments.
     */

    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("Missing resource", ImageWriter.class.getName(), "");
            }
            if (camera == null) {
                throw new MissingResourceException("Missing resource", Camera.class.getName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("Missing resource", RayTracerBase.class.getName(), "");
            }

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            //rendering the image with multi-threaded
            if (threadPool != null) {
                nextPixel = new Pixel(0, 0);
                threadPool.execute();

                    printPercentMultithreaded(); // blocks the main thread until finished and prints the progress

                threadPool.join();
                return;
            }

            // rendering the image when single-threaded
             adaptiv9(0,nY/2,nX/2,0,nX,nY,1);

            LinkedList<Ray> rays;

            // prints the 100% percent
                printPercent(nX*nY, nX*nY, lastPercent);
        }
        catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Render didn't receive " + e.getClassName());
        }

}

    public int sameColor9(int j1,int i1,int j2,int i2,int j3,int i3,int j4,int i4,int j5,int i5,int j6,int i6,int j7,int i7,int j8,int i8,int j9,int i9,int nX,int nY)
    {
        Color c1= rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j1, i1));
        Color c2 =rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j2, i2));
        Color c3=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j3, i3));
        Color c4=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j4, i4));
        Color c5=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j5, i5));
        Color c6=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY, j6, i6));
        Color c7=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY,j7 , i7));
        Color c8=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY,j8 , i8));
        Color c9=rayTracerBase.traceRay(camera.constructOneRayPixel(nX, nY,j9 , i9));
        int sum=0;
        if(c1==c2)
            sum++;
        if(c2==c3)
            sum++;
        if(c3==c4)
            sum++;
        if(c4==c5)
            sum++;
        if (c5 == c6 )
            sum++;
        if(c6 == c7 )
            sum++;
        if (c7 == c8 )
            sum++;
        if( c8 == c9)
            sum++;
        return sum;
    }


    public void adaptiv9(int j1,int i1,int j2,int i2,int nX,int nY,int level) {
        int numOfSame=sameColor9(j1,i1,j2,i2,j2*2,i1,j2,i1*2,j2,i1,j2/2,i1,j2+j2/2,i1,j2,i1/2,i1+nX/(level*2),i1+nY/(level*2),nX,nY);
       //if it the same color
        if(numOfSame==8) {
            LinkedList<Ray> rays;
            rays = camera.constructRayPixelAA(nX, nY, j1, i1);
            Color c = rayTracerBase.averageColor(rays);
            //pass through each pixel and calculate the color
            System.out.println(level);
            for (int i = i2; i < i2 + nY / level; i++) {
                for (int j = j1; j < j1 + nX / level; j++) {
                        int currentPixel = i * nX + j;
                        lastPercent = printPercent(currentPixel,nX*nY, lastPercent);
                     imageWriter.writePixel(j, i, c);
                }
            }
        }
        //different color  low level
       else if(numOfSame==9)
            {
                adaptiv9(j1,i1/2,j2/2,i2,nX,nY,level*2);
                adaptiv9(j2,j2/2,j2+j2/2,i2,nX,nY,level*2);
                adaptiv9(j1,i1+i1/2,j2/2,i1,nX,nY,level *2);
                adaptiv9(j2,i1+i1/2,j2+j2/2,i1,nX,nY,level*2);
            }
            else
            {
                LinkedList<Ray> rays;
                //pass through each pixel and calculate the color
                for (int i = i2; i < i2+nY/level; i++) {
                    for (int j = j1; j < j1+nX/level; j++) {
                            int currentPixel = i * nX + j;
                            lastPercent = printPercent(currentPixel, nX*nY, lastPercent);
                             castRay(nX, nY, j, i);
                    }
                }
            }
        }



    /**
     * Casts a ray through a given pixel and writes the color to the image.
     * @param nX the number of columns in the picture
     * @param nY the number of rows in the picture
     * @param col the column of the current pixel
     * @param row the row of the current pixel
     */
    private void castRay(int nX, int nY, int col, int row) {
        LinkedList<Ray> rays  = camera.constructRayPixelAA(nX, nY, col, row);
        Color pixelColor = rayTracerBase.averageColor(rays);
        imageWriter.writePixel(col, row, pixelColor);
    }

    /**
     * Prints the progress in percents only if it is greater than the last time printed the progress.
     * @param currentPixel the index of the current pixel
     * @param pixels the number of pixels in the image
     * @param lastPercent the percent of the last time printed the progress
     * @return If printed the new percent, returns the new percent. Else, returns {@code lastPercent}.
     */
    private int printPercent(int currentPixel, int pixels, int lastPercent) {
        int percent = currentPixel * 100 / pixels;
        if (percent > lastPercent) {
            System.out.printf("%02d%%\n", percent);
            System.out.flush();
            return percent;
        }
        return lastPercent;
    }

    /**
     * Adds a grid to the image.
     * @param interval num of the grid's lines
     * @param color the color of the grid's lines
     */
    public void printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Saves the image according to image writer.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Returns the next pixel to draw on multithreaded rendering.
     * If finished to draw all pixels, returns {@code null}.
     */
    private synchronized Pixel getNextPixel() {

            // notifies the main thread in order to print the percent
            notifyAll();


        Pixel result = new Pixel();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // updates the row of the next pixel to draw
        // if got to the end, returns null
        if (nextPixel.col >= nX) {
            if (++nextPixel.row >= nY) {
                return null;
            }
            nextPixel.col = 0;
        }

        result.col = nextPixel.col++;
        result.row = nextPixel.row;
        return result;
    }

    /**
     * Renders a given pixel on multithreaded rendering.
     * If the given pixel is null, returns false which means kill the thread.
     * @param p the pixel to render
     */
    private boolean renderImageMultithreaded(Pixel p) {
        if (p == null) {
            return false; // kill the thread
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        castRay(nX, nY, p.col, p.row);

        return true; // continue the rendering
    }

    /**
     * Must run on the main thread.
     * Prints the percent on multithreaded rendering.
     */
    private void printPercentMultithreaded() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        int pixels = nX * nY;
        int lastPercent = -1;

        while (nextPixel.row < nY) {
        // waits until got update from the rendering threads
            synchronized (this) {
                try { wait(); }
                catch (InterruptedException e) {
                }
            }

            int currentPixel = nextPixel.row * nX + nextPixel.col;
            lastPercent = printPercent(currentPixel, pixels, lastPercent);
        }
    }


/**
 * Helper class to represent a pixel to draw in a multithreading rendering.
 */
private static class Pixel {
    public int col, row;

    public Pixel(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Pixel() {}
}

}

