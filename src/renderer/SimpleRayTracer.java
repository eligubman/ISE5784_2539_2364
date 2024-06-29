package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * The {@code SimpleRayTracer} class extends {@code RayTracerBase} to provide a simple ray tracing functionality
 * for rendering 3D scenes. It implements the basic ray tracing algorithm to calculate the color of pixels in a scene
 * by simulating the way light interacts with objects. This class handles tracing rays from the camera into the scene,
 * finding intersections with objects, and calculating the color of the intersection points considering various
 * lighting effects.
 *
 * <p>Ray tracing is performed in several steps:
 * <ol>
 *     <li>For each pixel in the image, a ray is cast from the camera into the scene to find the closest object
 *     that intersects with the ray.</li>
 *     <li>If an intersection is found, the color at the intersection point is calculated based on the material
 *     properties of the object, the lighting in the scene, and the camera's perspective.</li>
 *     <li>The color calculation includes both local and global lighting effects. Local effects include
 *     diffuse and specular reflections based on the Phong reflection model. Global effects include reflections
 *     and refractions, calculated recursively to simulate light bouncing and passing through objects.</li>
 *     <li>The calculated color is then used to set the pixel's color in the rendered image.</li>
 * </ol>
 * </p>
 *
 * <p>This class also includes methods for calculating the effects of different light sources on the color
 * of an intersection point, handling shadows, and simulating reflection and refraction effects for transparent
 * and reflective materials.</p>
 *
 * <p>Usage of this class requires setting up a {@code Scene} with geometries, light sources, and a camera.
 * The {@code renderImage} method of the camera can then be called to render the scene using this ray tracer.</p>
 *
 * @see renderer.RayTracerBase
 * @see scene.Scene
 * @see primitives.Ray
 * @see geometries.Intersectable.GeoPoint
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;

    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var point = this.findClosestIntersection(ray);
        if (point == null) {
            return scene.background;
        }
        return calcColor(point, ray);
    }

    /**
 * The method operates by first adding the intensity of the ambient light in the scene to the point's color. Ambient
 * light is considered as a uniform light source that affects all objects equally, providing a base level of illumination.
 * After considering the ambient light, the method then recursively calculates the color contribution from other light
 * sources and interactions in the scene, such as reflections and refractions, by calling another overloaded version of
 * the `calcColor` method. This recursive approach allows for the simulation of complex lighting effects like color
 * bleeding, shadows, and the glossy or matte finish of surfaces.
 *
 * @param geoPoint The point on the geometry within the scene that is being analyzed. This parameter represents a
 *                 specific location on an object where a ray from the camera intersects the object.
 * @param ray      The ray that has been cast from the camera and intersects with an object at the geoPoint. This ray
 *                 is used to determine the direction of light and its interactions at the point of intersection.
 * @return         The calculated color at the intersection point, which is a combination of the ambient light and
 *                 the recursive analysis of other lighting effects. This color is then used to set the pixel's color
 *                 in the rendered image, contributing to the overall realism of the scene.
 */
private Color calcColor(GeoPoint geoPoint, Ray ray) {
    return scene.ambientLight.getIntensity().add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
}

    /**
 * Calculates the color of a point in the scene by considering both local and global lighting effects. This method is a key component of the ray tracing algorithm,
 * which aims to simulate realistic lighting by accounting for how light interacts with surfaces at a given point. The method uses recursion to accurately simulate
 * the effects of light bouncing off multiple surfaces (global effects) and combines this with direct illumination calculations (local effects) to determine the final color of the point.
 *
 * @param gp    The intersection point on a geometry in the scene, encapsulated as a {@link GeoPoint}. This includes both the point of intersection and the geometry object that was intersected.
 * @param ray   The ray that was cast from the camera and intersected the geometry. This ray's direction is used in calculating how light interacts with the surface at the intersection point.
 * @param level The current level of recursion for calculating global lighting effects. This is used to limit the depth of recursive calls to prevent infinite recursion. Each reflection or refraction decreases the level by one until it reaches 1, at which point no further global effects are calculated.
 * @param k     A {@link Double3} value representing the attenuation factor of the color intensity due to absorption by materials as light passes through them. This is used in the recursive calculation of global effects to simulate the diminishing intensity of light as it bounces or passes through transparent materials.
 * @return      The calculated {@link Color} at the intersection point, taking into account both the local effects (such as the material's own color and how it reflects direct light) and global effects (such as reflections and refractions from other surfaces).
 */
