import model.Model;
import model.customimage.CustomImage;
import model.customimage.Image;

/**
 * Creates a mock model for testing the controller.
 */
public class MockModel implements Model {

  private StringBuilder log;

  public MockModel(StringBuilder log, int uniqueCode) {
    this.log = log;
  }

  @Override
  public void load(Image sourceImage, String imageName) {
    log.append("Input: " + imageName + "\n");
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
  }

  @Override
  public void greenComponent(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void blueComponent(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void valueChannel(String imageName, String destImageName,
      double percentage) {
    log.append(
        "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void lumaChannel(String imageName, String destImageName,
      double percentage) {
    log.append(
        "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void intensityChannel(String imageName, String destImageName,
      double percentage) {
    log.append(
        "Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void verticalFlip(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void brightenImage(int integerConstant, String imageName, String destImageName) {
    log.append("Input: " + integerConstant + " " + imageName + " " + destImageName + "\n");

  }

  @Override
  public void rgbSplit(String imageName, String destImageNameRed, String destImageNameGreen,
      String destImageNameBlue) {
    log.append(
        "Input: " + imageName + " " + destImageNameRed + " " + destImageNameGreen + " "
            + destImageNameBlue + "\n");

  }

  @Override
  public void rgbCombine(String imageName, String redImage, String greenImage, String blueImage) {
    log.append(
        "Input: " + imageName + " " + redImage + " " + greenImage + " " + blueImage + "\n");

  }

  @Override
  public void blur(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void sharpen(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");

  }

  @Override
  public void sepia(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void compress(double compressPercentage, String imageName, String destImageName) {
    log.append("Input: " + compressPercentage + " " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void histogram(String imageName, String destImageName) {
    log.append("Input: " + imageName + " " + destImageName + "\n");
  }

  @Override
  public void colorCorrect(String imageName, String destImageName, double percentage) {
    log.append("Input: " + imageName + " " + destImageName + " " + percentage + "\n");
  }

  @Override
  public void levelAdjustment(int shadow, int mid, int highlight, String imageName,
      String destImageName, double percentage) {
    log.append(
        "Input: " + shadow + " " + mid + " " + highlight + " " + imageName + " " + destImageName
            + " " + percentage + "\n");
  }
}
