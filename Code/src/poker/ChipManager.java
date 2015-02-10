package poker;

public class ChipManager {
	private int[] chips;
	private int[] bets;
	private int pot;
	private int BB;

	public ChipManager(int startChips, int bb) {
		chips = new int[] { startChips, startChips };
		bets = new int[] { 0, 0 };
		BB = bb;
	}

	// place a bet
	public boolean bet(int p, int bid) {
		if (isValidBid(p, bid)) {
			pay(p, bid);
			return true;
		}
		return false;
	}

	// call opponent player
	public boolean call(int p) {
		if (p == 0)
			return bet(p, bets[1]);
		if (p == 1)
			return bet(p, bets[0]);

		return false;
	}

	// Adds the bets to the pot.
	// Any difference in bets will be put back to players chips
	public void commit() {
		int diff = bets[0] - bets[1];
		if (diff > 0) {
			chips[0] += diff;
			bets[0] -= diff;
		} else {
			chips[1] -= diff;
			bets[1] += diff;
		}

		pot += (bets[0] + bets[1]);
		bets[0] = 0;
		bets[1] = 0;
	}

	// gives the chips to the winner and resets bets and pot
	public void endRound(int winner) {
		chips[winner] += (pot + bets[0] + bets[1]);
		bets[0] = 0;
		bets[1] = 0;
		pot = 0;
	}

	// checks if a bid is valid
	private boolean isValidBid(int p, int yourBid) {
		if (p == 0)
			return yourBid >= bets[1] + BB;
		if (p == 1)
			return yourBid >= bets[0] + BB;

		return false;
	}

	// Will pay for a player.
	// If the amount exceeds his total chips then it will go all in.
	private void pay(int p, int amount) {
		int tmp = 0;
		tmp = (chips[p] < amount ? chips[p] : amount);
		bets[p] += tmp;
		chips[p] -= tmp;
	}

	public boolean isAllin(int p) {
		return chips[p] == 0;
	}
}
