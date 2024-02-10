import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import model.compression.HaarWaveletCompression;
import model.compression.HaarWaveletCompressionImpl;
import model.customimage.CustomImage;
import model.customimage.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * An abstract test class for HaarWaveletCompression implementation. Provides common setup
 * and test methods for different image file formats.
 */
public abstract class AbstractHaarWaveletCompressionTest {


  private Image inputImage;
  private String imagePath;
  private ImageFileHandler fileHandler;

  @Before
  public void setUp() {
    imagePath = getPath();
    fileHandler = getFileHandler();
    try {
      this.inputImage = fileHandler.loadImage(imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCompression() {


    HaarWaveletCompression haar = new HaarWaveletCompressionImpl();
    Image outputImage = haar.applyHaarCompression(this.inputImage, 0);
    int[][][] outputMatrix = outputImage.getPixelData();
    int[][][] inputMatrix = inputImage.getPixelData();

    for (int j = 0; j < inputImage.getHeight(); j++) {
      for (int i = 0; i < inputImage.getWidth(); i++) {
        for (int color = 0; color < 3; color++) {
          assertEquals(inputMatrix[j][i][color], outputMatrix[j][i][color], 1);
        }
      }
    }
  }

  @Test
  public void testCompressedImageDimensions() {
    int[][][] pixelData = {
            {{100, 150, 200}, {120, 130, 140}, {50, 60, 70}},
            {{80, 90, 100}, {110, 120, 130}, {140, 150, 160}},
            {{200, 210, 220}, {230, 240, 250}, {100, 110, 120}}
    };
    Image inputImage = new CustomImage(pixelData.length, pixelData[0].length);
    inputImage.setAllPixels(pixelData);

    double compressionPercent = 50.0;

    HaarWaveletCompression compressionImpl = new HaarWaveletCompressionImpl();

    Image compressedImage = compressionImpl.applyHaarCompression(inputImage,
            compressionPercent);

    assertEquals(inputImage.getHeight(), compressedImage.getHeight());
    assertEquals(inputImage.getWidth(), compressedImage.getWidth());
  }

  @Test
  public void testCompressedImageSize() {
    double compressedSize;
    double originalSize;
    double compressionPercent = 99;
    HaarWaveletCompression compressionImpl = new HaarWaveletCompressionImpl();
    try {
      originalSize = Files.size(Paths.get(imagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Image compressedImage = compressionImpl.applyHaarCompression(inputImage,
            compressionPercent);

    try {
      fileHandler.saveImage(compressedImage, "res/compressed.png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      compressedSize = Files.size(Paths.get("res/compressed.png"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(compressedSize < originalSize);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCompressionPercent() {
    HaarWaveletCompression haar = new HaarWaveletCompressionImpl();
    Image outputImage = haar.applyHaarCompression(this.inputImage, 240);
  }

  public abstract String getPath();

  public abstract ImageFileHandler getFileHandler();

  /**
   * A test class for JPG compression using HaarWaveletCompression.
   */
  public static class JPGCompressionTest extends AbstractHaarWaveletCompressionTest {
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
   * A test class for PNG compression using HaarWaveletCompression.
   */
  public static class PNGCompressionTest extends AbstractHaarWaveletCompressionTest {
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
   * A test class for PPM compression using HaarWaveletCompression.
   */
  public static class PPMCompressionTest extends AbstractHaarWaveletCompressionTest {

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
