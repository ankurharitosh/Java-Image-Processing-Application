package model.compression;

import model.customimage.Image;

/**
 * Interface for Haar Wavelet Compression algorithm used in image compression.
 */
public interface HaarWaveletCompression {

  /**
   * Applies Haar Wavelet Compression to the given CustomImage.
   *
   * @param inputImage         The CustomImage to be compressed.
   * @param compressionPercent The compression ratio specifying percentage of coefficients to keep.
   * @return A new CustomImage object representing the compressed image.
   */
  Image applyHaarCompression(Image inputImage, double compressionPercent);
}
