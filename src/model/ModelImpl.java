package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.adjustment.LevelAdjustment;
import model.adjustment.LevelAdjustmentImpl;
import model.colortransform.Sepia;
import model.compression.HaarWaveletCompression;
import model.compression.HaarWaveletCompressionImpl;
import model.customimage.Image;
import model.filter.BlurImage;
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
import model.processor.colorcombiner.ImageColorCombiner;
import model.processor.colorcombiner.ImageColorCombinerImpl;
import model.processor.colorcomponent.Blue;
import model.processor.colorcomponent.Green;
import model.processor.colorcomponent.ImageColorComponent;
import model.processor.colorcomponent.Red;
import model.processor.flip.HorizontalFlip;
import model.processor.flip.ImageFlip;
import model.processor.flip.VerticalFlip;

/**
 * The Model class in the MVC architecture is responsible for managing a collection of images/an
 * image and performing various image processing operations, such as blur, sharpen, etc. defined in
 * the Model interface. It encapsulates the application's data and logic related to image
 * manipulation.
 */
public class ModelImpl implements Model {

  private final Map<String, Image> imageMap;

  /**
   * Create a new ModelImpl instance.
   */
  public ModelImpl() {
    this.imageMap = new HashMap<>();
  }

  /**
   * Load an image from the specified path and with the given name.
   *
   * @param sourceImage The source image of the file.
   * @param imageName   The name of the image.
   * @throws IOException if there's an issue with loading the image.
   */
  @Override
  public void load(Image sourceImage, String imageName)
      throws IOException {
    this.imageMap.put(imageName, sourceImage);
  }

