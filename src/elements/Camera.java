package elements;

import primitives.Point3D;
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
}
