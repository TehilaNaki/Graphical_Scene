package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera  {

    final Point3D p0;
    final Vector vT0;
    final Vector vUp;
    final Vector vRight;
    private double distance;
    private double width;
    private double height;

    public Camera(Point3D p0, Vector vT0, Vector vUp) {
        this.p0 = p0;
        this.vT0 = vT0.normalized();
        this.vUp = vUp.normalized();
        if(vT0.dotProduct(vUp)==0)
        {
            throw new IllegalArgumentException("vUp Not Orthogonal to vTO");
        }
        vRight=vT0.crossProduct(vUp);
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getvT0() {
        return vT0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public void setWidth(double width) {
        if(width<=0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width = width;
    }

    public void setHeight(double height) {
        if(height<=0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.height = height;
    }

    public Vector getvRight() {
        return vRight;
    }
    public Camera setViewPlaneSize(double _width, double _height) {

        if(_width<=0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        if(_height<=0) {

            throw new IllegalArgumentException("Illegal value of height");
        }
        width=_width;
        height=_height;
        return this;
    }
    public Camera setDistance(double _distance){

        if(_distance<=0) {

            throw new IllegalArgumentException("Illegal value of distance");
        }
        distance=_distance;
        return this;
    }
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        return null;
    }
}
