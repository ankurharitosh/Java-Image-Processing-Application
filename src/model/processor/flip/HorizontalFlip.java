package model.processor.flip;

/**
 * A class for horizontally flipping an image.
 */
public class HorizontalFlip extends AbstractImageFlipper {
  /**
   * Gets the direction of the flip, which is "horizontal".
   *
   * @return The direction of the flip, which is "horizontal".
   */
  @Override
  public String getFlip() {
    return "horizontal";
  }
}
