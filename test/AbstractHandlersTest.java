import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.customimage.Image;

import static org.junit.Assert.assertTrue;

/**
 * Tests the implementation of image handlers for different formats.
 */
public abstract class AbstractHandlersTest {

  protected String outputPath;
  protected String inputPath;

  protected ImageFileHandler fileHandler;
  protected String extension;
  protected Image image;

  @Before
  public void setUp() {

    inputPath = getInputPath();
    outputPath = getOutputPath();
    fileHandler = getHandler();
    extension = getExtension();
    try {
      image = fileHandler.loadImage(inputPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test(expected = RuntimeException.class)
  public void testLoadInvalidPath() {
    String invalidFileLocation = "res/abcdefgh." + extension;
    try {
      fileHandler.loadImage(invalidFileLocation);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testSaveImage() {
    try {
      fileHandler.saveImage(image, outputPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    File file = new File(outputPath);
    assertTrue(file.exists());
  }


  @After
  public void cleanUp() {
    File file = new File(outputPath);
    if (file.delete()) {
      System.out.println("Deleted the file: " + file.getName());
    } else {
      System.out.println("Failed to delete the file.");
    }
  }

  protected abstract ImageFileHandler getHandler();

  protected abstract String getInputPath();

  protected abstract String getOutputPath();

  protected abstract String getExtension();

  /**
   * handler.JPGHandlerTest class is specifically for testing the functionality of JPEGFileHandler.
   */
  public static class JPGHandlerTest extends AbstractHandlersTest {
    @Override
    protected ImageFileHandler getHandler() {
      return new JPEGFileHandler();
    }

    @Override
    protected String getInputPath() {
      return "res/train.jpg";
    }

    @Override
    protected String getOutputPath() {
      return "res/trainSaved.jpg";
    }

    @Override
    protected String getExtension() {
      return "jpg";
    }
  }

  /**
   * handler.PNGHandlerTest class is specifically for testing the functionality of PNGFileHandler.
   */
  public static class PNGHandlerTest extends AbstractHandlersTest {

    @Override
    protected ImageFileHandler getHandler() {
      return new PNGFileHandler();
    }

    @Override
    protected String getInputPath() {
      return "res/manhattan-small.png";
    }

    @Override
    protected String getOutputPath() {
      return "res/manhattan-small-saved.png";
    }

    @Override
    protected String getExtension() {
      return "png";
    }
  }

  /**
   * handler.PPMHandlerTest class is specifically for testing the functionality of PPMFileHandler.
   */
  public static class PPMHandlerTest extends AbstractHandlersTest {
    @Override
    protected ImageFileHandler getHandler() {
      return new PPMFileHandler();
    }

    @Override
    protected String getInputPath() {
      return "res/Koala.ppm";
    }

    @Override
    protected String getOutputPath() {
      return "res/KoalaSaved.ppm";
    }

    @Override
    protected String getExtension() {
      return "ppm";
    }
  }
}
