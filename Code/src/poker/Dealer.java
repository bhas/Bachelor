package poker;

import java.util.ArrayList;

public class Dealer {
	
	private State state;
	private Deck deck;
	private ArrayList<Card> p1Cards = new ArrayList<Card>();
	private ArrayList<Card> p2Cards = new ArrayList<Card>();
	private ArrayList<Card> tableCards = new ArrayList<Card>();

	
	
	
	public enum State{
		START, PREFlOP, FLOP, TURN, RIVER;
	}
	
	//Observer pattern?
	
	public void deal(){
		
		switch (state){
		case START:
			deck.shuffle();
			p1Cards.add(deck.draw());
			p1Cards.add(deck.draw());
			p2Cards.add(deck.draw());
			p2Cards.add(deck.draw());
			state = State.PREFlOP;
			break;
		case PREFlOP:
			deck.draw(); // Burn card
			tableCards.add(deck.draw());
			tableCards.add(deck.draw());
			tableCards.add(deck.draw());
			state = State.FLOP;
			break;
		case FLOP:
			deck.draw(); // Burn card
			tableCards.add(deck.draw());
			state = State.TURN;
			break;
		case TURN:
			deck.draw(); // Burn card
			tableCards.add(deck.draw());
			state = State.RIVER;
			break;
		case RIVER:
			p1Cards.clear();
			p2Cards.clear();
			tableCards.clear();
			state = State.START;
			break;
		}
		
	}
	
	
	

}
