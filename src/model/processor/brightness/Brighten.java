package model.processor.brightness;

/**
 * A class for brightening an image.
 */
public class Brighten extends AbstractBrightnessController {
  /**
   * Determine whether to brighten or darken the image.
   *
   * @return True, indicating that the image should be brightened.
   */
  @Override
  protected boolean brightenOrDarken() {
    return true;
  }
}
