package program3.app.kmeans;

import java.io.File;
import java.io.IOException;

import program3.compression.ImageCompressor;

public class KMeansDriverPart3_ImageCompression {
	public static void main(String[] args) throws IOException {
		ImageCompressor compressor = new ImageCompressor(512, 512, 2, 2, 4539);
		String inputFile = "program3_data/part3/imagefile";

		int[] numClusters = new int[] { 8, 16, 32, 64, 128 };
		String[] outputFiles = new String[] { "program3_data/part3/imagefile_output_8clusters",
				"program3_data/part3/imagefile_output_16clusters", "program3_data/part3/imagefile_output_32clusters",
				"program3_data/part3/imagefile_output_64clusters", "program3_data/part3/imagefile_output_128clusters" };

		System.out.println("COMPRESSING FILE " + inputFile + " USING GROUP SIZE OF 2 AND VARIOUS CLUSTER NUMBERS...");
		System.out.println("\nWRITING COMPRESSED FILES TO:");
		for (int i = 0; i < outputFiles.length; i++) {
			System.out.println(outputFiles[i]);
		}

		System.out.println("\nALSO SAVING .png files for uncompressed and rendered files");

		compressor.compressMultiple(numClusters, inputFile, outputFiles);
		compressor.showMultipleCompressed(numClusters, outputFiles);
		compressor.showUncompressedImage(inputFile);
		compressor.uncompressAndSaveAsPNGMultiple(numClusters, outputFiles);

		//calculate compression factor
		File originalFile = new File(inputFile);
		double originalSizeInBytes = originalFile.length();

		File compressedFile = new File(outputFiles[0]);
		double compressedSizeInBytes = compressedFile.length();

		System.out.println("\nORIGINAL FILE SIZE = " + originalSizeInBytes + " bytes");
		System.out.println("COMPRESSED FILE SIZE = " + compressedSizeInBytes + " bytes");
		System.out.println("COMPRESSION FACTOR = " + compressedSizeInBytes / originalSizeInBytes);
	}

}
/*
SAMPLE OUTPUT
=============

COMPRESSING FILE program3_data/part3/imagefile USING GROUP SIZE OF 2 AND VARIOUS CLUSTER NUMBERS...

WRITING COMPRESSED FILES TO:
program3_data/part3/imagefile_output_8clusters
program3_data/part3/imagefile_output_16clusters
program3_data/part3/imagefile_output_32clusters
program3_data/part3/imagefile_output_64clusters
program3_data/part3/imagefile_output_128clusters

ALSO SAVING .png files for uncompressed and rendered files

ORIGINAL FILE SIZE = 973488.0 bytes
COMPRESSED FILE SIZE = 131088.0 bytes
COMPRESSION FACTOR = 0.1346580543365712
 */
