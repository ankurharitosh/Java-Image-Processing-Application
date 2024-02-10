package model.processor.flip;

import model.customimage.Image;

/**
 * An interface for flipping an image.
 */
public interface ImageFlip {
  /**
   * Flips an input image based on the specific flip operation.
   *
   * @param inputImage The input image to be flipped.
   * @return The flipped image.
   */
  Image flipImage(Image inputImage);
}
