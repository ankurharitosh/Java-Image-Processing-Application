package controller.commands;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import java.io.File;
import java.io.IOException;

import controller.CommandDesign;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.Model;
import model.customimage.Image;

/**
 * LoadHelper class for LoadHelper class that perform actions based on a given model.
 */
public class IOHelper implements CommandDesign {

  private final String ioOperation;
  private final String imagePath;
  private final String imageName;
  private static ImageFileHandler imageFileHandler;

  /**
   * Initializes a new `LoadHelper` instance with the provided image path and image name.
   *
   * @param imagePath The path to the image file to be loaded.
   * @param imageName The name to be assigned to the loaded image.
   */
  public IOHelper(String ioOperation, String imagePath, String imageName) {
    this.ioOperation = ioOperation;
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  /**
   * Executes the image loading action on the provided model.
   *
   * @param model The model on which the image loading action will be executed.
   * @throws IOException If an IO exception occurs during the action.
   */
  @Override
  public void execute(Model model) throws IOException {
    if (this.ioOperation.equals("load")) {
      this.load(model);
    } else if (this.ioOperation.equals("save")) {
      this.save(model);
    }
  }

  private void load(Model model) throws IOException {
    if (imagePath.endsWith(".png")) {
      imageFileHandler = new PNGFileHandler();
    } else if (imagePath.endsWith(".ppm")) {
      imageFileHandler = new PPMFileHandler();
    } else if (imagePath.endsWith(".jpeg") || imagePath.endsWith(".jpg")) {
      imageFileHandler = new JPEGFileHandler();
    } else {
      throw new IOException("Unsupported file provided");
    }

    Image sourceImage = imageFileHandler.loadImage(imagePath);
    model.load(sourceImage, imageName);
  }

  private void save(Model model) throws IOException {
    Image sourceImage = model.getFile(imageName);
    try {
      File file = new File(imagePath);
      String directory = file.getParent();
      Files.createDirectories(Paths.get(directory));
      if (imagePath.endsWith(".png")) {
        imageFileHandler = new PNGFileHandler();
      } else if (imagePath.endsWith(".ppm")) {
        imageFileHandler = new PPMFileHandler();
      } else if (imagePath.endsWith(".jpeg") || imagePath.endsWith(".jpg")) {
        imageFileHandler = new JPEGFileHandler();
      } else {
        throw new IOException("Unsupported file provided");
      }
      imageFileHandler.saveImage(sourceImage, imagePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
