package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint(){
        Ray ray = new Ray(new Point(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),
                new Vector(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getPoint with a correct ray negative value
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"negative value");

        // TC02: Test getPoint with a correct ray positive value
        assertEquals(new Point(1,1,1),ray.getPoint(Math.sqrt(3)-1),"positeve value");

        // =============== Boundary Values Tests ==================

        // TC03: Test getPoint with a correct ray zero value
        assertEquals(new Point(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),ray.getPoint(0),"zero value");
    }

    @Test
    void testFindClosestPoint(){

        Ray ray = new Ray(new Point(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),
                new Vector(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)));

        // ============ Equivalence Partitions Tests ==============

        //TC01: the closest point is in the middle of the list
        assertEquals(new Point(1,1,1),ray.findClosestPoint(List.of(new Point(4,5,6),new Point(1,1,1),new Point(0,0,0))),"closest point is the middle point");

        // =============== Boundary Values Tests ==================

        //TC02: the list is empty
        assertNull(ray.findClosestPoint(null), "closest point is null");

        //TC03: the point is the first point in the list
        assertEquals(new Point(1,1,1),ray.findClosestPoint(List.of(new Point(1,1,1),
                new Point(4,5,6),new Point(0,0,0))),"closest point is the first point");

        //TC04: the point is the last point in the list
        assertEquals(new Point(1,1,1),
                ray.findClosestPoint(List.of(new Point(4,5,6),new Point(0,0,0),new Point(1,1,1))),
                "closest point is the last point");


    }

}