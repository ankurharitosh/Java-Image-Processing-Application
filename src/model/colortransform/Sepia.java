package model.colortransform;

/**
 * A class for applying a Sepia color transformation to an image.
 */
public class Sepia extends AbstractColorTransform {
  private final double[][] kernel = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };

  /**
   * getKernel method returns the kernel for Sepia color transform.
   *
   * @return a 2D double matrix containing the values for the kernel.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
