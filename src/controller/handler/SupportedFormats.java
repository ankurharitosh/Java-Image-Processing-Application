package controller.handler;

/**
 * An enumeration of supported image formats.
 */
public enum SupportedFormats {
  png("png", "Portable Network Graphic"),
  jpeg("jpeg", "Joint Photographic Experts Group"),
  ppm("ppm", "Portable Pixmap Format");

  private final String fileFormat;
  private final String formatDescription;

  /**
   * Construct a SupportedFormats enum value with the given file format extension and description.
   *
   * @param extension The file format extension (e.g., "png").
   * @param name      The format description (e.g., "Portable Network Graphic").
   */
  private SupportedFormats(String extension, String name) {
    this.fileFormat = extension;
    this.formatDescription = name;
  }

  /**
   * Get the format description.
   *
   * @return The format description (e.g., "Portable Network Graphic").
   */
  public String getFormatDescription() {
    return this.formatDescription;
  }

  /**
   * Get the file format extension.
   *
   * @return The file format extension (e.g., "png").
   */
  public String getFileFormat() {
    return this.fileFormat;
  }

  @Override
  public String toString() {
    return this.fileFormat + " " + this.formatDescription;
  }
}
