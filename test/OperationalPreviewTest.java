import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import model.colortransform.ImageLinearColorTransform;
import model.colortransform.Sepia;
import model.customimage.CustomImage;
import model.customimage.Image;
import model.preview.OperationPreview;
import model.preview.OperationPreviewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Test class for the OperationPreview.
 */
public class OperationalPreviewTest {

  private Image inputImage;
  private ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    String imagePath = "res/train.jpg";
    try {
      fileHandler = new JPEGFileHandler();
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void previewTest() {
    OperationPreview operationPreview = new OperationPreviewImpl();
    ImageLinearColorTransform colorTransform = new Sepia();
    Image[] images = operationPreview.getNPercentImage(this.inputImage, 45.3);
    Image half = images[0];
    Image otherHalf = images[1];
    Image greyscaleImage = colorTransform.applyColorTransform(otherHalf);
    Image previewImage = operationPreview.concatenateImages(half, greyscaleImage);
    try {
      fileHandler.saveImage(previewImage, "res/split.jpg");
      fileHandler.saveImage(otherHalf, "res/split2.jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(previewImage);
  }

  @Test
  public void testGetNPercentImage() {
    OperationPreview operationPreview = new OperationPreviewImpl();

    int[][][] pixelData = {
            {{100, 120, 140}, {110, 130, 150}, {120, 140, 160}},
            {{90, 110, 130}, {95, 115, 135}, {100, 120, 140}}
    };
    Image originalImage = new CustomImage(pixelData.length, pixelData[0].length);

    Image[] splitImages = operationPreview.getNPercentImage(originalImage, 50.0);

    assertNotNull(splitImages);
    assertEquals(2, splitImages.length);

    int expectedWidth = originalImage.getWidth() / 2;
    assertEquals(expectedWidth, splitImages[0].getWidth(), 1);
    assertEquals(expectedWidth, splitImages[1].getWidth(), 1);
  }

  @Test
  public void testConcatenateImages() {
    OperationPreview operationPreview = new OperationPreviewImpl();

    int[][][] pixelData1 = {
            {{100, 120, 140}, {110, 130, 150}},
            {{90, 110, 130}, {95, 115, 135}}
    };
    int[][][] pixelData2 = {
            {{200, 220, 240}, {210, 230, 250}},
            {{190, 210, 230}, {195, 215, 235}}
    };
    Image image1 = new CustomImage(pixelData1.length, pixelData1[0].length);
    Image image2 = new CustomImage(pixelData2.length, pixelData2[0].length);


    Image concatenatedImage = operationPreview.concatenateImages(image1, image2);

    assertNotNull(concatenatedImage);

    int expectedWidth = image1.getWidth() + 3 + image2.getWidth();
    assertEquals(expectedWidth, concatenatedImage.getWidth());
  }

  @Test
  public void testConcatenateImagesWithDifferentHeight() {
    OperationPreview operationPreview = new OperationPreviewImpl();

    int[][][] pixelData1 = {
            {{100, 120, 140}, {110, 130, 150}},
            {{100, 120, 140}, {110, 130, 150}},
            {{90, 110, 130}, {95, 115, 135}}
    };
    int[][][] pixelData2 = {
            {{200, 220, 240}, {210, 230, 250}, {220, 240, 260}},
            {{190, 210, 230}, {195, 215, 235}, {200, 220, 240}}
    };
    Image image1 = new CustomImage(pixelData1.length, pixelData1[0].length);
    Image image2 = new CustomImage(pixelData2.length, pixelData2[0].length);
    assertThrows(IllegalArgumentException.class, () ->
            operationPreview.concatenateImages(image1, image2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSplitPercentMoreThan100() {
    OperationPreview operationPreview = new OperationPreviewImpl();

    int[][][] pixelData1 = {
            {{100, 120, 140}, {110, 130, 150}},
            {{100, 120, 140}, {110, 130, 150}},
            {{90, 110, 130}, {95, 115, 135}}
    };
    Image image1 = new CustomImage(pixelData1.length, pixelData1[0].length);
    operationPreview.getNPercentImage(image1, 120);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSplitPercentLessThan0() {
    OperationPreview operationPreview = new OperationPreviewImpl();

    int[][][] pixelData1 = {
            {{100, 120, 140}, {110, 130, 150}},
            {{100, 120, 140}, {110, 130, 150}},
            {{90, 110, 130}, {95, 115, 135}}
    };
    Image image1 = new CustomImage(pixelData1.length, pixelData1[0].length);
    operationPreview.getNPercentImage(image1, -10);
  }
}
