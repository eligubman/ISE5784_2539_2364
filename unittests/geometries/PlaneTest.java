package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

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


}