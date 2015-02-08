package dealer;

import java.util.ArrayList;

public class Dealer {
	
	private State state = State.START;
	private Deck deck;
	private ArrayList<Card> p1Cards = new ArrayList<Card>();
	private ArrayList<Card> p2Cards = new ArrayList<Card>();
	private ArrayList<Card> commCards = new ArrayList<Card>();
	
	public Dealer() {
		deck = new Deck();
	}
	
	public Dealer(int seed) {
		deck = new Deck(seed);
	}
	
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
			commCards.add(deck.draw());
			commCards.add(deck.draw());
			commCards.add(deck.draw());
			state = State.FLOP;
			break;
		case FLOP:
			deck.draw(); // Burn card
			commCards.add(deck.draw());
			state = State.TURN;
			break;
		case TURN:
			deck.draw(); // Burn card
			commCards.add(deck.draw());
			state = State.RIVER;
			break;
		case RIVER:
			newRound();
			break;
		}
	}
	
	public ArrayList<Card> getHand(int id) {
		switch (id) {
		case 1:
			return p1Cards;
		case 2:
			return p2Cards;
		default:
			return null;
		}
	}
	
	public ArrayList<Card> getCommunityCards() {
		return commCards;
	}
	
	public enum State{
		START, PREFlOP, FLOP, TURN, RIVER;
	}
	
	public void newRound(){
		p1Cards.clear();
		p2Cards.clear();
		commCards.clear();
		state = State.START;
		deck.shuffle();
	}
}
