package poker;

import game.Card;

import java.util.ArrayList;

public class PlayerControl {
	public final String name;
	private Game game;
	private ArrayList<Card> hand;
	
	public PlayerControl(String name, Game game) {
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
	
	public void payBlind(int i){
		
	}
}
