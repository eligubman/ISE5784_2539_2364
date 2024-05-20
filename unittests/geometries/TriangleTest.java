package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Triangles
 */
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

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections(){
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the triangle (1 point)
        assertEquals(List.of(new Point(1d/3, 1d/3, 1d/3)),
                triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1))),
                "Ray intersects the triangle"
        );
        // TC02: outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1))),
                "Ray outside against edge"
        );
        // TC03: outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, -1, 0))),
                "Ray outside against vertex"
        );
        // =============== Boundary Values Tests ==================
        // TC04: Ray intersects the triangle on edge
        assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1))),
                "Ray intersects the triangle on edge"
        );
        // TC05: Ray intersects the triangle on vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
                "Ray intersects the triangle on vertex"
        );
        // TC06: Ray intersects the triangle on edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
                "Ray intersects the triangle on edge's continuation"
        );


    }

}