package controller;

import controller.handler.ImageFileHandler;
import controller.handler.JPEGFileHandler;
import controller.handler.PNGFileHandler;
import controller.handler.PPMFileHandler;
import java.util.HashSet;
import model.ModelGUI;
import model.customimage.CustomImage;
import model.customimage.Image;
import view.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The GUIController class manages the interaction between the GUI, the model, and various image
 * processing operations. It handles button clicks and updates the view based on the actions
 * performed on the model.
 */
public class GUIControllerImpl implements GuiController {

  private View view;
  private ModelGUI model;
  private ImageFileHandler fileHandler;
  private Map<String, Runnable> buttonActions;
  private Image image;
  private BufferedImage originalImage;
  private Image splitImage;
  private Image prevImage;
  private Boolean splitToggle;
  private Double percentage;
  private String prevCommand;
  private HashSet<String> noSplitCommmands;

  /**
   * Constructs a new GUIController with the specified model.
   *
   * @param model The ModelGUI implementation to be used.
   */
  public GUIControllerImpl(ModelGUI model) {
    initializeButtonActions();
    this.model = model;
    splitToggle = false;
    percentage = 100.0;
    this.prevCommand = "";
    initializeNoSplitCommmands();
  }

  private void initializeButtonActions() {
    buttonActions = new HashMap<>();
    buttonActions.put("load", this::handleLoadButtonClick);
    buttonActions.put("save", this::handleSaveButtonClick);
    buttonActions.put("redComponent", this::handleRedComponentButtonClick);
    buttonActions.put("blueComponent", this::handleBlueComponentButtonClick);
    buttonActions.put("greenComponent", this::handleGreenComponentButtonClick);
    buttonActions.put("verticalFlip", this::handleVerticalFlipButtonClick);
    buttonActions.put("horizontalFlip", this::handleHorizontalFlipButtonClick);
    buttonActions.put("original", this::handleOriginalButtonClick);
    buttonActions.put("blur", this::handleBlurButtonClick);
    buttonActions.put("sharpen", this::handleSharpenButtonClick);
    buttonActions.put("luma", this::handleLumaButtonClick);
    buttonActions.put("sepia", this::handleSepiaButtonClick);
    buttonActions.put("compression", this::handleCompressionButtonClick);
    buttonActions.put("colorCorrect", this::handleColorCorrectButtonClick);
    buttonActions.put("adjustLevels", this::handleAdjustLevelsButtonClick);
    buttonActions.put("splitToggle", this::handleSplitToggleButtonClick);
  }

  private void initializeNoSplitCommmands() {
    noSplitCommmands = new HashSet<>();
    noSplitCommmands.add("redComponent");
    noSplitCommmands.add("blueComponent");
    noSplitCommmands.add("greenComponent");
    noSplitCommmands.add("verticalFlip");
    noSplitCommmands.add("horizontalFlip");
    noSplitCommmands.add("compression");
  }

  public void setView(View view) {
    this.view = view;
  }

  /**
   * Handles button clicks based on the provided action command. Retrieves the corresponding action
   * from the buttonActions map and executes it.
   *
   * @param actionCommand The command associated with the button click.
   */
  public void handleButtonClick(String actionCommand) {
    Runnable action = buttonActions.get(actionCommand);
    if (action != null) {
      action.run();
    }
  }

  /**
   * Handles the click of any specific button visible in the view and performs
   * model operations on it.
   */
  public void handleLoadButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    File file = view.handleLoadButtonClick();
    String extension = "";
    String filePath = file.getAbsolutePath();
    int i = filePath.lastIndexOf('.');
    if (i > 0) {
      extension = filePath.substring(i + 1);
    }
    extension = extension.toLowerCase();
    if (extension.endsWith("png")) {
      this.fileHandler = new PNGFileHandler();
    } else if (extension.endsWith("jpg") || extension.endsWith("jpeg")) {
      this.fileHandler = new JPEGFileHandler();
    } else if (extension.endsWith("ppm")) {
      this.fileHandler = new PPMFileHandler();
    }
    try {
      this.prevImage = this.image;
      this.image = this.fileHandler.loadImage(filePath);
    } catch (IOException e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    BufferedImage display = this.image.convertMatrixToImage();
    this.view.displayImage(display);
    this.originalImage = display;
    try {
      this.model.load(this.image, "");
    } catch (IOException e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.prevCommand = "load";
  }

  private void handleSaveButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    File outputFile = this.view.showSaveButtonFileChooser();
    try {
      this.fileHandler.saveImage(this.image, outputFile.getAbsolutePath());
    } catch (IOException e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.prevCommand = "save";
  }

  private void handleRedComponentButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.redComponent("", "");
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "redComponent";
  }

