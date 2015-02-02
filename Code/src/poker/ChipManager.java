package poker;

import java.util.HashMap;

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
	
	public boolean bet(PlayerControl p, int bid) {
		if (p == p1 && isValidBid(chips1, bid, bet2)) {
			bet1 = bid;
			return true;
		}
		if (p == p2 && isValidBid(chips2, bid, bet1)) {
			bet2 = bid;
			return true;
		}
		return false;
	}
	
	public boolean call(PlayerControl p) {
		if (p == p1)
			return bet(p, bet2);
		if (p == p2)
			return bet(p, bet1);
		
		return false;
	}
	
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
	
	public void roundEnded(PlayerControl winner) {
		if (winner == p1)
			chips1 += (pot + bet1 + bet2);
		else
			chips2 += (pot + bet1 + bet2);
	}
	
	private void newRound() {
		
	}
	
	private boolean isValidBid(int chips, int yourBid, int oppBid) {
		return yourBid <= chips && yourBid >= oppBid + BB;
	}
}
