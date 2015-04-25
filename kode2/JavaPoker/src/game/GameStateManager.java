package game;

import game.essentials.PokerAction;
import game.player.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

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
		}
		else { // some higher bids
			if (chips >= toCall) {
				pa.add(PokerAction.CALL);

				if (chips >= toCall + GameSettings.BIG_BLIND) {
					pa.add(PokerAction.RAISE);
				}
			}
			else {
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
		if (hasMoreThan(player, GameSettings.BIG_BLIND / 2))
			betTo(player, GameSettings.BIG_BLIND / 2);
		else {
			allIn(player);
		}
		player = next(player);
		if (hasMoreThan(player, GameSettings.BIG_BLIND))
			betTo(player, GameSettings.BIG_BLIND);
		else {
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
			p = test(totalPot);
		}
		for (Pot pot : roundPot) {
			for (PlayerState ps : pss.values()) {
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
			PlayerState ps = pss.get(seat);
			if (ps.bet > min) {
				if (ps.bet < minBid ||  nminBid == -1) {
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
		PlayerState ps = pss.get(seat);
		ps.chips -= amountToCall(seat);
		ps.bet = highestBid;
		actor = next(actor);
	}

	public int waitingFor() {
		return actor;
	}

	public int aggressor() {
		return aggressor;
	}

	public void betTo(int seat, int amount) {
		PlayerState pc = pss.get(seat);
		int toPay = amount - pc.bet;
		pc.chips -= toPay;
		pc.bet = amount;
		highestBid = amount;
		aggressor = seat;
		actor = next(seat);
	}

	public void allIn(int seat) {
		PlayerState pc = pss.get(seat);
		pc.bet += pc.chips;
		pc.chips = 0;
		pc.isActive = false;
		if (pc.bet > highestBid) {
			highestBid = pc.bet;
			aggressor = seat;
		}
		actor = next(seat);
	}

	public void fold(int seat) {
		pss.get(seat).isActive = false;
		actives.remove(seat);
	}

	public int getChips(int seat) {
		return pss.get(seat).chips;
	}

	public int getBet(int seat) {
		return pss.get(seat).bet;
	}

	public int chipsToCall(int seat) {
		return highestBid - pss.get(seat).bet;
	}

	public void removePlayer(int seat) {
		pss.remove(seat);
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
