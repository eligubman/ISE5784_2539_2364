package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Geometries class
 
 */
class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections(){
        Geometries Geomet=new Geometries();


        Geomet.add(new Plane(new Point(0,0,1),new Point(0,1,0),new Point(1,0,0)));
        Geomet.add(new Cylinder(1,new Ray(new Point(1,1,1),new Vector(1,1,1)),2));
        Geomet.add(new Sphere(1,new Point(1,0,0)));
        Geomet.add(new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1)));
        Geomet.add(new Tube(1,new Ray(new Point(0,0,1),new Vector(0,0,1))));
        // ============ Equivalence Partitions Tests ==============
        // TC01: some of the geometries intersect with the ray
        assertEquals(3,Geomet.findIntersections(new Ray(new Point(0,0,0),new Vector(1,1,1))).size(),"more then one intsersections");

        // =============== Boundary Values Tests ==================
        // TC02: no geometry intersect with the ray
        assertNull(Geomet.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "Ray does not intersect with any geometry");

        // TC03: only one geometry intersect with the ray
        assertEquals(1,Geomet.findIntersections(new Ray(new Point(0,0,0),new Vector(0,0,1))).size(),"one intsersections");

        // TC04: all geometries intersect with the ray
        assertEquals(4,Geomet.findIntersections(new Ray(new Point(-2,-2,-2),new Vector(1,1,1))).size(),"all intsersections");

        // TC05: empty list
        assertNull(Geomet.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "empty list");

    }
}