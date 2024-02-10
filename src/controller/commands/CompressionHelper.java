package controller.commands;

import java.io.IOException;

import controller.CommandDesign;
import model.Model;

/**
 * A command class for Compression of images based on a given model. This class applies a
 * Compression effect to a source image and saves the result to a destination image.
 */
public class CompressionHelper implements CommandDesign {

  private final double compressPercentage;
  private final String imageName;
  private final String destImageName;


  /**
   * Initializes a new `CompressionHelper` instance with the provided image filenames.
   *
   * @param compressPercentage The percentage of compression.
   * @param imageName          The name of the source image to be compressed.
   * @param destImageName      The name of destination image where compressed result will be saved.
   */
  public CompressionHelper(double compressPercentage, String imageName, String destImageName) {
    this.compressPercentage = compressPercentage;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the blur action on the provided model.
   *
   * @param model The model on which the color component manipulation action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    model.compress(compressPercentage, imageName, destImageName);
  }
}
