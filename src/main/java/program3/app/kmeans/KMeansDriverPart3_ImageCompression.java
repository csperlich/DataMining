package program3.app.kmeans;

import java.io.File;
import java.io.IOException;

import program3.compression.ImageCompressor;

public class KMeansDriverPart3_ImageCompression {
	public static void main(String[] args) throws IOException {
		ImageCompressor compressor = new ImageCompressor(512, 512, 2, 2, 4539);
		String inputFile = "program3_data/part3/imagefile_bytes";

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

		//calculate compression factors
		System.out.println("\nCALCULATING COMPRESSION FACTORS:");
		File originalFile = new File(inputFile);
		double originalSizeInBytes = originalFile.length();
		for (int i = 0; i < outputFiles.length; i++) {

			File compressedFile = new File(outputFiles[i]);
			double compressedSizeInBytes = compressedFile.length();

			System.out.println("\n" + inputFile + ", FILE SIZE = " + originalSizeInBytes + " bytes");
			System.out.println(outputFiles[i] + ", FILE SIZE = " + compressedSizeInBytes + " bytes");
			System.out.println("COMPRESSION FACTOR = " + compressedSizeInBytes / originalSizeInBytes);
		}
	}
}
/*
SAMPLE OUTPUT
=============

COMPRESSING FILE program3_data/part3/imagefile_bytes USING GROUP SIZE OF 2 AND VARIOUS CLUSTER NUMBERS...

WRITING COMPRESSED FILES TO:
program3_data/part3/imagefile_output_8clusters
program3_data/part3/imagefile_output_16clusters
program3_data/part3/imagefile_output_32clusters
program3_data/part3/imagefile_output_64clusters
program3_data/part3/imagefile_output_128clusters

ALSO SAVING .png files for uncompressed and rendered files

CALCULATING COMPRESSION FACTORS:

program3_data/part3/imagefile_bytes, FILE SIZE = 262144.0 bytes
program3_data/part3/imagefile_output_8clusters, FILE SIZE = 131088.0 bytes
COMPRESSION FACTOR = 0.50006103515625

program3_data/part3/imagefile_bytes, FILE SIZE = 262144.0 bytes
program3_data/part3/imagefile_output_16clusters, FILE SIZE = 131104.0 bytes
COMPRESSION FACTOR = 0.5001220703125

program3_data/part3/imagefile_bytes, FILE SIZE = 262144.0 bytes
program3_data/part3/imagefile_output_32clusters, FILE SIZE = 131136.0 bytes
COMPRESSION FACTOR = 0.500244140625

program3_data/part3/imagefile_bytes, FILE SIZE = 262144.0 bytes
program3_data/part3/imagefile_output_64clusters, FILE SIZE = 131200.0 bytes
COMPRESSION FACTOR = 0.50048828125

program3_data/part3/imagefile_bytes, FILE SIZE = 262144.0 bytes
program3_data/part3/imagefile_output_128clusters, FILE SIZE = 131328.0 bytes
COMPRESSION FACTOR = 0.5009765625
 */
