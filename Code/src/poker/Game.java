package poker;

import game.Dealer;

import java.util.Random;

public class Game {

	private Random random = new Random();
	private PlayerControl player1, player2;
	private Dealer dealer;
	private ChipManager cm;
	private int BB;
	private int SB;
	private int startChips;
	// overvejer ny datastruktur for at undgå if statements
	private PlayerControl isBB;
	// skal turn være playercontrol?
	private int turn;

	public Game(int BB, int startChips) {
		this.BB = BB;
		SB = BB / 2;
		this.startChips = startChips;
	}

	public void setPlayers(PlayerControl p1, PlayerControl p2) {
		player1 = p1;
		player2 = p2;
		isBB = random.nextInt(2) == 1 ? player1 : player2;
		cm = new ChipManager(p1, p2, BB, startChips);
		newRound();
	}

	private void newRound() {
		switchBlinds();
		payBlinds();
		dealer.newRound();
		dealer.deal();

		// notify sb player
		(isBB == player1 ? player2 : player1).onTurnedGained();	
	}
	
	public boolean call(PlayerControl p) {
		
	}

	private void payBlinds() {
		if (isBB == player1) {
			cm.bet(player1, BB);
			cm.bet(player2, SB);
		} else {
			cm.bet(player2, BB);
			cm.bet(player1, SB);
		}
	}
	
	private boolean hasTurn(PlayerControl p) {
		return p == 
	}

	private void switchBlinds() {
		isBB = player1 == isBB ? player2 : player1;
	}
}
