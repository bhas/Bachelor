package dataReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataRefactorer {
	public static final String INPUT_FILE = "data/hdb";
	public static final String OUTPUT_FILE = "data/refactored-data.txt";

	public static final void start() {

		FileReader fileReader = null;
		BufferedReader br = null;

		try {
			fileReader = new FileReader(INPUT_FILE);
			br = new BufferedReader(fileReader);
			String line;
			int i = 0;
			
			while ((line = br.readLine()) != null && i<100) {
				line = line.replaceAll("\\s+", " ");
				if (hasShowdown(line)) {
					System.out.println(line);
				}
				i++;
			}
			
			System.out.println("All done!");
			br.close();
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static boolean hasShowdown(String round) {
		String[] info = round.split(" ");
		return !info[7].startsWith("1/", 0);
	}

	public static final void start(String fromID) {

	}

	public static final String stop() {
		return null;
	}
}
