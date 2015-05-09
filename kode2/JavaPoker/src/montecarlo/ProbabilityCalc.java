package montecarlo;

import game.essentials.Card;
import game.essentials.Deck;
import game.essentials.Hand;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityCalc {
	private static final int CALCULATIONS = 50000;
	private static final int THREADS = 10;

	public static Probability getProbability(List<Card> hand,
			List<Card> community, int opponents) {
		// needs between 1 and 10 opponents and 2 cards
		if (opponents > 10 || opponents < 1 || hand == null || hand.size() < 2)
			return null;

		if (community == null)
			community = new ArrayList<Card>();

		ArrayList<Card> excludes = new ArrayList<Card>(hand);
		excludes.addAll(community);
		Probability result = new Probability(0, 0, 0);

		// creates the threads
		Thread[] threads = new Thread[THREADS];
		for (int i = 0; i < THREADS; i++) {
			threads[i] = createThread(hand, community, excludes, opponents,
					result);
			threads[i].start();
		}

		// waits for the threads
		for (int i = 0; i < THREADS; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private static Thread createThread(List<Card> hand, List<Card> cc,
			List<Card> excludes, int opps, Probability finalResult) {
		Thread th = new Thread() {
			public void run() {
				Deck d = new Deck(excludes);
				int wins = 0, loses = 0, draws = 0;
				// simulates the games
				for (int i = 0; i < CALCULATIONS / THREADS; i++) {
					int result = simulate(d, hand, cc, opps);
					if (result == 1)
						wins++;
					else if (result == 0)
						draws++;
					else
						loses++;
				}
				finalResult.add(wins, draws, loses);
			};
		};
		return th;
	}

	public static Probability getProbability(String hand, String cc,
			int opponents) {
		List<Card> h = Card.createMultiple(hand);
		List<Card> c2 = null;
		if (cc != null) {
			c2 = Card.createMultiple(cc);
		}
		return getProbability(h, c2, opponents);

	}

	// -1 = lose, 0 = draw, 1 = win
	private static int simulate(Deck deck, List<Card> handCards,
			List<Card> community, int opponents) {
		deck.shuffle();

		// Deal cards
		// The player
		if (handCards == null || handCards.isEmpty()) {
			handCards = deck.deal(2);
		}
		// Community cards
		ArrayList<Card> testComm = new ArrayList<Card>();
		testComm.addAll(community);
		if (community.size() < 5) {
			int missing = 5 - community.size();
			testComm.addAll(deck.deal(missing));
		}
		// create the hands
		Hand hand = new Hand(handCards, testComm);
		int result = 1;
		for (int i = 0; i < opponents; i++) {
			Hand oppHand = new Hand(deck.deal(2), testComm);
			int beatOpp = hand.compareTo(oppHand);
			if (beatOpp < 0) {
				result = -1;
				break;
			}
			if (beatOpp == 0)
				result = 0;
		}
		return result;
	}
}
