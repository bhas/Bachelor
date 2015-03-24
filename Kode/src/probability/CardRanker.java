package probability;

import java.util.ArrayList;

import texasholdem.Card;
import texasholdem.Deck;

public class CardRanker {

	private Deck deck = new Deck();

	public void getProbability(int n, Card[] hand, Card[] cc, int numOfPlayers) {

	}

	private void testCase(Card[] cards, int numOfPlayers) {

		deck.shuffle();

		ArrayList<Card> useCards = new ArrayList<Card>();

		for (int i = 0; i <= cards.length; i++) {
			useCards.add(cards[i]);
		}

	}

}
