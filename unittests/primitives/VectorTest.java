package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for primitives.Vector class
 */
class VectorTest {
    /**
     * Test method for {@link primitives.Vector#Vector}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct vector
        assertDoesNotThrow(() -> new Vector(1, 2, 3),
                "Failed constructing a correct vector"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Vector 0
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "Zero vector should throw an exception"
        );
        // TC03: Vector 0 with Double3.ZERO
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(Double3.ZERO),
                "Zero vector should throw an exception"
        );
    }

    /**
     * Test method for {@link primitives.Vector#add}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Add a vector to the vector
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(new Vector(3, 5, 7), v1.add(v2),
                "Wrong result of adding a vector to the vector"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Add a vector to the opposite vector
        Vector v1Opposite = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(v1Opposite),
                "Adding a vector to the opposite vector should throw an exception"
        );
    }
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Subtract a vector from the vector
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(new Vector(-1, -1, -1), v1.subtract(v2),
                "Wrong result of subtracting a vector from the vector"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Subtract the vector from itself
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1),
                "Subtracting the vector from itself should throw an exception"
        );
    }

    /**
     * Test method for {@link primitives.Vector#scale}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Scale the vector by a positive factor
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v1.scale(2),
                "Wrong result of scaling the vector"

        );
        // TC02: Scale the vector by a negative factor
        assertEquals(new Vector(-2, -4, -6), v1.scale(-2),
                "Wrong result of scaling the vector"
        );
        // =============== Boundary Values Tests ==================
        // TC03: Scale the vector by 0
        assertThrows(IllegalArgumentException.class,
                () -> v1.scale(0),
                "Scaling the vector by 0 should throw an exception"
        );

    }
    /**
     * Test method for {@link primitives.Vector#dotProduct}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Dot product of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(20, v1.dotProduct(v2),
                0.00001,
                "Wrong result of dot product of two vectors"
        );

    }
    /**
     * Test method for {@link primitives.Vector#crossProduct}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Cross product of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        assertEquals(new Vector(-1, 2, -1), v1.crossProduct(v2),
                "Wrong result of cross product of two vectors"
        );
        // TC02: Cross product of two parallel vectors
        Vector v3 = new Vector(2, 4, 6);
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "Cross product of parallel vectors should throw an exception"
        );
        // =============== Boundary Values Tests ==================
        // TC03: Cross product of two orthogonal vectors
        Vector v4 = v1.crossProduct(v2);
        assertEquals(0, v1.dotProduct(v4),
                0.00001,
                "Cross product of orthogonal vectors should be orthogonal to the operands"
        );
        assertEquals(0, v2.dotProduct(v4),
                0.00001,
                "Cross product of orthogonal vectors should be orthogonal to the operands"
        );

    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Calculate the squared length of the vector
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(),
                0.00001,
                "Wrong result of squared length of the vector"
        );
    }
    /**
     * Test method for {@link primitives.Vector#length}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Calculate the length of the vector
        Vector v1 = new Vector(3, 4, 0);
        assertEquals(5, v1.length(),
                0.00001,
                "Wrong result of length of the vector"
        );
    }
    /**
     * Test method for {@link primitives.Vector#normalize}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Normalize the vector
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(1 / Math.sqrt(14), 2 / Math.sqrt(14), 3 / Math.sqrt(14)), v1.normalize(),
                "Wrong result of normalizing the vector"
        );


    }
}