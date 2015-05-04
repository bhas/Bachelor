package testing;

import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;
import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class MonteCarloTester {
	
//	public static void main(String[] args) {
//		preflop("Jd Jh");
//	}

	private static void preflop(String hand) {
		int calcs = 50;
		GraphData gd = new GraphData();
		for (int i = 1; i <= calcs; i++) {
			Probability p = ProbabilityCalc.getProbability(hand, null, 1);
			gd.addEntry(i, p.percent());
			System.out.println(i);
		}
		
		Graph g = new Graph(gd);
		g.setViewY(0.7, 0.85);
		g.setViewX(0, calcs);
		g.drawRanges(true);

		new GraphWindow(g);
	}
	
	public static void main(String[] args) {
//		printProb("Ac Ad", 1);
//		printProb("Qc Kc", 1);
//		printProb("Js Qd", 1);
//		printProb("Th Jh", 1);
//		printProb("8c 8d", 1);
//		printProb("3d 3s", 1);
//		printProb("7d 2s", 1);
//		printProb("Ah 8s", 1);
//		printProb("9d 3s", 1);
		printProb("4d 5h", 1);
		printProb("2h 3h", 1);
		printProb("2d 7d", 1);
	}
	
	private static void printProb(String hand, int opps) {
		System.out.print(hand + " - ");
		Probability prob = ProbabilityCalc.getProbability(hand, null, opps);
		System.out.println(prob.percent());
	}
}
