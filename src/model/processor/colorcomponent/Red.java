package model.processor.colorcomponent;

import model.processor.SupportedColorComponents;

/**
 * A class that extracts the red color component from an input image.
 */
public class Red extends AbstractColorComponentExtractor {

  /**
   * Gets the supported color component (RED).
   *
   * @return The supported color component (RED).
   */
  @Override
  public SupportedColorComponents getColor() {
    return SupportedColorComponents.RED;
  }
}
