package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Interface for geometrics object
 * @author TehilaNaki & MeravIzhaki
 */
public interface Geometry extends Intersectable{

   /**
    * @param point on geometrics object
    * @return normal vector on this point
    */
   Vector getNormal(Point3D point) ;

}
