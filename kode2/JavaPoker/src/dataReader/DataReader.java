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
	
	public static boolean isAggressive(String s){
		return s.contains("b") || s.contains("r");
		
	}
	
	public static boolean isDefensive(String s){
		return !(isAggressive(s) || s.contains("f"));
	}
	
	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;

		String path = "data/players/pdb.PokiSimA";

		try {
			inputStream = new FileInputStream(path);
			sc = new Scanner(inputStream, "UTF-8");
			GraphData gda = new GraphData();
			GraphData gdd = new GraphData();
			int i1 = 0, i2 = 0;
			while (sc.hasNextLine()) {
				String line = sc.nextLine().replaceAll("\\s+", " ");
				String[] data = line.split(" ");
				if (data.length == 13) {
					if (isAggressive(data[4])) {
						String hand = data[11] + " " + data[12];
						Probability res = ProbabilityCalc.getProbability(
								Card.createMultiple(hand), null, 1);
						gda.addEntry(i1++ + 1, res.percent());
					}
					else if (isDefensive(data[4])) {
						String hand = data[11] + " " + data[12];
						System.out.println(hand);
						Probability res = ProbabilityCalc.getProbability(
								Card.createMultiple(hand), null, 1);
						gdd.addEntry(i2++ + 1, res.percent());
					}
				}
			}
			Graph ga = new Graph(gda);
			ga.addDataset(gdd);
			ga.setViewX(0,Math.max(i1, i2)+1);
			ga.setViewY(0, 1);
			new GraphWindow(ga);
			
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}
}
