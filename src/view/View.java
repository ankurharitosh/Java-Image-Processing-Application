package view;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Interface for the ImageProcessingGUI class opens a window for the user to interact graphically
 * with the application.
 */
public interface View {

  /**
   * Displays the given image on the GUI.
   *
   * @param image BufferedImage to be displayed.
   */
  void displayImage(BufferedImage image);

  /**
   * Handles the load button click event.
   *
   * @return File selected by the user through file chooser.
   */
  File handleLoadButtonClick();

  /**
   * Shows a file chooser dialog for the save button.
   *
   * @return File selected by the user for saving.
   */
  File showSaveButtonFileChooser();

  /**
   * Gets user input within a specified range using a slider.
   *
   * @param inputMessage Message to be displayed to the user.
   * @return User input as a String.
   */
  String getInput(String inputMessage);

  /**
   * Displays an error message dialog.
   *
   * @param errorMessage Error message to be displayed.
   */
  void showErrorMessage(String errorMessage);

  /**
   * Displays a warning message dialog.
   *
   * @param warningMessage Warning message to be displayed.
   */
  void showWarningMessage(String warningMessage);

  /**
   * Displays a general message dialog.
   *
   * @param message Message to be displayed.
   */
  void showMessage(String message);
}
