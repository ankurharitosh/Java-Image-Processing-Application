package model.filter;

/**
 * An implementation of an image filter to sharpen the image.
 */
public class SharpenImage extends ImageFilterTemplate {

  private final double[][] kernel = {
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
  };

  /**
   * Get the kernel matrix for the SharpenImage filter.
   *
   * @return a 2D double matrix containing the values for the kernel.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