  private void handleBlueComponentButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.blueComponent("", "");
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "blueComponent";
  }

  private void handleGreenComponentButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.greenComponent("", "");
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "greenComponent";
  }

  private void handleOriginalButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.prevImage = this.image;
      this.image = new CustomImage(originalImage.getHeight(), originalImage.getWidth());
      this.image.setPixelValuesFromBufferedImage(this.originalImage);
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.originalImage);
    this.prevCommand = "original";
  }

  private void handleBlurButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.blur("", "", 100);
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "blur";
  }

  private void handleSharpenButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.sharpen("", "", 100);
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "sharpen";
  }

  private void handleVerticalFlipButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.verticalFlip("", "");
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "verticalFlip";
  }

  private void handleHorizontalFlipButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.horizontalFlip("", "");
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "horizontalFlip";
  }

  private void handleLumaButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.lumaChannel("", "", 100);
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "luma";
  }

  private void handleSepiaButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.sepia("", "", 100);
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "sepia";
  }

  private void handleCompressionButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    String stringPercentage = view.getInput("Please enter the Compression Percentage");
    Double compressPercentage = Double.parseDouble(stringPercentage);
    try {
      this.model.compress(compressPercentage, "", "");
      this.prevImage = this.image;
      this.image = this.model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "compression";
  }

  private void handleColorCorrectButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    try {
      this.model.colorCorrect("", "", 100);
      this.prevImage = this.image;
      this.image = model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "colorCorrect";
  }

  private void handleAdjustLevelsButtonClick() {
    if (this.splitToggle) {
      this.view.showWarningMessage(
          "Please toggle the Split Image Button before doing any operation");
      return;
    }
    String stringBlack = view.getInput("Please enter the black value");
    int blackValue = Integer.parseInt(stringBlack);
    String stringMid = view.getInput("Please enter the mid value");
    int midValue = Integer.parseInt(stringMid);
    String stringWhite = view.getInput("Please enter the white value");
    int whiteValue = Integer.parseInt(stringWhite);
    try {
      this.model.levelAdjustment(blackValue, midValue, whiteValue, "",
          "", 100);
      this.prevImage = this.image;
      this.image = this.model.getCurrentImage();
    } catch (Exception e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.view.displayImage(this.image.convertMatrixToImage());
    this.prevCommand = "adjustLevels";
  }

  private void handleSplitToggleButtonClick() {
    if (this.noSplitCommmands.contains(this.prevCommand)) {
      this.view.showWarningMessage(
          "The split operation is not available for the" + this.prevCommand
                  + "command operation.");
      return;
    }
    if (this.splitToggle) {
      this.splitToggle = false;
      this.percentage = 100.0;
      this.view.displayImage(this.image.convertMatrixToImage());
      this.view.showMessage("The entire image is visible now");
    } else {
      String stringPercentage = view.getInput("Please enter the Split Percentage");
      this.percentage = Double.parseDouble(stringPercentage);
      this.splitToggle = true;
      try {
        this.splitImage = this.model.operationPreview(this.prevImage, this.image,
            this.percentage);
      } catch (Exception e) {
        this.view.showErrorMessage("Unable to split. Please perform an operation that "
                + "allows "
                + "split such as:" + "blur, sharpen, sepia, greyscale, color correction and level "
                + "adjustment");
        throw new RuntimeException("Unable to split. Please do operation that allows split: "
                + "blur, sharpen, sepia, greyscale");
      }
      this.view.displayImage(this.splitImage.convertMatrixToImage());
      this.view.showMessage("The split view is visible now");
    }
    this.prevCommand = "splitToggle";
  }

  /**
   * Retrieves histogram image from model for the current image and returns it as a BufferedImage.
   *
   * @return The histogram image as a BufferedImage.
   * @throws RuntimeException If an IOException occurs during the image loading process.
   */
  public BufferedImage getHistogramImage() {
    try {
      this.model.load(this.image, "");
    } catch (IOException e) {
      this.view.showErrorMessage(e.getMessage());
      throw new RuntimeException(e);
    }
    this.model.histogram("", "");
    Image histogramImage = this.model.getHistogramImage();
    return histogramImage.convertMatrixToImage();
  }
}