private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
    Vector v = ray.getDirection();
    Vector n = gp.geometry.getNormal(gp.point);
    double vn = v.dotProduct(n);
    if (isZero(vn))
        return Color.BLACK;

    Color color = calcLocalEffects(gp, ray, k).add(gp.geometry.getEmission());

    return 1 == level ? color : color.add(calcGlobalEffects(gp, v, level, k));
}


    /**
 * Calculates the global lighting effects on a point in the scene, considering both reflection and refraction.
 * This method is essential for adding realism to the rendered image by simulating how light interacts with materials
 * at a microscopic level, beyond the direct illumination and shadow effects.
 *
 * @param gp    The intersection point on a geometry in the scene, encapsulated as a {@link GeoPoint}. This includes
 *              both the point of intersection and the geometry object that was intersected. It is the starting point
 *              for calculating the reflected and refracted rays.
 * @param v     The direction vector of the incoming ray that hit the geometry. This vector is crucial for calculating
 *              the directions of the reflected and refracted rays based on the laws of physics.
 * @param level The recursion level for the calculation. This parameter is used to limit the depth of recursive
 *              reflection and refraction calculations to prevent infinite loops. Each time a global effect is
 *              calculated, the level is decremented, and the calculation stops when it reaches 0.
 * @param k     The attenuation factor, represented as a {@link Double3}, which reduces the intensity of the color
 *              as the light passes through transparent materials or reflects off surfaces. This factor is applied
 *              recursively to simulate the diminishing intensity of light with each reflection or refraction.
 * @return      The color contribution from global lighting effects at the given point, including both reflection
 *              and refraction components. This color is then combined with the local lighting effects to determine
 *              the final color of the pixel in the rendered image.
 */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kr;
        Double3 kkr = k.product(kr);
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        Ray reflectedRay = constructReflectedRay(gp.point, v, n, vn);
        Ray refractedRay = constructRefractedRay(gp.point, v, n);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(reflectedRay, level - 1, kr, kkr));
        }
        Double3 kt = material.kt;
        Double3 kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(refractedRay, level - 1, kt, kkt));
        }
        return color;
    }
