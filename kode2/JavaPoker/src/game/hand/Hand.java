package game.hand;

import game.cards.Card;
import game.cards.Deck;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand implements Comparable<Hand> {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> optimalHand = new ArrayList<Card>();
	private Rank rank;
	private int[] subRanks;
	private int[] suitDis = new int[4];
	private int[] rankDis = new int[13];
	private int fs, sr, maxDup, maxDupRank;

	public static void main(String[] args) {
		Hand h1 = generateHand();
		Hand h2 = generateHand();
		int res = h1.compareTo(h2);
		System.out.println(res);
		// int runs = 50;
		// int[] array = new int[10];
		// System.out.println(runs + " runs");
		// for(int i = 0; i<runs;i++){
		// array[testSingleHand().getValue()]++;
		//
		//
		// }
		// printArray(array, "test");
	}

	public static Hand generateHand() {
		System.out.println();
		Deck d = new Deck();
		d.shuffle();
		List<Card> cc = d.deal(5);
		List<Card> handCards = d.deal(2);

		Hand h = new Hand(handCards, cc);
		System.out.println(h);

		return h;

	}

	public static void printArray(int[] ar, String name) {
		System.out.print(name + ": [");
		for (int i : ar) {
			System.out.print(i + ", ");
		}
		System.out.println("]");
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

	public List<Card> getOptimalCards() {
		return optimalHand;
	}

	private void findMaxDup() {
		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (rankDis[i] > maxDup) {
				maxDup = rankDis[i];
				maxDupRank = i;
			}
		}
	}

	private void findFlushSuit() {
		for (int i = 0; i < suitDis.length; i++) {
			if (suitDis[i] >= 5) {
				fs = i;
				return;
			}
		}
		fs = -1;
	}

	private boolean isStraight() {

		if (sr == -1) {
			return false;
		}

		int[] usedCards = new int[5];
		for (Card c : cards) {
			int i = sr - c.getRank();
			if (i >= 0 && i < 5 && usedCards[i] == 0) {
				optimalHand.add(c);
				usedCards[i]++;
			}
		}
		subRanks = new int[] { sr };
		rank = Rank.STRAIGHT;
		return true;

	}

	private void findStraightRank(int[] ranks) {
		int count = 0;

		for (int i = ranks.length - 1; i >= 0; i--) {
			if (ranks[i] > 0) {
				count++;
				if (count > 4) {
					sr = i + 4;
					return;
				}
			} else {
				count = 0;
			}
		}
		sr = -1;
	}

	private boolean isFullHouse() {

		if (maxDup < 3) {
			return false;
		}

		int pairRank = -1;
		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (i != maxDupRank && rankDis[i] >= 2) {
				pairRank = i;
				break;
			}
		}
		if (pairRank == -1) {
			return false;
		}

		int i = 0;
		for (Card c : cards) {
			if (c.getRank() == maxDupRank || c.getRank() == pairRank && i < 2) {
				optimalHand.add(c);
			} else if (c.getRank() == pairRank && i < 2) {
				optimalHand.add(c);
				i++;
			}
		}

		rank = Rank.FULL_HOUSE;
		subRanks = new int[] { maxDupRank, pairRank };
		return true;

	}

	private boolean isQuads() {
		Card kicker = null;

		if (maxDup < 4) {
			return false;
		}

		for (Card c : cards) {
			if (c.getRank() == maxDupRank) {
				optimalHand.add(c);
			} else {
				if (kicker == null || kicker.getRank() < c.getRank()) {
					kicker = c;
				}
			}
		}

		optimalHand.add(kicker);
		subRanks = new int[] { maxDupRank, kicker.getRank() };
		rank = Rank.FOUR_OF_A_KIND;
		return true;
	}

	private boolean isStraightFlush() {

		if (sr == -1 || fs == -1) {
			return false;
		}

		int[] flushRanks = new int[13];
		for (Card c : cards) {

			if (c.getSuit() == fs) {
				flushRanks[c.getRank()]++;
			}
		}
		findStraightRank(flushRanks);

		if (sr == -1) {
			return false;
		} else {
			for (Card c : cards) {
				if (c.getSuit() == fs && c.getRank() <= sr) {
					if (c.getRank() > sr - 5) {
						optimalHand.add(c);
					} else if (sr == 3 && c.getRank() == 12) {
						optimalHand.add(c);
					}
				}
			}
			if (sr == 12) {
				rank = Rank.ROYAL_FLUSH;
			} else {
				subRanks = new int[] { sr };
				rank = Rank.STRAIGHT_FLUSH;
			}
			return true;
		}
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

		findFlushSuit();
		findStraightRank(rankDis);
		findMaxDup();

		if (isStraightFlush()) {
			return;
		}

		if (isQuads()) {
			return;
		}

		if (isFullHouse()) {
			return;
		}

		if (isFlush()) {
			return;
		}

		if (isStraight()) {
			return;
		}

		if (isThreeOfAKind()) {
			return;
		}

		if (isTwoPair()) {
			return;
		}
		if (isPair()) {
			return;
		}
		isHighCard();

	}

	private void isHighCard() {
		subRanks = new int[5];
		int a = 0;

		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (rankDis[i] == 1) {
				subRanks[a] = i;
				a++;
				if (a == 5) {
					break;
				}
			}
		}
		for (Card c : cards) {
			if (c.getRank() >= subRanks[4]) {
				optimalHand.add(c);
			}
		}
		rank = Rank.HIGH_CARD;

	}

	private boolean isPair() {
		if (maxDup != 2) {
			return false;
		}

		int kicker1 = -1, kicker2 = -1, kicker3 = -1;
		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (rankDis[i] == 1) {
				if (kicker1 == -1) {
					kicker1 = i;
				} else if (kicker2 == -1) {
					kicker2 = i;
				} else if (kicker3 == -1) {
					kicker3 = i;
					break;
				}

			}
		}

		for (Card c : cards) {
			int r = c.getRank();
			if (r == maxDupRank || r == kicker1 || r == kicker2 || r == kicker3) {
				optimalHand.add(c);
			}
		}
		subRanks = new int[] { maxDupRank, kicker1, kicker2, kicker3 };
		rank = Rank.ONE_PAIR;
		return true;
	}

	private boolean isTwoPair() {
		if (maxDup != 2) {
			return false;
		}

		int pair2 = -1;

		for (int i = maxDupRank - 1; i >= 0; i--) {
			if (rankDis[i] == 2 && i != maxDupRank) {
				pair2 = i;
			}
		}
		if (pair2 == -1) {
			return false;
		}
		Card kicker = null;

		for (Card c : cards) {
			if (c.getRank() == maxDupRank || c.getRank() == pair2) {
				optimalHand.add(c);
			} else if (kicker == null || c.getRank() > kicker.getRank()) {
				kicker = c;
			}
		}
		optimalHand.add(kicker);
		subRanks = new int[] { maxDupRank, pair2, kicker.getRank() };
		rank = Rank.TWO_PAIRS;
		return true;

	}

	private boolean isThreeOfAKind() {
		if (maxDup != 3) {
			return false;
		}

		int kicker1 = -1;
		int kicker2 = -1;

		for (int i = rankDis.length - 1; i >= 0; i--) {
			if (rankDis[i] > 0 && i != maxDupRank) {
				if (kicker1 == -1) {
					kicker1 = i;
				} else {
					kicker2 = i;
					break;
				}
			}
		}
		subRanks = new int[3];
		subRanks[0] = maxDupRank;

		for (Card c : cards) {
			if (c.getRank() == maxDupRank) {
				optimalHand.add(c);
			} else if (c.getRank() == kicker1) {
				optimalHand.add(c);
				subRanks[1] = kicker1;
				kicker1 = -1;
			} else if (c.getRank() == kicker2) {
				optimalHand.add(c);
				subRanks[2] = kicker2;
				kicker2 = -1;
			}
		}

		rank = Rank.THREE_OF_A_KIND;
		return true;
	}

	@Override
	public String toString() {
		String s = rank + " " + optimalHand + " - " + cards;

		return s;

	}

	private boolean isFlush() {
		if (fs == -1) {
			return false;
		}

		for (Card c : cards) {
			if (c.getSuit() == fs) {
				optimalHand.add(c);
			}
		}
		optimalHand.sort(new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				if (o1.getRank() < o2.getRank()) {
					return -1;
				} else if (o1.getRank() == o2.getRank()) {
					return 0;
				} else {
					return 1;
				}
			}
		});

		if (optimalHand.size() > 5) {
			optimalHand = new ArrayList<Card>(optimalHand.subList(0, 5));
		}
		subRanks = new int[5];
		for (int i = 0; i < 5; i++) {
			subRanks[i] = optimalHand.get(i).getRank();
		}
		rank = Rank.FLUSH;
		return true;
	}

	public int[] getSubRanks() {
		return subRanks;
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
	public int compareTo(Hand h) {
		if (rank.getValue() > h.getRank().getValue()) {
			return 1;
		} else if (rank.getValue() < h.getRank().getValue()) {
			return -1;
		} else {
			int[] sr = h.getSubRanks();
			for (int i = 0; i < subRanks.length; i++) {
				if (subRanks[i] < sr[i]) {
					return -1;
				} else if (subRanks[i] > sr[i]) {
					return 1;
				}
			}
		}
		return 0;
	}
}
