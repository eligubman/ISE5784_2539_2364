package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * TubeTest is a class for testing
    */
class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal to a point on the tube
     Tube t = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
         assertEquals(new Vector(0,0,1), t.getNormal(new Point(1,0,1)),
                 "Wrong normal to a point on the tube"
        );
        // =============== Boundary Values Tests ==================
        // TC02: Test normal to a point on the tube where the vector from the point to the origin is orthogonal to the axis
        assertEquals(new Vector(0,1,0), t.getNormal(new Point(0,1,0)),
                    "Wrong normal to a point on the tube"
            );
    }
}