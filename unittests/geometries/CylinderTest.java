package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
class CylinderTest {

    @Test
    void testGetNormal() {
        Cylinder c = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test normal to a point on the round surface
        Vector normalRoundSurface = c.getNormal(new Point(1, 0, 0));
        assertEquals(new Vector(1, 0, 0), normalRoundSurface,
                "TC01: Wrong normal to a point on the round surface");

        // TC02: Test normal to a point on the bottom base
        Vector normalBottomBase = c.getNormal(new Point(0, 0, -1));
        assertEquals(new Vector(0, 0, -1), normalBottomBase,
                "TC02: Wrong normal to a point on the bottom base");

        // TC03: Test normal to a point on the top base
        Vector normalTopBase = c.getNormal(new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), normalTopBase,
                "TC03: Wrong normal to a point on the top base");

        // =============== Boundary Values Tests ==================

        // TC04: Test normal to a point on the base edge (bottom)
        Vector normalBaseEdgeBottom = c.getNormal(new Point(0, 1, -1));
        assertTrue(normalBaseEdgeBottom.equals(new Vector(0, 1, 0)) ||
                        normalBaseEdgeBottom.equals(new Vector(0, -1, 0)),
                "TC04: Wrong normal to a point on the base edge (bottom)");

        // TC05: Test normal to a point on the base edge (top)
        Vector normalBaseEdgeTop = c.getNormal(new Point(0, 1, 1));
        assertTrue(normalBaseEdgeTop.equals(new Vector(0, 1, 0)) ||
                        normalBaseEdgeTop.equals(new Vector(0, -1, 0)),
                "TC05: Wrong normal to a point on the base edge (top)");

        // TC06: Test normal to the center of the bottom base
        Vector normalCenterBottomBase = c.getNormal(new Point(0, 0, -1));
        assertEquals(new Vector(0, 0, -1), normalCenterBottomBase,
                "TC06: Wrong normal to the center of the bottom base");

        // TC07: Test normal to the center of the top base
        Vector normalCenterTopBase = c.getNormal(new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), normalCenterTopBase,
                "TC07: Wrong normal to the center of the top base");
    }
  }