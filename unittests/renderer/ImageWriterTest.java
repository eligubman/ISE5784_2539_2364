package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter Class
 */
public class ImageWriterTest {

    /**
     * Test for creating an image with a grid pattern using the ImageWriter class.
     */
    @Test
    void testImageWriter() {
        // Create an ImageWriter object with the specified image name and dimensions
        ImageWriter image = new ImageWriter("initial image", 800, 500);

        // Loop through each pixel in the image
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                // Create a red grid line every 50 pixels
                if (i % 50 == 0 || j % 50 == 0) {
                    image.writePixel(j, i, new Color(255, 0, 0)); // Red color for the grid lines
                } else {
                    image.writePixel(j, i, new Color(255, 225, 0)); // Yellow color for the rest of the pixels
                }
            }
        }
        // Write the image to a file
        image.writeToImage();
    }
}
