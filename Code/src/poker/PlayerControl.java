package poker;

import game.Card;

import java.util.ArrayList;

public class PlayerControl {
	public final int id;
	
	private Game game;
	private ArrayList<Card> hand;
	private int chips;
	
	public PlayerControl(int id, int chips, Game game) {
		this.id = id;
		this.chips = chips;
		this.game = game;
	}
	
	public void onTurnedGained() {
		
	}
	
	public void onRoundEnded() {
		// cards, winner, amount
	}
	
	public void call() {
		// TODO Auto-generated method stub
	}
	
	public void raise() {
		// TODO Auto-generated method stub
	}
	
	public void fold() {
		// TODO Auto-generated method stub
	}
	
	public void getMyHand() {
		// TODO Auto-generated method stub
	}
	
	public void getMyChips() {
		// TODO Auto-generated method stub
	}
	
	public void getMyBet() {
		// TODO Auto-generated method stub
	}
	
	public void getOpponentsBet() {
		// TODO Auto-generated method stub
	}
	
	public void getPot() {
		// TODO Auto-generated method stub
	}
	
	public void getBB() {
		// TODO Auto-generated method stub
	}
	
	public void isMyTurn() {
		// TODO Auto-generated method stub
	}
}
