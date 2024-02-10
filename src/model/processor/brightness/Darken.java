package model.processor.brightness;

/**
 * A class for darkening an image.
 */
public class Darken extends AbstractBrightnessController {
  /**
   * Determine whether to brighten or darken the image.
   *
   * @return False, indicating that the image should be darkened.
   */
  @Override
  protected boolean brightenOrDarken() {
    return false;
  }
}
