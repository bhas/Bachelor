package testing;

import montecarlo.ProbabilityCalc;

public class PokerTest {

	private static final double TOTAL_CHIPS = 200000;

	public static void main(String[] args) {
		String hc = "4d Ac";
		String cc = "3c 3s 5h";
		int opponents = 8;
		int chips = 19900;
		int cost = 100;
		int pot = 500;
		PokerTest.getAction(hc, cc, opponents, chips, cost, pot);
	}

	public static void getAction(String hc, String cc, int opponents,
			int chips, int cost, int pot) {
		double[] in = new double[5];
		in[0] = ProbabilityCalc.getProbability(hc, cc, 1).percent();
		in[1] = opponents / 9.0;
		in[2] = chips / TOTAL_CHIPS;
		in[3] = cost / TOTAL_CHIPS;
		in[4] = pot / TOTAL_CHIPS;
		System.out.println(in[0] + " " + in[1] + " " + in[2] + " " + in[3]
				+ " " + in[4]);
	}
}
