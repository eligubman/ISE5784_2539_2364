package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private final List<Intersectable> Geomet= new LinkedList<>();

    Geometries(){}

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        Geomet.addAll(List.of(geometries));
        //for (Intersectable geometry : geometries) {
            //Geomet.add(geometry);
        //}
    }

    public List<Point> findIntsersections(Ray ray){
        List<Point> Intsersections = null;
        for (Intersectable geometry : Geomet) {
             List<Point>  i = (geometry.findIntsersections(ray));
             if(i!=null){
                 if (Intsersections==null){
                     Intsersections= new ArrayList<>();
                 }
                 Intsersections.addAll(i);
             }
        }
        return Intsersections;
    }
}
