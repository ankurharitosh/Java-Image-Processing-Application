package model.colortransform;

import java.util.Arrays;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract base class for implementing linear color transformations on images.
 */
public abstract class AbstractColorTransform implements ImageLinearColorTransform {

  private int[][] result;

  /**
   * Multiplies a matrix by a kernel.
   *
   * @param kernel    The kernel for multiplication.
   * @param rgbMatrix The RGB matrix to be multiplied.
   * @return The result of the matrix multiplication.
   */
  public int[][] multiplyMatrix(double[][] kernel, int[][] rgbMatrix) {
    for (int[] row : result) {
      Arrays.fill(row, 0);
    }
    for (int i = 0; i < 3; i++) {
      for (int k = 0; k < 3; k++) {
        result[i][0] += (int) Math.round(kernel[i][k] * rgbMatrix[k][0]);
      }
    }

    return result;
  }

  /**
   * Apply the color transformation to an input image and return the transformed image.
   *
   * @param inputImage The input image to be transformed.
   * @return The transformed image.
   */
  @Override
  public Image applyColorTransform(Image inputImage) {
    result = new int[3][1];
    double[][] kernel = getKernel();
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(height, width);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = inputImage.getPixelValue(y, x, SupportedColorComponents.RED.ordinal());
        int green = inputImage.getPixelValue(y, x, SupportedColorComponents.GREEN.ordinal());
        int blue = inputImage.getPixelValue(y, x, SupportedColorComponents.BLUE.ordinal());

        int[][] rgbMatrix = {{red}, {green}, {blue}};
        int[][] rgbDash = multiplyMatrix(kernel, rgbMatrix);

        int redDash = Math.min(255, Math.max(0, rgbDash[0][0]));
        int greenDash = Math.min(255, Math.max(0, rgbDash[1][0]));
        int blueDash = Math.min(255, Math.max(0, rgbDash[2][0]));

        outputImage.setPixelValue(y, x, SupportedColorComponents.RED.ordinal(), redDash);
        outputImage.setPixelValue(y, x, SupportedColorComponents.GREEN.ordinal(), greenDash);
        outputImage.setPixelValue(y, x, SupportedColorComponents.BLUE.ordinal(), blueDash);
      }
    }

    return outputImage;
  }

  /**
   * getKernel method gets the kernel for the implementing class.
   *
   * @return a 2D double matrix containing the values for the kernel.
   */
  public abstract double[][] getKernel();
}
