import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.customimage.Image;
import model.filter.BlurImage;
import model.filter.ImageFilters;
import model.filter.SharpenImage;

import static org.junit.Assert.assertEquals;

/**
 * This abstract test class serves as the base for testing image filter classes.
 */
public abstract class AbstractFilterTest {
  protected Image inputImage;
  protected String imagePath;
  protected ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    fileHandler = getFileHandler();
    imagePath = getPath();
    try {
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testBlurFilter() {
    ImageFilters filter = new BlurImage();
    Image outputImage = filter.applyFilter(inputImage);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] expectedBlur = new int[inputImage.getHeight()][inputImage.getWidth()][3];
    double[][] kernel = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    for (int j = 0; j < inputImage.getHeight(); j++) {
      for (int i = 0; i < inputImage.getWidth(); i++) {
        double rSum = 0.0;
        double gSum = 0.0;
        double bSum = 0.0;

        for (int k = -1; k <= 1; k++) {
          for (int l = -1; l <= 1; l++) {
            int x = i + k;
            int y = j + l;

            if (x >= 0 && x < inputImage.getWidth() && y >= 0 && y < inputImage.getHeight()) {
              rSum += inputImage.getRed(y, x) * kernel[k + 1][l + 1];
              gSum += inputImage.getGreen(y, x) * kernel[k + 1][l + 1];
              bSum += inputImage.getBlue(y, x) * kernel[k + 1][l + 1];
            }
          }
        }

        expectedBlur[j][i][0] = (int) rSum;
        expectedBlur[j][i][1] = (int) gSum;
        expectedBlur[j][i][2] = (int) bSum;

        assertEquals(outputMatrix[j][i][0], expectedBlur[j][i][0], 5);
        assertEquals(outputMatrix[j][i][1], expectedBlur[j][i][1], 5);
        assertEquals(outputMatrix[j][i][2], expectedBlur[j][i][2], 5);
      }
    }
  }


  @Test
  public void testSharpnessFilter() {
    ImageFilters filter = new SharpenImage();
    Image outputImage = filter.applyFilter(inputImage);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] inputMatrix = inputImage.getPixelData();
    int x = 123;
    int y = 123;

    // Sharpening kernel
    double[][] kernel = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    // Manually calculating the sharpened pixel values
    double[] sharpened = {0, 0, 0};

    sharpened[0] = inputMatrix[y - 2][x - 2][0] * kernel[0][0] + inputMatrix[y - 2][x - 1][0]
            * kernel[0][1] + inputMatrix[y - 2][x][0] * kernel[0][2] + inputMatrix[y - 2][x + 1][0]
            * kernel[0][3] + inputMatrix[y - 2][x + 2][0] * kernel[0][4]
            + inputMatrix[y - 1][x - 2][0] * kernel[1][0] + inputMatrix[y - 1][x - 1][0]
            * kernel[1][1] + inputMatrix[y - 1][x][0] * kernel[1][2] + inputMatrix[y - 1][x + 1][0]
            * kernel[1][3] + inputMatrix[y - 1][x + 2][0] * kernel[1][4]
            + inputMatrix[y][x - 2][0] * kernel[2][0] + inputMatrix[y][x - 1][0]
            * kernel[2][1] + inputMatrix[y][x][0] * kernel[2][2] + inputMatrix[y][x + 1][0]
            * kernel[2][3] + inputMatrix[y][x + 2][0] * kernel[2][4]
            + inputMatrix[y + 1][x - 2][0] * kernel[3][0] + inputMatrix[y + 1][x - 1][0]
            * kernel[3][1] + inputMatrix[y + 1][x][0] * kernel[3][2] + inputMatrix[y + 1][x + 1][0]
            * kernel[3][3] + inputMatrix[y + 1][x + 2][0] * kernel[3][4]
            + inputMatrix[y + 2][x - 2][0] * kernel[4][0] + inputMatrix[y + 2][x - 1][0]
            * kernel[4][1] + inputMatrix[y + 2][x][0] * kernel[4][2] + inputMatrix[y + 2][x + 1][0]
            * kernel[4][3] + inputMatrix[y + 2][x + 2][0] * kernel[4][4];

