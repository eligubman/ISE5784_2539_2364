package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class represents a triangle in 3D Cartesian coordinate system
 */
public class Triangle extends Plane{
    /**
     * constructor for Triangle class
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     */
    public Triangle(Point p1,Point p2,Point p3){
        super(p1,p2,p3);
    }

    @Override
    public List<Point> findIntsersections(Ray ray){
        return null;
    }
}
