package model.processor.channel;

import model.customimage.Image;

/**
 * An interface for applying channel operations to an image.
 */
public interface ImageChannel {

  /**
   * Applies the channel operation to the input image.
   *
   * @param inputImage The input image to which the channel operation is applied.
   * @return The image with the channel operation applied.
   */
  Image applyChannel(Image inputImage);
}
