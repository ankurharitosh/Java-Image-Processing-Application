package model.histogram;

import model.customimage.Image;

/**
 * The Histogram interface defines methods for visualizing Histogram for images.
 */
public interface Histogram {


  /**
   * Create a Histogram to visualize the image.
   *
   * @param inputImage The input image to be processed in 3D array format.
   * @return The processed image.
   */
  Image histogramOperation(Image inputImage);

}
