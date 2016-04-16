package program3.app;

import java.io.FileNotFoundException;

public class KMeansDriverPart3_ImageCompression {
	public static void main(String[] args) throws FileNotFoundException {
		ImageCompressor compressor = new ImageCompressor(512, 512, 2, 2, 4539);
		compressor.compressImage("imagefile", "imagefile_compressed");
		compressor.showOriginalImage();
		compressor.showCompressedImage("imagefile_compressed");
	}

}
