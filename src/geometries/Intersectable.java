package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface is the basic interface for all geometries that can be intersected by a ray
 */
public abstract class Intersectable {
    // the voxel grid boundaries

    double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY,
            maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;


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
     * getEdges function returns the voxel grid boundaries
     * @return the voxel grid boundaries
     */
    public final List<Double> getEdges() {
        return List.of(minX, minY, minZ, maxX, maxY, maxZ);
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
