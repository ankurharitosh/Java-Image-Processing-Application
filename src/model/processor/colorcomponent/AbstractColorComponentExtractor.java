package model.processor.colorcomponent;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class that provides a framework for extracting a color component from an image.
 */
public abstract class AbstractColorComponentExtractor implements ImageColorComponent {

  /**
   * Extracts the specified color component from the input image.
   *
   * @param inputImage The input image from which the color component will be extracted.
   * @return The image containing the extracted color component.
   * @throws IllegalArgumentException if an unsupported color component is specified.
   */
  @Override
  public Image getColorComponent(Image inputImage) throws IllegalArgumentException {
    SupportedColorComponents component = getColor();

    int red;
    int green;
    int blue;
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        switch (component) {
          case RED:
            red = inputImage.getPixelValue(j, i, SupportedColorComponents.RED.ordinal());
            outputImage.setPixelValue(j, i, 0, red);
            break;
          case GREEN:
            green = inputImage.getPixelValue(j, i, SupportedColorComponents.GREEN.ordinal());
            outputImage.setPixelValue(j, i, 1, green);
            break;
          case BLUE:
            blue = inputImage.getPixelValue(j, i, SupportedColorComponents.BLUE.ordinal());
            outputImage.setPixelValue(j, i, 2, blue);
            break;
          default:
            break;
        }
      }
    }
    return outputImage;
  }

  /**
   * Specifies the color component to be extracted.
   *
   * @return The supported color component to be extracted.
   */
  public abstract SupportedColorComponents getColor();
}
