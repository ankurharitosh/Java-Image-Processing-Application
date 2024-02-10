package controller;

import java.awt.image.BufferedImage;

import view.View;

/**
 * GUIController is the controller used for GUI. Follows the MVC design pattern and allows user
 * to interact with a GUI.
 */
public interface GuiController {

  /**
   * Sets the view to the required view for the controller.
   * @param view object of the view class.
   */
  void setView(View view);

  /**
   * Handles button clicks based on the provided action command. Retrieves the corresponding action
   * from the buttonActions map and executes it.
   *
   * @param actionCommand The command associated with the button click.
   */
  void handleButtonClick(String actionCommand);

  /**
   * Retrieves histogram image from model for the current image and returns it as a BufferedImage.
   *
   * @return The histogram image as a BufferedImage.
   * @throws RuntimeException If an IOException occurs during the image loading process.
   */
  BufferedImage getHistogramImage();
}
