package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

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
        List<Point>points=scene.geometries.findIntersections(ray);
        if (points==null){
            return scene.background;
        }
        Point closestPoint=ray.findClosestPoint(points);
        return calcColor(closestPoint);
    }

    /**
     * Calculate the color intensity in a point
     * @param point the point to calculate the color intensity
     * @return the color intensity in the point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}