package model.processor.colorcombiner;

import model.customimage.CustomImage;
import model.customimage.Image;

/**
 * An implementation of the ImageColorCombiner interface for combining color components.
 */
public class ImageColorCombinerImpl implements ImageColorCombiner {

  /**
   * Combines the red, green, and blue color components into a single image.
   *
   * @param redComponent   The image representing the red color component.
   * @param greenComponent The image representing the green color component.
   * @param blueComponent  The image representing the blue color component.
   * @return The image with the combined color components.
   */
  @Override
  public Image combineColorComponent(Image redComponent, Image greenComponent,
                                     Image blueComponent) {

    int height = redComponent.getHeight();
    int width = redComponent.getWidth();
    Image outputImage = new CustomImage(redComponent.getHeight(), redComponent.getWidth());

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int red = redComponent.getRed(j, i);
        int green = greenComponent.getGreen(j, i);
        int blue = blueComponent.getBlue(j, i);
        outputImage.setPixelValue(j, i, 0, red);
        outputImage.setPixelValue(j, i, 1, green);
        outputImage.setPixelValue(j, i, 2, blue);
      }
    }
    return outputImage;
  }
}
