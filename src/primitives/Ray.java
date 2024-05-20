package primitives;

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

}
