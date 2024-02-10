import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.customimage.Image;
import model.processor.flip.HorizontalFlip;
import model.processor.flip.ImageFlip;
import model.processor.flip.VerticalFlip;

import static org.junit.Assert.assertEquals;

/**
 * Tests the implementation of image flipping logic.
 */
public abstract class AbstractFlipTest {
  protected Image inputImage;
  protected String imagePath;
  protected ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    fileHandler = getFileHandler();
    imagePath = getImagePath();
    try {
      inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testVerticalFlip() {
    ImageFlip flipper = new VerticalFlip();
    Image flippedImage = flipper.flipImage(inputImage);
    assertEquals(inputImage.getHeight(), flippedImage.getHeight());
    assertEquals(inputImage.getWidth(), flippedImage.getWidth());
    int[][][] inputMatrix = inputImage.getPixelData();
    int[][][] outputMatrix = flippedImage.getPixelData();
    for (int j = 0; j < 10; j++) {
      for (int i = 0; i < 10; i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(inputMatrix[j][i][color],
                  outputMatrix[flippedImage.getHeight() - 1 - j][i][color]);
        }
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    ImageFlip flipper = new HorizontalFlip();
    Image flippedImage = flipper.flipImage(inputImage);
    assertEquals(inputImage.getHeight(), flippedImage.getHeight());
    assertEquals(inputImage.getWidth(), flippedImage.getWidth());
    int[][][] inputMatrix = inputImage.getPixelData();
    int[][][] outputMatrix = flippedImage.getPixelData();
    for (int j = 0; j < 10; j++) {
      for (int i = 0; i < 10; i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(inputMatrix[j][i][color],
                  outputMatrix[j][flippedImage.getWidth() - 1 - i][color]);
        }
      }
    }
  }

  public abstract String getImagePath();

  public abstract ImageFileHandler getFileHandler();

  /**
   * flip.JPGFlipTest class is specifically for testing flipping operations on JPG images.
   */
  public static class JPGFlipTest extends AbstractFlipTest {

    @Override
    public String getImagePath() {
      return "res/train.jpg";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new JPEGFileHandler();
    }
  }

  /**
   * flip.PNGFlipTest class is specifically for testing flipping operations on PNG images.
   */
  public static class PNGFlipTest extends AbstractFlipTest {
    @Override
    public String getImagePath() {
      return "res/manhattan-small.png";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new PNGFileHandler();
    }
  }


  /**
   * flip.PPMFlipTest class is specifically for testing flipping operations on PPM images.
   */
  public static class PPMFlipTest extends AbstractFlipTest {
    @Override
    public String getImagePath() {
      return "res/Koala.ppm";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new PPMFileHandler();
    }
  }
}
