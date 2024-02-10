package model;


import java.io.IOException;

import model.adjustment.LevelAdjustment;
import model.adjustment.LevelAdjustmentImpl;
import model.colortransform.ImageLinearColorTransform;
import model.colortransform.Sepia;
import model.compression.HaarWaveletCompression;
import model.compression.HaarWaveletCompressionImpl;
import model.customimage.Image;
import model.filter.BlurImage;
import model.filter.ImageFilters;
import model.filter.SharpenImage;
import model.histogram.ColorCorrection;
import model.histogram.Histogram;
import model.histogram.VisualizeHistogram;
import model.preview.OperationPreview;
import model.preview.OperationPreviewImpl;
import model.processor.brightness.Brighten;
import model.processor.brightness.Darken;
import model.processor.brightness.ImageBrightness;
import model.processor.channel.ImageChannel;
import model.processor.channel.Intensity;
import model.processor.channel.Luma;
import model.processor.channel.Value;
import model.processor.colorcomponent.Blue;
import model.processor.colorcomponent.Green;
import model.processor.colorcomponent.ImageColorComponent;
import model.processor.colorcomponent.Red;
import model.processor.flip.HorizontalFlip;
import model.processor.flip.ImageFlip;
import model.processor.flip.VerticalFlip;

/**
 * An extension to the existing ModelImpl. This model doesn't store images in a map and hence saving
 * space in the heap and allowing user to do more operations.
 */
public class ModelGUIImpl implements ModelGUI {

  private Image image;
  private Image histogramImage;

  /**
   * Load an image from the specified path and with the given name.
   *
   * @param sourceImage The source image of the file.
   * @param imageName   The name of the image.
   * @throws IOException if there's an issue with loading the image.
   */
  @Override
  public void load(Image sourceImage, String imageName) throws IOException {
    this.image = sourceImage;
  }

  /**
   * Save the current image to the specified path and with the given name.
   *
   * @param imageName The name of the image.
   */
  @Override
  public Image getFile(String imageName) {
    return null;
  }

  /**
   * Apply a red component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void redComponent(String imageName, String destImageName) {
    ImageColorComponent redComponent = new Red();
    this.image = redComponent.getColorComponent(this.image);
  }

  /**
   * Apply a green component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void greenComponent(String imageName, String destImageName) {
    ImageColorComponent greenComponent = new Green();
    this.image = greenComponent.getColorComponent(this.image);
  }

  /**
   * Apply a blue component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void blueComponent(String imageName, String destImageName) {
    ImageColorComponent blueComponent = new Blue();
    this.image = blueComponent.getColorComponent(this.image);
  }

  /**
   * Apply a value color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void valueChannel(String imageName, String destImageName, double percentage) {
    ImageChannel value = new Value();
    this.image = value.applyChannel(this.image);
  }

  /**
   * Apply a luma color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void lumaChannel(String imageName, String destImageName, double percentage) {
    ImageChannel luma = new Luma();
    this.image = luma.applyChannel(this.image);
  }

  /**
   * Apply an intensity color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void intensityChannel(String imageName, String destImageName, double percentage) {
    ImageChannel intensity = new Intensity();
    this.image = intensity.applyChannel(this.image);
  }

  /**
   * Apply an image vertical flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void verticalFlip(String imageName, String destImageName) {
    ImageFlip verticalFlip = new VerticalFlip();
    this.image = verticalFlip.flipImage(this.image);
  }

  /**
   * Apply an image horizontal flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    ImageFlip horizontalFlip = new HorizontalFlip();
    this.image = horizontalFlip.flipImage(this.image);
  }

  /**
   * Adjust the brightness of the image by the given integer constant.
   *
   * @param integerConstant The constant by which to adjust the brightness.
   * @param imageName       The name of the source image.
   * @param destImageName   The name of the destination image.
   */
  @Override
  public void brightenImage(int integerConstant, String imageName, String destImageName) {
    ImageBrightness imageBrightness = (integerConstant >= 0) ? new Brighten() : new Darken();
    this.image = imageBrightness.adjustImage(this.image, integerConstant);
  }

