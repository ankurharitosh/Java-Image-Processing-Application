package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for sepia filter images based on a given model.
 * This class applies a sepia effect to a source image and saves
 * the result to a destination image.
 */
public class SepiaHelper implements CommandDesign {

  private final String imageName;
  private final String destImageName;
  private final double percentage;

  /**
   * Initializes a new `SepiaHelper` instance with the provided parameters.
   *
   * @param imageName     The name of the source image for applying sepia tone.
   * @param destImageName name of the destination image where the sepia-toned image will be saved.
   * @param percentage    The percentage of splitting.
   */
  public SepiaHelper(String imageName, String destImageName, double percentage) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Executes the sepia tone action on the provided model.
   *
   * @param model The model on which the sepia tone action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.sepia(imageName, destImageName, percentage);
  }
}
