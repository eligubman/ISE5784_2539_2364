package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Ray class represents a ray in 3D Cartesian coordinate system
 */
public class Ray {
   private final Point head;
    private final Vector direction;

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
     * @param t
     * @return the new point
     */
    public Point getPoint(double t){
        if(isZero(t)){
            return head;
        }
        return head.add(direction.scale(t));
    }

    /**
     * A class that provides geometric utilities.
     */
    public class GeometryUtils {

        private Point head; // The reference point from which distances are measured

        /**
         * Finds the closest point to the reference point (head) from a list of points.
         *
         * @param points the list of points to search from
         * @return the closest point to the reference point, or null if the list is null or empty
         */
        public Point findClosestPoint(List<Point> points) {
            // Return null if the list is null or empty
            if (points == null || points.isEmpty()) {
                return null;
            }

            // Initialize the closest point as the first point in the list
            Point closest = points.get(0);
            double minDistance = head.distance(closest);

            // Iterate through each point in the list
            for (Point point : points) {
                double currentDistance = head.distance(point);
                // Update the closest point if the current point is closer
                if (currentDistance < minDistance) {
                    closest = point;
                    minDistance = currentDistance;
                }
            }

            return closest;
        }
    }
}
