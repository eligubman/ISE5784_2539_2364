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
        for (Intersectable element : geometries) {
            List<Double> edges = element.getEdges();
            double tMinX = edges.get(0), tMinY = edges.get(1), tMinZ = edges.get(2), tMaxX = edges.get(3),
                    tMaxY = edges.get(4), tMaxZ = edges.get(5);
            minX = tMinX < minX ? tMinX : minX;
            minY = tMinY < minY ? tMinY : minY;
            minZ = tMinZ < minZ ? tMinZ : minZ;
            maxX = tMaxX > maxX ? tMaxX : maxX;
            maxY = tMaxY > maxY ? tMaxY : maxY;
            maxZ = tMaxZ > maxZ ? tMaxZ : maxZ;
        }

    }
@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance){
        List<GeoPoint> Intersection = null;
        for (Intersectable geometry : Geometry) {
            var  i = (geometry.findGeoIntersections(ray,distance));
             if(i!=null){
                 if (Intersection==null){
                     Intersection= new LinkedList<>();
                 }
                 Intersection.addAll(i);
             }
        }
        return Intersection;
    }

    public List<Intersectable> getGeometries() {
        return Geometry;
    }
}
