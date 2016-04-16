package program3.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public static void convertByteFileToIntFile(String inputFile, String outputFile) throws IOException {
		FileInputStream fis = new FileInputStream(new File(inputFile));
		List<Integer> integerList = new ArrayList<>();

		int count = 0;

		int c;
		while ((c = fis.read()) != -1) {
			if (count++ < 10) {
				System.out.println(c);

			}
			integerList.add(c);
		}

		fis.close();

		PrintWriter pw = new PrintWriter(new File(outputFile));
		for (int i = 0; i < integerList.size(); i++) {
			pw.print(integerList.get(i) + " ");
		}

		pw.close();
	}

	public static void displayByteFileAsInts(String inputfile) throws IOException {
		FileInputStream fis = new FileInputStream(new File(inputfile));
		int c;
		int count = 0;
		while ((c = fis.read()) != -1) {
			System.out.print(c + " ");
			if (++count % 512 == 0) {
				System.out.println();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		/*String inputFile = "program3_data/part3/imagefile";
		String outputFile = "program3_data/part3/imagefile_bytes";
		convertIntFileToByteFile(inputFile, outputFile);
		
		inputFile = outputFile;
		outputFile = "program3_data/part3/imagefile_ints";
		convertByteFileToIntFile(inputFile, outputFile);
		*/
		displayByteFileAsInts("program3_data/part3/imagefile_bytes");
	}
}
