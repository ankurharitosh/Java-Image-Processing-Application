package model.filter;

/**
 * A class representing an image filter for applying blur effects.
 */
public class BlurImage extends ImageFilterTemplate {

  private final double[][] kernel = {
          {1.0 / 16, 1.0 / 8, 1.0 / 16},
          {1.0 / 8, 1.0 / 4, 1.0 / 8},
          {1.0 / 16, 1.0 / 8, 1.0 / 16}
  };

  /**
   * Returns the kernel used for the blur filter.
   *
   * @return a 2D double matrix containing the values for the kernel.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
