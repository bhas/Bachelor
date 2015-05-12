package game;

import game.essentials.Card;
import game.essentials.Deck;
import game.essentials.PokerAction;
import game.player.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table implements Runnable {
	
	private List<IPlayer> players;
	private int BB;
	private int startMoney;
	private GameStateManager cm;
	private Deck deck;
	private ArrayList<Card> cc = new ArrayList<Card>();
	private HashMap<IPlayer, List<Card>> hands;

	public Table(int BB, int startMoney) {
		this.BB = BB;
		this.startMoney = startMoney;
		cm = new GameStateManager();
		players = new ArrayList<IPlayer>();
		deck = new Deck();
		hands = new HashMap<IPlayer, List<Card>>();
	}

	private Set<PokerAction> possibleActions(IPlayer p) {
		int seat = -1;
		HashSet<PokerAction> pa = new HashSet<PokerAction>();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == p) {
				seat = i;
			}
		}
		int toCall = cm.amountToCall(seat);
		int chips = cm.getChips(seat);
		pa.add(PokerAction.FOLD);
		if (GameSettings.GAME_TYPE == GameType.NO_LIMIT) {
			pa.add(PokerAction.ALL_IN);

		}
		if (toCall == 0) {
			pa.add(PokerAction.CHECK);

			if (chips >= BB) {
				pa.add(PokerAction.BET);
			} else {
				pa.add(PokerAction.ALL_IN);
			}
		}

		else {
			pa.add(PokerAction.CHECK);

			if (chips >= toCall) {
				pa.add(PokerAction.CALL);

				if (chips >= toCall + BB) {
					pa.add(PokerAction.RAISE);
				}
			}

			else {
				pa.add(PokerAction.ALL_IN);
			}
		}

		return pa;

	}

	public void startGame() {
		for (IPlayer p : players) {
			p.gameStarted(players);
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		System.out.println("Started Game");

		while (true) {
			int noOfActivePlayers = 0;
			for (IPlayer player : players) {
				if (player.getCash() >= bigBlind) {
					noOfActivePlayers++;
				}
			}
			if (noOfActivePlayers > 1) {
				newRound();
			} else {
				break;
			}
		}

		// Game over.
		board.clear();
		pots.clear();
		bet = 0;
		notifyBoardUpdated();
		for (Player player : players) {
			player.resetHand();
		}
		notifyPlayersUpdated(false);
		notifyMessage("Game over.");
	}

	private void newRound() {
		cm.newRound();
		deck.shuffle();
		// Deals cards to every player
		for (IPlayer p : players) {
			List<Card> hand = deck.deal(2);
			hands.put(p, hand);
			p.cardsDealt(hand, null);
		}
	}

	public void startBettingRound() {
	int actor = cm.waitingFor();
	while(!cm.bettingRoundDone()){
		actor = cm.waitingFor();
		players.get(actor).act(minBet, currentBet, allowedActions)
	}
}

	public void addPLayer(IPlayer p, int seat) {
		players.add(seat, p);
		cm.addPlayer(seat, startMoney);
	}

	public void removePlayer(IPlayer p) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == p) {
				cm.removePlayer(i);
				players.remove(p);
				return;
			}
		}
	}

}
