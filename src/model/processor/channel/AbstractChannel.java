package model.processor.channel;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class for applying channel operations to an image.
 */
public abstract class AbstractChannel implements ImageChannel {
  /**
   * Applies the channel operation to the input image.
   *
   * @param inputImage The input image to which the channel operation is applied.
   * @return The image with the channel operation applied.
   */
  @Override
  public Image applyChannel(Image inputImage) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int result =
                getOperation(inputImage.getPixelValue(j, i, SupportedColorComponents.RED.ordinal()),
                        inputImage.getPixelValue(j, i, SupportedColorComponents.GREEN.ordinal()),
                        inputImage.getPixelValue(j, i, SupportedColorComponents.BLUE.ordinal())
                );
        outputImage.setPixelValue(j, i, 0, result);
        outputImage.setPixelValue(j, i, 1, result);
        outputImage.setPixelValue(j, i, 2, result);
      }
    }
    return outputImage;
  }

  /**
   * Gets the result of the channel operation for a pixel with RGB values.
   *
   * @param r The red component of the pixel.
   * @param g The green component of the pixel.
   * @param b The blue component of the pixel.
   * @return The result of the channel operation.
   */
  public abstract int getOperation(int r, int g, int b);
}

