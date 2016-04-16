package program3.app;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;
import program3.kmeans.Kmeans;

public class ImageCompressor {

	private int rows;
	private int cols;
	private int groupingSize;
	private Kmeans clusterer;
	private int[][] originalPixelValues;
	private int[][] compressedCentroidIndices;
	private BufferedImage originalImage;
	private int numClusters;
	private int clusterSeed;

	public ImageCompressor(int rows, int cols, int groupingSize, int numClusters, int clusterSeed) {
		this.rows = rows;
		this.cols = cols;
		this.groupingSize = groupingSize;
		this.originalPixelValues = new int[rows][cols];
		this.compressedCentroidIndices = new int[rows][cols / groupingSize];
		this.clusterer = new Kmeans(null);
		this.numClusters = numClusters;
		this.clusterSeed = clusterSeed;
	}

	public void compressImage(String inputImage, String outputImage) throws FileNotFoundException {
		this.readPixelData(inputImage);
		this.originalImage = this.getImage(this.originalPixelValues);
		System.out.println(this.getRecordsFromPixels().size());
		this.clusterer.load(this.getRecordsFromPixels());
		this.clusterer.setParameters(this.numClusters, this.clusterSeed);
		this.clusterer.cluster();
		List<IClusteringRecord> records = this.clusterer.getRecords();

		int recordsIndex = 0;
		for (int i = 0; i < this.compressedCentroidIndices.length; i++) {
			for (int j = 0; j < this.compressedCentroidIndices[i].length; j++) {
				this.compressedCentroidIndices[i][j] = records.get(recordsIndex++).getCluster();
			}
		}
		this.writeCompressedImage(outputImage);
	}

	private void writeCompressedImage(String outputImage) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File("program3_data/part3/" + outputImage));
		for (int i = 0; i < this.compressedCentroidIndices.length; i++) {
			for (int j = 0; j < this.compressedCentroidIndices[i].length; j++) {
				writer.print(this.compressedCentroidIndices[i][j] + " ");
			}
			writer.println();
		}

		List<IClusteringRecord> centroids = this.clusterer.getCentroids();
		System.out.println(centroids.size());
		for (int i = 0; i < this.numClusters; i++) {
			double[] clusterAttributes = centroids.get(i).getAttributes();
			for (int j = 0; j < clusterAttributes.length; j++) {
				System.out.println(clusterAttributes[j]);
				writer.print(this.convertToGrayscaleInt(clusterAttributes[j]) + " ");
			}
		}
		writer.close();
	}

	private int convertToGrayscaleInt(double d) {
		if (d < 0) {
			return 0;
		} else if (d > 255) {
			return 255;
		} else {
			return (int) Math.round(d);
		}
	}

	public void showOriginalImage() throws FileNotFoundException {

		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(this.originalImage)));
		frame.pack();
		frame.setVisible(true);

	}

	public void showCompressedImage(String fileName) throws FileNotFoundException {
		Scanner reader = new Scanner(new File("program3_data/part3/" + fileName));
		int[][] centroidIndices = new int[this.rows][this.cols / this.groupingSize];

		for (int i = 0; i < centroidIndices.length; i++) {
			for (int j = 0; j < centroidIndices[i].length; j++) {
				centroidIndices[i][j] = reader.nextInt();
			}
		}

		List<IClusteringRecord> centroids = new ArrayList<>();
		for (int i = 0; i < this.numClusters; i++) {
			double[] attributes = new double[this.groupingSize];
			for (int j = 0; j < attributes.length; j++) {
				attributes[j] = reader.nextDouble();
			}
			centroids.add(new ClusteringRecord(attributes));
		}

		int[][] decompressed = this.decompress(centroidIndices, centroids);
		BufferedImage decompressedImage = this.getImage(decompressed);

		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(decompressedImage)));
		frame.pack();
		frame.setVisible(true);

		reader.close();

	}

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

	public List<IClusteringRecord> getRecordsFromPixels() {
		List<IClusteringRecord> records = new ArrayList<>();
		for (int i = 0; i < this.originalPixelValues.length; i++) {
			for (int j = 0; j < this.originalPixelValues[i].length; j += this.groupingSize) {
				double[] attributes = new double[this.groupingSize];
				for (int k = 0; k < attributes.length; k++) {
					attributes[k] = this.originalPixelValues[i][j + k];
				}
				records.add(new ClusteringRecord(attributes));
			}
		}
		return records;
	}

	public void readPixelData(String fileName) throws FileNotFoundException {
		Scanner reader = new Scanner(new File("program3_data/part3/" + fileName));
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				this.originalPixelValues[r][c] = reader.nextInt();
			}
		}
		reader.close();
	}

	public BufferedImage getImage(int[][] grayscalePixels) {
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

}
