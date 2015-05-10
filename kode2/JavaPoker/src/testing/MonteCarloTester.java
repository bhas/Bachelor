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
//		long startTime = System.nanoTime();
//		for(int i = 0; i<100; i++){
			printProb("As Ac", 2);
			printProb("As Ac", 3);
			printProb("As Ac", 4);
			printProb("As Ac", 5);
			printProb("As Ac", 6);
			printProb("As Ac", 7);
			printProb("As Ac", 8);
			printProb("As Ac", 9);
			
//		}
//		long endTime = System.nanoTime();
//		double approx = ((endTime-startTime)/1000000000.0);
//		System.out.println("Took "+ approx/100 + " seconds"); 

	}
	
	private static void printProb(String hand, int opps) {
		double st = 0.0;
		for(int i = 0; i<100; i++){
			Probability prob = ProbabilityCalc.getProbability(hand, null, opps);
			st += prob.percent();

		}
		System.out.print(hand + " - ");
		System.out.println(st/100.0);
	}
}
