package model.customimage;

import java.awt.image.BufferedImage;

/**
 * A class representing a custom image with various operations for working with image data.
 */
public class CustomImage implements Image {

  private final int height;
  private final int width;

  // 3D array for RGB values
  private int[][][] pixelData;


  /**
   * Constructs a CustomImage with the specified height and width.
   *
   * @param height The height of the image.
   * @param width  The width of the image.
   */
  public CustomImage(int height, int width) {
    this.height = height;
    this.width = width;
    this.pixelData = new int[height][width][3];
  }

  /**
   * Converts the pixel matrix to a BufferedImage.
   *
   * @return The BufferedImage representation of the image.
   */
  @Override
  public BufferedImage convertMatrixToImage() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = this.pixelData[y][x][0];
        int g = this.pixelData[y][x][1];
        int b = this.pixelData[y][x][2];
        int rgb = (r << 16) | (g << 8) | b;
        image.setRGB(x, y, rgb);
      }
    }
    return image;
  }

  /**
   * Gets the red color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The red color component value of the pixel.
   */
  @Override
  public int getRed(int y, int x) {
    return pixelData[y][x][0];
  }


  /**
   * Gets the green color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The green color component value of the pixel.
   */
  @Override
  public int getGreen(int y, int x) {
    return pixelData[y][x][1];
  }


  /**
   * Gets the blue color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The blue color component value of the pixel.
   */
  @Override
  public int getBlue(int y, int x) {
    return pixelData[y][x][2];
  }


  /**
   * Gets the color component value of a pixel at the specified coordinates based on color.
   *
   * @param y         The y-coordinate of the pixel.
   * @param x         The x-coordinate of the pixel.
   * @param component The color component to retrieve.
   * @return The color component value of the pixel.
   * @throws IllegalArgumentException If the specified color component is not supported.
   */
  @Override
  public int getPixelValue(int y, int x, int component) {
    return this.pixelData[y][x][component];
  }


  /**
   * Gets the width of the image.
   *
   * @return The width of the image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }


  /**
   * Gets the height of the image.
   *
   * @return The height of the image.
   */
  @Override
  public int getHeight() {
    return this.height;
  }


  /**
   * Gets the pixel data of the image as a three-dimensional array.
   *
   * @return The pixel data of the image.
   */
  @Override
  public int[][][] getPixelData() {
    return this.pixelData;
  }

  /**
   * Sets the pixel values of the image based on a BufferedImage.
   *
   * @param image The BufferedImage to set pixel values from.
   */
  @Override
  public void setPixelValuesFromBufferedImage(BufferedImage image) {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        this.pixelData[j][i][0] = r;
        this.pixelData[j][i][1] = g;
        this.pixelData[j][i][2] = b;
      }
    }
  }

  /**
   * Gets the RGB value of a pixel at the specified coordinates (x, y).
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return The RGB value of the pixel.
   * @throws IllegalArgumentException If the coordinates are outside the valid image bounds.
   */
  @Override
  public int getRGB(int x, int y) throws IllegalArgumentException {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      int r = pixelData[y][x][0];
      int g = pixelData[y][x][1];
      int b = pixelData[y][x][2];
      return (r << 16) | (g << 8) | b;
    } else {
      throw new IllegalArgumentException("Invalid x and y coordinates");
    }
  }

  /**
   * Sets the RGB value of a pixel at the specified coordinates (x, y) and color.
   *
   * @param i        The x-coordinate of the pixel.
   * @param j        The y-coordinate of the pixel.
   * @param color    the color of the pixel.
   * @param adjusted the new value at the pixel.
   * @throws IllegalArgumentException If the coordinates are outside the valid image bounds.
   */
  public void setPixelValue(int j, int i, int color, int adjusted) throws IllegalArgumentException {
    if (color < 0 || color > 2) {
      throw new IllegalArgumentException("Invalid color value provided");
    }
    this.pixelData[j][i][color] = adjusted;
  }

  /**
   * Sets all pixel values of the image using a three-dimensional pixel data array.
   *
   * @param pixelData The pixel data array.
   */
  public void setAllPixels(int[][][] pixelData) {
    this.pixelData = pixelData;
  }
}
