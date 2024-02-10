package model.processor.colorcomponent;

import model.processor.SupportedColorComponents;

/**
 * A class that extracts the green color component from an image.
 */
public class Green extends AbstractColorComponentExtractor {
  /**
   * Specifies that the green color component should be extracted.
   *
   * @return The supported color component to be extracted, which is the green color component.
   */
  @Override
  public SupportedColorComponents getColor() {
    return SupportedColorComponents.GREEN;
  }
}
