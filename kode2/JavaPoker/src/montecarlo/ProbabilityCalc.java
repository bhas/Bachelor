package montecarlo;

import game.cards.Card;
import game.cards.Deck;
import game.hand.Hand;
import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilityCalc {
	public static final int CALCULATIONS = 10000;

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
		if (community != null)
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
		if (community.size() < 5) {
			int missing = 5 - community.size();
			community.addAll(deck.deal(missing));
		}
		// create the hands
		Hand hand = new Hand(handCards, community);
		int result = 1;
		for (int i = 0; i < opponents; i++) {
			int beatOpp = hand.compareTo(new Hand(deck.deal(2), community));
			if (beatOpp < 0)
				return -1;
			if (beatOpp == 0)
				result = 0;
		}

		return result;
//		Random r = new Random();
//		int res = r.nextInt(10);
//		if (res == 0)
//			return 0;
//		if (res < 4)
//			return 1;
//		else
//			return -1;
	}

	public static void main(String[] args) {
		ArrayList<Card> arr = new ArrayList<Card>();
		arr.add(Card.C6);
		arr.add(Card.D7);
		ArrayList<Card> arr2 = new ArrayList<Card>();
		arr2.add(Card.S5);
		arr2.add(Card.D4);
		arr2.add(Card.CQ);
		arr2.add(Card.H10);

		Probability result = new Probability(0, 0, 0);
		GraphData gd = new GraphData();
		for (int i = 1; i <= 10; i++) {
			Probability p = ProbabilityCalc.getProbability(arr, arr2, 5);
			result.add(p);
			gd.addEntry(i * ProbabilityCalc.CALCULATIONS, result.percent());
			System.out.println("Run " + i + ": " + result.percent());
		}

		Graph g = new Graph(gd);
		g.setDescriptions("Monte Carlo tests", "Probability");
		g.setViewY(0, 1);
		g.setViewX(0, 10 * ProbabilityCalc.CALCULATIONS);

		new GraphWindow(g);
	}
}
