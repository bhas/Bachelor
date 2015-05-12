package game;

import game.essentials.PokerAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameStateManager {

	private PlayerState[] pss;
	private ArrayList<Pot> pots = new ArrayList<Pot>();
	private int highestBid, aggressor, actor, dealer, raises;

	public void newGame(String[] players) {
		pss = new PlayerState[GameSettings.TABLE_SEATS];
		for (int i = 0; i < GameSettings.TABLE_SEATS; i++) {
			if (players[i] != null)
				pss[i] = new PlayerState();
		}

		highestBid = 0;
		aggressor = -1;
		actor = 0;
		raises = 0;
		dealer = -1;
	}

	public void newRound() {
		highestBid = GameSettings.BIG_BLIND;
		raises = 0;
		aggressor = -1;
		dealer = next(dealer);
		for (PlayerState ps : pss) {
			if (ps != null) {
				ps.active = true;
				ps.folded = false;
				ps.bet = 0;
			}
		}

		payBlinds();
	}

	public Set<PokerAction> possibleActions(int seat) {
		// Only players who have not folded can perform an action
		if (pss[seat] == null || pss[seat].folded)
			return null;

		Set<PokerAction> pa = new HashSet<PokerAction>();
		int toCall = amountToCall(seat);
		int chips = getChips(seat);

		pa.add(PokerAction.FOLD);
		if (GameSettings.GAME_TYPE == GameType.NO_LIMIT) {
			pa.add(PokerAction.ALL_IN);
		}
		if (toCall == 0) { // no higher bids
			pa.add(PokerAction.CHECK);

			if (chips >= GameSettings.BIG_BLIND) {
				pa.add(PokerAction.BET);
			} else {
				pa.add(PokerAction.ALL_IN);
			}
		} else { // some higher bids
			if (chips >= toCall) {
				pa.add(PokerAction.CALL);

				if (chips >= toCall + GameSettings.BIG_BLIND) {
					pa.add(PokerAction.RAISE);
				}
			} else {
				pa.add(PokerAction.ALL_IN);
			}
		}

		return pa;
	}

	/*
	 * Find the position of the next player -1 if no player is found
	 */
	private int next(int seat) {
		for (int i = 1; i < GameSettings.TABLE_SEATS; i++) {
			int nextSeat = (seat + i) % GameSettings.TABLE_SEATS;

			if (pss[nextSeat] != null)
				return nextSeat;
		}
		return -1;
	}

	/*
	 * Find the position of the next active player -1 if no player is found
	 */
	private int nextActive(int seat) {
		int next = next(seat);
		while (next != seat || next != -1) {
			if (pss[next].active)
				return next;
		}
		return -1;
	}

	private void payBlinds() {
		// next player
		int player = next(dealer);
		if (hasMoreThan(player, GameSettings.BIG_BLIND / 2)) {
			pay(player, GameSettings.BIG_BLIND / 2);
			actor = nextActive(actor);
		} else {
			allIn(player);
		}

		player = next(player);
		if (hasMoreThan(player, GameSettings.BIG_BLIND)) {
			pay(player, GameSettings.BIG_BLIND);
			actor = nextActive(actor);
		} else {
			allIn(player);
		}
	}

	private boolean hasMoreThan(int seat, int amount) {
		PlayerState pc = pss[seat];
		return (pc.bet + pc.chips) > amount;
	}

	public int getHighestBid() {
		return highestBid;
	}

	public int numberOfRaises() {
		return raises;
	}

	public boolean bettingRoundDone() {
		return aggressor == actor;
	}

	public int amountToCall(int seat) {
		return highestBid - pss[seat].bet;
	}

	public void commit() {
		ArrayList<Pot> roundPot = new ArrayList<Pot>();
		int totalPot = 0;
		Pot p = getPot(0);
		while (p != null) {
			roundPot.add(p);
			totalPot += p.amountPerPlayer;
			p = getPot(totalPot);
		}
		for (Pot pot : roundPot) {
			for (PlayerState ps : pss) {
				if (ps.bet == 0) {
					continue;
				}
				if (ps.bet < pot.amountPerPlayer) {
					pot.totalAmount += ps.bet;
					ps.bet = 0;
				} else {
					pot.totalAmount += pot.amountPerPlayer;
					ps.bet -= pot.amountPerPlayer;
				}
			}
		}

	}

	/*
	 * Returns the
	 */
	public Pot getPot(int min) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		int minBid = -1;

		for (int seat : players) {
			PlayerState ps = pss[seat];
			if (ps.bet > min) {
				if (ps.bet < minBid || minBid == -1) {
					minBid = ps.bet;
				}
				players.add(seat);
			}
		}
		if (players.size() == 0) {
			return null;
		} else {
			Pot p = new Pot();
			p.players = players;
			p.amountPerPlayer = minBid - min;
			return p;
		}
	}

	public void call(int seat) {
		PlayerState ps = pss[seat];
		ps.chips -= amountToCall(seat);
		ps.bet = highestBid;
		actor = nextActive(actor);
	}

	public int waitingFor() {
		return actor;
	}

	public int aggressor() {
		return aggressor;
	}

	public void setWinner() {

	}

	public void raise(int seat) {
		int toPay = amountToCall(seat) + GameSettings.BIG_BLIND;
		pay(seat, toPay);
		aggressor = seat;
		actor = nextActive(seat);
	}

	private void pay(int seat, int amount) {
		PlayerState pc = pss[seat];
		pc.chips -= amount;
		pc.bet += amount;
	}

	public void allIn(int seat) {
		PlayerState pc = pss[seat];
		pc.bet += pc.chips;
		pc.chips = 0;
		pc.active = false;
		if (pc.bet > highestBid) {
			highestBid = pc.bet;
			aggressor = seat;
		}
		actor = nextActive(seat);
	}

	public void fold(int seat) {
		pss[seat].active = false;
		actor = nextActive(seat);
	}

	public int getChips(int seat) {
		return pss[seat].chips;
	}

	public int getBet(int seat) {
		return pss[seat].bet;
	}

	public int chipsToCall(int seat) {
		return highestBid - pss[seat].bet;
	}

	public void removePlayer(int seat) {
		pss[seat] = null;
	}

	private class PlayerState {

		public int bet;
		public int chips;
		public boolean folded;
		public boolean active;

		public PlayerState() {
			this.chips = GameSettings.STARTING_CHIPS;
		}

		@Override
		public String toString() {
			if (active) {
				return "b:" + bet + " c:" + chips + " active";
			} else {
				return "b:" + bet + " c:" + chips;
			}
		}

	}

	private class Pot {
		public int amountPerPlayer;
		public int totalAmount;
		public ArrayList<Integer> players = new ArrayList<Integer>();
	}
}
