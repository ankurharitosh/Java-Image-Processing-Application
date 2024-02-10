import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import model.adjustment.LevelAdjustment;
import model.adjustment.LevelAdjustmentImpl;
import model.customimage.CustomImage;
import model.customimage.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * A test class for the LevelAdjustment implementation.
 */
public class LevelAdjustmentTest {

  private Image inputImage;
  private ImageFileHandler fileHandler;


  @Before
  public void setUp() {
    // Avoiding creation of abstract test classes so that we can limit the file submission size on
    // server
    this.fileHandler = new JPEGFileHandler();
    String imagePath = "res/train.jpg";
    try {
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testLevelAdjustment() {
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    Image outputImage = levelAdjustment.adjustImageLevels(inputImage,
            0, 128, 255);
    fileHandler = new PNGFileHandler();
    try {
      fileHandler.saveImage(outputImage, "res/levelAdjusted.png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(outputImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAdjustmentValues() {
    LevelAdjustmentImpl levelAdjustment = new LevelAdjustmentImpl();


    Image outputImage = levelAdjustment.adjustImageLevels(inputImage,
            240, 100, 200);
  }


  @Test
  public void testAdjustImageLevelsCalculation() {
    int[][][] initialArray = {
            {{100, 150, 200}, {120, 180, 220}, {80, 100, 120}},
            {{50, 70, 90}, {200, 210, 220}, {30, 40, 50}},
            {{160, 180, 200}, {90, 100, 110}, {140, 160, 180}}
    };

    Image arrayImage = new CustomImage(initialArray.length, initialArray[0].length);
    arrayImage.setAllPixels(initialArray);
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    int shadow = 50;
    int mid = 128;
    int highlight = 200;

    Image outputImage = levelAdjustment.adjustImageLevels(arrayImage, shadow, mid, highlight);

    double a = (shadow * shadow * (mid - highlight))
            - (shadow * ((mid * mid) - (highlight * highlight)))
            + (highlight * (mid * mid)) - (mid * (highlight * highlight));
    double aa = (-shadow * (128 - 255)) + (128 * highlight) - (255 * mid);
    double ab = (shadow * shadow * (128 - 255)) + (255 * mid * mid) - (128 * highlight * highlight);
    double ac = (shadow * shadow * (255 * mid - 128 * highlight)) - shadow * (255 * mid
            * mid - 128 * highlight * highlight);

    double[] coefficients = {aa / a, ab / a, ac / a};


    assertNotNull(outputImage);
    double expectedValue = coefficients[0] * initialArray[0][0][0] * initialArray[0][0][0]
            + coefficients[1] * initialArray[0][0][0] + coefficients[2];
    expectedValue = Math.max(0, Math.min(255, expectedValue));
    assertEquals((int) expectedValue, outputImage.getPixelValue(0, 0, 0));

    expectedValue = coefficients[0] * initialArray[0][0][1] * initialArray[0][0][1]
            + coefficients[1] * initialArray[0][0][1] + coefficients[2];
    expectedValue = Math.max(0, Math.min(255, expectedValue));
    assertEquals((int) expectedValue, outputImage.getPixelValue(0, 0, 1));

    expectedValue = coefficients[0] * initialArray[0][0][2] * initialArray[0][0][2]
            + coefficients[1] * initialArray[0][0][2] + coefficients[2];
    expectedValue = Math.max(0, Math.min(255, expectedValue));
    assertEquals((int) expectedValue, outputImage.getPixelValue(0, 0, 2));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsLevelValuesShadow() {
    int[][][] initialArray = {
            {{100, 150, 200}, {120, 180, 220}, {80, 100, 120}},
            {{50, 70, 90}, {200, 210, 220}, {30, 40, 50}},
            {{160, 180, 200}, {90, 100, 110}, {140, 160, 180}}
    };

    Image arrayImage = new CustomImage(initialArray.length, initialArray[0].length);
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    int shadow = 280;
    int mid = 128;
    int highlight = 200;
    Image outputImage = levelAdjustment.adjustImageLevels(arrayImage, shadow, mid, highlight);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsLevelValuesMid() {
    int[][][] initialArray = {
            {{100, 150, 200}, {120, 180, 220}, {80, 100, 120}},
            {{50, 70, 90}, {200, 210, 220}, {30, 40, 50}},
            {{160, 180, 200}, {90, 100, 110}, {140, 160, 180}}
    };

    Image arrayImage = new CustomImage(initialArray.length, initialArray[0].length);
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    int shadow = 210;
    int mid = -20;
    int highlight = 200;
    Image outputImage = levelAdjustment.adjustImageLevels(arrayImage, shadow, mid, highlight);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsLevelValuesHighlight() {
    int[][][] initialArray = {
            {{100, 150, 200}, {120, 180, 220}, {80, 100, 120}},
            {{50, 70, 90}, {200, 210, 220}, {30, 40, 50}},
            {{160, 180, 200}, {90, 100, 110}, {140, 160, 180}}
    };

    Image arrayImage = new CustomImage(initialArray.length, initialArray[0].length);
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    int shadow = 10;
    int mid = 20;
    int highlight = 1000;
    Image outputImage = levelAdjustment.adjustImageLevels(arrayImage, shadow, mid, highlight);
  }
}
