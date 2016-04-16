package program3.app;

import java.io.File;
import java.io.IOException;

public class KMeansDriverPart3_ImageCompression {
	public static void main(String[] args) throws IOException {
		ImageCompressor compressor = new ImageCompressor(512, 512, 2, 2, 4539);
		String inputFile = "program3_data/part3/imagefile";

		int[] numClusters = new int[] { 8, 16, 32, 64, 128 };
		String[] outputFiles = new String[] { "program3_data/part3/imagefile_output_8clusters",
				"program3_data/part3/imagefile_output_16clusters", "program3_data/part3/imagefile_output_32clusters",
				"program3_data/part3/imagefile_output_64clusters", "program3_data/part3/imagefile_output_128clusters" };

		System.out.println("COMPRESSING FILE " + inputFile + " USING GROUP SIZE OF 2 AND VARIOUS CLUSTER NUMBERS...");
		System.out.println("WRITING COMPRESSED FILES TO:");
		for (int i = 0; i < outputFiles.length; i++) {
			System.out.println(outputFiles[i]);
		}
		System.out.println("ALSO SAVING .png files for uncompressed and rendered files");
		compressor.compressMultiple(numClusters, inputFile, outputFiles);
		compressor.showMultipleCompressed(numClusters, outputFiles);
		compressor.showUncompressedImage(inputFile);
		compressor.uncompressAndSaveAsPNGMultiple(numClusters, outputFiles);

		File originalFile = new File(inputFile);
		double originalSizeInBytes = originalFile.length();

		File compressedFile = new File(outputFiles[0]);
		double compressedSizeInBytes = compressedFile.length();

		System.out.println("ORIGINAL FILE SIZE = " + originalSizeInBytes);
		System.out.println("COMPRESSED FILE SIZE = " + compressedSizeInBytes);
		System.out.println("COMPRESSION FACTOR = " + compressedSizeInBytes / originalSizeInBytes);
	}

}
