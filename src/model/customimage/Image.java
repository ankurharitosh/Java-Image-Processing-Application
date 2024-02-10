package model.customimage;

import java.awt.image.BufferedImage;

/**
 * An interface for working with images. This interface provides multiple functions such as
 * getting or setting pixel values.
 */
public interface Image {

  /**
   * Gets the red color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The red color component value.
   */
  int getRed(int y, int x);

  /**
   * Gets the green color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The green color component value.
   */
  int getGreen(int y, int x);

  /**
   * Gets the blue color component value of a pixel at the specified coordinates (x, y).
   *
   * @param y The y-coordinate of the pixel.
   * @param x The x-coordinate of the pixel.
   * @return The blue color component value.
   */
  int getBlue(int y, int x);

  /**
   * Gets the color component value of a pixel at the specified coordinates (x, y) based on the
   * specified color component.
   *
   * @param y         The y-coordinate of the pixel.
   * @param x         The x-coordinate of the pixel.
   * @param component The color component to retrieve.
   * @return The color component value of the pixel.
   * @throws IllegalArgumentException If the specified color component is not supported.
   */
  int getPixelValue(int y, int x, int component);

  /**
   * Gets the width of the image.
   *
   * @return The width of the image.
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return The height of the image.
   */
  int getHeight();

  /**
   * Gets the pixel data of the image as a three-dimensional array.
   *
   * @return The pixel data of the image.
   */
  int[][][] getPixelData();

  /**
   * Gets the RGB value of a pixel at the specified coordinates (x, y).
   *
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @return The RGB value of the pixel.
   * @throws IllegalArgumentException If the specified coordinates are outside the valid image
   *                                  bounds.
   */
  int getRGB(int x, int y);

  /**
   * Sets the color component value of a pixel at the specified coordinates (x, y).
   *
   * @param j        The y-coordinate of the pixel.
   * @param i        The x-coordinate of the pixel.
   * @param color    The color component to set (0 for red, 1 for green, 2 for blue).
   * @param adjusted The adjusted color component value.
   */
  void setPixelValue(int j, int i, int color, int adjusted);

  /**
   * Sets the pixel values of the image based on a BufferedImage.
   *
   * @param image The BufferedImage to set pixel values from.
   */
  void setPixelValuesFromBufferedImage(BufferedImage image);

  /**
   * Converts the pixel matrix to a BufferedImage.
   *
   * @return The BufferedImage representation of the image.
   */
  BufferedImage convertMatrixToImage();

  /**
   * Sets all pixel values of the image using a three-dimensional pixel data array.
   *
   * @param pixelData The pixel data array.
   */
  void setAllPixels(int[][][] pixelData);
}
