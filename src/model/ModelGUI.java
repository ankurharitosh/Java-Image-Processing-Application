package model;

import model.customimage.Image;


/**
 * An advanced model for the GUI of the application. This model implements additional methods in
 * addition to the methods already defined in the original Model class.
 */
public interface ModelGUI extends Model {

  /**
   * getCurrentImage method returns the current image that's being worked on.
   *
   * @return the current image.
   */
  Image getCurrentImage();

  /**
   * getHistogramImage method returns the current image's histogram that's being worked on.
   *
   * @return the current image's histogram.
   */
  Image getHistogramImage();

  /**
   * Generates a split preview image of the result of an image split operation.
   *
   * @param prevImage  The previous state of the image.
   * @param image      The current state of the image.
   * @param percentage The percentage of splitting for the operation.
   * @return The preview image of the split operation result.
   */
  Image operationPreview(Image prevImage, Image image, double percentage);
}
