package primitives;

import geometries.Intersectable;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.isZero;

/**
 * Ray class represents a ray in 3D Cartesian coordinate system
 */
public class Ray {
   private final Point head;
    private final Vector direction;
    private static final double DELTA = 0.00001; // Small value used for offset the ray origin

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * constructor for Ray class
     * @param head the head of the ray
     * @param direction the direction of the ray
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * constructor for Ray class with normal to the direction
     * @param head the head of the ray
     * @param direction the direction of the ray
     * @param normal the normal to the direction
     */
    public Ray(Point head, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA);
        this.head = head.add(delta);
        this.direction = direction.normalize();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(head);
        result = 31 * result + Objects.hashCode(direction);
        return result;
    }

    /**
     *
     * @param t the distance from the head of the ray
     * @return the new point
     */
    public Point getPoint(double t){
        if(isZero(t)){
            return head;
        }
        return head.add(direction.scale(t));
    }
    /**
     * find the closest point to the head of the ray
     * @param points list of points
     * @return the closest point
     */


        public Point findClosestPoint(List<Point> points) {
            return points == null || points.isEmpty() ? null
                    : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
        }


    /**
     * find the closest point to the head of the ray
     * @param points list of points
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points){
        if(points==null){
            return null;
        }
        GeoPoint closest=points.get(0);
        double distance = head.distance(closest.point);
        for(GeoPoint p:points){
            if(head.distance(p.point)<distance){
                closest=p;
                distance=head.distance(p.point);
            }
        }
        return closest;
    }
}
