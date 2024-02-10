package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for getting specific channels of images based on a given model. This class
 * applies a greyscale - sepia, luma, intensity effect to a source image and saves the result to a
 * destination image.
 */
public class ImageChannelsHelper implements CommandDesign {

  private final String command;
  private final String imageName;
  private final String destImageName;
  private final double percentage;

  /**
   * Initializes a new `RepresentComponentHelper` instance with the provided parameters.
   *
   * @param command       The command representing the type of color representation to be
   *                      performed.
   * @param imageName     The name of the source image.
   * @param destImageName The name to be assigned to the destination image.
   * @param percentage    The percentage of splitting.
   */
  public ImageChannelsHelper(String command, String imageName, String destImageName,
      double percentage) {
    this.command = command;
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Applies a greyscale - sepia, luma, intensity effect to a source image on the provided model.
   *
   * @param model The model on which the color representation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    if (command.equals("value-component")) {
      model.valueChannel(imageName, destImageName, percentage);
    } else if (command.equals("luma-component")) {
      model.lumaChannel(imageName, destImageName, percentage);
    } else {
      model.intensityChannel(imageName, destImageName, percentage);
    }
  }
}
