package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;
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
        if(geometries.length>0)Geometry.addAll(List.of(geometries));

    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (var geometry : Geometry) {
            List<GeoPoint> returnList = geometry.findGeoIntersections(ray, maxDistance);
            if (returnList != null) { //if it's not null (there are intersections)
                if (intersections == null)
                    intersections = new LinkedList<>(returnList);
                else
                    intersections.addAll(returnList);
            }
        }
        return intersections;
    }

    @Override
    public int[][] calcBoundary() {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (var geometry : Geometry) {
            if(geometry.boundary==null) {
                continue;
            }
                if (geometry.boundary[0][0] < minX)
                    minX = geometry.boundary[0][0];

            if (geometry.boundary[0][1] > maxX)
                maxX = geometry.boundary[0][1];
            if (geometry.boundary[1][0] < minY)
                minY = geometry.boundary[1][0];
            if (geometry.boundary[1][1] > maxY)
                maxY = geometry.boundary[1][1];
            if (geometry.boundary[2][0] < minZ)
                minZ = geometry.boundary[2][0];
            if (geometry.boundary[2][1] > maxZ)
                maxZ = geometry.boundary[2][1];
        }
        return new int[][]{{(int) minX, (int) Math.ceil(maxX)},
                {(int) minY, (int) Math.ceil(maxY)},
                {(int) minZ, (int) Math.ceil(maxZ)}};
    }
    public Geometries remove(Geometry givenGeometry) {
        Geometries list = new Geometries();
        for (var geometry : Geometry) {
            if (!geometry.equals(givenGeometry))
                list.add(geometry);
        }
        return list;
    }

    /**
     * move over all geometric entities of a scene and return a hashmap of all the none empty voxels
     *
     * @param scene the scene
     * @return the hash map of voxels
     */
    public HashMap<Double3, Geometries> attachVoxel(Scene scene) {
        HashMap<Double3, Geometries> voxels = new HashMap<>();
        List<Double3> voxelIndexes;
        int i = 0;
        for (var geometry : Geometry) {
            if(i==231){
                int u = 5;
            }
            i++;
            voxelIndexes = geometry.findVoxels(scene);
            for (var index : voxelIndexes) {
                if (!voxels.containsKey(index))//the voxel is already exists in thr map
                    voxels.put(index, new Geometries(geometry));
                else {
                    voxels.get(index).add(geometry);
                }
            }
        }
        return voxels;
    }

    /**
     * boundary getter
     * @return the matrix of the boundary
     */
    public int[][] getBoundary(){
        return boundary;
    }
}
