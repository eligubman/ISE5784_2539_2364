package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane {
    private final Point q;
    private final Vector normal;

    Plane(Point p1,Point p2,Point p3){
        this.normal=null;
        this.q=p1;
    }

    Plane(Point p,Vector normal){
        this.normal=normal.normalize();
        this.q=p;
    }

    public Vector getNormal() {
        return normal;
    }

    public Vector getNormal(Point p){
        return normal;
    }
}
