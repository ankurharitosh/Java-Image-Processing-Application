package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for brightening images based on a given model.
 * This class applies a brightening effect to a source image and saves
 * the result to a destination image.
 */
public class BrightenHelper implements CommandDesign {

  private final int integerConstant;
  private final String imageName;
  private final String destImageName;

  /**
   * Initializes a new `BrightenHelper` instance with the provided image filenames.
   *
   * @param imageName     The name of the source image to be blurred.
   * @param destImageName The name of the destination image where the blurred result will be saved.
   */
  public BrightenHelper(int integerConstant, String imageName, String destImageName) {
    this.integerConstant = integerConstant;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the brightening manipulation action on the provided model.
   *
   * @param model The model on which the color component manipulation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.brightenImage(integerConstant, imageName, destImageName);
  }
}
