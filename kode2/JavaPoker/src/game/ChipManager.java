package game;

import game.essentials.PokerAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ChipManager {

	private ArrayList<Pot> pots = new ArrayList<Pot>();
	private ArrayList<Integer> actives = new ArrayList<Integer>();
	private HashMap<Integer, PlayerState> pss;
	private int highestBid = 0;
	private int BB;
	private int aggressor = -1;
	private int actor;
	private int raises = 0;
	private int dealer; 
	
	public ChipManager(int BB) {
		pss = new HashMap<Integer, PlayerState>();
		this.BB = BB;
		dealer = -1;
	}
	
	public static void main(String[] args) {
		ChipManager cm = new ChipManager(20);
		cm.addPlayer(0, 200);
		cm.addPlayer(2, 200);
		cm.addPlayer(3, 200);
		cm.addPlayer(5, 200);
		cm.newRound();
		cm.payBlinds();
		int actor = cm.waitingFor();
		cm.call(actor); // 5
		actor = cm.waitingFor();
		cm.call(actor); // 0
		actor = cm.waitingFor();
		cm.betTo(actor, 40); // 2
		actor = cm.waitingFor();
		cm.call(actor);// 3
		actor = cm.waitingFor();
		cm.call(actor); // 5
		actor = cm.waitingFor();
		cm.call(actor); // 0
		
	}
	
	

	public void newRound() {
		highestBid = BB;
		raises = 0;
		aggressor = -1;
		dealer = next(dealer);
		for (Entry<Integer, PlayerState> entry : pss.entrySet()) {
			entry.getValue().isActive = true;
			actives.add(entry.getKey());
		}

	}
	
	public int getHighestBid(){
		return highestBid;
	}
	
	public int numberOfRaises(){
		return raises;
	}
	
	public boolean bettingRoundDone(){
		return aggressor == actor;
	}

	public int amountToCall(int seat) {
		return highestBid - pss.get(seat).bet;
	}

	public boolean hasMoreThan(int seat, int amount) {
		PlayerState pc = pss.get(seat);
		return (pc.bet + pc.chips) > amount;
	}

	public void commit() {
		ArrayList<Pot> roundPot = new ArrayList<Pot>();
		raises = 0;
		aggressor = dealer;
		int totalPot = 0;
		Pot p = test(0);
		while (p != null) {
			roundPot.add(p);
			totalPot += p.amountPerPlayer;
			p = test(totalPot);
		}
		for (Pot pot : roundPot) {
			for (PlayerState ps : pss.values()) {
				if(ps.bet == 0){
					continue;
				}
				if (ps.bet < pot.amountPerPlayer) {
					pot.totalAmount += ps.bet;
					ps.bet = 0;
				}
				else{
					pot.totalAmount += pot.amountPerPlayer;
					ps.bet -= pot.amountPerPlayer;
				}
			}
		}

	}

	public Pot test(int min) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		int minBid = -1;

		for (int seat : actives) {
			PlayerState ps = pss.get(seat);
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

	private int next(int seat) {
		for (int i = 1; i < GameSettings.TABLE_SEATS; i++) {
			int nextSeat = (seat + i) % GameSettings.TABLE_SEATS;

			if (pss.containsKey(nextSeat) && pss.get(nextSeat).isActive) {
				return nextSeat;
			}
		}
		return -1;
	}

	public void fold(int seat) {
		pss.get(seat).isActive = false;
		actives.remove(seat);
	}

	public void payBlinds() {
		// next player
		int player = next(dealer);
		if (hasMoreThan(player, BB / 2))
			betTo(player, BB / 2);
		else {
			allIn(player);
		}
		player = next(player);
		if (hasMoreThan(player, BB))
			betTo(player, BB);
		else {
			allIn(player);
		}
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

	public void addPlayer(int seat, int amount) {
		pss.put(seat, new PlayerState(amount));
	}

	public void removePlayer(int seat) {
		pss.remove(seat);
	}

	private class PlayerState {

		public int bet = 0;
		public int chips;
		public boolean isActive = true;

		public PlayerState(int chips) {
			this.chips = chips;
		}
		
		public String toString(){
			if(isActive){
				return "b:" +  bet + " c:" + chips + " active";
			}
			else{
				return "b:" +  bet + " c:" + chips;
			}
		}

	}

	private class Pot {
		public int amountPerPlayer;
		public int totalAmount;
		public ArrayList<Integer> players = new ArrayList<Integer>();
	}
}
