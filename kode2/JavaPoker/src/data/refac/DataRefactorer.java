package data.refac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class DataRefactorer {
	private static final String INPUT_FILE = "data/hdb";
	private static final String OUTPUT_FILE = "data/refactored-data.txt";
	private static DataFinder rosterFinder = null;
	private static HashMap<String, DataFinder> playerFinders = new HashMap<String, DataFinder>();
	private static BufferedWriter bw;
	
	public static final void refactor() throws IOException {

		FileReader fileReader = null;
		BufferedReader br = null;
		FileWriter fileWriter = null;
		bw = null;

		try {
			rosterFinder = new DataFinder("data/hroster");
			fileReader = new FileReader(INPUT_FILE);
			br = new BufferedReader(fileReader);
			fileWriter = new FileWriter(OUTPUT_FILE);
			bw = new BufferedWriter(fileWriter);
			String line;

			while ((line = br.readLine()) != null) {
				String[] data = getData(line);
				// if it has show down collect data
				if (!data[7].startsWith("1/", 0)) {
					collectData(data);
				}
			}

			System.out.println("All done!");
		} finally {
			// close all connections
			for (Entry<String, DataFinder> e : playerFinders.entrySet()) {
				e.getValue().closeConnection();
			}
			br.close();
			fileReader.close();
			bw.close();
			fileWriter.close();
		}
	}
	
	private static void writeln(String s) throws IOException {
		bw.write(s);
		bw.newLine();
	}

	private static void collectData(String[] hdbData) throws IOException {
		String id = hdbData[0];

		// finding players
		String[] rosterData = getData(rosterFinder.find(id));
		int numOfPlayers = rosterData.length - 2;
		// String[][] playerData = new String[numOfPlayers][];

		String[] names = new String[numOfPlayers];
		String[] preflopActs = new String[numOfPlayers];
		String[] flopActs = new String[numOfPlayers];
		String[] turnActs = new String[numOfPlayers];
		String[] riverActs = new String[numOfPlayers];
		String[] chips = new String[numOfPlayers];
		String[] hands = new String[numOfPlayers];

		// iterates through all players in the round
		for (int i = 2; i < rosterData.length; i++) {
			String name = rosterData[i];
			
			// error in the data set causes this player to be missing
			if (name.equals("ak47\\")) {
				return;
			}

			// add playerFinder if non has been created
			if (!playerFinders.containsKey(name)) {
				playerFinders.put(name, new DataFinder("data/players/pdb."
						+ name));
			}
			// find data about the player
			String line = playerFinders.get(name).find(id);
			String[] playerData = getData(line);
			int pos = Integer.parseInt(playerData[3]) - 1;
			names[pos] = name;
			preflopActs[pos] = playerData[4];
			flopActs[pos] = playerData[5];
			turnActs[pos] = playerData[6];
			riverActs[pos] = playerData[7];
			chips[pos] = playerData[9] + "/" + playerData[10] + "/"
					+ playerData[8];
			hands[pos] = readCards(playerData, 11);
		}

		String sep = "; ";
		writeln("#" + id + sep + Arrays.toString(names));
		System.out.println("#" + id + sep + Arrays.toString(names));
		writeln(Arrays.toString(preflopActs) + sep + hdbData[4]);
		writeln(Arrays.toString(flopActs) + sep + hdbData[5]);
		writeln(Arrays.toString(turnActs) + sep + hdbData[6]);
		writeln(Arrays.toString(riverActs) + sep + hdbData[7]);

		writeln(readCards(hdbData, 8) + sep + Arrays.toString(hands)
				+ sep + Arrays.toString(chips));
		writeln("");
	}

	private static String readCards(String[] data, int from) {
		String s = "";
		for (int i = from; i < data.length; i++) {
			s += data[i] + " ";
		}
		return s == "" ? "-" : s.trim();
	}

	private static String[] getData(String line) {
		return line.replaceAll("\\s+", " ").split(" ");
	}
}
