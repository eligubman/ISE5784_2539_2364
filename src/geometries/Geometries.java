package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Geometries class represents a collection of geometries in 3D Cartesian coordinate system
 */
public class Geometries implements Intersectable{
    private final List<Intersectable> Geometry = new LinkedList<>();

    Geometries(){}

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        Geometry.addAll(List.of(geometries));

    }
@Override
    public List<Point> findIntersections(Ray ray){
        List<Point> Intersection = null;
        for (Intersectable geometry : Geometry) {
             List<Point>  i = (geometry.findIntersections(ray));
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
