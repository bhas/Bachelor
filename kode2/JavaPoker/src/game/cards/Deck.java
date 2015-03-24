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

		for (Card c : Card.values()) {
			if (excludes != null && excludes.contains(c))
				continue;

			if (!stack.isEmpty()) {
				int index = random.nextInt(stack.size());
				stack.add(index, c);
			} else {
				stack.add(c);
			}
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
