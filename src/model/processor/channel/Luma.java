package model.processor.channel;

/**
 * A class that calculates the luma of an RGB color based on the specified coefficients.
 */
public class Luma extends AbstractChannel {
  /**
   * Calculates the luma (perceived brightness) of an RGB color based on the specified coefficients.
   *
   * @param r The red color channel value (0-255).
   * @param g The green color channel value (0-255).
   * @param b The blue color channel value (0-255).
   * @return The calculated luma value (0-255).
   */
  @Override
  public int getOperation(int r, int g, int b) {
    return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
  }
}
