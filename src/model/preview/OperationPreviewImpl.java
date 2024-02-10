package model.preview;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * Implementation of the OperationPreview interface providing operations for image preview.
 */
public class OperationPreviewImpl implements OperationPreview {

  /**
   * Splits the given Image into two parts based on the specified percentage.
   *
   * @param originalImage The original Image to split.
   * @param n             The percentage of the original width to keep in the first part.
   * @return An array containing two CustomImage objects representing the split images.
   * @throws IllegalArgumentException If the provided percentage is invalid (not between 0 and 100)
   */
  @Override
  public Image[] getNPercentImage(Image originalImage, double n) {
    int originalWidth = originalImage.getWidth();
    int originalHeight = originalImage.getHeight();

    if (n < 0 || n > 100) {
      throw new IllegalArgumentException("Invalid percentage: " + n);
    }

    int newWidth = (int) (originalWidth * n) / 100;

    Image newImage = new CustomImage(originalHeight, newWidth);
    Image otherHalf = new CustomImage(originalHeight, originalWidth - newWidth);

    for (int j = 0; j < originalHeight; j++) {
      for (int i = 0; i < originalWidth; i++) {
        int originalPixelValueRed = originalImage.getPixelValue(j, i,
                SupportedColorComponents.RED.ordinal());
        int originalPixelValueGreen = originalImage.getPixelValue(j, i,
                SupportedColorComponents.GREEN.ordinal());
        int originalPixelValueBlue = originalImage.getPixelValue(j, i,
                SupportedColorComponents.BLUE.ordinal());

        int[][][] targetArray = (i < newWidth) ? newImage.getPixelData() : otherHalf.getPixelData();
        int targetIndex = (i < newWidth) ? i : (i - newWidth);

        targetArray[j][targetIndex][0] = originalPixelValueRed;
        targetArray[j][targetIndex][1] = originalPixelValueGreen;
        targetArray[j][targetIndex][2] = originalPixelValueBlue;
      }
    }

    return new Image[]{newImage, otherHalf};
  }

  /**
   * Concatenates two images with a white separator in between.
   *
   * @param image1 The first Image for concatenation.
   * @param image2 The second Image for concatenation.
   * @return A new CustomImage object representing the concatenated image.
   * @throws IllegalArgumentException If the images have different heights.
   */
  @Override
  public Image concatenateImages(Image image1, Image image2) throws IllegalArgumentException {
    if (image1.getHeight() != image2.getHeight()) {
      throw new IllegalArgumentException("Images must have the same height for concatenation.");
    }

    int width1 = image1.getWidth();
    int width2 = image2.getWidth();
    int totalWidth = width1 + 3 + width2;

    int height = image1.getHeight();

    Image concatenatedImage = new CustomImage(height, totalWidth);

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width1; i++) {
        int pixelValueRed = image1.getPixelValue(j, i, SupportedColorComponents.RED.ordinal());
        int pixelValueGreen = image1.getPixelValue(j, i, SupportedColorComponents.GREEN.ordinal());
        int pixelValueBlue = image1.getPixelValue(j, i, SupportedColorComponents.BLUE.ordinal());

        concatenatedImage.setPixelValue(j, i, 0, pixelValueRed);
        concatenatedImage.setPixelValue(j, i, 1, pixelValueGreen);
        concatenatedImage.setPixelValue(j, i, 2, pixelValueBlue);
      }
    }

    for (int j = 0; j < height; j++) {
      concatenatedImage.setPixelValue(j, width1 + 2, 0, 255);
      concatenatedImage.setPixelValue(j, width1 + 2, 1, 255);
      concatenatedImage.setPixelValue(j, width1 + 2, 2, 255);
    }

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width2; i++) {
        int pixelValueRed = image2.getPixelValue(j, i, SupportedColorComponents.RED.ordinal());
        int pixelValueGreen = image2.getPixelValue(j, i, SupportedColorComponents.GREEN.ordinal());
        int pixelValueBlue = image2.getPixelValue(j, i, SupportedColorComponents.BLUE.ordinal());

        concatenatedImage.setPixelValue(j, width1 + 2 + i, 0, pixelValueRed);
        concatenatedImage.setPixelValue(j, width1 + 2 + i, 1, pixelValueGreen);
        concatenatedImage.setPixelValue(j, width1 + 2 + i, 2, pixelValueBlue);
      }
    }

    return concatenatedImage;
  }
}
