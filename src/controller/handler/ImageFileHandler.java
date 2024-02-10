package controller.handler;

import java.io.IOException;

import model.customimage.Image;

/**
 * An interface for handling image loading and saving operations.
 */
public interface ImageFileHandler {

  /**
   * Load an image from a file and return it as a CustomImage object.
   *
   * @param filePath The path to the image file.
   * @return the loaded file.
   * @throws IOException if there's an issue with file loading.
   */
  Image loadImage(String filePath) throws IOException;


  /**
   * Save a CustomImage to the specified output path.
   *
   * @param image      The CustomImage to save.
   * @param outputPath The path where the image will be saved.
   * @throws IOException if there's an issue with file saving.
   */
  void saveImage(Image image, String outputPath) throws IOException;
}
