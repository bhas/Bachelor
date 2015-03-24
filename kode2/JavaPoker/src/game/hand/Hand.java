package game.hand;

import game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand implements Comparable<Hand> {
	private static ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> optimalHand = new ArrayList<Card>();
	private Rank rank;
	private int[] subRanks;
	private int[] suitDis = new int[4];
	private int[] rankDis = new int[13];

	public static void main(String[] args) {
		ArrayList<Card> cc = new ArrayList<Card>();
		cc.add(Card.CA);
		cc.add(Card.CQ);
		cc.add(Card.S3);
		cc.add(Card.CK);
		cc.add(Card.D5);

		ArrayList<Card> handCards = new ArrayList<Card>();
		handCards.add(Card.CJ);
		handCards.add(Card.C10);

		Hand hand = new Hand(handCards, cc);
		System.out.println(hand.getRank());
		System.out.println(hand.getAllCards());
		System.out.println(hand.getoptimalCards());
	}

	public Hand(List<Card> hand, List<Card> community) {
		cards.addAll(hand);
		if (community != null)
			cards.addAll(community);
		determineRank();
	}

	public List<Card> getAllCards() {
		return cards;
	}

	public List<Card> getoptimalCards() {
		return optimalHand;
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

	private int maxDup() {
		int max = 0;
		for (int i = 0; i < rankDis.length; i++) {
			if (rankDis[i] > max)
				max = rankDis[i];
		}
		return max;
	}

	private boolean isFlush() {
		return findSuit() != -1;
	}

	private int findStraightRank(int[] ranks) {
		int count = 0;

		for (int i = ranks.length - 1; i >= 0; i--) {
			if (ranks[i] > 0) {
				count++;
				if (count > 4) {
					return i + 4;
				}
			} else {
				count = 0;
			}
		}
		return -1;
	}

	private boolean isStraight() {

		int count = 0;

		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (rankDis[i] > 0) {
				count++;
				if (count > 4) {
					return true;
				}
			} else {
				count = 0;
			}
		}

		if (count == 4)
			return rankDis[Card.ACE] != 0;
		return false;
	}

	private boolean findStraightFlush() {
		int fs = findSuit();

		int[] flushRanks = new int[13];
		for (Card c : cards) {

			if (c.getSuit() == fs) {
				flushRanks[c.getRank()]++;
			}
		}
		int straightRank = findStraightRank(flushRanks);

		if (straightRank == -1) {
			return false;
		} else {
			for (Card c : cards) {
				if (c.getSuit() == fs && c.getRank() <= straightRank) {
					if (c.getRank() > straightRank - 5) {
						optimalHand.add(c);
					} else if (straightRank == 3 && c.getRank() == 12) {
						optimalHand.add(c);
					}
				}
			}
			if (straightRank == 12) {
				rank = Rank.ROYAL_FLUSH;
			} else {
				subRanks = new int[] { straightRank };
				rank = Rank.STRAIGHT_FLUSH;
			}
			return true;
		}
	}

	private int findSuit() {

		for (int i = 0; i < suitDis.length; i++) {
			if (suitDis[i] >= 5) {
				return i;
			}
		}
		return -1;
	}

	private int highestCard() {

		for (int i = 12; i >= 0; i--) {
			if (rankDis[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	public Rank getRank() {
		return rank;
	}

	private void determineRank() {
		// find the distributions of cards
		for (Card c : cards) {
			suitDis[c.getSuit()]++;
			rankDis[c.getRank()]++;
		}

		boolean flush = isFlush();
		boolean straight = isStraight();
		int maxDup = maxDup();

		if (flush && straight) {
			if (findStraightFlush()) {
				return;
			}
		}

		if (maxDup == 4) {

		}
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

	@Override
	public int compareTo(Hand o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
