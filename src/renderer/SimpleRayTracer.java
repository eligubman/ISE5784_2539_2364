package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

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

    private Color calcColor(Point point) {
        return scene.background;
    }
}