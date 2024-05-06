package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    private Point center;

    Sphere(double radius){
        super(radius);
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
