package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
class CylinderTest {

    @Test
    void testGetNormal() {
        // Equivalence Partitions Tests
        // TC01: Test normal to a point on the round surface
        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(1, 0, 1)),
                "Wrong normal to a point on the round surface");

        // TC02: Test normal to a point on the bottom base
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 0, 0.5)),
                "Wrong normal to a point on the bottom base");

        // TC03: Test normal to a point on the top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 1.5)),
                "Wrong normal to a point on the top base");

        // Boundary Values Tests
        // TC04: Test normal to a point in the center of the bottom base
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 0, 0)),
                "Wrong normal to a point in the center of the bottom base");

        // TC05: Test normal to a point in the center of the top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 2)),
                "Wrong normal to a point in the center of the top base");
    }
}