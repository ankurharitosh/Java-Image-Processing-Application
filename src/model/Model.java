package model;

import java.io.IOException;

import model.customimage.Image;

/**
 * The Model interface defines methods for manipulating and processing images.
 */
public interface Model {

  /**
   * Load an image from the specified path and with the given name.
   *
   * @param sourceImage The source image of the file.
   * @param imageName   The name of the image.
   * @throws IOException if there's an issue with loading the image.
   */
  void load(Image sourceImage, String imageName)
      throws IOException;

  /**
   * Save the current image to the specified path and with the given name.
   *
   * @param imageName The name of the image.
   */
  Image getFile(String imageName);

  /**
   * Apply a red component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  void redComponent(String imageName, String destImageName);

  /**
   * Apply a green component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  void greenComponent(String imageName, String destImageName);

  /**
   * Apply a blue component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  void blueComponent(String imageName, String destImageName);

  /**
   * Apply a value color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  void valueChannel(String imageName, String destImageName, double percentage);

  /**
   * Apply a luma color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  void lumaChannel(String imageName, String destImageName, double percentage);

  /**
   * Apply a intensity color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  void intensityChannel(String imageName, String destImageName, double percentage);

  /**
   * Apply an image vertical flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  void verticalFlip(String imageName, String destImageName);

  /**
   * Apply an image horizontal flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  void horizontalFlip(String imageName, String destImageName);

  /**
   * Adjust the brightness of the image by the given integer constant.
   *
   * @param integerConstant The constant by which to adjust the brightness.
   * @param imageName       The name of the source image.
   * @param destImageName   The name of the destination image.
   */
  void brightenImage(int integerConstant, String imageName, String destImageName)
  ;

  /**
   * Split the RGB channels of the image into separate images.
   *
   * @param imageName          The name of the source image.
   * @param destImageNameRed   The name of the destination red channel image.
   * @param destImageNameGreen The name of the destination green channel image.
   * @param destImageNameBlue  The name of the destination blue channel image.
   */
  void rgbSplit(String imageName, String destImageNameRed, String destImageNameGreen,
      String destImageNameBlue);

  /**
   * Combine the red, green, and blue channel images into a single RGB image.
   *
   * @param imageName  The name of the source image.
   * @param redImage   The name of the red channel image.
   * @param greenImage The name of the green channel image.
   * @param blueImage  The name of the blue channel image.
   */
  void rgbCombine(String imageName, String redImage, String greenImage, String blueImage)
  ;

  /**
   * Apply a blur filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the blur.
   * @param percentage    The percentage of splitting.
   */
  void blur(String imageName, String destImageName, double percentage);

  /**
   * Apply a sharpen filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sharpen filter.
   * @param percentage    The percentage of splitting.
   */
  void sharpen(String imageName, String destImageName, double percentage);

  /**
   * Apply a sepia filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sepia filter.
   * @param percentage    The percentage of splitting.
   */
  void sepia(String imageName, String destImageName, double percentage);

  /**
   * Apply a compression to the image.
   *
   * @param compressPercentage The percentage of compression.
   * @param imageName          The name of the source image.
   * @param destImageName      The name of the destination image after applying the colorCorrect
   *                           filter.
   */
  void compress(double compressPercentage, String imageName, String destImageName);

  /**
   * Apply a histogram filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sepia filter.
   */
  void histogram(String imageName, String destImageName);

  /**
   * Apply a color correction to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the colorCorrect filter.
   * @param percentage    The percentage of splitting.
   */
  void colorCorrect(String imageName, String destImageName, double percentage);

  /**
   * Apply a color correction to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying levelAdjustment filter.
   * @param percentage    The percentage of splitting.
   */
  void levelAdjustment(int shadow, int mid, int highlight, String imageName, String destImageName,
      double percentage);
}
