package program3.app;

import java.io.FileNotFoundException;

public class KMeansDriverPart3_ImageCompression {
	public static void main(String[] args) throws FileNotFoundException {
		ImageCompressor compressor = new ImageCompressor(512, 512, 2, 2, 4539);
		//compressor.compressImage("imagefile", "imagefile_compressed");
		//compressor.showUncompressedImage("imagefile");
		//compressor.showCompressedImage("imagefile_compressed");

		int[] numClusters = new int[] { 8, 16, 32, 64, 128 };
		String[] outputFiles = new String[] { "imagefile_output_8clusters", "imagefile_output_16clusters",
				"imagefile_output_32clusters", "imagefile_output_64clusters", "imagefile_output_128clusters" };

		compressor.compressMultiple(numClusters, "imagefile", outputFiles);
		compressor.showMultipleCompressed(numClusters, outputFiles);
	}

}
