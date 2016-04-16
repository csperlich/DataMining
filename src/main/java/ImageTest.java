import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageTest {

	public static void main(String[] args) throws FileNotFoundException {
		int rows = 512;
		int cols = 512;
		int[][] pixels = readPixelData("imagefile", rows, cols);

		BufferedImage image = getImage(pixels, rows, cols);

		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setVisible(true);
	}

	public static int[][] readPixelData(String fileName, int rows, int cols) throws FileNotFoundException {
		int[][] pixels = new int[rows][cols];
		Scanner reader = new Scanner(new File("program3_data/part3/" + fileName));
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				pixels[r][c] = reader.nextInt();
			}
		}
		reader.close();
		return pixels;
	}

	public static BufferedImage getImage(int[][] grayscalePixels, int rows, int cols) {
		BufferedImage theImage = new BufferedImage(rows, cols, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				int value = grayscalePixels[y][x] << 16 | grayscalePixels[y][x] << 8 | grayscalePixels[y][x];
				theImage.setRGB(x, y, value);
			}
		}
		return theImage;
	}
}
