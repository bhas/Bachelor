package montecarlo;

import game.essentials.Card;
import game.essentials.Deck;
import game.essentials.Hand;
import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityCalc {
	public static final int CALCULATIONS = 50000;

	public static Probability getProbability(List<Card> hand,
			List<Card> community, int opponents) {
		// needs between 1 and 10 opponents and 2 cards
		if (opponents > 10 || opponents < 1 || hand == null || hand.size() < 2)
			return null;

		if (community == null)
			community = new ArrayList<Card>();

		int wins = 0;
		int loses = 0;
		int draws = 0;
		ArrayList<Card> excludes = new ArrayList<Card>(hand);
		excludes.addAll(community);
		Deck deck = new Deck(excludes);

		// do the simulations
		for (int i = 0; i < CALCULATIONS; i++) {
			int result = simulate(deck, hand, community, opponents);
			if (result == 1)
				wins++;
			else if (result == 0)
				draws++;
			else
				loses++;
		}
		return new Probability(wins, draws, loses);
	}
	
	public static Probability getProbability(String hand, String cc, int opponents){
		List<Card> h = Card.createMultiple(hand);
		List<Card> c2 = null;
		if(cc != null){
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
