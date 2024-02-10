import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import model.histogram.ColorCorrection;
import model.histogram.VisualizeHistogram;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.customimage.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the AbstractHistogramTest.
 */
public class AbstractHistogramTest {

  VisualizeHistogram visualizeHistogram;
  ColorCorrection colorCorrection;
  private Image inputImage;
  private ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    String imagePath = "res/train.jpg";
    visualizeHistogram = new VisualizeHistogram();
    colorCorrection = new ColorCorrection();
    try {
      fileHandler = new JPEGFileHandler();
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testHistogram() {
    Image histogramImage = visualizeHistogram.histogramOperation(inputImage);
    try {
      fileHandler.saveImage(histogramImage, "res/Hist-Test.jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(histogramImage);
  }

  @Test
  public void testColorCorrect() {
    Image colorCorrectedImage = colorCorrection.histogramOperation(inputImage);
    Image histogramImage = visualizeHistogram.histogramOperation(colorCorrectedImage);
    try {
      fileHandler.saveImage(colorCorrectedImage, "res/ColorCorrect-Test.jpg");
      fileHandler.saveImage(histogramImage, "res/CC-Hist-Test.jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(colorCorrectedImage);
    assertNotNull(histogramImage);
  }

  @Test
  public void testHistogramDimensions() {
    Image histogramImage = visualizeHistogram.histogramOperation(inputImage);
    assertEquals(histogramImage.getHeight(), 256);
    assertEquals(histogramImage.getWidth(), 256);
  }

  @Test
  public void testColorCorrectDimensions() {
    Image colorCorrectedImage = colorCorrection.histogramOperation(inputImage);
    Image histogramImage = visualizeHistogram.histogramOperation(colorCorrectedImage);
    assertEquals(colorCorrectedImage.getHeight(), inputImage.getHeight());
    assertEquals(colorCorrectedImage.getWidth(), inputImage.getWidth());
    assertEquals(histogramImage.getHeight(), 256);
    assertEquals(histogramImage.getWidth(), 256);
  }
}