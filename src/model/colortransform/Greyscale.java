package model.colortransform;

/**
 * A class for applying Greyscale color transformation to an image.
 */
public class Greyscale extends AbstractColorTransform {

  private final double[][] kernel = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  };

  /**
   * Get the kernel matrix for the Greyscale color transform.
   *
   * @return A 2D double matrix containing the values for the kernel.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
