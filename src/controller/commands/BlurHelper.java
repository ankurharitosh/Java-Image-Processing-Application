package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for blurring images based on a given model.
 * This class applies a blur effect to a source image and saves the result to a destination image.
 */
public class BlurHelper implements CommandDesign {

  private final String imageName;
  private final String destImageName;
  private final double percentage;


  /**
   * Initializes a new `BlurHelper` instance with the provided image filenames.
   *
   * @param imageName     The name of the source image to be blurred.
   * @param destImageName The name of the destination image where the blurred result will be saved.
   * @param percentage    The percentage of splitting.
   */
  public BlurHelper(String imageName, String destImageName, double percentage) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Executes the blur action on the provided model.
   *
   * @param model The model on which the color component manipulation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.blur(imageName, destImageName, percentage);
  }
}
