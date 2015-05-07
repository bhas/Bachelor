package data.analyse;

public class DataAnalyser {
	private int pi;
	private int ai;
	private int players;

	public State[] getStates(int playerId, String[] actions, int preProfit,
			int preBalance) {
		players = actions.length;
		int remainingOpps = getOpponents(actions);
		int expStates = actions[playerId].replaceAll("B", "").length();
		State[] states = new State[expStates];
		int[] payments = new int[actions.length];
		int max = 0;
		pi = 0;
		ai = 0;

		// blind
		if (actions[0].startsWith("B")) {
			payments[0] = 5;
			payments[1] = 10;
			max = 10;
			increase();
			increase();
		}
		// for each state
		int i = 0;
		while (i < expStates) {
			String action = actions[pi];

			// is looking at the player we track
			if (pi == playerId) {
				State s = new State();
				for (int p : payments) {
					s.profit += p;
				}
				s.profit += preProfit;
				s.cost = max - payments[pi];
				s.numOfOpponents = remainingOpps;
				s.aggressive = isAggressive(action, ai);
				s.preBal = preBalance - payments[pi];
				s.postBal = s.preBal - (s.cost + (s.aggressive ? 10 : 0));
				states[i] = s;
				i++;
			}

			// check if there is an action
			if (action.length() > ai && !action.equals("-")) {
				// aggressive action
				if (isAggressive(action, ai)) {
					max += 10;
					payments[pi] = max;
				} else if (isDefensive(action, ai)) {
					payments[pi] = max;
				} else {
					remainingOpps--;
				}
			}
			increase();
		}

		return states;
	}

	private void increase() {

		if (pi + 1 == players) {
			pi = 0;
			ai++;

		} else {
			pi++;
		}
	}

	private int getOpponents(String[] actions) {
		int players = -1;
		for (String s : actions) {
			if (!s.equals("-")) {
				players++;
			}
		}
		return players;
	}

	private boolean isAggressive(String s, int index) {
		String c = s.charAt(index) + "";
		return c.equals("b") || c.equals("r");
	}

	private boolean isDefensive(String s, int index) {
		String c = s.charAt(index) + "";
		return c.equals("c") || c.equals("k");
	}

	public class State {
		// balance
		public int preBal, postBal;
		public int cost, profit, numOfOpponents;
		public boolean aggressive;
	}
}
