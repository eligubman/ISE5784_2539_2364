package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface is the basic interface for all geometries that can be intersected by a ray
 */
public abstract class Intersectable {
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
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * findGeoIntersectionsHelper function returns a list of intersection points of a ray with the geometry
     *
     * @param ray the ray that intersects the geometry
     * @return a list of intersection points of the ray with the geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);





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
