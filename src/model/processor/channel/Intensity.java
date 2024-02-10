package model.processor.channel;

/**
 * A class that calculates the intensity of an RGB color by averaging the three color channels.
 */
public class Intensity extends AbstractChannel {
  /**
   * Calculates the intensity of an RGB color based on the average of red, green, and blue channels.
   *
   * @param r The red color channel value (0-255).
   * @param g The green color channel value (0-255).
   * @param b The blue color channel value (0-255).
   * @return The calculated intensity value (0-255).
   */
  @Override
  public int getOperation(int r, int g, int b) {
    return (int) (r + g + b) / 3;
  }
}
