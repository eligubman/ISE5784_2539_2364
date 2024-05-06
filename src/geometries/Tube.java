package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{
    public Ray radius;

    Tube(double radius){
        super(radius);
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
