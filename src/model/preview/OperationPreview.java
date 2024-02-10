package model.preview;

import model.customimage.Image;

/**
 * Interface for providing operations related to image preview.
 */
public interface OperationPreview {

  /**
   * Splits the given Image into two parts based on the specified percentage.
   *
   * @param originalImage The original Image to split.
   * @param n             The percentage of the original width to keep in the first part.
   * @return An array containing two Image objects representing the split images.
   */
  Image[] getNPercentImage(Image originalImage, double n);

  /**
   * Concatenates two images.
   *
   * @param image1 The first Image for concatenation.
   * @param image2 The second Image for concatenation.
   * @return A new Image object representing the concatenated image.
   */
  Image concatenateImages(Image image1, Image image2);
}
