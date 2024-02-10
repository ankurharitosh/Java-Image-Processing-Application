package controller.handler;

/**
 * A class for handling PPM image files, extending AbstractImageHandler.
 */
public class PPMFileHandler extends AbstractImageHandler {
  @Override
  public String getFormat() {
    return SupportedFormats.ppm.getFileFormat();
  }
}
