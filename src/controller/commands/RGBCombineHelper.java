package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for getting combining R,G and B images based on a given model.
 * This class combines RGB components to make a single colored image and saves
 * the result to a destination image.
 */
public class RGBCombineHelper implements CommandDesign {

  private final String imageName;
  private final String imageNameRed;
  private final String imageNameGreen;
  private final String imageNameBlue;


  /**
   * A constuctor for RGBCombineHelper class that perform actions based on a given model.
   *
   * @param imageName      The image name of the file.
   * @param imageNameRed   The red component image name of the file.
   * @param imageNameGreen The green component image name of the file.
   * @param imageNameBlue  The blue component image name of the file.
   */
  public RGBCombineHelper(String imageName, String imageNameRed, String imageNameGreen,
                          String imageNameBlue) {
    this.imageName = imageName;
    this.imageNameRed = imageNameRed;
    this.imageNameGreen = imageNameGreen;
    this.imageNameBlue = imageNameBlue;
  }

  /**
   * Executes the RGB combination operation on the given Model.
   *
   * @param model The Model on which the RGB combination operation is performed.
   * @throws IOException If an I/O error occurs while performing the operation.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.rgbCombine(imageName, imageNameRed, imageNameGreen, imageNameBlue);
  }
}
