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
    ImageWriter imageWriter=null;

    /**
     * The camera in the scene
     */
    Camera camera=null;
    /**
     * The rays from the camera
     */
    RayTracerBase rayTracerBase=null;

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
    private boolean printPercent = false;

    /**
     * Chaining method for setting the image writer
     * @param imageWriter the image writer to set
     * @return the current render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Chaining method for setting the camera
     * @param camera the camera to set
     * @return the current render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Chaining method for setting the ray tracer
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
     * @param threads number of threads to use
     * @exception IllegalArgumentException when threads is less than 0
     * @return the current render
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
     * Chaining method for making the render to print the progress of the rendering in percents.
     * @param print if true, prints the percents
     * @return the current render
     */
    public Render setPrintPercent(boolean print) {
        printPercent = print;
        return this;
    }

    /**
     * Renders the image
     *
     * @exception UnsupportedOperationException when the render didn't receive all the arguments.
     */
    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("Missing resource", ImageWriter.class.getName(), "");
            }
            if (camera == null) {
                throw new MissingResourceException("Missing resource", Camera.class.getName(), "");
            }
            if (rayTracerBase== null) {
                throw new MissingResourceException("Missing resource", RayTracerBase.class.getName(), "");
            }

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            //rendering the image when multi-threaded
            if (threadPool != null) {
                nextPixel = new Pixel(0, 0);
                threadPool.execute();
                if (printPercent) {
                    printPercentMultithreaded(); // blocks the main thread until finished and prints the progress
                }
                threadPool.join();
                return;
            }

            // rendering the image when single-threaded
            int lastPercent = -1;
            int pixels = nX * nY;
            LinkedList<Ray> rays;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if (printPercent) {
                        int currentPixel = i * nX + j;
                        lastPercent = printPercent(currentPixel, pixels, lastPercent);
                    }
                    castRay(nX, nY, j, i);
                }
            }
            // prints the 100% percent
            if (printPercent) {
                printPercent(pixels, pixels, lastPercent);
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Render didn't receive " + e.getClassName());
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
        if (printPercent) {
            // notifies the main thread in order to print the percent
            notifyAll();
        }

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
            synchronized(this) {
                try {
                    wait();
                } catch (InterruptedException e) { }
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
