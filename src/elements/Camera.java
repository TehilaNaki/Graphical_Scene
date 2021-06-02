package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * Camera object in 3d scene for creating rays through pixels.
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Camera {

    final Point3D p0;
    final Vector vTo;
    final Vector vUp;
    final Vector vRight;
    private double distance;
    private double width;
    private double height;
    private int numOfRays = 0; //num of rays in every pixel(default = 1)

    /**
     * Constructs a camera with location, to and up vectors.
     * The right vector is being calculated by the to and up vectors.
     *
     * @param p0  The camera's location.
     * @param vTo The direction to where the camera is looking.
     * @param vUp The direction of the camera's upper direction.
     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("Up vector is not Orthogonal with To vector");
        }
        this.p0 = p0;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();
        vRight = vTo.crossProduct(vUp);
    }

    /**
     * Returns the camera location.
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * Returns the camera's forward direction.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Returns the camera's upper direction.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Returns the camera's right direction.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Returns the view plane's width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the view plane's height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance between the camera and the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set the new view plane's width.
     *
     * @throws IllegalArgumentException When width illegal.
     */
    public void setWidth(double width) {
        if (width <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width = width;
    }

    /**
     * Set the new view plane's height.
     *
     * @throws IllegalArgumentException When height illegal.
     */
    public void setHeight(double height) {
        if (height <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.height = height;
    }

    /**
     * Chaining method for setting the view plane's size.
     *
     * @param width  The new view plane's width.
     * @param height The new view plane's height.
     * @return The camera itself.
     */
    public Camera setViewPlaneSize(double width, double height) {
        setWidth(width);
        setHeight(height);
        return this;
    }

    /**
     * Chaining method for setting the distance between the camera and the view plane.
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     */
    public Camera setDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }

        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return A ray going through the given pixel.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D pIJ=CalculatCenterPointInPixel(nX,nY,j,i);
        Vector vIJ=pIJ.subtract(p0);

        return new Ray(p0,vIJ);
    }


    public LinkedList<Ray> constructRayPixel(int nX, int nY, int j, int i) {
        if (isZero(distance))
            throw new IllegalArgumentException("distance can't be 0");

        LinkedList<Ray> rays = new LinkedList<>();

        double rX = width / nX;
        double rY = height / nY;

        double  randX,randY;

        Point3D pCenterPixel = CalculatCenterPointInPixel(nX,nY,j,i);
        rays.add(new Ray(p0, pCenterPixel.subtract(p0)));

        Point3D pInPixel;
        for (int k = 0; k < numOfRays; k++) {
                randX= random(-rX/2,rX/2);
                randY =  random(-rY/2,rY/2);
                pInPixel = new Point3D(pCenterPixel.getX()+randX,pCenterPixel.getY()+randY,pCenterPixel.getZ());
                rays.add(new Ray(p0, pInPixel.subtract(p0)));
        }
        return rays;
    }

    private Point3D CalculatCenterPointInPixel(int nX, int nY, int j, int i) {
        Point3D pC = p0.add(vTo.scale(distance));
        Point3D pIJ=pC;

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        if(!isZero(xJ)){
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if(!isZero(yI)){
            pIJ = pIJ.add(vUp.scale(yI));
        }
     return pIJ;
    }


    public Camera setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
        return this;
    }
}
