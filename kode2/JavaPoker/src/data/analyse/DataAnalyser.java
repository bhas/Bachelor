package data.analyse;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class DataAnalyser {
	private static final String INPUT_FILE = "data/refactored-data.txt";

	private static boolean isAggressive(String s) {
		return s.contains("b") || s.contains("r");
	}

	private static boolean isDefensive(String s) {
		return !(isAggressive(s) || s.contains("f"));
	}

	private static Probability prob(String hand) {
		return ProbabilityCalc.getProbability(hand, null, 1);
	}

//	public static Graph scanPlayer(String name, int state) throws IOException {
//		FileInputStream inputStream = null;
//		Scanner sc = null;
//
//		String path = "data/players/pdb." + name;
//
//		try {
//			inputStream = new FileInputStream(path);
//			sc = new Scanner(inputStream, "UTF-8");
//
//			GraphData gda = new GraphData();
//			GraphData gdd = new GraphData();
//			int i1 = 0, i2 = 0;
//			while (sc.hasNextLine()) {
//				i1++;
//				if (i1 % 100 == 0)
//					System.out.println("loaded " + i1 + " lines");
//				String line = sc.nextLine().replaceAll("\\s+", " ");
//				String[] data = line.split(" ");
//				if (data.length == FULL_DATA) {
//					if (isAggressive(data[state])) {
//						Probability res = getProb(data[CARD1] + " "
//								+ data[CARD2]);
//						gda.addEntry(i1, res.percent());
//					} else if (isDefensive(data[state])) {
//						Probability res = getProb(data[CARD1] + " "
//								+ data[CARD2]);
//						gdd.addEntry(i1, res.percent());
//					}
//				}
//			}
//			Graph g = new Graph(gda);
//			g.addDataset(gdd);
//			g.setViewX(0, Math.max(i1, i2) + 1);
//			g.setViewY(0, 1);
//			g.drawRanges(true);
//			return g;
//
//		} finally {
//			if (inputStream != null) {
//				inputStream.close();
//			}
//			if (sc != null) {
//				sc.close();
//			}
//		}
//	}
}
