package model.processor.channel;

/**
 * A class that calculates the value of an RGB color by taking the maximum of its channels.
 */
public class Value extends AbstractChannel {
  /**
   * Calculates the value of an RGB color by taking the maximum of red, green, and blue channels.
   *
   * @param r The red color channel value (0-255).
   * @param g The green color channel value (0-255).
   * @param b The blue color channel value (0-255).
   * @return The calculated value (0-255).
   */
  @Override
  public int getOperation(int r, int g, int b) {
    return Math.max(r, Math.max(g, b));
  }
}
