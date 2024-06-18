/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Geometries;
import geometries.Plane;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)//1000,1000??
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1.0)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)//10000,10000
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)//1000,1000
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void newSc6ene() {
        Scene scene = new Scene("New Scene")
                .setBackground(new Color(128, 128, 0));
        Material material = new Material().setKd(0.4).setKs(1).setShininess(50).setKt(0.0d).setKr(0.5).setKs(0.5);

        double radius = 50;
        double height = Math.sqrt(3) * radius;

        // Add spheres with different colors and positions
        scene.geometries.add(
                new Sphere(35, new Point(-200, 50, 200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(0.1).setKr(0.9).setShininess(20)).setEmission(new Color(250, 128, 114)),
                new Sphere(35, new Point(-100, 50, 200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(0.1).setKr(0.9).setShininess(20)).setEmission(new Color(PINK)),
                new Sphere(35, new Point(0, -75, 200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(0.1).setKr(0.9).setShininess(20)).setEmission(new Color(BLUE)),
                new Sphere(35, new Point(100, 50, 200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(0.1).setKr(0.9).setShininess(20)).setEmission(new Color(64, 224, 208)),
                new Sphere(35, new Point(200, 50, 200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(0.1).setKr(0.9).setShininess(20)).setEmission(new Color(255, 215, 0))
        );

        //pyramids
        Point A1 = new Point(-280, 0, 50);
        Point C1 = new Point(-350, 200, 300);
        Point D1 = new Point(-150, 150, 50);
        Point E1 = new Point(-500, 150, 50);
        Point F1 = new Point(-350, 300, 50);

        Point A2 = new Point(280, 0, 50);
        Point C2 = new Point(350, 200, 300);
        Point D2 = new Point(150, 150, 50);
        Point E2 = new Point(500, 150, 50);
        Point F2 = new Point(350, 300, 50);

        Point A3 = new Point(0, 0, 150);
        Point C3 = new Point(0, 200, 300);
        Point D3 = new Point(-150, 150, 50);
        Point E3 = new Point(170, 150, 50);
        Point F3 = new Point(0, 300, 50);

        //left pyramid
        scene.geometries.add(

                new Triangle(A1, E1, C1).setMaterial(material).setEmission(new Color(0, 0, 128)),
                new Triangle(A1, D1, C1).setMaterial(material).setEmission(new Color(64, 224, 208)),
                new Triangle(F1, E1, C1).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F1, D1, C1).setMaterial(material).setEmission(new Color(64, 224, 208))
        );
        //right pyramid
        scene.geometries.add(
                new Triangle(A2, E2, C2).setMaterial(material).setEmission(new Color(135, 206, 235)),
                new Triangle(A2, D2, C2).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F2, E2, C2).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F2, D2, C2).setMaterial(material).setEmission(new Color(0, 235, 0))
        );
        //front pyramid
        scene.geometries.add(
                new Triangle(A3, E3, C3).setMaterial(material).setEmission(new Color(135, 206, 235)),
                new Triangle(A3, D3, C3).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F3, E3, C3).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F3, D3, C3).setMaterial(material).setEmission(new Color(0, 235, 0))
        );

        Material floorMaterial = new Material().setKd(0.2).setShininess(50).setKt(0.65).setKr(0.8).setKs(0.4);
        scene.geometries.add(
                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
                        .setMaterial(floorMaterial).setEmission(new Color(0, 0, 128))
        );

        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 200, -200)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 200)));

        cameraBuilder.setLocation(new Point(0, -600, 0)).setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1)).
                setRayTracer(new SimpleRayTracer(scene)).setVpSize(150, 150).setVpDistance(100)
                .setImageWriter(new ImageWriter("take104", 1000, 1000)).build().renderImage().writeToImage();

    }

    @Test
    public void myShape4() {

        final Camera.Builder bonuscameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(1, 0, 0), new Vector(0, 0, 1));

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255).reduce(6), new Double3(0.15)));
        scene.lights.add(new SpotLight(new Color(RED), new Point(-300, 6, 10), new Vector(1, 0, 0)));

        double angle = 0;
        double height = 0;

        scene.geometries.add(new Plane(new Point(-4, 4, 0), new Vector(0, 0, 1))
                .setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(100).setKt(0.7).setKr(0.5)));

        java.awt.Color[] colors = { YELLOW, RED, ORANGE, BLUE };

        for (int i = 25; i < 200; ++i) {
            int colorIndex = i % colors.length;

            scene.geometries
                    .add(new Sphere(0.5, new Point(i / 25.0 * Math.cos(angle), i / 25.0 * Math.sin(angle), height))
                            .setEmission(new Color(colors[colorIndex]).reduce(2))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

            angle += Math.PI / 15.0;
            height += 0.15;
        }

        java.awt.Color[] colors2 = { BLUE, GREEN, PINK, BLACK, RED, GRAY };

        height = 10;
        for (int i = 25; i < 100; ++i) {
            int colorIndex = i % colors2.length;

            scene.geometries.add(new Sphere(0.3, new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height))
                    .setEmission(new Color(colors2[colorIndex]).reduce(2))
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

            angle += Math.PI / 30.0;
            height += 0.5;
        }

        height = 10;
        for (int i = 25; i < 300; ++i) {

            scene.geometries.add(new Sphere(0.05, new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height))
                    .setEmission(new Color(WHITE))
                    .setMaterial(new Material().setKd(1).setKs(1d).setShininess(100).setKt(1d)));

            angle += Math.PI / 60.0;
            height += 0.1 % 50;
        }

        scene.lights.add(new SpotLight(new Color(255, 255, 255).reduce(2), new Point(-150, 0, 5), new Vector(1, 0, 0)));
        scene.lights.add(new SpotLight(new Color(GREEN).reduce(2), new Point(50, 0, 5), new Vector(1, 0, 0)));

        scene.setBackground(new Color(BLUE).reduce(TRANSLUCENT));

        ImageWriter imageWriter = new ImageWriter("myShape4", 500, 500);

        bonuscameraBuilder.setLocation(new Point(-330, 0, 5))
                .setVpDistance(1000d)
                .setVpSize(200, 200)
                .setRayTracer(new SimpleRayTracer(scene))
                .setImageWriter(imageWriter)
                .build()
                .renderImage()
                .writeToImage();
    }


}
