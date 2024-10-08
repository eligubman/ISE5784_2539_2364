package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate
 * system
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * constructor for Cylinder class
     *
     * @param radius radius of the cylinder
     * @param axis axis of the cylinder
     * @param height height of the cylinder
     */
    public Cylinder(Ray axis,double radius,  double height) {
        super(radius, axis);
        this.height = height;
        this.boundary=calcBoundary();
    }

    @Override
    public Vector getNormal(Point pnt) {
        //the points to represent each base center point
        Point base1Point = axis.getHead();
        Vector dir = axis.getDirection();
        double t;
        try {
            t = pnt.subtract(base1Point).dotProduct(dir);
        } catch (IllegalArgumentException ignore) {
            t = 0;
        }
        if (isZero(t) || isZero(t - height)) return dir;

        return super.getNormal(pnt);
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector dir = axis.getDirection();
        Point baseCenter = axis.getHead();
        Point secondBaseCenter = axis.getPoint(height);
        Plane base = new Plane(baseCenter, dir);
        Plane secondBase = new Plane(secondBaseCenter, dir);
        double rdSqr = radius * radius;
        List<GeoPoint> temp;

        boolean cond = ray.getDirection().equals(dir) || ray.getDirection().scale(-1).equals(dir);

        temp = base.findGeoIntersections(ray, maxDistance);
        GeoPoint gp1 = temp == null || onBase(temp.get(0), baseCenter, rdSqr, cond) ? null : new GeoPoint(this, temp.get(0).point);

        temp = secondBase.findGeoIntersections(ray, maxDistance);
        GeoPoint gp2 = temp == null || onBase(temp.get(0), secondBaseCenter, rdSqr, cond) ? null : new GeoPoint(this, temp.get(0).point);

        if (gp1 != null && gp2 != null) {
            if (ray.getHead().distanceSquared(gp1.point) < ray.getHead().distanceSquared(gp2.point)) {
                return List.of(gp1, gp2);
            } else {
                return List.of(gp2, gp1);
            }
        }

        GeoPoint gpBase = gp1 != null ? gp1 : gp2;
        temp = super.findGeoIntersectionsHelper(ray, maxDistance);

        //No intersections with the casing
        if (temp == null) return gpBase == null ? null : List.of(gpBase);
        //Two intersections with the casing
        if (temp.size() == 2) {
            //Between the planes
            boolean cond1 = onCylinder(temp.get(0), baseCenter, secondBaseCenter, dir);
            boolean cond2 = onCylinder(temp.get(1), baseCenter, secondBaseCenter, dir);
            if (cond1 && cond2) return temp;
            if (!cond1 && !cond2) return null;
            if (cond1) return List.of(temp.get(0), gpBase);
            if (cond2) return List.of(gpBase, temp.get(1));
        }

        if (!(onCylinder(temp.get(0), baseCenter, secondBaseCenter, dir))) {//לשים לב שזה ממש בתוך
            return gpBase == null ? null : List.of(gpBase);
        } else if (gpBase == null) {
            return temp;
        }
        double d1 = temp.get(0).point.distanceSquared(ray.getHead());
        double d2 = gpBase.point.distanceSquared(ray.getHead());

        return d1 < d2 ? List.of(temp.get(0), gpBase) : List.of(gpBase, temp.get(0));
    }

    private boolean onCylinder(GeoPoint geoPoint, Point b1, Point b2, Vector dir) {
        return geoPoint.point.subtract(b1).dotProduct(dir) > 0 &&
                geoPoint.point.subtract(b2).dotProduct(dir.scale(-1)) > 0;
    }

    private boolean onBase(GeoPoint check, Point center, double rdSqr, boolean cond) {
        return (check.point.distanceSquared(center) == rdSqr && cond) ||
                (check.point.distanceSquared(center) > rdSqr);
    }


    @Override
    public int[][] calcBoundary() {
        Point firstBaseCenter = axis.getHead();
        Point secondBaseCenter = axis.getPoint(height);

        return new int[][]{minMax(firstBaseCenter.getX(),secondBaseCenter.getX()),
                minMax(firstBaseCenter.getY(), secondBaseCenter.getY()),
                minMax(firstBaseCenter.getZ(), secondBaseCenter.getZ())};
    }

    private int[] minMax(double first,double second){
        return first < second? new int[]{(int)Math.floor(first-radius),(int)Math.ceil(second+radius)}:
                new int[]{(int)Math.floor(second-radius),(int)Math.ceil(first+radius)};
    }

}
