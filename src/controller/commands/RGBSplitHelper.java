package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for getting R,G and B images based on a given model.
 * This class separates RGB components to make a 3 single colored images and saves
 * the result to a destination image.
 */
public class RGBSplitHelper implements CommandDesign {

  private final String imageName;
  private final String destImageNameRed;
  private final String destImageNameGreen;
  private final String destImageNameBlue;

  /**
   * A constuctor for RGBCombineHelper class that perform actions based on a given model.
   *
   * @param imageName          The image name of the file.
   * @param destImageNameRed   The red component image name of the file.
   * @param destImageNameGreen The green component image name of the file.
   * @param destImageNameBlue  The blue component image name of the file.
   */
  public RGBSplitHelper(String imageName, String destImageNameRed, String destImageNameGreen,
                        String destImageNameBlue) {
    this.imageName = imageName;
    this.destImageNameRed = destImageNameRed;
    this.destImageNameGreen = destImageNameGreen;
    this.destImageNameBlue = destImageNameBlue;
  }

  /**
   * Executes the RGB combination operation on the given Model.
   *
   * @param model The Model on which the RGB combination operation is performed.
   * @throws IOException If an I/O error occurs while performing the operation.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.rgbSplit(imageName, destImageNameRed, destImageNameGreen, destImageNameBlue);
  }
}
