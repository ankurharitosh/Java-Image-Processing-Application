import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.colortransform.Greyscale;
import model.colortransform.ImageLinearColorTransform;
import model.colortransform.Sepia;
import model.customimage.Image;

import static org.junit.Assert.assertEquals;

/**
 * Tests the implementation of color transformation.
 */
public abstract class AbstractColorTransformTest {
  protected String imagePath;
  protected Image inputImage;
  protected ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    imagePath = getImagePath();
    fileHandler = getFileHandler();
    try {
      inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSepiaFilter() {


    ImageLinearColorTransform transformer = new Sepia();
    Image outputImage = transformer.applyColorTransform(inputImage);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] expectedSepia = new int[inputImage.getHeight()][inputImage.getWidth()][3];
    for (int j = 0; j < inputImage.getHeight(); j++) {
      for (int i = 0; i < inputImage.getWidth(); i++) {
        int r = (int) (0.393 * inputImage.getRed(j, i)
                + 0.769 * inputImage.getGreen(j, i) + 0.189 * inputImage.getBlue(j, i));
        int g = (int) (0.349 * inputImage.getRed(j, i)
                + 0.686 * inputImage.getGreen(j, i) + 0.168 * inputImage.getBlue(j, i));
        int b = (int) (0.272 * inputImage.getRed(j, i)
                + 0.534 * inputImage.getGreen(j, i) + 0.131 * inputImage.getBlue(j, i));
        expectedSepia[j][i][0] = Math.min(255, r);
        expectedSepia[j][i][1] = Math.min(255, g);
        expectedSepia[j][i][2] = Math.min(255, b);
        assertEquals(outputMatrix[j][i][0], expectedSepia[j][i][0], 10);
        assertEquals(outputMatrix[j][i][1], expectedSepia[j][i][1], 10);
        assertEquals(outputMatrix[j][i][2], expectedSepia[j][i][2], 10);
      }
    }
  }

  @Test
  public void testGrayscaleFilter() {
    ImageLinearColorTransform transformer = new Greyscale();
    Image outputImage = transformer.applyColorTransform(inputImage);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] expectedGrayscale = new int[inputImage.getHeight()][inputImage.getWidth()][3];
    for (int j = 0; j < inputImage.getHeight(); j++) {
      for (int i = 0; i < inputImage.getWidth(); i++) {
        int r = (int) (0.2126 * inputImage.getRed(j, i)
                + 0.7152 * inputImage.getGreen(j, i) + 0.0722 * inputImage.getBlue(j, i));
        int g = r;
        int b = r;
        expectedGrayscale[j][i][0] = Math.min(255, r);
        expectedGrayscale[j][i][1] = Math.min(255, g);
        expectedGrayscale[j][i][2] = Math.min(255, b);
        assertEquals(outputMatrix[j][i][0], expectedGrayscale[j][i][0], 10);
        assertEquals(outputMatrix[j][i][1], expectedGrayscale[j][i][1], 10);
        assertEquals(outputMatrix[j][i][2], expectedGrayscale[j][i][2], 10);
      }
    }
  }

  protected abstract String getImagePath();

  protected abstract ImageFileHandler getFileHandler();

  /**
   * colortransform.JPGColorTransformTest class is specifically for testing color transformation
   * on JPG images.
   */
  public static class JPGColorTransformTest extends AbstractColorTransformTest {
    @Override
    protected String getImagePath() {
      return "res/train.jpg";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new JPEGFileHandler();
    }
  }

  /**
   * colortransform.PNGColorTransformTest class is specifically for testing color transformation
   * on PNG images.
   */
  public static class PNGColorTransformTest extends AbstractColorTransformTest {

    @Override
    protected String getImagePath() {
      return "res/manhattan-small.png";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PNGFileHandler();
    }
  }

  /**
   * colortransform.PPMColorTransformTest class is specifically for testing color transformation
   * on PPM images.
   */
  public static class PPMColorTransformTest extends AbstractColorTransformTest {
    @Override
    protected String getImagePath() {
      return "res/Koala.ppm";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PPMFileHandler();
    }
  }


}
