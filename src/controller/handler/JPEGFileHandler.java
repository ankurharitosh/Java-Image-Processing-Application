package controller.handler;

/**
 * A class for handling JPEG image files, extending AbstractImageHandler.
 */
public class JPEGFileHandler extends AbstractImageHandler {

  /**
   * Get the format of the image, which is "JPEG."
   *
   * @return The format of the image, which is "JPEG."
   */
  @Override
  public String getFormat() {
    return SupportedFormats.jpeg.getFileFormat();
  }
}
