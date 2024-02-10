package model.processor.colorcomponent;

import model.customimage.Image;

/**
 * An interface for extracting a specific color component from an image.
 */
public interface ImageColorComponent {
  /**
   * Extracts a specific color component from the input image.
   *
   * @param inputImage The input image from which to extract the color component.
   * @return A new image containing only the specified color component.
   * @throws IllegalArgumentException If the color component extraction is not supported.
   */
  Image getColorComponent(Image inputImage) throws IllegalArgumentException;
}
