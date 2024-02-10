package controller.handler;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class representing an image handler for loading and saving images.
 */
public abstract class AbstractImageHandler implements ImageFileHandler {
  private BufferedImage image;

  /**
   * Load an image from a file and return it as an Image object.
   *
   * @param filePath The path to the image file.
   * @return the loaded image for further operations.
   * @throws IOException if there's an issue with file loading.
   */
  @Override
  public Image loadImage(String filePath) throws IOException {
    if (filePath.toLowerCase().endsWith(".ppm")) {
      ppmToBufferedImage(filePath);
    } else {
      loadImageIOSupportedImage(filePath);
    }
    Image loadedImage = new CustomImage(this.image.getHeight(), this.image.getWidth());
    loadedImage.setPixelValuesFromBufferedImage(this.image);
    return loadedImage;
  }

  /**
   * Save an outputImage to the specified output path.
   *
   * @param outputImage The outputImage to save.
   * @param outputPath  The path where the outputImage will be saved.
   * @throws IOException if there's an issue with file saving.
   */
  @Override
  public void saveImage(Image outputImage, String outputPath) throws IOException {
    String format = getFormat();
    if (getFormat().equalsIgnoreCase("ppm")) {
      savePPMImage(outputImage, outputPath);
      return;
    }
    File outputFile = new File(outputPath);
    this.image = outputImage.convertMatrixToImage();
    ImageIO.write(this.image, format, outputFile);
  }


  /**
   * Get the format of the image.
   *
   * @return the format of the image as a string.
   */
  public abstract String getFormat();

  /**
   * Save an image in PPM format.
   *
   * @param inputImage The image to save.
   * @param outputPath The path where the image will be saved.
   * @throws IOException if there's an issue with PPM file saving.
   */
  private void savePPMImage(Image inputImage, String outputPath) throws IOException {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();

    // Open a file for writing
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

    // Write the PPM header
    writer.write("P3\n");
    writer.write(width + " " + height + "\n");
    writer.write("255\n");

    // Write the RGB values from the matrix
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = inputImage.getPixelValue(y, x, SupportedColorComponents.RED.ordinal());
        int green = inputImage.getPixelValue(y, x, SupportedColorComponents.GREEN.ordinal());
        int blue = inputImage.getPixelValue(y, x, SupportedColorComponents.BLUE.ordinal());

        writer.write(red + " " + green + " " + blue + " ");
      }
      writer.write("\n");
    }
    writer.close();
  }

  private void loadImageIOSupportedImage(String filePath) throws IOException {
    try {
      this.image = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      System.out.println("Error loading the image: " + e.getMessage());
      throw e;
    }
  }

  private void ppmToBufferedImage(String filePath) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filePath + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        this.image.setRGB(j, i, r << 16 | g << 8 | b);
      }
    }
    sc.close();
  }
}
