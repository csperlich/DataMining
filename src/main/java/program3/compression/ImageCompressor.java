package program3.compression;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import program3.clustering.Kmeans;
import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

public class ImageCompressor {

	private int rows;	//row dim of image
	private int cols;	//col dim of image
	private int groupingSize; //number of values to group together for cluster compression
	private Kmeans clusterer;
	private int numClusters;  //num clusters to use in cluster compression
	private int clusterSeed;

	public ImageCompressor(int rows, int cols, int groupingSize, int numClusters, int clusterSeed) {
		this.rows = rows;
		this.cols = cols;
		this.groupingSize = groupingSize;
		this.clusterer = new Kmeans(null);
		this.numClusters = numClusters;
		this.clusterSeed = clusterSeed;
	}

	public void setNumClusters(int numClusters) {
		this.numClusters = numClusters;
	}

	//reads an uncompressed image stored as grayscale byte values and 
	//outputs writes a compressed image whose values are bytes corresponding to
	//which centroid (cluster) the grouped values belong to
	public void compressImage(String inputImage, String outputImage) throws IOException {
		//read in image
		int[][] originalPixelValues = this.readUncompressedPixelData(inputImage);

		//perform clustering
		this.clusterer.load(this.getRecordsFromPixels(originalPixelValues));
		this.clusterer.setParameters(this.numClusters, this.clusterSeed);
		this.clusterer.cluster();
		List<IClusteringRecord> records = this.clusterer.getRecords();

		//compress based on centroids of clusters
		int recordsIndex = 0;
		int[][] compressedCentroidIndices = new int[this.rows][this.cols / this.groupingSize];
		for (int i = 0; i < compressedCentroidIndices.length; i++) {
			for (int j = 0; j < compressedCentroidIndices[i].length; j++) {
				compressedCentroidIndices[i][j] = records.get(recordsIndex++).getCluster();
			}
		}
		this.writeCompressedImage(outputImage, compressedCentroidIndices);
	}

	// writes each row of compressed centroid image data to one line
	// writes the centroid values as ints between 0 and 255 to the end of the file
	private void writeCompressedImage(String outputImage, int[][] compressedCentroidIndices) throws IOException {

		FileOutputStream fos = new FileOutputStream(new File(outputImage));
		for (int i = 0; i < compressedCentroidIndices.length; i++) {
			for (int j = 0; j < compressedCentroidIndices[i].length; j++) {
				fos.write(compressedCentroidIndices[i][j]);
			}
		}

		List<IClusteringRecord> centroids = this.clusterer.getCentroids();
		for (int i = 0; i < this.numClusters; i++) {
			double[] clusterAttributes = centroids.get(i).getAttributes();
			for (int j = 0; j < clusterAttributes.length; j++) {
				fos.write(this.convertToGrayscaleInt(clusterAttributes[j]));
			}
		}
		fos.close();
	}

	//bounds a double value to a 0 to 255 int grayscale value
	private int convertToGrayscaleInt(double d) {
		if (d < 0) {
			return 0;
		} else if (d > 255) {
			return 255;
		} else {
			return (int) Math.round(d);
		}
	}

	//reads and displays an uncompressed image
	public void showUncompressedImage(String fileName) throws IOException {

		int[][] pixelData = this.readUncompressedPixelData(fileName);
		this.displayImage(this.getImage(pixelData), "original");

	}

	//reads a compressed image and returns the centroid group numbers in a 2d array
	private int[][] readCompressedImage(String fileName) throws IOException {

		FileInputStream fis = new FileInputStream(new File(fileName));
		int[][] centroidIndices = new int[this.rows][this.cols / this.groupingSize];

		for (int i = 0; i < centroidIndices.length; i++) {
			for (int j = 0; j < centroidIndices[i].length; j++) {
				centroidIndices[i][j] = fis.read();
			}
		}

		List<IClusteringRecord> centroids = new ArrayList<>();
		for (int i = 0; i < this.numClusters; i++) {
			double[] attributes = new double[this.groupingSize];
			for (int j = 0; j < attributes.length; j++) {
				int attribute = fis.read();
				attributes[j] = attribute;
			}
			centroids.add(new ClusteringRecord(attributes));
		}
		fis.close();

		return this.decompress(centroidIndices, centroids);
	}

