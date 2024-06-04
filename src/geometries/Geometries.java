package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Geometries class represents a collection of geometries in 3D Cartesian coordinate system
 */
public class Geometries extends Intersectable{
    private final List<Intersectable> Geometry = new LinkedList<>();

    Geometries(){}

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        Geometry.addAll(List.of(geometries));

    }
@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> Intersection = null;
        for (Intersectable geometry : Geometry) {
            var  i = (geometry.findGeoIntersections(ray));
             if(i!=null){
                 if (Intersection==null){
                     Intersection= new LinkedList<>();
                 }
                 Intersection.addAll(i);
             }
        }
        return Intersection;
    }


}
