package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for sharpening images based on a given model.
 * This class applies a sharpening effect to a source image and saves
 * the result to a destination image.
 */
public class SharpenHelper implements CommandDesign {

  private final String imageName;
  private final String destImageName;
  private final double percentage;

  /**
   * Initializes a new `SharpenHelper` instance with the provided parameters.
   *
   * @param imageName     The name of the source image for applying sharpening.
   * @param destImageName The name of the destination image where the sharpened image will be saved.
   * @param percentage    The percentage of splitting.
   */
  public SharpenHelper(String imageName, String destImageName, double percentage) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Executes the sharpening action on the provided model.
   *
   * @param model The model on which the sharpening action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.sharpen(imageName, destImageName, percentage);
  }
}
