package model.filter;

import model.customimage.Image;

/**
 * An interface for applying image filters to an input image.
 */
public interface ImageFilters {

  /**
   * Apply the image filter operation to an input image and return the processed image.
   *
   * @param inputImage The input image to be processed in 3D array format.
   * @return The processed image.
   */
  Image applyFilter(Image inputImage);
}
