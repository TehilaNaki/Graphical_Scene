package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Cylinder class represent a three-dimensional cylinder- inherited from a tube and a height added to it
 * @author TehilaNaki & MeravIzhaki
 */
public class Cylinder extends Tube{

    private double height;

    @Override
    public String toString() {
        return super.toString()+
                "height=" + height ;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return super.getNormal(p);
    }
}
