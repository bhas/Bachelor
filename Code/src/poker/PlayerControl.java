package poker;

import java.util.ArrayList;

import dealer.Card;

public class PlayerControl {
	public final String name;
	private GameController game;
	private ArrayList<Card> hand;
	
	public PlayerControl(String name, GameController game) {
		this.name = name;
		this.game = game;
	}
	
	public void onTurnedGained() {
		call();	
	}
	
	public void onRoundEnded() {
		// cards, winner, amount
	}
	
	public void call() {
		
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
	
	public int getMyChips() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getMyBet() {
		// TODO Auto-generated method stub
		return 0;
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
	
	public void payBlind(int i){
		
	}
	
}
