package model.compression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.customimage.CustomImage;
import model.customimage.Image;


/**
 * Implementation of the Haar Wavelet Compression algorithm for image compression.
 */
public class HaarWaveletCompressionImpl implements HaarWaveletCompression {

  /**
   * Applies Haar Wavelet Compression to the given CustomImage.
   *
   * @param inputImage         The CustomImage to be compressed.
   * @param compressionPercent The percentage of coefficients to keep during compression.
   * @return A new CustomImage object representing the compressed image.
   */
  public Image applyHaarCompression(Image inputImage, double compressionPercent)
          throws IllegalArgumentException {
    if (compressionPercent < 0 || compressionPercent > 100) {
      throw new IllegalArgumentException("Compression percent should be between 0 and 100");
    }

    int actualHeight = inputImage.getHeight();
    int actualWidth = inputImage.getWidth();
    int[][][] originalData = inputImage.getPixelData();
    Image outputImage = new CustomImage(inputImage.getHeight(), inputImage.getWidth());

    for (int color = 0; color < 3; color++) {
      double[][] channelData = new double[actualHeight][actualWidth];
      for (int y = 0; y < actualHeight; y++) {
        for (int x = 0; x < actualWidth; x++) {
          channelData[y][x] = originalData[y][x][color];
        }
      }

      double[][] transformedChannel = applyHaarTransform(channelData, compressionPercent);
      double[][] reconstructedChannel = inverseHaarTransform(transformedChannel);

      for (int y = 0; y < actualHeight; y++) {
        for (int x = 0; x < actualWidth; x++) {
          outputImage.setPixelValue(y, x, color, (int) Math.max(0, Math.min(255,
                  reconstructedChannel[y][x])));
        }
      }
    }

    return outputImage;
  }


  /**
   * Applies Haar Wavelet Transform to a single channel of an image.
   *
   * @param imageSingleChannel The single-channel image data.
   * @param compressionPercent The percentage of coefficients to keep during compression.
   * @return The transformed image data.
   */
  private double[][] applyHaarTransform(double[][] imageSingleChannel, double compressionPercent) {
    int height = imageSingleChannel.length;
    int width = imageSingleChannel[0].length;
    if (height == 1 || width <= 1) {
      return imageSingleChannel;
    }

    int paddedHeight = nextPowerOfTwo(height);
    int paddedWidth = nextPowerOfTwo(width);
    int c = Math.max(paddedWidth, paddedHeight);

    double[][] paddedData = new double[c][c];

    for (int i = 0; i < height; i++) {
      System.arraycopy(imageSingleChannel[i], 0, paddedData[i], 0, width);
    }

    while (c > 1) {
      for (double[] paddedDatum : paddedData) {
        System.arraycopy(transformRow(paddedDatum, c), 0, paddedDatum,
                0, c);
      }
      for (int x = 0; x < paddedData.length; x++) {
        double[] columnData = new double[c];
        for (int y = 0; y < c; y++) {
          columnData[y] = paddedData[y][x];
        }
        double[] transformedColumn = transformRow(columnData, c);
        for (int y = 0; y < c; y++) {
          paddedData[y][x] = transformedColumn[y];
        }
      }
      c /= 2;
    }


    paddedData = setNSmallestToZero(paddedData, (int) compressionPercent);
    return paddedData;
  }


  /**
   * Calculates the next power of two for a given number.
   *
   * @param num The input number.
   * @return The next power of two.
   */
  private int nextPowerOfTwo(int num) {
    int result = 1;
    while (result < num) {
      result *= 2;
    }
    return result;
  }


  /**
   * Transforms a row using the Haar Wavelet Transform.
   *
   * @param array The input array.
   * @param size  The size of the array.
   * @return The transformed array.
   */
  private double[] transformRow(double[] array, int size) {
    int halfLength = size / 2;
    double[] avgArray = new double[halfLength];
    double[] diffArray = new double[halfLength];

    for (int i = 0; i < size; i += 2) {
      double a = array[i];
      double b = array[i + 1];
      avgArray[i / 2] = (a + b) / Math.sqrt(2);
      diffArray[i / 2] = (a - b) / Math.sqrt(2);
    }

    double[] transformedArray = new double[size];


    System.arraycopy(avgArray, 0, transformedArray, 0, halfLength);
    System.arraycopy(diffArray, 0, transformedArray, halfLength, halfLength);

    return transformedArray;
  }

  /**
   * Applies the inverse Haar Wavelet Transform to the given data.
   *
   * @param data The Haar-transformed data.
   * @return The inverse-transformed data.
   */
  private double[][] inverseHaarTransform(double[][] data) {
    int c = 2;

    int height = data.length;
    int width = data[0].length;
    int s = Math.max(nextPowerOfTwo(height), nextPowerOfTwo(width));

    while (c <= s) {
      for (int j = 0; j < s; j++) {
        double[] columnData = new double[c];
        for (int i = 0; i < c; i++) {
          columnData[i] = data[i][j];
        }
        double[] transformedColumn = inverseTransformRow(columnData, c);
        for (int i = 0; i < c; i++) {
          data[i][j] = transformedColumn[i];
        }
      }
      for (int i = 0; i < s; i++) {
        System.arraycopy(inverseTransformRow(data[i], c), 0, data[i], 0, c);
      }

      c *= 2;
    }

    return data;
  }

  /**
   * Applies the inverse Haar Wavelet Transform to a single row of data.
   *
   * @param array The input array.
   * @param size  The size of the array.
   * @return The inverse-transformed array.
   */
  private double[] inverseTransformRow(double[] array, int size) {
    int halfLength = size / 2;
    double[] avgArray = new double[halfLength];
    double[] diffArray = new double[halfLength];

    for (int i = 0, j = halfLength; i < halfLength; i++, j++) {
      double a = array[i];
      double b = array[j];
      avgArray[i] = (a + b) / Math.sqrt(2);
      diffArray[i] = (a - b) / Math.sqrt(2);
    }

    double[] invertedArray = new double[size];

    for (int i = 0; i < halfLength; i++) {
      invertedArray[2 * i] = avgArray[i];
      invertedArray[(2 * i) + 1] = diffArray[i];
    }

    return invertedArray;
  }

  /**
   * Sets the N smallest absolute values in a 2D array to zero.
   *
   * @param array The input array.
   * @param n     The number of smallest values to set to zero.
   * @return The modified array.
   */
  private double[][] setNSmallestToZero(double[][] array, int n) {
    if (n <= 0 || n >= array.length * array[0].length) {
      return array;
    }

    int rowCount = array.length;
    int colCount = array[0].length;
    int nonZeroCount = 0;

    Set<Double> uniqueAbsValues = new HashSet<>();

    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        double absValue = Math.abs(array[i][j]);
        if (absValue != 0 && uniqueAbsValues.add(absValue)) {
          nonZeroCount++;
        }
      }
    }

    Double[] uniqueAbsArray = uniqueAbsValues.toArray(new Double[0]);
    Arrays.sort(uniqueAbsArray);

    int thresholdIndex = (int) (n / 100.0 * (nonZeroCount - 1));
    double threshold = uniqueAbsArray[thresholdIndex];

    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < colCount; j++) {
        if (Math.abs(array[i][j]) < threshold) {
          array[i][j] = 0;
        }
      }
    }
    return array;
  }
}