package poker;


public class ChipManager {
	private PlayerControl p1, p2;
	private int chips1, chips2;
	private int bet1, bet2;
	private int pot;
	private int BB;
	
	public ChipManager(PlayerControl p1, PlayerControl p2, int startChips, int bb) {
		this.p1 = p1;
		this.p2 = p2;
		chips1 = startChips;
		chips2 = startChips;
		BB = bb;
	}
	
	// place a bet
	public boolean bet(PlayerControl p, int bid) {
		if (p == p1 && isValidBid(bid, bet2)) {
			pay(p, bid);
			return true;
		}
		if (p == p2 && isValidBid(bid, bet1)) {
			pay(p, bid);
			return true;
		}
		return false;
	}
	
	// call opponent player
	public boolean call(PlayerControl p) {
		if (p == p1)
			return bet(p, bet2);
		if (p == p2)
			return bet(p, bet1);
		
		return false;
	}
	
	// Adds the bets to the pot.
	// Any difference in bets will be put back to players chips
	public void commit() {
		if (bet1 > bet2) {
			int diff = bet1 - bet2;
			chips1 += diff;
			bet1 -= diff;
		} else if (bet2 > bet1) {
			int diff = bet2 - bet1;
			chips2 += diff;
			bet2 -= diff;
		}
		
		pot += (bet1 + bet2);
		bet1 = 0;
		bet2 = 0;
	}
	
	// gives the chips to the winner and resets bets and pot
	public void roundEnded(PlayerControl winner) {
		if (winner == p1)
			chips1 += (pot + bet1 + bet2);
		else
			chips2 += (pot + bet1 + bet2);
		
		bet1 = 0;
		bet2 = 0;
		pot = 0;
	}
	
	// checks if a bid is valid
	private boolean isValidBid(int yourBid, int oppBid) {
		return yourBid >= oppBid + BB;
	}
	
	// Will pay for a player.
	// If the amount exceeds his total chips then it will go all in.
	private void pay(PlayerControl p, int amount) {
		int tmp = 0;
		if (p == p1) {
			tmp = (chips1 < amount ? chips1 : amount);
			bet1 += tmp;
			chips1 -= tmp;
		}
		else if (p == p2) {
			tmp = (chips2 < amount ? chips2 : amount);
			bet2 += tmp;
			chips2 -= tmp;
		}
	}
	
	public boolean isAllin(PlayerControl p) {
		if (p == p1)
			return chips1 == 0;
		else if (p == p2)
			return chips2 == 0;
		
		return false;
	}
}
