package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testShepes(){
        Geometries Geomet=new Geometries();

        assertNull(Geomet.findIntsersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "empty list");

        Geomet.add(new Plane(new Point(0,0,1),new Point(0,1,0),new Point(1,0,0)));
        Geomet.add(new Cylinder(1,new Ray(new Point(1,1,1),new Vector(1,1,1)),2));
        Geomet.add(new Sphere(1,new Point(1,0,0)));
        Geomet.add(new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1)));
        Geomet.add(new Tube(1,new Ray(new Point(0,0,1),new Vector(0,0,1))));

        assertNull(Geomet.findIntsersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "Ray not Intsersections whit no one");

        assertEquals(1,Geomet.findIntsersections(new Ray(new Point(0,0,0),new Vector(0,0,1))).size(),"one intsersections");

        assertEquals(3,Geomet.findIntsersections(new Ray(new Point(0,0,0),new Vector(1,1,1))).size(),"more then one intsersections");

        assertEquals(4,Geomet.findIntsersections(new Ray(new Point(-2,-2,-2),new Vector(1,1,1))).size(),"all intsersections");
    }
}