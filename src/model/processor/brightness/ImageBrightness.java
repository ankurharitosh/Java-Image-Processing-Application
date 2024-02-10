package model.processor.brightness;

import model.customimage.Image;

/**
 * An interface for adjusting the brightness of an image.
 */
public interface ImageBrightness {
  /**
   * Adjusts the brightness of the input image.
   *
   * @param inputImage      The input image to be adjusted.
   * @param integerConstant An integer constant to control the brightness adjustment.
   */
  Image adjustImage(Image inputImage, int integerConstant);
}
