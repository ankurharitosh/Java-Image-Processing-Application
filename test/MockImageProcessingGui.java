import controller.GUIControllerImpl;
import view.ImageProcessingGUI;

import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Create a mock view for testing the controller.
 */
public class MockImageProcessingGui extends ImageProcessingGUI {

  private String loadFilePath;
  private String saveFilePath;
  private String input;
  private boolean messageShown;
  private BufferedImage displayImage;
  private GUIControllerImpl controller;
  private boolean isDisplayImageCalled;

  public MockImageProcessingGui(GUIControllerImpl controller) {
    super(controller);
    this.controller = controller;
  }

  public void setLoadFilePath(String loadFilePath) {
    this.loadFilePath = loadFilePath;
  }

  public void setSaveFilePath(String saveFilePath) {
    this.saveFilePath = saveFilePath;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public boolean isMessageShown() {
    return messageShown;
  }

  public void setDisplayImage(BufferedImage displayImage) {
    this.displayImage = displayImage;
  }

  @Override
  public File handleLoadButtonClick() {
    return new File(loadFilePath);
  }

  @Override
  public File showSaveButtonFileChooser() {
    return new File(saveFilePath);
  }


  public String getInput(String inputMessage) {
    return input;
  }

  @Override
  public void showMessage(String inputMessage) {
    messageShown = true;
  }

  @Override
  public void displayImage(BufferedImage image) {
    displayImage = image;
    isDisplayImageCalled = true;
  }

  public GUIControllerImpl getController() {
    return controller;
  }

  public boolean isDisplayImageMethodCalled() {
    return isDisplayImageCalled;
  }
}


