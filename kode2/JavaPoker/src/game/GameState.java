package game;

import game.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

	private final int pot;
	private final HashMap<Integer, PlayerState> playerStates;
	private final ArrayList<Card> cards;
	private final int seatToAct;
	private final int dealer;
	private final int aggressor;

	public GameState(int seatToAct, int pot,
			HashMap<Integer, PlayerState> playerStates,
			ArrayList<Card> communityCards, int dealer, int aggressor) {
		this.pot = pot;
		this.seatToAct = seatToAct;
		this.dealer = dealer;
		this.aggressor = aggressor;
		this.playerStates = playerStates;
		this.cards = communityCards;
	}

	public int getPot() {
		return pot;
	}
	
	public PlayerState waitingFor() {
		return playerStates.get(seatToAct);
	}

	public ArrayList<Card> getCommuntyCards() {
		return cards;
	}

	public int getDealerPosition() {
		return dealer;
	}

	public PlayerState getPlayer(int seat) {
		return playerStates.get(seat);
	}

	public PlayerState getNextPlayer(int seat) {
		int i = 0;
		while (i < GameSettings.TABLE_SEATS - 1) {
			int next = ((seat + i) % GameSettings.TABLE_SEATS) + 1;
			if (playerStates.get(next) != null)
				return playerStates.get(next);
			i++;
		}

		return null;
	}

	public ArrayList<PlayerState> getAllPlayers() {
		ArrayList<PlayerState> states = new ArrayList<PlayerState>();
		for (int i = 0; i < GameSettings.TABLE_SEATS; i++) {
			PlayerState ps = playerStates.get(i);
			if (ps != null)
				states.add(ps);
		}
		return states;
	}

	public ArrayList<PlayerState> getAllActivePlayers() {
		ArrayList<PlayerState> states = new ArrayList<PlayerState>();
		for (int i = 0; i < GameSettings.TABLE_SEATS; i++) {
			PlayerState ps = playerStates.get(i);
			if (ps != null && ps.isActive)
				states.add(ps);
		}
		return states;
	}

	public PlayerState getAggressor() {
		return playerStates.get(aggressor);
	}

	public class PlayerState {

		public final String name;
		public final int bet;
		public final int chips;
		public final boolean isActive;
		public final int seat;

		public PlayerState(String name, int bet, int chips, boolean active,
				int seat) {
			this.name = name;
			this.bet = bet;
			this.chips = chips;
			this.isActive = active;
			this.seat = seat;
		}
	}
}
