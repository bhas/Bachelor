package test;

import poker.Deck;

public class GameComponent {

	
	
	public static void main(String[] args) {
		Deck d = new Deck(1338);
		d.shuffle();
		
		for (int i = 0; i < 10; i++) {
			System.out.println(d.draw());	
		}
	}
}
