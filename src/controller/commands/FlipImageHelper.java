package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for flipping images based on a given model.
 * This class applies a flipping effect to a source image and saves
 * the result to a destination image.
 */
public class FlipImageHelper implements CommandDesign {

  private final String command;
  private final String imageName;
  private final String destImageName;

  /**
   * Initializes a new `FlipImageHelper` instance with the provided flip command image filenames.
   *
   * @param command       The specific flip command (e.g., "vertical-flip" or "horizontal-flip").
   * @param imageName     The name of the source image to be flipped.
   * @param destImageName The name of the destination image where the flipped result will be saved.
   */
  public FlipImageHelper(String command, String imageName, String destImageName) {
    this.command = command;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }


  /**
   * Executes the image flipping action on the provided model.
   *
   * @param model The model on which the image flipping action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    if (command.equals("horizontal-flip")) {
      model.horizontalFlip(this.imageName, this.destImageName);
    } else {
      model.verticalFlip(this.imageName, this.destImageName);
    }
  }
}
