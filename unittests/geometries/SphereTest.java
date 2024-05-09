package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Sphere
 */
class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal to a point on the sphere
        Sphere s = new Sphere(1, new Point(3,4,5));
        assertEquals(new Vector(1, 0, 0), s.getNormal(new Point(4,4,5)),
                "Wrong normal to a point on the sphere"
        );
    }
}