  /**
   * Save the current image to the specified path and with the given name.
   *
   * @param imageName The name of the image.
   */
  @Override
  public Image getFile(String imageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map");
    }
    return sourceImage;
  }

  /**
   * Apply a red component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void redComponent(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map");
    }

    ImageColorComponent imageColorComponent = new Red();
    Image destinationImage = imageColorComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageName, destinationImage);
  }

  /**
   * Apply a green component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void greenComponent(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map");
    }

    ImageColorComponent imageColorComponent = new Green();
    Image destinationImage = imageColorComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageName, destinationImage);
  }

  /**
   * Apply a blue component operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void blueComponent(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map");
    }

    ImageColorComponent imageColorComponent = new Blue();
    Image destinationImage = imageColorComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageName, destinationImage);
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
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageChannel imageChannel = new Value();
    Image previewImage = this.getChannelImage(sourceImage, imageChannel, percentage);
    this.imageMap.put(destImageName, previewImage);
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
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageChannel imageChannel = new Luma();
    Image previewImage = this.getChannelImage(sourceImage, imageChannel, percentage);
    this.imageMap.put(destImageName, previewImage);
  }


  /**
   * Apply a intensity color representation operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void intensityChannel(String imageName, String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageChannel imageChannel = new Intensity();
    Image previewImage = this.getChannelImage(sourceImage, imageChannel, percentage);
    this.imageMap.put(destImageName, previewImage);
  }


  private Image getChannelImage(Image sourceImage, ImageChannel imageChannel, double percentage) {
    Image previewImage;
    if (percentage == 100) {
      previewImage = imageChannel.applyChannel(sourceImage);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image channelImage = imageChannel.applyChannel(half);
      previewImage = operationPreview.concatenateImages(channelImage, otherHalf);
    }
    return previewImage;
  }

  /**
   * Apply an image Vertical flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void verticalFlip(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageFlip imageFlip = new VerticalFlip();
    Image destinationImage = imageFlip.flipImage(sourceImage);
    this.imageMap.put(destImageName, destinationImage);
  }

  /**
   * Apply an image Horizontal flip operation to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image.
   */
  @Override
  public void horizontalFlip(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageFlip imageFlip = new HorizontalFlip();
    Image destinationImage = imageFlip.flipImage(sourceImage);
    this.imageMap.put(destImageName, destinationImage);
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
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageBrightness imageBrightness = (integerConstant >= 0) ? new Brighten() : new Darken();
    Image destinationImage = imageBrightness.adjustImage(sourceImage, integerConstant);
    this.imageMap.put(destImageName, destinationImage);
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
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    ImageColorComponent redComponent = new Red();
    Image redImage = redComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageNameRed, redImage);

    ImageColorComponent greenComponent = new Green();
    Image greenImage = greenComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageNameGreen, greenImage);

    ImageColorComponent blueComponent = new Blue();
    Image blueImage = blueComponent.getColorComponent(sourceImage);
    this.imageMap.put(destImageNameBlue, blueImage);
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
    Image redComponent = this.imageMap.get(redImage);
    Image greenComponent = this.imageMap.get(greenImage);
    Image blueComponent = this.imageMap.get(blueImage);

    if (redComponent == null || greenComponent == null || blueComponent == null) {
      throw new IllegalArgumentException("One or more channel images not found in the map.");
    }

    ImageColorCombiner imageColorComponent = new ImageColorCombinerImpl();
    Image combinedImage = imageColorComponent.combineColorComponent(
        redComponent, greenComponent, blueComponent);

    this.imageMap.put(imageName, combinedImage);
  }

  /**
   * Apply a blur filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the blur.
   */
  @Override
  public void blur(String imageName, String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    BlurImage blurImage = new BlurImage();
    Image previewImage;
    if (percentage == 100) {
      previewImage = blurImage.applyFilter(sourceImage);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image blurredImage = blurImage.applyFilter(half);
      previewImage = operationPreview.concatenateImages(blurredImage, otherHalf);
    }

    this.imageMap.put(destImageName, previewImage);
  }

  /**
   * Apply a sharpen filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sharpen filter.
   */
  @Override
  public void sharpen(String imageName, String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    SharpenImage sharpenImage = new SharpenImage();
    Image previewImage;
    if (percentage == 100) {
      previewImage = sharpenImage.applyFilter(sourceImage);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image sharpenedImage = sharpenImage.applyFilter(half);
      previewImage = operationPreview.concatenateImages(sharpenedImage, otherHalf);
    }

    this.imageMap.put(destImageName, previewImage);
  }

  /**
   * Apply a sepia filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the sepia filter.
   */
  @Override
  public void sepia(String imageName, String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    Sepia sepia = new Sepia();
    Image previewImage;
    if (percentage == 100) {
      previewImage = sepia.applyColorTransform(sourceImage);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image sharpenedImage = sepia.applyColorTransform(half);
      previewImage = operationPreview.concatenateImages(sharpenedImage, otherHalf);
    }

    this.imageMap.put(destImageName, previewImage);
  }

  /**
   * Apply a compression to the image.
   *
   * @param compressPercentage The percentage of compression.
   * @param imageName          The name of the source image.
   * @param destImageName      The name of destination image after applying colorCorrect filter.
   */
  @Override
  public void compress(double compressPercentage, String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    HaarWaveletCompression haar = new HaarWaveletCompressionImpl();
    Image previewImage = haar.applyHaarCompression(sourceImage, compressPercentage);
    this.imageMap.put(destImageName, previewImage);
  }

  /**
   * Apply a histogram filter to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying the histogram filter.
   */
  @Override
  public void histogram(String imageName, String destImageName) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }
    Histogram histogram = new VisualizeHistogram();
    Image histogramImage = histogram.histogramOperation(sourceImage);
    this.imageMap.put(destImageName, histogramImage);
  }

  /**
   * Apply a color correction to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of destination image after applying color correction filter.
   */
  @Override
  public void colorCorrect(String imageName, String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    Histogram colorCorrection = new ColorCorrection();
    Image previewImage;
    if (percentage == 100) {
      previewImage = colorCorrection.histogramOperation(sourceImage);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image correctedImage = colorCorrection.histogramOperation(half);
      previewImage = operationPreview.concatenateImages(correctedImage, otherHalf);
    }

    this.imageMap.put(destImageName, previewImage);
    this.histogram(destImageName, "Histogram-" + destImageName);
  }

  /**
   * Apply a color correction to the image.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name of the destination image after applying levelAdjustment filter.
   * @param percentage    The percentage of splitting.
   */
  @Override
  public void levelAdjustment(int shadow, int mid, int highlight, String imageName,
      String destImageName, double percentage) {
    Image sourceImage = this.imageMap.get(imageName);
    if (sourceImage == null) {
      throw new IllegalArgumentException("Source image not found in the map.");
    }

    LevelAdjustment levelAdjustment = new LevelAdjustmentImpl();
    Image previewImage;
    if (percentage == 100) {
      previewImage = levelAdjustment.adjustImageLevels(sourceImage, shadow, mid, highlight);
    } else {
      OperationPreview operationPreview = new OperationPreviewImpl();
      Image[] images = operationPreview.getNPercentImage(sourceImage, percentage);
      Image half = images[0];
      Image otherHalf = images[1];
      Image levelAdjustmentImage = levelAdjustment.adjustImageLevels(half, shadow, mid, highlight);
      previewImage = operationPreview.concatenateImages(levelAdjustmentImage, otherHalf);
    }

    this.imageMap.put(destImageName, previewImage);
  }
}
