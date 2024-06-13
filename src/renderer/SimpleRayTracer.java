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
 * SimpleRayTracer class represents a simple ray tracer
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;

    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersectionsHelper(ray);
        if (points == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(points);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of a point in the scene.
     *
     * @param geoPoint The point on the geometry in the scene.
     * @param ray The ray from the camera to the intersection.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
    }

    /**
     * get point in scene and calculate its color
     *
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        if (isZero(vn))
            return Color.BLACK;

        Color color = calcLocalEffects(gp,ray).add(gp.geometry.getEmission());

        return 1 == level ? color : color.add(calcGlobalEffects(gp,v,level,k));
    }


    /**
     * Calculate global effect reflected and refracted
     *
     * @param gp
     * @param v
     * @param level
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp.point, v),level, material.kr, k).add(calcGlobalEffect(constructReflectedRay(gp.point, v),level, material.kt, k));}

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background :calcColor(gp, ray, level-1, kkx)).scale(kx);
    }


    /**
     * get light and gp and move ao all the objects between them and calculate the
     * transparency
     *
     * @param gp
     * @param light
     * @param l
     * @param n
     * @return
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        Double3 ktr = new Double3(1d);
        if (intersections == null)
            return ktr;

        for (GeoPoint p : intersections) {
            ktr = ktr.product(p.geometry.getMaterial().kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }

        return ktr;
    }

    /**
     * Calculate refracted ray
     *
     * @param pointGeo
     * @param v
     * @return
     */
    private Ray constructReflectedRay(Point pointGeo, Vector v) {
        return new Ray(pointGeo, v);
    }

    /**
     * Calculate Reflected ray
     *
     * @param pointGeo
     * @param v
     * @param n
     * @param vn
     * @return
     */
    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n, double vn) {
        // 𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r);
    }

    /**
     * Calculates the effect of different light sources on a point in the scene
     * according to the Phong model.
     *
     * @param intersection The point on the geometry in the scene.
     * @param ray The ray from the camera to the intersection.
     * @return The color of the point affected by local light sources.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        int nShininess = intersection.geometry.getMaterial().nShininess;

        Double3 kd = intersection.geometry.getMaterial().kd, ks = intersection.geometry.getMaterial().ks;

        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if ((nl * nv > 0)&&(unshaded(intersection,l,n,lightSource,nl))) { // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffuse(kd, nl, lightIntensity),
                        calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse component of light reflection.
     *
     * @param kd The diffuse reflection coefficient.
     * @param nl The dot product between the normal vector and the light
     * vector.
     * @param lightIntensity The intensity of the light source.
     * @return The color contribution from the diffuse reflection.
     */
    private Color calcDiffuse(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * Calculates the specular component of light reflection.
     *
     * @param ks The specular reflection coefficient.
     * @param l The light vector.
     * @param n The normal vector.
     * @param nl The dot product between the normal vector and the light
     * vector.
     * @param v The view vector.
     * @param nShininess The shininess coefficient.
     * @param lightIntensity The intensity of the light source.
     * @return The color contribution from the specular reflection.
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

    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nl){
        Vector lightDirection = l.scale(-1);//from the point to light score

        Vector epsVector = n.scale(nl<0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);

        Ray ray = new Ray(point,lightDirection);

        List<GeoPoint> Intersection = scene.geometries.findGeoIntersections(ray);
        if(Intersection == null) return true;

        for (GeoPoint g : Intersection)
            if (g.geometry.getMaterial().kt == Double3.ZERO)
                return false;
        return true;
    }

    /**
     * get ray and return the closet intersection geoPoint
     *
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * get list of ray
     *
     * @param rays
     * @param level
     * @param kkx
     * @return average color of the intersection of the rays
     */
    Color calcAverageColor(List<Ray> rays, int level, Double3 kkx) {
        Color color = Color.BLACK;

        for (Ray ray : rays) {
            GeoPoint intersection = findClosestIntersection(ray);
            color = color.add(intersection == null ? scene.background : calcColor(intersection, ray, level - 1, kkx));
        }

        return color.reduce(rays.size());
    }

}