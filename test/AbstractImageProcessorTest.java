import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.customimage.Image;
import model.processor.SupportedColorComponents;
import model.processor.channel.ImageChannel;
import model.processor.channel.Intensity;
import model.processor.channel.Luma;
import model.processor.channel.Value;
import model.processor.colorcombiner.ImageColorCombiner;
import model.processor.colorcombiner.ImageColorCombinerImpl;
import model.processor.colorcomponent.Blue;
import model.processor.colorcomponent.Green;
import model.processor.colorcomponent.ImageColorComponent;
import model.processor.colorcomponent.Red;

import static org.junit.Assert.assertEquals;

/**
 * Tests the implementation of various image processor operations.
 */
public abstract class AbstractImageProcessorTest {
  protected ImageColorComponent imageColorHandler;
  protected ImageChannel imageChannel;
  protected Image inputImage;
  protected ImageFileHandler fileHandler;
  protected String imagePath;
  protected String redFile;
  protected String greenFile;
  protected String blueFile;

  protected abstract String getImagePath();

  protected abstract ImageFileHandler getFileHandler();

  protected abstract String getRedFile();

  protected abstract String getBlueFile();

  protected abstract String getGreenFile();


  @Before
  public void setUp() {
    imagePath = getImagePath();
    fileHandler = getFileHandler();
    redFile = getRedFile();
    blueFile = getBlueFile();
    greenFile = getGreenFile();
    try {
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testGetColorComponent_Red() {
    imageColorHandler = new Red();
    Image redComponentImage = imageColorHandler.getColorComponent(inputImage);

    try {
      fileHandler.saveImage(redComponentImage, redFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Check that the red component image contains only red and no green (0) or blue (0)
    for (int y = 0; y < redComponentImage.getHeight(); y++) {
      for (int x = 0; x < redComponentImage.getWidth(); x++) {
        assertEquals(0, redComponentImage.getGreen(y, x));
        assertEquals(0, redComponentImage.getBlue(y, x));
      }
    }
  }

  @Test
  public void testGetColorComponent_Blue() {
    imageColorHandler = new Blue();
    Image blueComponentImage = imageColorHandler.getColorComponent(inputImage);
    try {
      fileHandler.saveImage(blueComponentImage, blueFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // Check that the blue component image contains only blue and no red (0) or green (0)
    for (int y = 0; y < blueComponentImage.getHeight(); y++) {
      for (int x = 0; x < blueComponentImage.getWidth(); x++) {
        assertEquals(0, blueComponentImage.getRed(y, x));
        assertEquals(0, blueComponentImage.getGreen(y, x));
      }
    }
  }

  @Test
  public void testGetColorComponent_Green() {
    imageColorHandler = new Green();
    Image greenComponentImage = imageColorHandler.getColorComponent(inputImage);
    try {
      fileHandler.saveImage(greenComponentImage, greenFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Check that the green component image contains only green and no red (0) or blue (0)
    for (int y = 0; y < greenComponentImage.getHeight(); y++) {
      for (int x = 0; x < greenComponentImage.getWidth(); x++) {
        assertEquals(0, greenComponentImage.getRed(y, x));
        assertEquals(0, greenComponentImage.getBlue(y, x));
      }
    }
  }

  @Test
  public void testCombineColorComponents() {
    Image redImage;
    Image greenImage;
    Image blueImage;
    try {
      redImage = fileHandler.loadImage(redFile);
      greenImage = fileHandler.loadImage(greenFile);
      blueImage = fileHandler.loadImage(blueFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ImageColorCombiner colorCombiner = new ImageColorCombinerImpl();
    Image outputImage = colorCombiner.combineColorComponent(redImage, greenImage, blueImage);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] inputMatrix = inputImage.getPixelData();
    for (int j = 0; j < outputImage.getHeight(); j++) {
      for (int i = 0; i < outputImage.getWidth(); i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(inputMatrix[j][i][color], outputMatrix[j][i][color], 180);
        }
      }
    }

  }

  @Test
  public void testGetValueChannel() {
    imageChannel = new Value();
    Image valueChannelImage = imageChannel.applyChannel(inputImage);
    // Check that the value channel image contains only grayscale values
    for (int y = 0; y < valueChannelImage.getHeight(); y++) {
      for (int x = 0; x < valueChannelImage.getWidth(); x++) {
        int r = valueChannelImage.getPixelValue(y, x, SupportedColorComponents.RED.ordinal());
        int g = valueChannelImage.getPixelValue(y, x, SupportedColorComponents.GREEN.ordinal());
        int b = valueChannelImage.getPixelValue(y, x, SupportedColorComponents.BLUE.ordinal());
        int value = Math.max(r, Math.max(g, b));
        assertEquals(value, r);
        assertEquals(value, g);
        assertEquals(value, b);
      }
    }
  }

  @Test
  public void testGetIntensityChannel() {
    imageChannel = new Intensity();
    Image intensityChannelImage = imageChannel.applyChannel(inputImage);
    // Check that the intensity channel image contains only grayscale intensity values
    for (int y = 0; y < intensityChannelImage.getHeight(); y++) {
      for (int x = 0; x < intensityChannelImage.getWidth(); x++) {
        int r = intensityChannelImage.getPixelValue(y, x, SupportedColorComponents.RED.ordinal());
        int g = intensityChannelImage.getPixelValue(y, x, SupportedColorComponents.GREEN.ordinal());
        int b = intensityChannelImage.getPixelValue(y, x, SupportedColorComponents.BLUE.ordinal());
        int intensity = (r + g + b) / 3;
        assertEquals(intensity, r);
        assertEquals(intensity, g);
        assertEquals(intensity, b);
      }
    }
  }

  @Test
  public void testGetLumaChannel() {
    imageChannel = new Luma();
    Image lumaChannelImage = imageChannel.applyChannel(inputImage);
    // Check that the luma channel image contains luma values
    for (int y = 0; y < lumaChannelImage.getHeight(); y++) {
      for (int x = 0; x < lumaChannelImage.getWidth(); x++) {
        int r = lumaChannelImage.getPixelValue(y, x, SupportedColorComponents.RED.ordinal());
        int g = lumaChannelImage.getPixelValue(y, x, SupportedColorComponents.GREEN.ordinal());
        int b = lumaChannelImage.getPixelValue(y, x, SupportedColorComponents.BLUE.ordinal());
        int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        assertEquals(luma, r, 1);
        assertEquals(luma, g, 1);
        assertEquals(luma, b, 1);
      }
    }
  }

  /**
   * imageprocessor.JPGImageProcessorTest class is specifically for testing the image
   * processing of JPEG files using JPEGFileHandler.
   */
  public static class JPGImageProcessorTest extends AbstractImageProcessorTest {
    @Override
    protected String getImagePath() {
      return "res/train.jpg";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new JPEGFileHandler();
    }

    @Override
    protected String getRedFile() {
      return "res/train-red.jpg";
    }

    @Override
    protected String getBlueFile() {
      return "res/train-blue.jpg";
    }

    @Override
    protected String getGreenFile() {
      return "res/train-green.jpg";
    }
  }

  /**
   * imageprocessor.PNGImageProcessorTest class is specifically for testing the image
   * processing of PNG files using PNGFileHandler.
   */
  public static class PNGImageProcessorTest extends AbstractImageProcessorTest {
    @Override
    protected String getImagePath() {
      return "res/manhattan-small.png";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PNGFileHandler();
    }

    @Override
    protected String getRedFile() {
      return "res/manhattan-small-red.png";
    }

    @Override
    protected String getBlueFile() {
      return "res/manhattan-small-blue.png";
    }

    @Override
    protected String getGreenFile() {
      return "res/manhattan-small-green.png";
    }
  }

  /**
   * imageprocessor.PPMImageProcessorTest class is specifically for testing
   * the image processing of PPM files using PPMFileHandler.
   */
  public static class PPMImageProcessorTest extends AbstractImageProcessorTest {
    @Override
    protected String getImagePath() {
      return "res/Koala.ppm";
    }

    @Override
    protected ImageFileHandler getFileHandler() {
      return new PPMFileHandler();
    }

    @Override
    protected String getRedFile() {
      return "res/Koala-red.ppm";
    }

    @Override
    protected String getBlueFile() {
      return "res/Koala-blue.ppm";
    }

    @Override
    protected String getGreenFile() {
      return "res/Koala-green.ppm";
    }
  }

}
