package UnitTest.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Vector;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link ImageWriter} class.
 */
public class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}.
     */

    @Test
    void TestWriteToImage() {

        ImageWriter imageWriter=new ImageWriter("TestTurquoise",800,500);

        for (int i=0;i<800;i++)
        {
            for (int j=0;j<500;j++)
            {
                // 800/16=50, 500/10=50
                if(i%50==0 || j%50==0){
                    imageWriter.writePixel(i,j, Color.BLACK);
                }

                else {
                    imageWriter.writePixel(i,j, new Color(20,200,200));
                }

            }
        }
        imageWriter.writeToImage();
    }

}