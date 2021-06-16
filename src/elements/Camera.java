package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * Camera object in 3d scene for creating rays through pixels.
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class Camera {


    /**
     * Camera's location.
     */
    private Point3D p0;
    /**
     * Camera's upper direction.
     */
    private final Vector vUp;
    /**
     * Camera's forward direction.
     */
    private final Vector vTo;
    /**
     * Camera's right direction
     */
    private Vector vRight;
    /**
     * View plane's width.
     */
    private double width;
    /**
     * View plane's height.
     */
    private double height;
    private boolean focus=false;
    private Point3D focalPix = null;
    public double disFocal = 0;
    /**
     * The distance between the camera and the view plane.
     */
    private double distance;
    /**
     * The number of rays sent by the camera.
     */
    private int numOfRays = 0;

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
     * set 3 double number of the point
     */
    public Camera setP0(double x, double y, double z) {
        this.p0 = new Point3D(x, y, z);
        return this;

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
    public Camera setWidth(double width) {
        if (width <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width = width;
        return this;
    }

    /**
     * Set the new view plane's height.
     *
     * @throws IllegalArgumentException When height illegal.
     */
    public Camera setHeight(double height) {
        if (height <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.height = height;
        return this;
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


        public List<Ray> constructRayThroughPixel ( int nX, int nY, int j, int i){
            Point3D pIJ = CalculateCenterPointInPixel(nX, nY, j, i);
            List<Ray> lr=null;
            Vector vIJ = pIJ.subtract(p0);
            lr.add(new Ray(p0, vIJ));
            if(focus&&!isFocus(j,i)) {
                lr = CalculatCornerRayInPixel(pIJ, nX, nY, j, i);
            }
            return lr;
        }

    private List<Ray> CalculatCornerRayInPixel(Point3D center,int nX, int nY, int j, int i) {

        Point3D p =center;
        List<Ray> lcorner=new LinkedList<>();

        //up
        double yu = nY/(height*2);
        //right
        double xr = nX/(width*2);

        //left up
        if(!isZero(xr)){
            p = center.add(vRight.scale(-xr));
        }
        if(!isZero(yu)){
            p = center.add(vUp.scale(yu));
        }
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //right up
        p = center.add(vRight.scale(xr));
        p = center.add(vUp.scale(yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //left down
        p = center.add(vRight.scale(-xr));
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //right down
        p = center.add(vRight.scale(xr));
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;
        //left middle
        p = center.add(vRight.scale(-xr));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //right middle
        p = center.add(vRight.scale(xr));
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //middle up
        p = center.add(vUp.scale(yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;

        //middle down
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0,p.subtract(p0)));
        p=center;
        return lcorner;
    }


        public Ray constructOneRayPixel ( int X, int Y, int j, int i){
            Point3D pCenterPixel = CalculateCenterPointInPixel(X, Y, j, i);
            return new Ray(p0, pCenterPixel.subtract(p0));
        }

        /**
         * The function calculate the center point of the pixel.
         *
         * @param nX Total number of pixels in the x dimension.
         * @param nY Total number of pixels in the y dimension.
         * @param j  The index of the pixel on the x dimension.
         * @param i  The index of the pixel on the y dimension.
         * @return the center point in the pixel.
         */
        private Point3D CalculateCenterPointInPixel ( int nX, int nY, int j, int i){
            Point3D pC = p0.add(vTo.scale(distance));
            Point3D pIJ = pC;

            double rY = height / nY;
            double rX = width / nX;

            double yI = -(i - (nY - 1) / 2d) * rY;
            double xJ = (j - (nX - 1) / 2d) * rX;

            if (!isZero(xJ)) {
                pIJ = pIJ.add(vRight.scale(xJ));
            }
            if (!isZero(yI)) {
                pIJ = pIJ.add(vUp.scale(yI));
            }
            return pIJ;
        }


        /**
         * Chaining method for setting the  number of rays constructed by the camera.
         * @param numOfRays The number of rays constructed.
         * @return The camera itself.
         */
        public Camera setNumOfRays ( int numOfRays){
            this.numOfRays = numOfRays;
            return this;
        }


        /**
         * Adds the given amount to the camera's position
         * @return the current camera
         */
        public Camera move (Vector amount){
            p0 = p0.add(amount);
            return this;
        }

        /**
         * Adds x, y, z to the camera's position
         * @return the current camera
         */
        public Camera move ( double x, double y, double z){
            return move(new Vector(x, y, z));
        }

        /**
         * Rotates the camera around the axes with the given angles
         * @param amount vector of angles
         * @return the current camera
         */
        public Camera rotate (Vector amount){
            return rotate(amount.getX(), amount.getY(), amount.getZ());
        }


        /**
         * Rotates the camera around the axes with the given angles
         * @param x angles to rotate around the x axis
         * @param y angles to rotate around the y axis
         * @param z angles to rotate around the z axis
         * @return the current camera
         */
        public Camera rotate ( double x, double y, double z){
            vTo.rotateX(x).rotateY(y).rotateZ(z);
            vUp.rotateX(x).rotateY(y).rotateZ(z);
            vRight = vTo.crossProduct(vUp);

            return this;
        }
    public LinkedList<Ray> constructRayPixelAA(int nX, int nY, int j, int i) {
        if (isZero(distance))
            throw new IllegalArgumentException("distance can't be 0");

        LinkedList<Ray> rays = new LinkedList<>();

        double rX = width / nX;
        double rY = height / nY;

        double  randX,randY;

        Point3D pCenterPixel = CalculateCenterPointInPixel(nX,nY,j,i);
        rays.add(new Ray(p0, pCenterPixel.subtract(p0)));
        if(focus&&!isFocus(j,i))
        rays.addAll(CalculatCornerRayInPixel(pCenterPixel, nX, nY, j, i));

        Point3D pInPixel;
        for (int k = 0; k < numOfRays; k++) {
            randX= random(-rX/2,rX/2);
            randY =  random(-rY/2,rY/2);
            pInPixel = new Point3D(pCenterPixel.getX()+randX,pCenterPixel.getY()+randY,pCenterPixel.getZ());
            rays.add(new Ray(p0, pInPixel.subtract(p0)));
        }
        return rays;
    }
    public Camera setFocus(Point3D fp, double length)
    {
        focalPix=fp;
        disFocal=length;
        focus=true;
        return this;
    }
     private boolean isFocus(int j,int i)
     {
        if(focalPix.getX()<=j&&j<=focalPix.getX()+disFocal&&focalPix.getY()<=i&&i<=focalPix.getY()+disFocal)
          return true;
        return false;
     }

    }

