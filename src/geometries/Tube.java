package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 */
public class Tube extends RadialGeometry {
    /**
     * axis of the tube
     */
    protected final Ray axis;

    /**
     * constructor for Tube class
     *
     * @param radius radius of the tube
     * @param axis axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        // the formula to calculate the normal of a point on the tube is:
        // p-o where o is the point on the axis that is closest to p
        //now let's find o
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        // if the point is the head of the axis
        if (p.equals(p0)) {
            return v.scale(-1);
        }
        Vector delta = p.subtract(p0);
        double t = v.dotProduct(delta);
        Point o;

        // if delta is orthogonal to the axis
        if (t == 0) {
            o = p0;
        }
        // if t>0, the point o is on the axis but not at the head
        else {
            o = p0.add(v.scale(t));
        }
        return p.subtract(o).normalize();
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //The overall idea is to form a quadratic equation that it's
        //solutions are the scale factor for the getPoint method.
        //We form this quadratic equation by setting two restriction on an arbitrary point:
        // 1. It is on the ray (i.e. of the form p+t*v)
        // 2. It is on the tube (i.e. it's distance from the tube axis ray is r)
        //Give those two restrictions we extract the requested quadratic equation.
        Vector tubeDir = this.axis.getDirection();
        Vector rayDir = ray.getDirection();

        // if the ray is parallel  to the tube axis ray return null
        if (tubeDir.equals(rayDir) || tubeDir.equals(rayDir.scale(-1))) {
            return null;
        }

        double dotP1 = Util.alignZero(rayDir.dotProduct(tubeDir));
        //if rayDir and tubeDir are orthogonal return just the rayDir,
        //else return their dot product.
        Vector vec1 = dotP1 == 0 ? rayDir : rayDir.subtract(tubeDir.scale(dotP1));
        double radiusSquared = this.radius * this.radius;

        //First coefficient of the quadratic equation.
        double a = Util.alignZero(vec1.lengthSquared());

        if (ray.getHead().equals(this.axis.getHead())) {
            return alignZero(radiusSquared / a - maxDistance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(radiusSquared / a))));
        }

        //The vector between the ray heads.
        Vector deltaP = ray.getHead().subtract(this.axis.getHead());

        //If the ray starts at the tube axis ray
        if (tubeDir.equals(deltaP.normalize()) || tubeDir.equals(deltaP.normalize().scale(-1))) {
            return alignZero(radiusSquared / a - maxDistance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(radiusSquared / a))));
        }

        double dotP2 = Util.alignZero(deltaP.dotProduct(tubeDir));
        var vec2 = dotP2 == 0 ? deltaP : deltaP.subtract(tubeDir.scale(dotP2));

        //Second coefficient for the quadratic equation
        double b = Util.alignZero(2 * (vec1.dotProduct(vec2)));
        //Third coefficient for the quadratic equation
        double c = Util.alignZero(vec2.lengthSquared() - radiusSquared);

        //Discriminant for the quadratic equation
        double det = Util.alignZero(b * b - 4 * a * c);

        //If the discriminant is smaller or equal to 0,
        // the ray is outside the tube.
        if (det <= 0) return null;

        //Solving the quadratic equation.
        det = Math.sqrt(det);
        double t1 = Util.alignZero((-b + det) / (2 * a));
        double t2 = Util.alignZero((-b - det) / (2 * a));

        //The intersection points are behind the head of the ray
        if (t1 <= 0 || alignZero(t2 - maxDistance) > 0) return null;
        if (t1 - maxDistance > 0) {
            return t2 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t2)));
        } else {
            //Check if there are one or two intersection points.
            return t2 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) :
                    List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
        }
    }

    @Override
    public int[][] calcBoundary() {//there is no boundary to infinite geometric entity
        return null;
    }

}
