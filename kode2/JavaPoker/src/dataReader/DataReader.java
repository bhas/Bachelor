package dataReader;

import game.essentials.Card;
import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class DataReader {
	private static final int FULL_DATA = 13;
	private static final int PREFLOP = 4;
	private static final int FLOP = 5;
	private static final int TURN = 6;
	private static final int RIVER = 7;
	private static final int CARD1 = 11;
	private static final int CARD2 = 12;

	public static boolean isAggressive(String s) {
		return s.contains("b") || s.contains("r");
	}

	public static boolean isDefensive(String s) {
		return !(isAggressive(s) || s.contains("f"));
	}

	public static Probability getProb(String hand) {
		return ProbabilityCalc.getProbability(
				Card.createMultiple(hand), null, 1);
	}

	public static Graph scanPlayer(String name, int state) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;

		String path = "data/players/pdb." + name;

		try {
			inputStream = new FileInputStream(path);
			sc = new Scanner(inputStream, "UTF-8");

			GraphData gda = new GraphData();
			GraphData gdd = new GraphData();
			int i1 = 0, i2 = 0;
			while (sc.hasNextLine()) {
				i1++;
				if (i1 % 100 == 0)
					System.out.println("loaded "+i1+" lines");
				String line = sc.nextLine().replaceAll("\\s+", " ");
				String[] data = line.split(" ");
				if (data.length == FULL_DATA) {
					if (isAggressive(data[state])) {
						Probability res = getProb(data[CARD1] + " " + data[CARD2]);
						gda.addEntry(i1, res.percent());
					} else if (isDefensive(data[state])) {
						Probability res = getProb(data[CARD1] + " " + data[CARD2]);
						gdd.addEntry(i1, res.percent());
					}
				}
			}
			Graph g = new Graph(gda);
			g.addDataset(gdd);
			g.setViewX(0, Math.max(i1, i2) + 1);
			g.setViewY(0, 1);
			g.drawRanges(true);
			return g;
			
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			String player = "Bar";
			
			Graph preFlop = scanPlayer(player, PREFLOP);
			preFlop.setDescriptions("preflop", "");
			new GraphWindow(preFlop);
			
//			Graph flop = scanPlayer(player, FLOP);
//			flop.setDescriptions("flop", "");
//			new GraphWindow(flop);
//			
//			Graph turn = scanPlayer(player, TURN);
//			turn.setDescriptions("turn", "");
//			new GraphWindow(turn);
//			
//			
//			Graph river = scanPlayer(player, RIVER);
//			river.setDescriptions("river", "");
//			new GraphWindow(river);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
