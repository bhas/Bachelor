package game.cards;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck {
	private List<Card> excludes;
	private Stack<Card> stack;
	private SecureRandom random;

	public Deck() {
		stack = new Stack<Card>();
		random = new SecureRandom();
		shuffle();
	}

	public Deck(List<Card> excludes) {
		stack = new Stack<Card>();
		random = new SecureRandom();
		this.excludes = excludes;
	}

	public void shuffle() {
		stack.clear();
		ArrayList<Card> allCards = new ArrayList<Card>();
		for (Card c : Card.values()) {
			allCards.add(c);
		}
		if (excludes != null) {
			allCards.removeAll(excludes);
		}
		while (!allCards.isEmpty()) {
			int i = random.nextInt(allCards.size());
			stack.push(allCards.remove(i));
		}
	}

	public Card deal() {
		return stack.pop();
	}

	public List<Card> deal(int noOfCards) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < noOfCards; i++) {
			cards.add(stack.pop());
		}

		return cards;
	}
}