    sharpened[1] = inputMatrix[y - 2][x - 2][1] * kernel[0][0] + inputMatrix[y - 2][x - 1][1]
            * kernel[0][1] + inputMatrix[y - 2][x][1] * kernel[0][2] + inputMatrix[y - 2][x + 1][1]
            * kernel[0][3] + inputMatrix[y - 2][x + 2][1] * kernel[0][4]
            + inputMatrix[y - 1][x - 2][1] * kernel[1][0] + inputMatrix[y - 1][x - 1][1]
            * kernel[1][1] + inputMatrix[y - 1][x][1] * kernel[1][2] + inputMatrix[y - 1][x + 1][1]
            * kernel[1][3] + inputMatrix[y - 1][x + 2][1] * kernel[1][4]
            + inputMatrix[y][x - 2][1] * kernel[2][0] + inputMatrix[y][x - 1][1]
            * kernel[2][1] + inputMatrix[y][x][1] * kernel[2][2] + inputMatrix[y][x + 1][1]
            * kernel[2][3] + inputMatrix[y][x + 2][1] * kernel[2][4]
            + inputMatrix[y + 1][x - 2][1] * kernel[3][0] + inputMatrix[y + 1][x - 1][1]
            * kernel[3][1] + inputMatrix[y + 1][x][1] * kernel[3][2] + inputMatrix[y + 1][x + 1][1]
            * kernel[3][3] + inputMatrix[y + 1][x + 2][1] * kernel[3][4]
            + inputMatrix[y + 2][x - 2][1] * kernel[4][0] + inputMatrix[y + 2][x - 1][1]
            * kernel[4][1] + inputMatrix[y + 2][x][1] * kernel[4][2] + inputMatrix[y + 2][x + 1][1]
            * kernel[4][3] + inputMatrix[y + 2][x + 2][1] * kernel[4][4];

    sharpened[2] = inputMatrix[y - 2][x - 2][2] * kernel[0][0] + inputMatrix[y - 2][x - 1][2]
            * kernel[0][1] + inputMatrix[y - 2][x][2] * kernel[0][2] + inputMatrix[y - 2][x + 1][2]
            * kernel[0][3] + inputMatrix[y - 2][x + 2][2] * kernel[0][4]
            + inputMatrix[y - 1][x - 2][2] * kernel[1][0] + inputMatrix[y - 1][x - 1][2]
            * kernel[1][1] + inputMatrix[y - 1][x][2] * kernel[1][2] + inputMatrix[y - 1][x + 1][2]
            * kernel[1][3] + inputMatrix[y - 1][x + 2][2] * kernel[1][4]
            + inputMatrix[y][x - 2][2] * kernel[2][0] + inputMatrix[y][x - 1][2]
            * kernel[2][1] + inputMatrix[y][x][2] * kernel[2][2] + inputMatrix[y][x + 1][2]
            * kernel[2][3] + inputMatrix[y][x + 2][2] * kernel[2][4]
            + inputMatrix[y + 1][x - 2][2] * kernel[3][0] + inputMatrix[y + 1][x - 1][2]
            * kernel[3][1] + inputMatrix[y + 1][x][2] * kernel[3][2] + inputMatrix[y + 1][x + 1][2]
            * kernel[3][3] + inputMatrix[y + 1][x + 2][2] * kernel[3][4]
            + inputMatrix[y + 2][x - 2][2] * kernel[4][0] + inputMatrix[y + 2][x - 1][2]
            * kernel[4][1] + inputMatrix[y + 2][x][2] * kernel[4][2] + inputMatrix[y + 2][x + 1][2]
            * kernel[4][3] + inputMatrix[y + 2][x + 2][2] * kernel[4][4];

    // Now, sharpened[] contains the sharpened pixel values for the specific pixel [123][123]
    int expectedRed = (int) sharpened[0];
    int expectedGreen = (int) sharpened[1];
    int expectedBlue = (int) sharpened[2];

    assertEquals(expectedRed, outputMatrix[y][x][0]);
    assertEquals(expectedGreen, outputMatrix[y][x][1]);
    assertEquals(expectedBlue, outputMatrix[y][x][2]);
  }

  public abstract String getPath();

  public abstract ImageFileHandler getFileHandler();

  /**
   * filter.JPGFilterTest class is specifically for testing filters on JPG images.
   */
  public static class JPGFilterTest extends AbstractFilterTest {
    @Override
    public String getPath() {
      return "res/train.jpg";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new JPEGFileHandler();
    }
  }

  /**
   * filter.PNGFilterTest class is specifically for testing filters on PNG images.
   */
  public static class PNGFilterTest extends AbstractFilterTest {

    @Override
    public String getPath() {
      return "res/manhattan-small.png";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new PNGFileHandler();
    }
  }

  /**
   * filter.PPMFilterTest class is specifically for testing filters on PPM images.
   */
  public static class PPMFilterTest extends AbstractFilterTest {

    @Override
    public String getPath() {
      return "res/Koala.ppm";
    }

    @Override
    public ImageFileHandler getFileHandler() {
      return new PPMFileHandler();
    }
  }
}
