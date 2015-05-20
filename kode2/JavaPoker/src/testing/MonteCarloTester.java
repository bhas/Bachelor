package testing;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

public class MonteCarloTester {

	public static void main(String[] args) {
		// Change the values in order to test the different results of the
		// calculator
		String communityCards = "Ad 4h Ks";
		String holeCards = "As 9s";
		int opponents = 9;
		printProb(holeCards, communityCards, opponents);

	}

	private static void printProb(String hand, String cc, int opps) {
		Probability prob = ProbabilityCalc.getProbability(hand, cc, opps);
		// The result of the calculator
		System.out.println("result: " + prob.percent());
	}
}
