package data.refac;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataFinder {
	FileReader fr;
	BufferedReader br;

	public DataFinder(String file) throws IOException {
		fr = new FileReader(file);
		br = new BufferedReader(fr);
	}

	public String find(String game) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains(game)) {
				return line;
			}
		}
		return null;
	}

	public void closeConnection() throws IOException {
		br.close();
		fr.close();
	}
}
