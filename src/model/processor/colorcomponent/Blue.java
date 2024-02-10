package model.processor.colorcomponent;

import model.processor.SupportedColorComponents;

/**
 * A class that extracts the blue color component from an image.
 */
public class Blue extends AbstractColorComponentExtractor {

  /**
   * Specifies that the blue color component should be extracted.
   *
   * @return The supported color component to be extracted, which is the blue color component.
   */
  @Override
  public SupportedColorComponents getColor() {
    return SupportedColorComponents.BLUE;
  }
}
