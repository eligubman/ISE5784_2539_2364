package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import java.util.List;

/**
 * Testing Plane
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    void testConstructor(){
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test constructor with a correct plane
        Plane p = new Plane(new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1)
        );
        assertEquals(new Vector(1, 1, 1).normalize(), p.getNormal(new Point(0, 0, 0)),
                "Wrong plane normal");
        // =============== Boundary Values Tests ==================
        // TC02: Test constructor with the same point
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0),
                new Point(1, 0, 0),
                new Point(1, 0, 0)
        ), "Constructed a plane with the same point");

        // TC03: Test constructor with the 2 same points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 0, 1)
        ), "Constructed a plane with the same point");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test getNormal with a correct plane
        Plane p = new Plane(new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1)
        );
        assertEquals(new Vector(1, 1, 1).normalize(), p.getNormal(new Point(1, 0, 0)),
                "Wrong plane normal");

    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections(){
        Plane p =new Plane(new Point(1, 1, 1),new Vector(1,1,1));
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============

        //TC01 start not in the plane and Intsersect the plane
        result = p.findIntersections(new Ray(new Point(0, 1, 1), new Vector(1, 0, 1)));
        assertEquals(result, List.of(new Point(0.5, 1, 1.5)), "Error start not in the plane and Intsersect the plane");

        //TC02 start not in the plane and not Intsersect the plane
        result = p.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "start not in the plane and not Intsersect the plane");

        // =============== Boundary Values Tests ==================

        //TC03 on the plane and not Intsersect the plane (paralel)
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(0, -1, 1)));
        assertNull(result, "on the plane and not Intsersect the plane (paralel)");

        //TC04 not on the plane and not Intsersect the plane (paralel(up))
        result = p.findIntersections(new Ray(new Point(5, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "not on the plane and not Intsersect the plane (paralel(up))");

        //TC05 not on the plane and not Intsersect the plane (paralel(down))
        result = p.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "not on the plane and not Intsersect the plane (paralel(down))");

        //TC06 perpendicular to the plane (before)
        result = p.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1)));
        assertEquals(result, List.of(new Point(1, 1, 1)), "perpendicular to the plane (before)");

        //TC07 perpendicular to the plane (on)
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 1, 1)));
        assertNull(result, "perpendicular to the plane (on)");

        //TC08 perpendicular to the plane (after)
        result = p.findIntersections(new Ray(new Point(2, 2, 2), new Vector(1, 1, 1)));
        assertNull(result, "perpendicular to the plane (after)");

        //TC09 starts on the normal but the ray not on the plane
        result = p.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)));
        assertNull(result, "starts on the normal but the ray not on the plane");

        //TC10 starts on the normal and the ray on the plane
        result = p.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "starts on the normal and the ray on the plane");

        //TC11 starts on the normal and the ray not on the plane
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0)));
        assertNull(result, "starts on the normal and the ray not on the plane");
    }
}