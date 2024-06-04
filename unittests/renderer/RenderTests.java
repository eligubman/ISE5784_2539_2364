package renderer;

import static java.awt.Color.*;

import JSON.Json;
import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import scene.Scene;

/**
 * Test rendering a basic image.
 */
public class RenderTests {
   /** Scene of the tests */
   private final Scene scene = new Scene("Test scene");

   /** Camera builder of the tests */
   private final Camera.Builder camera = Camera.getBuilder()
           .setRayTracer(new SimpleRayTracer(scene))
           .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
           .setVpDistance(100)
           .setVpSize(500, 500);

   /**
    * Produce a scene with a basic 3D model and render it into a PNG image with a grid.
    */
   @Test
   public void renderTwoColorTest() {
      scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down left
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down right
      scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
              .setBackground(new Color(75, 127, 90));

      camera
              .setImageWriter(new ImageWriter("base render test", 1000, 1000))
              .build()
              .renderImage()
              .printGrid(100, new Color(YELLOW))
              .writeToImage();
   }
   /**
    * Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid
    */
   @Test
   public void renderMultiColorTest() {
      scene.geometries.add( // center
              new Sphere(50, new Point(0, 0, -100)),
              // up left
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                      .setEmission(new Color(GREEN)),
              // down left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                      .setEmission(new Color(RED)),
              // down right
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                      .setEmission(new Color(BLUE)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2, 0.2, 0.2))); //
        scene.setBackground(new Color(BLACK));
      camera
              .setImageWriter(new ImageWriter("color render test", 1000, 1000))
              .build()
              .renderImage()
              .printGrid(100, new Color(WHITE))
              .writeToImage();
   }






   /**
    * Test for JSON-based scene - for bonus.
    */
   @Test
   public void basicRenderJson() {
      // Create the scene with geometries and lights
      scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down left
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down right
      scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
              .setBackground(new Color(75, 127, 90));

      // Write the scene to a JSON file
      Json.write(scene, "SceneTests.json");

      // Read the scene from the JSON file
      Scene scen = Json.read("SceneTests.json");

      // Render the image from the loaded scene
      camera.setRayTracer(new SimpleRayTracer(scen))
              .setImageWriter(new ImageWriter("json render test", 1000, 1000))
              .build()
              .renderImage()
              .printGrid(100, new Color(YELLOW))
              .writeToImage();
   }
}
