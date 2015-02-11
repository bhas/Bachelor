package poker;

import java.util.Random;

import dealer.Dealer;

public class GameController {

	private Random random = new Random();
	private PlayerControl player1, player2;
	private Dealer dealer;
	private ChipManager cm;
	private int BB;
	private int SB;
	private int startChips;
	private PlayerControl isBB;
	private PlayerControl waitingFor;

	public GameController(int BB, int startChips) {
		this.BB = BB;
		SB = BB / 2;
		this.startChips = startChips;
	}

	public void setPlayers(PlayerControl p1, PlayerControl p2) {
		player1 = p1;
		player2 = p2;
		isBB = random.nextInt(2) == 1 ? player1 : player2;
		cm = new ChipManager(BB, startChips);
		newRound();
	}

	private void newRound() {
		switchBlinds();
		payBlinds();
		dealer.newRound();
		dealer.deal();

		// notify sb player
		waitingFor = (isBB == player1 ? player2 : player1);
		waitingFor.onTurnedGained();
	}
	
	private void endRound(PlayerControl winner) {
		int i = winner == player1 ? 0 : 1;
		cm.endRound(i);
		newRound();
	}
	
	public boolean bet(PlayerControl p, int bid) {
		if (waitingFor != p)
			return false;
		
		if(p == player1)
			return cm.bet(0, bid);
		if(p == player2)
			return cm.bet(1,bid);
		
		return false;
	}
	
	public boolean call(PlayerControl p) {
		if (waitingFor != p)
			return false;
		
		if (p == player1)
			return cm.call(0);
		if (p == player2)
			return cm.call(1);
		
		return false;
	}
	
	public boolean fold(PlayerControl p) {
		if (waitingFor != p)
			return false;
		
		if (p == player1) {
			cm.endRound(1);
			newRound();
		}
		if (p == player2) {
			cm.endRound(0);
			newRound();
		}
		
		return false;
	}

	private void payBlinds() {
		if (isBB == player1) {
			cm.bet(0, BB);
			cm.bet(1, SB);
		} else {
			cm.bet(1, BB);
			cm.bet(0, SB);
		}
	}
	
	private void switchTurn() {
		waitingFor = waitingFor == player1 ? player2 : player1;
		waitingFor.onTurnedGained();
	}

	private void switchBlinds() {
		isBB = player1 == isBB ? player2 : player1;
	}	
}
