package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint(){
        Ray ray = new Ray(new Point(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),new Vector(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getPoint with a correct ray negative value
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"negative value");
        // TC02: Test getPoint with a correct ray positeve value
        assertEquals(new Point(1,1,1),ray.getPoint(Math.sqrt(3)-1),"positeve value");
        // =============== Boundary Values Tests ==================
        // TC03: Test getPoint with a correct ray zero value
        assertEquals(new Point(1/Math.sqrt(3),1/Math.sqrt(3),1/Math.sqrt(3)),ray.getPoint(0),"zero value");
    }

}