package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @org.junit.jupiter.api.Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct triangle
        assertDoesNotThrow(() -> new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Failed constructing a correct triangle"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Triangle with all points on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 2, 0)),
                "Triangle with all points on the same line should throw an exception"
        );
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal to a point on the triangle
        Triangle t = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        assertEquals(new Vector(1, 1, 1).normalize(), t.getNormal(new Point(0, 0, 0)),
                "Wrong normal to a point on the triangle"
        );
    }

}