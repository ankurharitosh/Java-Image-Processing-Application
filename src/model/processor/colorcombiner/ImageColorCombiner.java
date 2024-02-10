package model.processor.colorcombiner;


import model.customimage.Image;

/**
 * An interface for combining color components into a single image.
 */
public interface ImageColorCombiner {

  /**
   * Combines the red, green, and blue color components into a single image.
   *
   * @param redComponent   The image representing the red color component.
   * @param greenComponent The image representing the green color component.
   * @param blueComponent  The image representing the blue color component.
   * @return The image with the combined color components.
   */
  Image combineColorComponent(Image redComponent, Image greenComponent,
                              Image blueComponent);
}
