package model.histogram;

/**
 * A class to create a Color Correction Histogram to visualize the image.
 */
public class ColorCorrection extends AbstractHistogram {

  @Override
  protected boolean isColorCorrect() {
    return true;
  }
}
