package data.analyse;

public class DataReader {
	private final static String DATA_FILE = "data/refactored-data.txt";

	public DataReader() {

	}

	// finds a specific round
	public DataHolder find(String id) {
		return null;
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
		public int[][] profits;

	}
}
