package game.hand;

import game.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.math.BigDecimal;
import java.lang.Math;

public class Hand implements Comparable<Hand> {
	private static ArrayList<Card> cards = new ArrayList<Card>();
	private static ArrayList<Card> cards2 = new ArrayList<Card>();
	private static ArrayList<Card> cc = new ArrayList<Card>();
	private Card[] optimalHand;
	private Rank rank;
	private int[] subRanks;
	private int[] suitDis = new int[4];
	private int[] rankDis = new int[13];

	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		evaluate();
		System.out.println(findStraightFlush());
	}

	public static void main(String[] args) {
		cc.add(Card.CA);
		cc.add(Card.C8);
		cc.add(Card.S3);
		cc.add(Card.C4);
		cc.add(Card.S5);

		cards.add(Card.C7);
		cards.add(Card.C6);
		cards.addAll(cc);

		Hand hand = new Hand(cards);
	}

	private void evaluate() {
		// find the distributions of cards
		for (Card c : cards) {
			suitDis[c.getSuit()]++;
			rankDis[c.getRank()]++;
		}
		// determinRank(suitDis, rankDis);
	}

	private int findStraight() {
		int count = 0;

		for (int i = rankDis.length-1; i >=0; i--) {
			if (rankDis[i] > 0) {
				count++;
				if (count > 4) {
					return i+4;
				}
			} else {
				count = 0;
			}
		}
		return 0;
	}



	private ArrayList<Integer> findNumOfDuplicates(int numOfDups) {

		ArrayList<Integer> dups = new ArrayList<Integer>();

		for (int i = 0; i < rankDis.length; i++) {
			if (rankDis[i] == numOfDups) {
				dups.add(i);
			}
		}
		return dups;
	}

	private int isItFlush() {
		for (int i = 0; i < suitDis.length; i++) {
			if (suitDis[i] >= 5) {
				return i;
			}
		}
		return 0;
	}
	
	private boolean findStraightFlush(){
		ArrayList<Integer> rankDisSuit = new ArrayList<Integer>();
		int count = 0;

		for(int i = 0; i < suitDis.length; i++){
			if (suitDis[i] >= 5){
				for (Card c : cards) {
					if(c.getSuit() == i) {
						rankDisSuit.add(c.getRank());
					}
				}
				for (int j = rankDisSuit.size(); j >=0; j--) {
					if (rankDis[i] > 0) {
						count++;
						if (count > 4) {
							return true;
						}
			}
		}
				
			}
			}
		return false;
		}



	private int highestCard() {
		
		for(int i = 12; i>=0; i--){
			if(rankDis[i] > 0){
				return i;
			}
		}
		return 0;
	}

	//
	// private void determinRank(int[] suitDis, int[] rankDis) {
	// if (isRoyalFlush(suitDis, rankDis)) {
	//
	// } else {
	//
	// }
	// }
	//
	//
	//
	//
	//
	//
	//
	//
	// private boolean isStraightFlush(int[] suitDis, int[] rankDis) {
	// return isFlush(suitDis) && isStraight(rankDis);
	// }
	//
	// private boolean isRoyalFlush
	


	private boolean isFlush(int[] suits) {
		for (int s : suits) {
			if (s >= 5)
			return true;
		}
		return false;
	}

	// private boolean isStraight(int[] ranks) {
	// for(int s : suits) {
	// if (s >= 5)
	// return true;
	// }
	// return false;
	// }

	@Override
	public int compareTo(Hand h) {
		// TODO Auto-generated method stub
		return 0;
	}

	public enum Rank {
		ROYAL_FLUSH(9), STRAIGHT_FLUSH(8), FOUR_OF_A_KIND(7), FULL_HOUSE(6), FLUSH(
				5), STRAIGHT(4), THREE_OF_A_KIND(3), TWO_PAIRS(2), ONE_PAIR(1), HIGH_CARD(
				0);

		private int value;

		Rank(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
