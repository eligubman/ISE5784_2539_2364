package primitives;

import geometries.Intersectable;

import java.util.*;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

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
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null)
            return null;

        GeoPoint closest = null;
        double d = Integer.MAX_VALUE;
        double calcD;
        // For each point, checks if it's closer than the previous, and if so, replaces
        // it
        for (GeoPoint gp : points) {
            calcD = gp.point.distanceSquared(head);
            if (calcD < d) {
                closest = gp;
                d = calcD;
            }
        }
        return closest;
    }

    /**
     * Generates a beam of rays spread out from the main ray within a specified radius and distance.
     * The beam is formed by calculating additional rays at random points within a circle defined by the radius
     * and centered at a point along the main ray's direction at the specified distance.
     *
     * @param n The normal vector to the plane in which the rays are spread.
     * @param radius The radius of the circle within which the rays are spread.
     * @param distance The distance from the head of the main ray to the center of the circle.
     * @param numOfRays The number of rays to generate within the beam, including the main ray.
     * @return A list of {@link Ray} objects representing the main ray and the additional rays forming the beam.
     */
    public List<Ray> generateBeam(Vector n, double radius, double distance, int numOfRays) {
    List<Ray> rays = new LinkedList<Ray>();
    rays.add(this); // Including the main ray in the list of rays to be returned

    // If only one ray is requested or the radius is zero, return the list containing only the current ray
    if (numOfRays == 1 || isZero(radius))
        return rays;

    // Calculate two orthogonal vectors (nX, nY) on the plane perpendicular to the direction of the ray
    Vector nX = direction.createNormal(); // Create a normal vector to the direction of the ray
    Vector nY = direction.crossProduct(nX); // Create another vector orthogonal to both the direction and nX

    // Calculate the center of the circle at the specified distance along the ray from its head
    Point centerCircle = this.getPoint(distance);

    Point randomPoint; // Placeholder for points on the circle
    Vector v12; // Placeholder for the direction vector from the ray's head to a point on the circle

    double rand_x, rand_y; // Random x and y coordinates on the circle
    double delta_radius = radius / (numOfRays - 1); // Decrement of radius for each additional ray
    double nv = n.dotProduct(direction); // Dot product of the normal vector and the ray's direction

    // Generate additional rays within the beam
    for (int i = 1; i < numOfRays; i++) {
        randomPoint = centerCircle; // Start at the center of the circle

        // Generate random x and y coordinates within the circle
        rand_x = random(-radius, radius);
        rand_y = randomSign() * Math.sqrt(radius * radius - rand_x * rand_x); // Ensure the point lies within the circle

        // Offset the center point by the random x and y coordinates to get a point on the circle
        try {
            randomPoint = randomPoint.add(nX.scale(rand_x));
        } catch (Exception ex) {
        }

        try {
            randomPoint = randomPoint.add(nY.scale(rand_y));
        } catch (Exception ex) {
        }

        // Calculate the direction vector from the ray's head to the random point on the circle
        v12 = randomPoint.subtract(head).normalize();

        // Calculate the dot product of the normal vector and this new direction vector
        double nt = alignZero(n.dotProduct(v12));

        // Add the new ray to the list if it's in the same general direction as the original ray
        if (nv * nt > 0) {
            rays.add(new Ray(head, v12));
        }

        // Decrease the radius for the next iteration to distribute rays evenly across the beam
        radius -= delta_radius;
    }

    return rays; // Return the list of rays forming the beam
}
}