  /**
   * Split the RGB channels of the image into separate images.
   *
   * @param imageName          The name of the source image.
   * @param destImageNameRed   The name of the destination red channel image.
   * @param destImageNameGreen The name of the destination green channel image.
   * @param destImageNameBlue  The name of the destination blue channel image.
   */
  @Override
  public void rgbSplit(String imageName, String destImageNameRed, String destImageNameGreen,
      String destImageNameBlue) {
    // Not required for GUI
  }

  /**
   * Combine the red, green, and blue channel images into a single RGB image.
   *
   * @param imageName  The name of the source image.
   * @param redImage   The name of the red channel image.
   * @param greenImage The name of the green channel image.
   * @param blueImage  The name of the blue channel image.
   */
  @Override
  public void rgbCombine(String imageName, String redImage, String greenImage, String blueImage) {
    // Implementation is not required for GUI
  }

  /**
   * Apply a blur filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the blur.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void blur(String imageName, String destImageName, double percentage) {
    ImageFilters blur = new BlurImage();
    this.image = blur.applyFilter(this.image);
  }

  /**
   * Apply a sharpen filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sharpen filter.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void sharpen(String imageName, String destImageName, double percentage) {
    ImageFilters sharpen = new SharpenImage();
    this.image = sharpen.applyFilter(this.image);
  }

  /**
   * Apply a sepia filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sepia filter.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void sepia(String imageName, String destImageName, double percentage) {
    ImageLinearColorTransform sepia = new Sepia();
    this.image = sepia.applyColorTransform(this.image);
  }

  /**
   * Apply a compression to the image.
   *
   * @param compressPercentage The percentage of compression.
   * @param imageName          The name of the source image.
   * @param destImageName      The name of the destination image after applying the colorCorrect
   *                           filter.
   */
  @Override
  public void compress(double compressPercentage, String imageName, String destImageName) {
    HaarWaveletCompression haar = new HaarWaveletCompressionImpl();
    this.image = haar.applyHaarCompression(this.image, compressPercentage);
  }

  /**
   * Apply a histogram filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sepia filter.
   */
  @Override
  public void histogram(String imageName, String destImageName) {
    Histogram histogram = new VisualizeHistogram();
    this.histogramImage = histogram.histogramOperation(this.image);
  }

  /**
   * Apply a color correction to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the colorCorrect filter.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void colorCorrect(String imageName, String destImageName, double percentage) {
    Histogram colorCorrection = new ColorCorrection();
    this.image = colorCorrection.histogramOperation(this.image);
  }

  /**
   * Apply a color correction to the image.
   *
   * @param shadow        shadow/black component
   * @param mid           mid component
   * @param highlight     highlight/white
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying levelAdjustment filter.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void levelAdjustment(int shadow, int mid, int highlight, String imageName,
      String destImageName, double percentage) {
    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    this.image = levelAdjustment.adjustImageLevels(this.image, shadow, mid,
        highlight);
  }

  @Override
  public Image operationPreview(Image prevImage, Image image, double percentage) {
    Image previewImage;
    if (percentage == 100) {
      previewImage = image;
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] sourceImages = operationPreview.getNPercentImage(prevImage, 100.0 - percentage);
      Image[] images = operationPreview.getNPercentImage(image, 100.0 - percentage);
      Image half = sourceImages[0];
      Image otherHalf = images[1];
      previewImage = operationPreview.concatenateImages(half, otherHalf);
    }
    return previewImage;
  }

  /**
   * getCurrentImage method returns the current image that's being worked on.
   *
   * @return the current image.
   */
  public Image getCurrentImage() {
    return this.image;
  }

  /**
   * getHistogramImage method returns the current image's histogram that's being worked on.
   *
   * @return the current image's histogram.
   */
  @Override
  public Image getHistogramImage() {
    return this.histogramImage;
  }
}
