package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for color correction of images based on a given model.
 * This class applies a color correction effect to a source image and saves
 * the result to a destination image.
 */
public class ColorCorrectHelper implements CommandDesign {

  private final String imageName;
  private final String destImageName;
  private final double percentage;

  /**
   * Initializes a new `HistogramHelper` instance with the provided parameters.
   *
   * @param imageName     The name of the source image for applying sepia tone.
   * @param destImageName name of the destination image where the sepia-toned image will be saved.
   * @param percentage    The percentage of splitting.
   */
  public ColorCorrectHelper(String imageName, String destImageName, double percentage) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Executes the color correction action on the provided model.
   *
   * @param model The model on which the sepia tone action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.colorCorrect(imageName, destImageName, percentage);
  }
}