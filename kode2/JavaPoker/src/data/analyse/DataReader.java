package data.analyse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import neuralnetwork.NNManager;

public class DataReader {
	private final static String DATA_FILE = "data/refactored-data.txt";
	FileReader fr;
	BufferedReader br;
	DataHolder dataHolder = new DataHolder();
	ArrayList<String> games = new ArrayList<String>();

	public DataReader() {
		try {
			openConnection(DATA_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runIt(String name) throws IOException{
		findHands(name);
		NNManager nn = new NNManager(3, games.size(), games, name);
//		ArrayList<DataHolder> drs = new ArrayList<DataHolder>();
//		openConnection(DATA_FILE);
//		for(int i = 0; i<games.size(); i++){
//			drs.add(find(games.get(i)));
//
//		}
	}
	
	public void findHands(String name) throws IOException{
		String PLAYER = "data/players/pdb." + name;
		openConnection(PLAYER);		
		String line;
		
		while((line = br.readLine()) != null){
			line = line.trim().replaceAll("\\s+", " ");
			if(line.split(" ").length > 11){
				games.add(line.split(" ")[1].substring(0, 9));
			}
		}
		closeConnection();
	}

//	public DataHolder findGamesForPlayer(String name) throws IOException {
//		String line;
//		String extraLine;
//		int num = 0;
//		while ((line = br.readLine()) != null) {
//			if (line.contains(name)) {
//				String[] lineData = line.split("; ");
//				for (int i = 0; i < lineData.length; i++) {
//					if (lineData[i].contains(name)) {
//						num = i;
//					}
//				}
//				extraLine = br.readLine();
//				extraLine = br.readLine();
//				extraLine = br.readLine();
//				extraLine = br.readLine();
//				extraLine = br.readLine();
//				String[] lineData2 = extraLine.split("; ");
//				String[] handsArr = lineData2[1].toString().split(", ");
//				if (!handsArr[num].contains("-")) {
//					return readData(line);
//				}
//			}
//		}
//		return null;
//	}

	public void openConnection(String file) throws IOException {
		this.fr = new FileReader(file);
		this.br = new BufferedReader(fr);
	}

	// finds a specific round
	public DataHolder find(String id) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains(id)) {
				return readData(line);
			}
		}

		return null;
	}

	private DataHolder readData(String line) throws IOException {
		// first line
		String[] lineData = line.split("; ");
		dataHolder.id = Integer.parseInt(lineData[0].substring(1));
		dataHolder.players = toArray(lineData[1]);
		dataHolder.numOfPlayers = dataHolder.players.length;

		// second line
		line = br.readLine();
		lineData = line.split("; ");
		dataHolder.preflopActs = toArray(lineData[0]);
		dataHolder.preflopPlayers = getData(lineData[1], 0);
		dataHolder.preflopPot = getData(lineData[1], 1);

		// third line
		line = br.readLine();
		lineData = line.split("; ");
		dataHolder.flopActs = toArray(lineData[0]);
		dataHolder.flopPlayers = getData(lineData[1], 0);
		dataHolder.flopPot = getData(lineData[1], 1);

		// fourth line
		line = br.readLine();
		lineData = line.split("; ");
		dataHolder.turnActs = toArray(lineData[0]);
		dataHolder.turnPlayers = getData(lineData[1], 0);
		dataHolder.turnPot = getData(lineData[1], 1);

		// fifth line
		line = br.readLine();
		lineData = line.split("; ");
		dataHolder.riverActs = toArray(lineData[0]);
		dataHolder.riverPlayers = getData(lineData[1], 0);
		dataHolder.riverPot = getData(lineData[1], 1);

		// last line
		line = br.readLine();
		lineData = line.split("; ");
		dataHolder.cc = lineData[0];
		dataHolder.hands = toArray(lineData[1]);
		dataHolder.profits = new int[dataHolder.numOfPlayers][3];
		String[] profits = toArray(lineData[2]);
		for (int i = 0; i < profits.length; i++) {
			String[] sarr = profits[i].split("/");
			for (int j = 0; j < 3; j++) {
				dataHolder.profits[i][j] = Integer.parseInt(sarr[j]);
			}
		}

		return dataHolder;
	}

	private String[] toArray(String arr) {
		String s = arr.substring(1, arr.length() - 1);
		return s.split(", ");
	}

	public int getData(String s, int i) {
		return Integer.parseInt(s.split("/")[i]);
	}

	// returns object of next round
	public DataHolder next() throws IOException {

		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains("#")) {
				return readData(line);
			}
		}
		return null;

	}

	public void closeConnection() {
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
