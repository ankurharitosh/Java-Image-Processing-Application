package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for colorComponent of images based on a given model.
 * This class gives the RGB images of a source image and saves
 * the result to a destination image.
 */
public class ColorComponentHelper implements CommandDesign {

  private final String command;
  private final String imageName;
  private final String destImageName;

  /**
   * Initializes a new `ColorComponentHelper` instance with the provided command & image filenames.
   *
   * @param command       The specific color component manipulation command.
   * @param imageName     The name of the source image to be processed.
   * @param destImageName The name of the destination image where the result will be saved.
   */
  public ColorComponentHelper(String command, String imageName, String destImageName) {
    this.command = command;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the color component separation of RGB of image on the provided model.
   *
   * @param model The model on which the color component manipulation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    if (command.equals("red-component")) {
      model.redComponent(this.imageName, this.destImageName);
    } else if (command.equals("green-component")) {
      model.greenComponent(this.imageName, this.destImageName);
    } else {
      model.blueComponent(this.imageName, this.destImageName);
    }
  }
}
