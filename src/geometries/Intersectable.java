package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * Intersectable interface is the basic interface for all geometries that can be intersected by a ray
 */
public abstract class Intersectable {

    /**
     * boundary of the entity represented by the array [x[min,max],y[min,max],z[min,max]]
     */
    public int[][] boundary;

    /**
     * finds the boundary values of the geometric entity or a group of geometric entities
     *
     * @return the geometry boundary
     */
    protected abstract int[][] calcBoundary();
    /**
     * findIntersections function returns a list of intersection points of a ray with the geometry
     *
     * @param ray the ray that intersects the geometry
     * @return a list of intersection points of the ray with the geometry
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * findGeoIntersections function returns a list of intersection points of a ray with the geometry
     *
     * @param ray the ray that intersects the geometry
     * @return a list of intersection points of the ray with the geometry
     */
    public final   List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * findGeoIntersections function returns a list of intersection points of a ray with the geometry
     *
     * @param ray the ray that intersects the geometry
     * @param maxDistance the maximum distance from the ray head to the intersection point
     * @return a list of intersection points of the ray with the geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * findGeoIntersectionsHelper function returns a list of intersection points of a ray with the geometry
     *
     * @param ray the ray that intersects the geometry
     * @return a list of intersection points of the ray with the geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double distance);

    /**
     * boundary getter
     *
     * @return the boundary
     */
    public int[][] getBoundary() {
        return boundary;
    }


    /**
     * return the indexes of all voxels that the geometric entity intersects with
     * @param scene the scene that we would use its voxels
     * @return the indexes of the voxels intersected with this
     */
    protected List<Double3> findVoxels(Scene scene) {
        List<Double3> indexes = new LinkedList<>();//since we won't remove any voxel but only add we will use linked list
        double xEdgeVoxel=scene.getXEdgeVoxel();
        double yEdgeVoxel=scene.getYEdgeVoxel();
        double zEdgeVoxel=scene.getZEdgeVoxel();

        if(this.boundary==null){
            return indexes;
        }

        int xMinIndex = (int) ((this.boundary[0][0] - scene.geometries.boundary[0][0]) / xEdgeVoxel - 0.01);
        int xMaxIndex = (int) ((this.boundary[0][1] - scene.geometries.boundary[0][0]) / xEdgeVoxel - 0.01);
        int yMinIndex = (int) ((this.boundary[1][0] - scene.geometries.boundary[1][0]) / yEdgeVoxel - 0.01);
        int yMaxIndex = (int) ((this.boundary[1][1] - scene.geometries.boundary[1][0]) / yEdgeVoxel - 0.01);
        int zMinIndex = (int) ((this.boundary[2][0] - scene.geometries.boundary[2][0]) / zEdgeVoxel - 0.01);
        int zMaxIndex = (int) ((this.boundary[2][1] - scene.geometries.boundary[2][0]) / zEdgeVoxel - 0.01);
        //move over all the voxels in the range of indexes
        for (int i = xMinIndex; i <= xMaxIndex; i++) {
            for (int j = yMinIndex; j <= yMaxIndex; j++) {
                for (int k = zMinIndex; k <= zMaxIndex; k++) {
                    indexes.add(new Double3(i, j, k));
                }
            }
        }
        return indexes;
    }




    /**
     * GeoPoint class is a helper class that holds a geometry and a point
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * GeoPoint constructor
         *
         * @param geometry the geometry
         * @param point the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;

        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;

            return geometry==geoPoint.geometry && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