/**
 * Calculates the color contribution from a ray after global effects like reflection or refraction.
 * This method is a part of the recursive algorithm for ray tracing, handling the global lighting effects
 * by considering the interactions of rays with objects in the scene. It accounts for the attenuation of light
 * as it passes through or reflects off surfaces, contributing to the realism of the rendered image.
 *
 * @param ray The ray being traced through the scene. This could be a reflected or refracted ray originating
 *            from previous interactions with objects.
 * @param level The current recursion depth. The method uses this to limit the recursion and prevent infinite loops.
 *              Each recursive call for global effects decreases this level by one until it reaches zero.
 * @param k The cumulative attenuation factor up to this point. Represents the product of all previous attenuation
 *          factors (due to reflections and refractions) applied to the ray.
 * @param kx The attenuation factor for the current interaction. This is specific to the material properties of the
 *           object that the ray interacts with at this point in the scene.
 * @return The color contribution from the ray at this recursion level, considering the global effects. If the ray
 *         does not intersect with any object, or if the cumulative attenuation factor is below a certain threshold,
 *         the method returns black, indicating no significant contribution to the scene's color at this point.
 */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background.scale(kx) :
               isZero( gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection()) ) ? Color.BLACK
                        : calcColor(gp, ray, level - 1, kkx).scale(k));
    }


    /**
 * Calculates the transparency level of a point in the scene with respect to a light source. This method is used to determine
 * the amount of light that reaches a point after passing through transparent objects. It is essential for simulating shadows
 * and light transmission through materials like glass or water.
 *
 * The method operates by casting a ray from the point of interest towards the light source. It then checks for intersections
 * with other geometries along this path. For each intersected object, the method multiplies the current transparency level
 * (ktr) by the transparency coefficient (kt) of the material of the intersected object. This process simulates the cumulative
 * effect of light being partially absorbed or scattered by each transparent object it passes through.
 *
 * If the cumulative transparency (ktr) falls below a certain threshold (MIN_CALC_COLOR_K), indicating that very little light
 * reaches the point, the method returns zero transparency, effectively casting a shadow. Otherwise, it returns the calculated
 * transparency level, which is used to scale the intensity of the light reaching the point.
 *
 * @param gp The intersection point on a geometry in the scene, encapsulated as a {@link GeoPoint}. This point is the target
 *           for which the transparency is being calculated.
 * @param light The light source towards which the transparency is calculated. This parameter is used to determine the direction
 *              and distance of the ray cast towards the light source.
 * @param l The direction vector from the light source to the point. This vector is used to calculate the direction of the ray
 *          cast towards the light source.
 * @param n The normal vector at the intersection point. This is used to slightly offset the starting point of the ray to avoid
 *          self-intersection due to numerical inaccuracies.
 * @return The calculated transparency level as a {@link Double3} value. This value represents the cumulative effect of light
 *         transmission through transparent materials and is used to scale the intensity of light reaching the point.
 */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        Double3 ktr=new Double3(1d);
        if (intersections == null) return ktr;

        for (GeoPoint g : intersections) {
            ktr = ktr.product(g.geometry.getMaterial().kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;

    }

    /**
 * Constructs a refracted ray based on Snell's Law, given the intersection point, the incident ray's direction, and the normal at the point.
 * This method is crucial for simulating the bending of light as it passes from one medium into another,
 * which is essential for rendering realistic transparent materials.
 *
 * The refracted ray is calculated assuming the incident ray enters a new medium at the intersection point.
 * The direction of the refracted ray depends on the angle of incidence, the normal at the intersection point,
 * and the indices of refraction for the two media (not directly provided to this method but assumed to be incorporated into the vectors).
 *
 * @param pointGeo The point of intersection on the geometry where the refraction occurs. This is the starting point of the refracted ray.
 * @param v The direction vector of the incident ray. This vector should ideally be normalized to ensure accurate calculations.
 * @param n The normal vector at the point of intersection. It is crucial for calculating the angle of refraction. This vector should also be normalized.
 * @return A new Ray object representing the refracted ray, which starts at the intersection point and proceeds in the calculated direction of refraction.
 */
private Ray constructRefractedRay(Point pointGeo, Vector v, Vector n) {
    return new Ray(pointGeo, v, n);
}

    /**
 * Constructs a reflected ray from a given point on a geometry, considering the incident ray's direction and the surface normal at that point.
 * This method is essential for simulating the reflection of light off surfaces, which is a key aspect of rendering realistic images in ray tracing.
 *
 * Reflection is calculated using the formula r = v - 2 * (v . n) * n, where:
 * - r is the direction of the reflected ray,
 * - v is the direction vector of the incident ray,
 * - n is the normal vector at the point of intersection, and
 * - (v . n) is the dot product of v and n.
 *
 * The reflected ray is constructed with a slight offset along the normal to prevent self-intersection, which can cause rendering artifacts.
 *
 * @param pointGeo The point of intersection on the geometry where the reflection occurs. This is the starting point of the reflected ray.
 * @param v The direction vector of the incident ray. This vector should ideally be normalized to ensure accurate calculations.
 * @param n The normal vector at the point of intersection. It is crucial for calculating the direction of the reflected ray. This vector should also be normalized.
 * @param vn The dot product of the incident ray's direction vector and the normal vector at the point of intersection. This value is pre-calculated to optimize the reflection calculation.
 * @return A new Ray object representing the reflected ray, which starts at the intersection point and proceeds in the calculated direction of reflection.
 */
private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n, double vn) {
    // 𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
    Vector r = v.subtract(n.scale(2 * vn));
    return new Ray(pointGeo, r, n);
}

    /**
 * Calculates the effect of different light sources on a point in the scene according to the Phong reflection model.
 * This method is pivotal for simulating realistic lighting by considering how light from various sources affects the color
 * and brightness of a point on a geometry. It incorporates both the diffuse and specular components of light reflection,
 * which are essential for achieving a sense of depth and material properties in the rendered scene.
 *
 * The Phong model divides the light reflection into two main components:
 * 1. Diffuse reflection, which scatters light in all directions. This is determined by the angle between the light source
 *    and the normal to the surface at the point of intersection. It simulates the roughness of the surface and is independent
 *    of the viewer's position.
 * 2. Specular reflection, which reflects light in a specific direction. This depends on the position of the viewer and simulates
 *    the shiny spots on surfaces where the light source is directly reflected to the viewer's eye.
 *
 * This method iterates over all light sources in the scene, calculating their contributions to the point's color based on
 * their intensity, distance, and angle of incidence. It also considers the material properties of the geometry, such as
 * its shininess and reflection coefficients, to accurately simulate how the surface interacts with the light.
 *
 * Additionally, the method checks for shadows by determining if any other geometries in the scene block the light from reaching
 * the point. This is done by casting shadow rays towards each light source and checking for intersections with other objects.
 *
 * @param intersection The point on the geometry within the scene that is being illuminated. This parameter encapsulates both
 *                     the location of the point and the geometry it belongs to, allowing for the calculation of normal vectors
 *                     and other surface properties.
 * @param ray The ray from the camera to the intersection point. This is used to calculate specular reflection, as it represents
 *            the viewer's perspective.
 * @return The color of the point as affected by the local light sources, taking into account both diffuse and specular reflections,
 *         as well as shadowing effects. This color is a combination of the contributions from all light sources in the scene.
 */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 kx) {
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kd;
        Double3 ks = intersection.geometry.getMaterial().ks;
        Color color = Color.BLACK;
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(ray.getDirection()));

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {//&&unshaded(intersection, l, n, lightSource, nl))
                Double3 ktr = transparency(intersection, lightSource, l, n);
                if (!ktr.product(kx).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffuse(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, ray.getDirection(), nShininess, lightIntensity));
                }


            }
        }
        return color;

    }

    /**
 * Calculates the diffuse component of light reflection based on the Lambertian reflectance model. This model is essential
 * for simulating the way light is scattered in all directions uniformly from a rough surface. The diffuse reflection
 * contributes to the perceived color and intensity of objects under direct and indirect lighting conditions.
 *
 * The amount of light reflected is directly proportional to the cosine of the angle between the light source direction
 * vector and the normal vector at the point of intersection. This relationship ensures that surfaces facing directly
 * towards the light source receive the maximum illumination, while those angled away receive less, simulating a realistic
 * shading effect.
 *
 * @param kd The diffuse reflection coefficient, representing the ratio of reflected light to incident light. This parameter
 *           is a material property that affects how bright the surface appears under direct illumination.
 * @param nl The dot product between the normal vector at the point of intersection and the light direction vector. This
 *           value is used to calculate the angle of incidence to determine the proportion of light that is diffusely reflected.
 * @param lightIntensity The intensity of the light source at the point of intersection. This value is scaled by the diffuse
 *                       reflection coefficient and the cosine of the angle of incidence to compute the final color contribution
 *                       from diffuse reflection.
 * @return The color contribution from the diffuse reflection component, which is added to the total color calculated for the
 *         point on the surface. This contribution is crucial for achieving a natural appearance of materials under various
 *         lighting conditions.
 */
    private Color calcDiffuse(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(nl<0?-nl:nl));
    }

    /**
     * Calculates the specular component of light reflection based on the Phong reflection model. This model is crucial
     * for simulating the shiny highlights observed on smooth surfaces when illuminated by a light source. The specular
     * reflection contributes to the perceived glossiness and shininess of materials.
     *
     * The intensity of the specular reflection is determined by the angle between the viewer's line of sight and the
     * direction of the reflected light. It is influenced by the material's shininess coefficient, which controls the size and intensity of the specular highlight.
     *
     * @param ks The specular reflection coefficient, a material property that determines the intensity of the specular reflection. Higher values result in more pronounced specular highlights.
     * @param l The direction vector from the light source to the point on the surface. This vector is used to calculate the direction of the reflected light.
     * @param n The normal vector at the point on the surface. It is perpendicular to the surface and is used to calculate the reflection direction.
     * @param nl The dot product between the normal vector and the light direction vector. This value is used in the calculation of the reflection direction.
     * @param v The direction vector from the point on the surface to the viewer. This vector is used to determine the angle between the viewer and the reflected light direction.
     * @param nShininess The shininess coefficient of the material. This value controls the fall-off of the specular highlight, with higher values resulting in smaller, more intense highlights.
     * @param lightIntensity The intensity of the light source at the point of intersection. This value is modulated by the specular reflection calculation to determine the final color contribution of the specular highlight.
     * @return The color contribution from the specular reflection component. This is calculated as the light intensity
     * scaled by the specular reflection coefficient and raised to the power of the shininess coefficient, based on the angle between the reflected light direction and the viewer's direction.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
                               Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // View from direction opposite to r vector
        }
        return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }

    /**
 * Determines if a point in the scene is unshaded by checking if any geometries between the point and a light source block the light.
 * This method is crucial for accurately simulating shadows in the rendered scene. It casts a ray from the point of interest towards
 * the light source and checks for intersections with scene geometries along this path. If an intersection is found with a geometry
 * that is not transparent (kt = 0), the method concludes that the point is in shadow (shaded) and returns false. Otherwise, it returns
 * true, indicating the point is unshaded and directly illuminated by the light source.
 *
 * @param gp The intersection point on a geometry in the scene, encapsulated as a {@link GeoPoint}. This point is the target
 *           for which the shading is being calculated.
 * @param l The direction vector from the point to the light source. This vector is used to calculate the direction of the ray
 *          cast towards the light source.
 * @param n The normal vector at the intersection point. This is used to slightly offset the starting point of the ray to avoid
 *          self-intersection due to numerical inaccuracies.
 * @param lightSource The light source for which the shading is being checked. This parameter is used to determine the distance
 *                    of the light source from the point, limiting the ray's intersection checks to this distance.
 * @param nl The dot product between the normal vector at the point of intersection and the light direction vector. This value
 *           is not directly used in this method but is included for consistency with other lighting calculation methods.
 * @return True if the point is unshaded (directly illuminated by the light source), false if it is in shadow (blocked by another geometry).
 */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nl) {
        Vector lightDirection = l.scale(-1);//from the point to light score
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> Intersection = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        if (Intersection == null) return true;

        for (GeoPoint g : Intersection)
            if (g.geometry.getMaterial().kt == Double3.ZERO)

                return false;
        return true;
    }

    /**
 * Finds the closest intersection point between a given ray and any geometry in the scene.
 * This method is crucial for ray tracing, as it determines the first object that a ray intersects with from the camera's perspective.
 * This information is used to calculate the color of pixels by simulating the interaction of light with objects in the scene.
 *
 * The method operates by querying the scene's geometries for any intersections with the provided ray. Among all potential intersection points,
 * it identifies the one closest to the ray's origin. This closest point is critical for accurately rendering the scene, as it represents the first
 * object visible along the ray's path, thereby determining which object's color and material properties should be used in the color calculation for the corresponding pixel.
 *
 * @param ray The ray for which the closest intersection point is to be found. The ray originates from the camera and passes through a specific pixel on the view plane.
 * @return The closest {@link GeoPoint} representing the intersection point and the geometry it belongs to. If the ray does not intersect with any geometry in the scene, returns {@code null}.
 */
private GeoPoint findClosestIntersection(Ray ray) {
    return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
}


}