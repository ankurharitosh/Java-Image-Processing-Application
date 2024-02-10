import java.util.HashSet;
import java.util.Set;

import model.ModelGUI;
import model.customimage.CustomImage;
import model.customimage.Image;

/**
 * Creates a mock GUI model for testing the controller.
 */
public class MockGuiModel implements ModelGUI {

  private StringBuilder log;
  private Set<String> calledMethods;


  public MockGuiModel(StringBuilder log, int uniqueCode) {
    this.log = log;
    calledMethods = new HashSet<>();
  }

  @Override
  public void load(Image sourceImage, String imageName) {
    log.append("Input: " + imageName + "\n");
    calledMethods.add("load");
  }

  @Override
  public Image getFile(String imageName) {
    log.append("Input: " + imageName + "\n");
    Image sourceImage = new CustomImage(256, 256);
    return sourceImage;
  }

  @Override
  public void redComponent(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("red");
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("green");
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("blue");
  }

  @Override
  public void valueChannel(String imageName, String destImageName,
                           double percentage) {
    log.append(
            "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("value");
  }

  @Override
  public void lumaChannel(String imageName, String destImageName,
                          double percentage) {
    log.append(
            "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("luma");
  }

  @Override
  public void intensityChannel(String imageName, String destImageName,
                               double percentage) {
    log.append(
            "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("intensity");
  }

  @Override
  public void verticalFlip(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("verticalFlip");
  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("horizontalFlip");
  }

  @Override
  public void brightenImage(int integerConstant, String imageName, String destImageName) {
    log.append("Input: " + integerConstant + " " + imageName + " " + destImageName + "\n");
    calledMethods.add("brighten");

  }

  @Override
  public void rgbSplit(String imageName, String destImageNameRed, String destImageNameGreen,
                       String destImageNameBlue) {
    log.append(
            "Input: " + imageName + " " + destImageNameRed + " " + destImageNameGreen + " "
                    + destImageNameBlue + "\n");
    calledMethods.add("rgbSplit");

  }

  @Override
  public void rgbCombine(String imageName, String redImage, String greenImage, String blueImage) {
    log.append(
            "Input: " + imageName + " " + redImage + " " + greenImage + " " + blueImage + "\n");
    calledMethods.add("rgbCombine");

  }

  @Override
  public void blur(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("blur");
  }

  @Override
  public void sharpen(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("sharpen");

  }

  @Override
  public void sepia(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("sepia");
  }

  @Override
  public void compress(double compressPercentage, String imageName, String destImageName) {
    log.append("Input: " + compressPercentage + " " + imageName + " " + destImageName + "\n");
    calledMethods.add("compress");
  }

  @Override
  public void histogram(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
    calledMethods.add("histogram");
  }

  @Override
  public void colorCorrect(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
    calledMethods.add("colorCorrect");
  }

  @Override
  public void levelAdjustment(int shadow, int mid, int highlight, String imageName,
                              String destImageName, double percentage) {
    log.append(
            "Input: " + shadow + " " + mid + " " + highlight + " " + imageName + " " + destImageName
                    + " " + percentage + "\n");
    calledMethods.add("levelAdjustment");
  }

  /**
   * getCurrentImage method returns the current image that's being worked on.
   *
   * @return the current image.
   */
  @Override
  public Image getCurrentImage() {
    return new CustomImage(1000, 2000);
  }

  /**
   * getHistogramImage method returns the current image's histogram that's being worked on.
   *
   * @return the current image's histogram.
   */
  @Override
  public Image getHistogramImage() {
    return null;
  }


  @Override
  public Image operationPreview(Image prevImage, Image image, double percentage) {
    return null;
  }

  public boolean isMethodCalled(String methodName) {
    return calledMethods.contains(methodName);
  }
}
