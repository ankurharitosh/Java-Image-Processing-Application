import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.customimage.Image;
import model.processor.brightness.Brighten;
import model.processor.brightness.Darken;
import model.processor.brightness.ImageBrightness;

import static org.junit.Assert.assertEquals;

/**
 * AbstractBrighteningTest abstract test class serves as the base for testing brightness adjustment.
 */
public abstract class AbstractBrighteningTest {
  protected Image inputImage;
  protected String path;
  protected ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    path = getPath();
    fileHandler = getFileHandler();
    try {
      inputImage = fileHandler.loadImage(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testBrighten() {
    ImageBrightness imageBrightness = new Brighten();
    Image outputImage = imageBrightness.adjustImage(inputImage, 10);
    int[][][] inputMatrix = inputImage.getPixelData();
    int[][][] outputMatrix = outputImage.getPixelData();
    for (int j = 0; j < 10; j++) {
      for (int i = 0; i < 10; i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(Math.min(inputMatrix[j][i][color] + 10, 255),
                  outputMatrix[j][i][color]);
        }
      }
    }
  }

  @Test
  public void testDarken() {
    ImageBrightness imageBrightness = new Darken();
    Image outputImage = imageBrightness.adjustImage(inputImage, 10);
    int[][][] inputMatrix = inputImage.getPixelData();
    int[][][] outputMatrix = outputImage.getPixelData();
    for (int j = 0; j < 10; j++) {
      for (int i = 0; i < 10; i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(Math.max(inputMatrix[j][i][color] - 10, 0),
                  outputMatrix[j][i][color]);
        }
      }
    }
  }

  protected abstract String getPath();

  protected abstract ImageFileHandler getFileHandler();

  /**
   * Test the logic of Brightening or darkening a jpg image.
   */
  public static class JPGBrighteningTest extends AbstractBrighteningTest {

    @Override
    protected String getPath() {
      return "res/train.jpg";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new JPEGFileHandler();
    }
  }

  /**
   * Test the logic of Brightening or darkening a png image.
   */
  public static class PNGBrighteningTest extends AbstractBrighteningTest {
    @Override
    protected String getPath() {
      return "res/manhattan-small.png";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PNGFileHandler();
    }
  }

  /**
   * Test the logic of Brightening or darkening a ppm image.
   */
  public static class PPMBrighteningTest extends AbstractBrighteningTest {

    @Override
    protected String getPath() {
      return "res/Koala.ppm";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PPMFileHandler();
    }
  }

}