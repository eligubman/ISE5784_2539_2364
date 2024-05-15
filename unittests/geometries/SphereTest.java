package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
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


    /**
     * Test method for {@link geometries.Sphere#findIntsersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(1d,new Point(1, 0, 0) );
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntsersections(new Ray(p01, v310)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntsersections(new Ray(new Point(0.5, 0, 0), v110));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(new Point(1, 0.5, 0), result2, "Ray starts inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(2, 0, 0), v110)), "Ray starts after sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        final var result3 = sphere.findIntsersections(new Ray(gp2, v110));
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(new Point(0.9999999999999989, 0.9999999999999999, 0), result3,
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(gp2, v310)), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntsersections(new Ray(p01, v110.scale(-1)));
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result4,
                "Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        final var result5 = sphere.findIntsersections(new Ray(gp1, v110));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(new Point(0.0651530771650466, 0.355051025721682, 0), result5,
                "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 points)
        final var result6 = sphere.findIntsersections(new Ray(new Point(0.5, 0, 0), v110));
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(new Point(1.866025403, 0.5, 0), result6, "Ray starts inside");

        // TC16: Ray starts at the center (1 points)
        final var result7 = sphere.findIntsersections(new Ray(new Point(1, 0, 0), v110));
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(new Point(2, 1, 0), result7, "Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntsersections(new Ray(gp1, v310)), "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(new Point(2, 0, 0), v110)), "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(1, -1, 0))),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntsersections(new Ray(gp1, new Vector(1, -1, 0))), "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntsersections(new Ray(new Point(2, 0, 0), new Vector(1, -1, 0))),
                "Ray starts after the tangent point");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(0, 1, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }
}
