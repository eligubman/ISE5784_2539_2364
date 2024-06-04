package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * SimpleRayTracer class represents a simple ray tracer

 */
public class SimpleRayTracer extends RayTracerBase {
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint>points=scene.geometries.findGeoIntersectionsHelper(ray);
        if (points==null){
            return scene.background;
        }
       GeoPoint  closestPoint=ray.findClosestGeoPoint(points);
        return calcColor(closestPoint);
    }

    /**
     * Calculate the color intensity in a point
     * @param geoPoint the point to calculate the color intensity
     * @return the color intensity in the point
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());
    }
}