package testing;

import game.essentials.Card;
import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.util.ArrayList;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class MonteCarloTester {
	
//	public static void main(String[] args) {
//		ArrayList<Card> arr = new ArrayList<Card>();
//		arr.add(Card.S6);
//		arr.add(Card.C7);
//		ArrayList<Card> arr2 = new ArrayList<Card>();
//		arr2.add(Card.C5);
//		arr2.add(Card.HQ);
//		arr2.add(Card.HJ);
//		arr2.add(Card.DA);
//		arr2.add(Card.HT);
//
//		Probability result = new Probability(0, 0, 0);
//		GraphData gd = new GraphData();
//		for (int i = 1; i <= 10; i++) {
//			Probability p = ProbabilityCalc.getProbability(arr, null, 1);
//			result.add(p);
//			gd.addEntry(i * ProbabilityCalc.CALCULATIONS, result.percent());
//			System.out.println("Run " + i + ": " + result.percent());
//		}
//
//		Graph g = new Graph(gd);
//		g.setDescriptions("Monte Carlo tests", "Probability");
//		g.setViewY(0, 1);
//		g.setViewX(0, 10 * ProbabilityCalc.CALCULATIONS);
//		g.setExpectedVal(0.0979);
//
//		new GraphWindow(g);
//	}
	
	public static void main(String[] args) {
		printProb("Ac Ad", 1);
		printProb("Qc Kc", 1);
		printProb("Js Qd", 1);
		printProb("Th Jh", 1);
		printProb("8c 8d", 1);
		printProb("3d 3s", 1);
		printProb("7d 2s", 1);
		printProb("Ah 8s", 1);
		printProb("9d 3s", 1);
	}
	
	private static void printProb(String hand, int opps) {
		System.out.print(hand + " - ");
		Probability prob = ProbabilityCalc.getProbability(hand, null, opps);
		System.out.println(prob.percent());
	}
}
