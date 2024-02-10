package model.processor.brightness;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class for adjusting the brightness of an image.
 */
public abstract class AbstractBrightnessController implements ImageBrightness {

  /**
   * Adjust the brightness of an input image.
   *
   * @param inputImage      The input image to be adjusted.
   * @param integerConstant The constant value to adjust brightness.
   * @return The adjusted image.
   */
  @Override
  public Image adjustImage(Image inputImage, int integerConstant) {
    boolean brighten = brightenOrDarken();
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int originalRed = inputImage.getPixelValue(j, i,
                SupportedColorComponents.RED.ordinal());
        int originalGreen = inputImage.getPixelValue(j, i,
                SupportedColorComponents.GREEN.ordinal());
        int originalBlue = inputImage.getPixelValue(j, i,
                SupportedColorComponents.BLUE.ordinal());

        int adjustedRed = brighten ? originalRed + integerConstant
                : originalRed - integerConstant;
        int adjustedGreen = brighten ? originalGreen + integerConstant
                : originalGreen - integerConstant;
        int adjustedBlue = brighten ? originalBlue + integerConstant
                : originalBlue - integerConstant;

        // Ensure that adjusted values are within the valid range [0, 255]
        adjustedRed = Math.min(255, Math.max(0, adjustedRed));
        adjustedGreen = Math.min(255, Math.max(0, adjustedGreen));
        adjustedBlue = Math.min(255, Math.max(0, adjustedBlue));

        // Update the pixel values in the inputImage
        outputImage.setPixelValue(j, i, 0, adjustedRed);
        outputImage.setPixelValue(j, i, 1, adjustedGreen);
        outputImage.setPixelValue(j, i, 2, adjustedBlue);
      }
    }
    return outputImage;
  }


  /**
   * Determine whether to brighten or darken the image.
   *
   * @return True if brightening, false if darkening.
   */
  protected abstract boolean brightenOrDarken();

}
