package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * unit test for primitives.Point class
 */
class PointTest {

    /**
     * Test method for {@link primitives.Point#add(Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Add a vector to the point
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(2, 3, 4);
        assertEquals(new Point(3, 5, 7), p.add(v),
                "Wrong result of adding a vector to the point"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Add a vector to the opposite vector
        Vector vOpposite = new Vector(-2, -3, -4);
       assertThrows(IllegalArgumentException.class,
                () -> v .add(vOpposite),
                "Adding a vector to the opposite vector should throw an exception"
        );

    }
    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Subtract a point from the point
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 3, 4);
        assertEquals(new Vector(1, 1, 1), p2.subtract(p1),
                "Wrong result of subtracting a point from the point"
        );
        // =============== Boundary Values Tests ==================

        // TC02: Subtract a point from the same point
        Point pSame = new Point(1, 2, 3);
        assertThrows(IllegalArgumentException.class,
                () -> pSame.subtract(pSame),
                "Subtracting a point from the same point should throw an exception"
        );
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test distance squared between two points
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        assertEquals(14, p1.distanceSquared(p2),
                "Wrong squared distance between two points"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Test distance squared between a point and itself
        assertEquals(0, p1.distanceSquared(p1),0.00001,
                "Wrong squared distance between a point and itself"
        );
    }
    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test distance between two points
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        assertEquals(Math.sqrt(14), p1.distance(p2),
                0.00001,
                "Wrong distance between two points"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Test distance between a point and itself
        assertEquals(0, p1.distance(p1),0.00001,
                "Wrong distance between a point and itself"
        );
    }
}