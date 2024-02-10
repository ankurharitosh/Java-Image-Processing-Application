package model.histogram;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import model.customimage.CustomImage;
import model.customimage.Image;
import model.processor.SupportedColorComponents;

/**
 * An abstract class providing a common structure for histogram-related operations on images.
 * Classes extending this abstract class are expected to implement specific histogram operations.
 */
public abstract class AbstractHistogram implements Histogram {

  protected int histogramHeight;
  protected int histogramWidth;
  protected int[][] histogramMatrix;
  protected BufferedImage bufferedImage;
  protected float[] scaledColors;
  protected SupportedColorComponents[] pixelColors;
  protected Color[] lineColor;
  protected boolean isGreyScale;

  /**
   * A constructor to set up the Histogram to visualize the image.
   */
  public AbstractHistogram() {
    this.histogramHeight = 256;
    this.histogramWidth = 256;
    this.histogramMatrix = new int[3][this.histogramWidth];
    for (int i = 0; i < 3; i++) {
      Arrays.fill(this.histogramMatrix[i], 0);
    }
    this.bufferedImage = new BufferedImage(this.histogramWidth, this.histogramHeight,
        BufferedImage.TYPE_INT_RGB);
    this.scaledColors = new float[3];
    this.lineColor = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
    this.pixelColors = new SupportedColorComponents[]{SupportedColorComponents.RED,
        SupportedColorComponents.GREEN, SupportedColorComponents.BLUE};
    this.isGreyScale = true;
  }

  /**
   * Create a Histogram to visualize the image.
   *
   * @param inputImage The input image to be processed in 3D array format.
   * @return The processed image.
   */
  @Override
  public Image histogramOperation(Image inputImage) {
    setColorFrequency(inputImage);
    setColorScale();
    if (isColorCorrect()) {
      return colorCorrection(inputImage);
    }
    scaleFreqMatrix();
    checkGreyScale(inputImage);
    createHistogramGraphics();
    Image outputImage = new CustomImage(this.histogramHeight, this.histogramWidth);
    outputImage.setPixelValuesFromBufferedImage(this.bufferedImage);
    return outputImage;
  }

  protected abstract boolean isColorCorrect();

  protected void setColorFrequency(Image inputImage) {
    int imageHeight = inputImage.getHeight();
    int imageWidth = inputImage.getWidth();
    int color;

    for (int y = 0; y < imageHeight; y++) {
      for (int x = 0; x < imageWidth; x++) {
        for (int i = 0; i < 3; i++) {
          color = inputImage.getPixelValue(y, x, this.pixelColors[i].ordinal());
          this.histogramMatrix[i][color]++;
        }
      }
    }
  }

  protected void scaleFreqMatrix() {
    for (int i = 0; i < this.histogramWidth; i++) {
      for (int c = 0; c < 3; c++) {
        this.histogramMatrix[c][i] = getScaledFreq(c, i);
      }
    }
  }

  protected Image colorCorrection(Image inputImage) {
    int[] colorIndexes = new int[3];
    Arrays.fill(colorIndexes, 0);

    for (int i = 0; i < 3; i++) {
      colorIndexes[i] = getMaxIndex(this.histogramMatrix[i]);
    }

    int avgIndex = (int) Arrays.stream(colorIndexes).average().getAsDouble();

    return adjustChannels(inputImage, colorIndexes, avgIndex);
  }

  private Image adjustChannels(Image inputImage, int[] colorIndexes, int avgIndex) {
    int imageHeight = inputImage.getHeight();
    int imageWidth = inputImage.getWidth();
    Image outputImage = new CustomImage(imageHeight, imageWidth);
    int[] color = new int[3];

    for (int y = 0; y < imageHeight; y++) {
      for (int x = 0; x < imageWidth; x++) {
        for (int i = 0; i < 3; i++) {
          color[i] = inputImage.getPixelValue(y, x, this.pixelColors[i].ordinal());
          color[i] = adjustColor(color[i], colorIndexes[i], avgIndex);
          outputImage.setPixelValue(y, x, this.pixelColors[i].ordinal(), color[i]);
        }
      }
    }

    return outputImage;
  }

  private int adjustColor(int color, int colorIndex, int avgIndex) {
    if (color > 10 && color < 245) {
      int offset = avgIndex - colorIndex;
      color = Math.max(0, Math.min(255, color + offset));
    }

    return color;
  }

  protected void checkGreyScale(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int red;
    int green;
    int blue;

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        red = image.getRed(j, i);
        green = image.getGreen(j, i);
        blue = image.getBlue(j, i);

        if (red != green || green != blue) {
          this.isGreyScale = false;
          return;
        }
      }
    }
  }


  protected void createHistogramGraphics() {
    Graphics2D histogramImageGraphics = setupInitialGraph();

    for (int i = 1; i < this.histogramWidth; i++) {
      // Draws a line, using the current color, between the points (x1, y1) and (x2, y2) in this
      // graphics context's coordinate system.
      for (int c = 0; c < 3; c++) {
        if (isGreyScale) {
          histogramImageGraphics.setColor(Color.DARK_GRAY);
        } else {
          histogramImageGraphics.setColor(this.lineColor[c]);
        }

        histogramImageGraphics.drawLine(i - 1, this.histogramMatrix[c][i - 1],
            i, this.histogramMatrix[c][i]);
      }
    }
    createGridPattern(histogramImageGraphics);
    histogramImageGraphics.dispose();
  }

  protected Graphics2D setupInitialGraph() {
    Graphics2D histogramImageGraphics = this.bufferedImage.createGraphics();
    histogramImageGraphics.setBackground(Color.WHITE);
    histogramImageGraphics.clearRect(0, 0, this.histogramWidth, this.histogramHeight);
    return histogramImageGraphics;
  }

  protected void createGridPattern(Graphics2D histogramImageGraphics) {
    histogramImageGraphics.setColor(Color.LIGHT_GRAY);
    histogramImageGraphics.setStroke(new BasicStroke(1));

    for (int j = 0; j < this.histogramHeight; j += 16) {
      histogramImageGraphics.drawLine(0, j, this.histogramWidth, j);
    }

    for (int i = 0; i < this.histogramWidth; i += 16) {
      histogramImageGraphics.drawLine(i, 0, i, this.histogramHeight);
    }
  }

  protected int getScaledFreq(int c, int i) {
    int highestPeak = this.histogramHeight - 1;
    int normalizedValue = this.histogramMatrix[c][i] * highestPeak / getMaxFrequency();
    return Math.max(0, Math.min(255, highestPeak - normalizedValue));
  }


  private int getMaxFrequency() {
    int maxFrequency = 0;
    for (int c = 0; c < 3; c++) {
      int channelMax = Arrays.stream(histogramMatrix[c]).max().orElse(1);
      maxFrequency = Math.max(maxFrequency, channelMax);
    }
    return maxFrequency;
  }

  protected void setColorScale() {
    for (int i = 0; i < 3; i++) {
      float highestFrequency = Arrays.stream(this.histogramMatrix[i]).max().orElse(1);
      this.scaledColors[i] = ((float) this.histogramHeight / highestFrequency);
    }
  }

  protected int getMaxIndex(int[] freqMatrix) {
    int maxIndex = -1;
    int maxValue = Integer.MIN_VALUE;

    for (int i = 11; i < freqMatrix.length - 11; i++) {
      if (freqMatrix[i] > maxValue) {
        maxValue = freqMatrix[i];
        maxIndex = i;
      }
    }

    return maxIndex;
  }
}
