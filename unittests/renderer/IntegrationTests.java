package renderer;

import geometries.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {
private  final Camera.Builder builder=Camera.getBuilder()
        .setLocation(Point.ZERO)
        .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
        .setVpDistance(10);
private Camera camera;


    /**
     * sum of intersections between ray cast from a camera and shape
     * @param camera the camera
     * @param shape the shape we want to find intersections with
     * @return the amount of intersections
     */
    int intersectionsSum(Camera camera, Geometry shape){
        int count=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Ray ray = camera.constructRay(3, 3, i, j);
                List<Point>points= shape.findIntersections(ray);
                if(points!=null){
                    count+=points.size();
                }
            }
        }
        return count;

    }

    @Test
    void sphereTest(){
         camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setVpDistance(1)
                .build();
        Sphere sphere=new Sphere(1, new Point(0,0,-3));
        //TC01: sphere is in front of the view plane 2 intersections
        assertEquals(2,intersectionsSum(camera,sphere),"sphere is in front of the view plane");

        //TC02: sphere is in front of all the view plane 18 intersections
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setLocation(new Point(0,0,0.5))
                .setVpDistance(1)
                .build();
        sphere=new Sphere(2.5, new Point(0,0,-2.5));
        assertEquals(18,intersectionsSum(camera,sphere),"camera ray starts at the sphere and intersect it");

        //TC03: sphere is in front of part of camera 10 intersections
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setLocation(new Point(0,0,0.5))
                .setVpDistance(1)
                .build();
        sphere=new Sphere(2, new Point(0,0,-2));
        assertEquals(10,intersectionsSum(camera,sphere),"camera ray starts at the sphere and intersect it");

        //TC04: the view plane is inside the sphere 9 intersections
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setLocation(new Point(0,0,1))
                .setVpDistance(1)
                .build();
        sphere=new Sphere(4, Point.ZERO);
        assertEquals(9,intersectionsSum(camera,sphere),"the view plane is inside the sphere");

        //TC05: the sphere is behind the camera 0 intersections
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setLocation(new Point(0,0,0.5))
                .setVpDistance(1)
                .build();
        sphere=new Sphere(0.5, new Point(0,0,1));
        assertEquals(0,intersectionsSum(camera,sphere),"the sphere is behind the camera");

    }

    @Test
    void planeTest(){
    //TC01 the plane is in front the view plane 9 intersections
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setVpDistance(1)
                .setLocation(Point.ZERO)
                .build();
        Plane plane=new Plane(new Point(0,0,-2),new Vector(0,0,1));
        assertEquals(9,intersectionsSum(camera,plane),"the plane is in front the view plane");

        //TC02 the plane is in front of the view panel diagonally 9 intersections
         plane=new Plane(new Point(1,2,-3.5),
                 new Point(1,3,-3),
                 new Point(4,2,-3.5));
         assertEquals(9,intersectionsSum(camera,plane),"the plane is in front of the view panel diagonally");

         //TC03 the plane is in front of the view panel diagonally 6 intersections
        plane=new Plane(new Point(1,2,-3.5),
                new Point(1,3,-2.5),
                new Point(4,2,-3.5));
            assertEquals(6,intersectionsSum(camera,plane),"the plane is in front of the view panel diagonally");

    }

    @Test
    void TriangleTest(){
        //TC01 the triangle is in front of the view panel 1 intersection
        camera=builder.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3,3)
                .setVpDistance(1)
                .setLocation(Point.ZERO)
                .build();
        Triangle triangle=new Triangle(new Point(-1,-1,-2),
                new Point(1,-1,-2),
                new Point(0,1,-2));
        assertEquals(1,intersectionsSum(camera,triangle),"the triangle is in front of the view panel");

        //TC02 the triangle is in front of the view panel 2 intersections
        triangle=new Triangle(new Point(-1,-1,-2),
                new Point(1,-1,-2),
                new Point(0,20,-2));
        assertEquals(2,intersectionsSum(camera,triangle),"the triangle is in front of the view panel");
    }
}
