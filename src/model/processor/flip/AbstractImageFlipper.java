package model.processor.flip;


import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class that provides a framework for flipping an input image.
 */
public abstract class AbstractImageFlipper implements ImageFlip {

  /**
   * Flips the input image horizontally or vertically based on the implementation.
   *
   * @param inputImage The input image to be flipped.
   * @return The flipped image.
   */
  @Override
  public Image flipImage(Image inputImage) {
    String flip = getFlip();
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(height, width);
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int flippedJ = (flip.equals("vertical")) ? (height - 1 - j) : j;
        int flippedI = (flip.equals("horizontal")) ? (width - 1 - i) : i;


        int red = inputImage.getPixelValue(j, i, SupportedColorComponents.RED.ordinal());
        int green = inputImage.getPixelValue(j, i, SupportedColorComponents.GREEN.ordinal());
        int blue = inputImage.getPixelValue(j, i, SupportedColorComponents.BLUE.ordinal());
        outputImage.setPixelValue(flippedJ, flippedI, 0, red);
        outputImage.setPixelValue(flippedJ, flippedI, 1, green);
        outputImage.setPixelValue(flippedJ, flippedI, 2, blue);
      }
    }
    return outputImage;
  }

  /**
   * Gets the direction of the flip (e.g., "horizontal" or "vertical").
   *
   * @return The direction of the flip.
   */
  public abstract String getFlip();
}