	//reads and shows an compressed file
	public void showCompressedImage(String fileName) throws IOException {
		BufferedImage decompressedImage = this.getImage(this.readCompressedImage(fileName));
		this.displayImage(decompressedImage, "clusters=" + this.numClusters + ", groupSize=" + this.groupingSize);
	}

	//displays an image in a jframe
	private void displayImage(BufferedImage image, String label) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.setTitle(label);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	//converts centroid group numbers int a 2d array of grayscale values that has the 
	//original uncompressed dimensions
	private int[][] decompress(int[][] centroidIndices, List<IClusteringRecord> centroids) {
		int[][] decompressed = new int[this.rows][this.cols];
		int decompRow = 0;
		int decompCol = 0;

		for (int i = 0; i < centroidIndices.length; i++) {
			for (int j = 0; j < centroidIndices[i].length; j++) {
				double[] attributes = centroids.get(centroidIndices[i][j]).getAttributes();
				for (int k = 0; k < attributes.length; k++) {
					decompressed[decompRow][decompCol++] = (int) attributes[k];
				}
			}
			decompRow++;
			decompCol = 0;
		}

		return decompressed;
	}

	//converts pixel data into record data for clustering
	private List<IClusteringRecord> getRecordsFromPixels(int[][] pixelData) {
		List<IClusteringRecord> records = new ArrayList<>();
		for (int i = 0; i < pixelData.length; i++) {
			for (int j = 0; j < pixelData[i].length; j += this.groupingSize) {
				double[] attributes = new double[this.groupingSize];
				for (int k = 0; k < attributes.length; k++) {
					attributes[k] = pixelData[i][j + k];
				}
				records.add(new ClusteringRecord(attributes));
			}
		}
		return records;
	}

	//reads uncompressed pixel data and returns a 2d array of the values
	private int[][] readUncompressedPixelData(String fileName) throws IOException {
		int[][] pixelData = new int[this.rows][this.cols];
		FileInputStream fis = new FileInputStream(new File(fileName));
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				pixelData[r][c] = fis.read();
			}
		}
		fis.close();
		return pixelData;
	}

	//converts a 2d array of grayscale ints into a buffered image
	private BufferedImage getImage(int[][] grayscalePixels) {
		BufferedImage theImage = new BufferedImage(grayscalePixels.length, grayscalePixels[0].length,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < grayscalePixels.length; y++) {
			for (int x = 0; x < grayscalePixels[y].length; x++) {
				int value = grayscalePixels[y][x] << 16 | grayscalePixels[y][x] << 8 | grayscalePixels[y][x];
				theImage.setRGB(x, y, value);
			}
		}
		return theImage;
	}

	public void compressMultiple(int[] clusterValues, String fileInput, String[] outputFiles) throws IOException {

		for (int i = 0; i < outputFiles.length; i++) {
			this.setNumClusters(clusterValues[i]);
			this.compressImage(fileInput, outputFiles[i]);
		}

	}

	public void showMultipleCompressed(int[] clusterValues, String[] compressedFiles) throws IOException {
		for (int i = 0; i < clusterValues.length; i++) {
			this.setNumClusters(clusterValues[i]);
			this.showCompressedImage(compressedFiles[i]);
		}
	}

	public void uncompressAndSaveAsPNGMultiple(int[] clusterValues, String[] compressedFiles) throws IOException {
		for (int i = 0; i < clusterValues.length; i++) {
			this.setNumClusters(clusterValues[i]);
			BufferedImage decompressedImage = this.getImage(this.readCompressedImage(compressedFiles[i]));
			ImageIO.write(decompressedImage, "png", new File(compressedFiles[i] + ".png"));
		}
	}

}
