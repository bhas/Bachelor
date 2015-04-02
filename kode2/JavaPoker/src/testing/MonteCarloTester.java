package testing;

import game.essentials.Card;
import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.util.ArrayList;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class MonteCarloTester {
	
	public static void main(String[] args) {
		ArrayList<Card> arr = new ArrayList<Card>();
		arr.add(Card.H9);
		arr.add(Card.C4);
		ArrayList<Card> arr2 = new ArrayList<Card>();
		arr2.add(Card.C5);
		arr2.add(Card.HQ);
		arr2.add(Card.HJ);
		arr2.add(Card.DA);
		arr2.add(Card.H10);

		Probability result = new Probability(0, 0, 0);
		GraphData gd = new GraphData();
		for (int i = 1; i <= 10; i++) {
			Probability p = ProbabilityCalc.getProbability(arr, null, 1);
			result.add(p);
			gd.addEntry(i * ProbabilityCalc.CALCULATIONS, result.percent());
			System.out.println("Run " + i + ": " + result.percent());
		}

		Graph g = new Graph(gd);
		g.setDescriptions("Monte Carlo tests", "Probability");
		g.setViewY(0, .5);
		g.setViewX(0, 10 * ProbabilityCalc.CALCULATIONS);
		g.setExpectedVal(0.051);

		new GraphWindow(g);
	}
}
