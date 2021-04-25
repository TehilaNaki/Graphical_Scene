package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 * Geometries class is a collection of intersectables and can calculates their intersections.
 * It is using Composite design.
 * @author TehilaNaki & MeravIzhaki
 */
public class Geometries implements Intersectable {

   private final List<Intersectable> intersectables;


    /**
     * Default constructor.
     * Creates an empty list of intersectables.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * Creates a list of given intersectables.
     * @param intersectables List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        this.intersectables=new LinkedList<>(Arrays.asList(intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     * @param intersectables List of intersectables to add
     */
    public void add(Intersectable... intersectables){
        this.intersectables.addAll(Arrays.asList(intersectables));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
      List<Point3D> result=null;

      for(Intersectable geo: intersectables){
          List<Point3D> geoPoints=geo.findIntersections(ray);

          if(geoPoints!=null){

              if(result==null){
                  result=new LinkedList<>();
              }

              result.addAll(geoPoints);
          }

      }
     return result;

    }

}
