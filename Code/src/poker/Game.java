package poker;

import game.Dealer;

import java.util.Random;

public class Game {
	
	private PlayerControl player1, player2;
	private Dealer dealer;
	private int bb = 50;
	private int sb = 25;
	private int startChips = 1000;
	private PlayerControl isBB;
	
	
	
	public void start() {
		
		Random random = new Random();
	    
	    if(random.nextInt(2) == 1){
	    	player1.onRoundStarted(true);
	    	player2.onRoundStarted(false);
	    	}
	    else{
	    	player1.onRoundStarted(false);
	    	player2.onRoundStarted(true);
	    }
	    	
	
}
	
	private void newRound(){
		PlayerControl isSB = null;
		if(player1 == isBB){
			isSB = player1;
			isBB = player2;
		}
		else{
			isSB = player2;
			isBB = player1;
		}
		
		isSB.payBlind(sb);
		isBB.payBlind(bb);
		dealer.newRound();
		dealer.deal();
		isSB.onTurnedGained();
		

	}
	
	    
		
	
	
	
	//Observer
	
	
	public void getCardsOnTable(){
		
		
	}
	
	
	private void getCommunityCards() {
		// TODO Auto-generated method stub

	}
}
