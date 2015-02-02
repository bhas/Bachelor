package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Deck {
	private Random random;
	private Stack<Card> stack;

	public Deck() {
		random = new Random();
		stack = new Stack<Card>();
	}
	
	public Deck(int seed) {
		random = new Random(seed);
		stack = new Stack<Card>();
	}

	public void shuffle() {
		ArrayList<Card> temp = new ArrayList<Card>(Arrays.asList(Card.values()));
		stack.clear();
		
		while (!temp.isEmpty()) {
			int i = random.nextInt(temp.size());
			stack.push(temp.remove(i));
		}
	}
	
	public Card draw() {
		return stack.pop();
	}
}
