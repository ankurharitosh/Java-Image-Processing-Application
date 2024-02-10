package controller.handler;

/**
 * A class for handling PNG image files, extending AbstractImageHandler.
 */
public class PNGFileHandler extends AbstractImageHandler {

  /**
   * Get the format of the image, which is "PNG."
   *
   * @return The format of the image, which is "PNG."
   */
  @Override
  public String getFormat() {
    return SupportedFormats.png.getFileFormat();
  }
}
