package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for getting a histogram image based on a given image and model.
 * This class provides a histogram of the source image and saves
 * the result to a destination image.
 */
public class HistogramHelper implements CommandDesign {

  private final String imageName;
  private final String destImageName;

  /**
   * Initializes a new `HistogramHelper` instance with the provided parameters.
   *
   * @param imageName     The name of the source image for applying sepia tone.
   * @param destImageName name of the destination image where the sepia-toned image will be saved.
   */
  public HistogramHelper(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the creation of Histogram for the image.
   *
   * @param model The model on which the sepia tone action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.histogram(imageName, destImageName);
  }
}
