package game.hand;

import game.cards.Card;

import java.util.ArrayList;

public class Hand implements Comparable<Hand>{
	private ArrayList<Card> cards;
	private Card[] optimalHand;
	private Rank rank;
	private int[] subRanks;
	
	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		evaluate();
	}
	
	private void evaluate() {
		// find the distributions of cards
		int[] suitDis = new int[4];
		int[] rankDis = new int[13];
		for (Card c : cards) {
			suitDis[c.getSuit()]++;
			rankDis[c.getRank()]++;
		}
		
		determinRank(suitDis, rankDis);
	}
	
	private void determinRank(int[] suitDis, int[] rankDis) {		
		if (isRoyalFlush(suitDis, rankDis)) {
			
		} else {
			
		}
	}
	
	
	
	
	
	
	
	
	private boolean isStraightFlush(int[] suitDis, int[] rankDis) {
		return isFlush(suitDis) && isStraight(rankDis);
	}
	
	private boolean isRoyalFlush
	
	private boolean isFlush(int[] suits) {
		for(int s : suits) {
			if (s >= 5)
				return true;
		}
		return false;
	}
	
	private boolean isStraight(int[] ranks) {
		for(int s : suits) {
			if (s >= 5)
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Hand h) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public enum Rank {
		ROYAL_FLUSH(9),
	    STRAIGHT_FLUSH(8),
	    FOUR_OF_A_KIND(7),
	    FULL_HOUSE(6),
	    FLUSH(5),
	    STRAIGHT(4),
	    THREE_OF_A_KIND(3),
	    TWO_PAIRS(2),
	    ONE_PAIR(1),
	    HIGH_CARD(0);
	    
	    private int value;
	    
	    Rank(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
}
