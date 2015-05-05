package data.analyse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataReader {
	private final static String DATA_FILE = "data/refactored-data.txt";
	FileReader fr;
	BufferedReader br;

	public DataReader() {
		try {
			openConnection(DATA_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		DataReader dr = new DataReader();
		dr.find("975646885");

		
	}

	public void openConnection(String file) throws IOException {
		this.fr = new FileReader(file);
		this.br = new BufferedReader(fr);
	}

	// finds a specific round
	public DataHolder find(String id) throws IOException {
		DataHolder dataHolder = new DataHolder();
		dataHolder.id = Integer.parseInt(id);
		String line;
		String nextLine;
		int num = 1;
		while ((line = br.readLine()) != null) {
			if (line.contains(id)) {
				//first line
				dataHolder.players = line.substring(13)
						.substring(0, line.substring(13).length() - 1)
						.replace(",", "").split("\\s+");
				while ((nextLine = br.readLine()) != null && nextLine != "") {
					switch (num) {

					//second line
					case 1:
						dataHolder.preflopActs = nextLines(nextLine);

						// update preflopPlayers
						dataHolder.preflopPlayers = getPlayers(nextLine);
						// Update preflopPot
						dataHolder.preflopPot = getPot(nextLine);
						num++;
						continue;
					//third line
					case 2:
						dataHolder.flopActs = nextLines(nextLine);
						dataHolder.flopPlayers = getPlayers(nextLine);
						dataHolder.flopPot = getPot(nextLine);

						num++;
						continue;
					//fourth line
					case 3:
						dataHolder.turnActs = nextLines(nextLine);
						dataHolder.turnPlayers = getPlayers(nextLine);
						dataHolder.turnPot = getPot(nextLine);
						num++;
						continue;
					//fith line
					case 4:
						dataHolder.riverActs = nextLines(nextLine);
						dataHolder.riverPlayers = getPlayers(nextLine);
						dataHolder.riverPot = getPot(nextLine);
						num++;
						continue;
					//last line
					case 5:
						dataHolder.cc = nextLine.split(";")[0];
						dataHolder.hands = nextLine.split(";")[1]
								.replace("]", "").replace("[", "").split(",");

						String[] o = nextLine.split(";")[2].replace("[", "")
								.replace("]", "").replace(" ", "").split(",");

						for (int i = 0; i < o.length; i++) {
							for (int s = 0; s < 3; s++) {
								dataHolder.profits[i][s] = Integer
										.parseInt(o[i].split("/")[s]);

							}
						}
						return dataHolder;
					}
				}
			}

		}

		return null;
	}
	
	public int getPot(String nextLine){
		return Integer.parseInt(nextLine
				.replace(" ", "").split(";")[1].substring(2));
	}
	
	public int getPlayers(String nextLine){
		return Integer.parseInt(nextLine.replace(" ", "").split(";")[1]
				.substring(0, 1));
	}

	public String[] nextLines(String nextLine) {
		return Arrays
				.copyOf(Arrays.copyOf(nextLine.replace("]", "")
						.replace(",", "").substring(1).split(";"),
						nextLine.replace("]", "").replace(",", "").substring(1)
								.split(";").length - 1)[0].split(" "), Arrays
						.copyOf(nextLine.replace("]", "").replace(",", "")
								.substring(1).split(";"),
								nextLine.replace("]", "").replace(",", "")
										.substring(1).split(";").length - 1)[0]
						.split(" ").length);

	}

	// returns object of next round
	public DataHolder next() {
		return null;
	}

	// object containing the data of a round
	public class DataHolder {
		public int id, winner, numOfPlayers;
		public String cc;
		public String[] players;
		public String[] hands;
		public String[] preflopActs;
		public String[] flopActs;
		public String[] turnActs;
		public String[] riverActs;
		public int preflopPlayers, preflopPot;
		public int flopPlayers, flopPot;
		public int turnPlayers, turnPot;
		public int riverPlayers, riverPot;
		public int[][] profits = new int[9][3];
	}
}
