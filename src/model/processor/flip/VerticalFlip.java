package model.processor.flip;

/**
 * A class for performing a vertical flip on an image.
 */
public class VerticalFlip extends AbstractImageFlipper {
  /**
   * Returns the type of flip operation, which is "vertical."
   *
   * @return The type of flip operation, which is "vertical."
   */
  @Override
  public String getFlip() {
    return "vertical";
  }
}
