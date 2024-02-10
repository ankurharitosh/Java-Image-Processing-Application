package model.adjustment;

import model.customimage.Image;

/**
 * An interface for adjusting levels in an image, allowing modification of shadows, midtones,
 * and highlights.
 * Implementing classes should provide methods to apply adjustments to different components
 * of an image.
 */
public interface LevelAdjustment {

  /**
   * Adjusts the levels of an image by specifying values for shadow, midtone,
   * and highlight adjustments.
   *
   * @param image     The image to be adjusted.
   * @param shadow    The adjustment value for the shadow areas.
   * @param mid       The adjustment value for the midtone areas.
   * @param highlight The adjustment value for the highlight areas.
   * @return The adjusted image after applying the level adjustments.
   */
  Image adjustImageLevels(Image image, int shadow, int mid, int highlight);
}
