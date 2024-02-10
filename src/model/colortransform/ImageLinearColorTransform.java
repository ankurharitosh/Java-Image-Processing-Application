package model.colortransform;

import model.customimage.Image;

/**
 * An interface for applying linear color transformations to images.
 */
public interface ImageLinearColorTransform {

  /**
   * Apply the image color transform to an input image and return the processed image.
   *
   * @param inputImage The input image to be processed in 3D array format.
   * @return The processed image.
   */
  Image applyColorTransform(Image inputImage);
}
