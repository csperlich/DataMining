package program3.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class with tools to convert a text file of ints with range[0-255] to 
 * a file of bytes with the same range
 * @author cade
 *
 */
public class IntToByteFileConverter {
	public static void convertIntFileToByteFile(String inputFile, String outputFile) throws IOException {
		Scanner reader = new Scanner(new File(inputFile));
		List<Byte> byteList = new ArrayList<>();
		while (reader.hasNext()) {
			byteList.add((byte) reader.nextInt());
		}
		reader.close();

		byte[] byteArray = new byte[byteList.size()];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = byteList.get(i);
		}

		FileOutputStream fos = new FileOutputStream(new File(outputFile));
		fos.write(byteArray);
		fos.close();
	}

	public static void main(String[] args) throws IOException {
		String inputFile = "program3_data/part3/imagefile";
		String outputFile = "program3_data/part3/imagefile_bytes";
		convertIntFileToByteFile(inputFile, outputFile);
	}
}
