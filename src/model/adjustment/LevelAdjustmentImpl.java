package model.adjustment;

import model.customimage.CustomImage;
import model.customimage.Image;

/**
 * Implementation of the LevelAdjustment interface that adjusts the levels of an image by
 * specifying values for shadow, midtone, and highlight adjustments.
 */
public class LevelAdjustmentImpl implements LevelAdjustment {

  /**
   * Adjusts the levels of an image by specifying values for shadow, midtone,
   * and highlight adjustments.
   *
   * @param inputImage The image to be adjusted.
   * @param shadow     The adjustment value for the shadow areas.
   * @param mid        The adjustment value for the midtone areas.
   * @param highlight  The adjustment value for the highlight areas.
   * @return The adjusted image after applying the level adjustments.
   */
  @Override
  public Image adjustImageLevels(Image inputImage, int shadow, int mid, int highlight)
          throws IllegalArgumentException {
    if (!(shadow < mid && mid < highlight)) {
      throw new IllegalArgumentException("Invalid level values: shadow < mid < highlight");
    } else if ((shadow < 0 || shadow > 255) || (mid < 0 || mid > 255) || (highlight < 0
            || highlight > 255)) {
      throw new IllegalArgumentException("Invalid level values: values should be greater than -1"
              + "and smaller than 255");
    }

    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();

    double[] coefficients = getCoefficients(shadow, mid, highlight);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        for (int c = 0; c < 3; c++) {
          int oldValue = inputImage.getPixelValue(y, x, c);
          int newValue = (int) (coefficients[0] * oldValue * oldValue
                  + coefficients[1] * oldValue + coefficients[2]);

          newValue = Math.max(0, Math.min(255, newValue));
          outputImage.setPixelValue(y, x, c, newValue);
        }
      }
    }

    return outputImage;
  }

  private double[] getCoefficients(int b, int m, int w) {
    double a = (b * b * (m - w)) - (b * ((m * m) - (w
            * w))) + (w * (m * m)) - (m * (w * w));
    double aa = (-b * (128 - 255)) + (128 * w) - (255 * m);
    double ab = (b * b * (128 - 255)) + (255 * m * m) - (128 * w * w);
    double ac = (b * b * (255 * m - 128 * w)) - b * (255 * m
            * m - 128 * w * w);

    return new double[]{aa / a, ab / a, ac / a};
  }
}
