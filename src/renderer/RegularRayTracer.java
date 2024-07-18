package renderer;

import java.util.List;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.*;

/**
 * a class that represents a ray tracer that traces a grid of voxels(greatly
 * improves performance)
 *
 * @autor Yahel and Ashi
 */
public class RegularRayTracer extends SimpleRayTracer {

    private Grid grid;

    /**
     * constructor for the ray tracer grid
     *
     * @param scene
     * @param density
     */
    public RegularRayTracer(Scene scene, int density) {
        super(scene);
        grid = new Grid(scene.geometries, density);
    }

    @Override
    public Color traceRay(Ray ray) {
        return super.traceRay(ray);
    }

    @Override
    protected GeoPoint findClosestIntersection(Ray ray) {
        Geometries outerGeometries = grid.getOuterGeometries();
        GeoPoint closestPoint = null;
        if (!outerGeometries.getGeometries().isEmpty())
            closestPoint = ray.findClosestGeoPoint(outerGeometries.findGeoIntersections(ray));
        double result = grid.cutsGrid(ray);
        if (result == Double.POSITIVE_INFINITY)
            return null;

        double distanceToOuterGeometries = closestPoint == null ? Double.POSITIVE_INFINITY
                : closestPoint.point.distance(ray.getHead());
        if (distanceToOuterGeometries < result)
            return closestPoint;

        var intersections = grid.traverse(ray, false);
        GeoPoint point = ray.findClosestGeoPoint(intersections);
        if (point != null) {
            return point.point.distance(ray.getHead()) < distanceToOuterGeometries ? point : closestPoint;
        }
        return closestPoint;
    }

    @Override
    protected boolean unshaded(GeoPoint gp, Vector l, Vector n,LightSource light, double nl) {
        Vector lightDir = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDir, n);
        List<GeoPoint> intersections;
        double res = grid.cutsGrid(lightRay);
        if (res == Double.POSITIVE_INFINITY)
            intersections = grid.getOuterGeometries().findGeoIntersections(lightRay);
        else {
            intersections = grid.traverse(lightRay, true);
        }
        if (intersections == null)
            return true;
        for (GeoPoint p : intersections)
            if (!p.geometry.getMaterial().kt.equals(Double3.ZERO))
                return false;
        return true;
    }

    @Override
    protected Double3 transparency(GeoPoint gp,LightSource light, Vector l, Vector n ) {
        Vector lightDir = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDir, n);
        List<GeoPoint> intersections;
        double res = grid.cutsGrid(lightRay);
        if (res == Double.POSITIVE_INFINITY)
            intersections = grid.getOuterGeometries().findGeoIntersections(lightRay);
        else {
            intersections = grid.traverse(lightRay, true);
        }
        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;
        for (GeoPoint p : intersections) {
            ktr = ktr.product(p.geometry.getMaterial().kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }
}