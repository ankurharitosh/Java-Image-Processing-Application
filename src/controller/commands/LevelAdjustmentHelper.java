package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for level adjustment of images based on a given model.
 * This class applies a level adjust effect to a source image and saves
 * the result to a destination image.
 */
public class LevelAdjustmentHelper implements CommandDesign {

  private final int shadow;
  private final int mid;
  private final int highlight;
  private final String imageName;
  private final String destImageName;
  private final double percentage;


  /**
   * Initializes a new `LevelAdjustmentHelper` instance with the provided image filenames.
   *
   * @param shadow        The shadow of image.
   * @param mid           The mid of image.
   * @param highlight     The highlight of image.
   * @param imageName     The name of the source image to be blurred.
   * @param destImageName The name of the destination image where the blurred result will be saved.
   * @param percentage    The percentage of splitting.
   */
  public LevelAdjustmentHelper(int shadow, int mid, int highlight, String imageName,
                               String destImageName, double percentage) {
    this.shadow = shadow;
    this.mid = mid;
    this.highlight = highlight;
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  /**
   * Executes the level adjustmentHelper manipulation action on the provided model.
   *
   * @param model The model on which the color component manipulation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.levelAdjustment(shadow, mid, highlight, imageName, destImageName, percentage);
  }
}