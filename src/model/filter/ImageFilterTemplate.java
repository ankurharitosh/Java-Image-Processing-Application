package model.filter;

import model.customimage.CustomImage;
import model.customimage.Image;

/**
 * An abstract class serving as a template for implementing image filters.
 */
public abstract class ImageFilterTemplate implements ImageFilters {
  /**
   * Apply the image filter operation to an input image and return the processed image.
   *
   * @param inputImage The input image to be processed in 3D array format.
   * @return The processed image.
   */
  @Override
  public Image applyFilter(Image inputImage) {
    double[][] kernel = getKernel();
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());

    // Distance from the center pixel to the farthest contributing pixel
    int kernelArea = kernel.length / 2;

    // Loop for image height
    for (int y = 0; y < height; y++) {
      // Loop for image width
      for (int x = 0; x < width; x++) {
        // Calculating the blurred pixel RGB value. Initially set to 0, which means black
        double[] blurred = {0, 0, 0};

        // Loop to go through the kernel area
        // Initially, calculating the y
        for (int yy = -kernelArea; yy <= kernelArea; yy++) {
          int pixelY = y + yy;
          if (pixelY < 0 || pixelY >= height) {
            continue;
          }
          double[] currentRowBlur = kernel[yy + kernelArea];
          for (int xx = -kernelArea; xx <= kernelArea; xx++) {
            int pixelX = x + xx;
            if (pixelX < 0 || pixelX >= width) {
              continue; // Skip out-of-bounds columns
            }

            double kernelValue = currentRowBlur[xx + kernelArea];
            int pixel = inputImage.getRGB(pixelX, pixelY);

            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = pixel & 0xFF;

            blurred[0] += r * kernelValue;
            blurred[1] += g * kernelValue;
            blurred[2] += b * kernelValue;
          }
        }

        for (int color = 0; color < 3; color++) {
          // Clamp the pixel values to the range [0, 255]
          outputImage.setPixelValue(y, x, color, (int) Math.max(0, Math.min(255, blurred[color])));

        }
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
