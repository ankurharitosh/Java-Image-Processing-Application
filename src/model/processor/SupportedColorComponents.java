package model.processor;

/**
 * This enum represents the various color components used in image processing.
 */
public enum SupportedColorComponents {

  /**
   * The red color component.
   */
  RED("r"),

  /**
   * The green color component.
   */
  GREEN("g"),

  /**
   * The blue color component.
   */
  BLUE("b");

  private final String color;

  SupportedColorComponents(String color) {
    this.color = color;
  }


  /**
   * Returns a string representation of the color component.
   *
   * @return A string representation of the color component.
   */
  @Override
  public String toString() {
    return this.color;
  }
}
