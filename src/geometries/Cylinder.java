package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube{
    double heighe;

    Cylinder(double radius){
        super(radius);
    }

    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}
