package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase class represents a ray tracer base

 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene){
        this.scene=scene;
    }
    /**
     * Trace a ray in the scene
     * @param ray the ray to trace
     * @return the color of the ray
     */
    public abstract Color traceRay(Ray ray);
}
