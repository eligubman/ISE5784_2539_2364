package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter Class
 */
public class ImageWriterTest {

    @Test
    void testImageWriter(){
        ImageWriter image=new ImageWriter("initial image",800,500);

for(int i=0;i<500;i++){
            for(int j=0;j<800;j++){
                if(i%50==0||j%50==0){
                    image.writePixel(j, i, new Color(255,0,0));
                }
                else{
                    image.writePixel(j, i, new Color(255, 225, 0));
                }
            }
        }
            image.writeToImage();
    }
}
