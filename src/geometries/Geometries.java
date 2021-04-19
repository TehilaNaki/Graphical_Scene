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
        // ArrayList has constant-time access and the class probably will access the list a lot more
        // than add to the list, so I chose to use Array List instead of LinkedList.
        intersectables = new ArrayList<>();
    }

    /**
     * Creates a list of given intersectables.
     * @param _intersectables List of intersectables
     */
    public Geometries(Intersectable... _intersectables) {
        intersectables = new ArrayList<>(Arrays.asList(_intersectables));
    }

    /**
     * Adds a list of given intersectables to the current list.
     * @param _intersectables List of intersectables to add
     */
    public void add(Intersectable... _intersectables){
        intersectables.addAll(Arrays.asList(_intersectables));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
      List<Point3D> result=null;

      for(Intersectable geo: intersectables){
          List<Point3D> geoPoints=geo.findIntersections(ray);

          if(geoPoints!=null){

              if(result==null){
                  result=new ArrayList<>();
              }

              result.addAll(geoPoints);
          }

      }
     return result;

    }

}
