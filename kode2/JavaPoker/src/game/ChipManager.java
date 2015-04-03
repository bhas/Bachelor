package game;

import java.util.ArrayList;
import java.util.HashMap;

public class ChipManager {
	
	private ArrayList<Pot> pots = new ArrayList<Pot>();
	private ArrayList<Integer> allIns = new ArrayList<Integer>();
	private HashMap<Integer, PlayerChips> pcs;
	private int highestBid = 0;
	private int BB;
	
	public ChipManager(int BB) {
		pcs = new HashMap<Integer, PlayerChips>();
		this.BB = BB;
	}
	
	public boolean canAfford(int seat, int amount) {
		PlayerChips pc = pcs.get(seat);
		return (pc.bet + pc.chips) >= amount;
	}
	
	public void commit() {
		
	}
	
	public void betTo(int seat, int amount) {
		PlayerChips pc = pcs.get(seat);
		pc.chips -= amount - pc.bet;
		pc.bet = amount;
	}
	
	public void allIn(int seat) {
		PlayerChips pc = pcs.get(seat);
		pc.bet += pc.chips;
		allIns.add(seat);
	}
	
	public int next(int seat) {
		int nextSeat = (seat + 1) % GameSettings.TABLE_SEATS;
		while(!pcs.containsKey(nextSeat) && nextSeat != seat) {
			nextSeat = ++nextSeat  % GameSettings.TABLE_SEATS;
		}
		
		if (nextSeat == seat)
			return -1;
		else
			return nextSeat;
	}
	
	public void payBlinds(int dealer) {
		// next player
		int player = next(dealer);
		if (canAfford(player, BB / 2))
			betTo(player, BB / 2);
		else {
			allIn(player);
		}
		player = next(player);
		if (canAfford(player, BB / 2))
			betTo(player, BB / 2);
		else {
			allIn(player);
		}
		dealer = (dealer + 1) % GameSettings.TABLE_SEATS;
		
	}
	
	public int getChips(int seat) {
		return pcs.get(seat).chips;
	}
	
	public int getBet(int seat) {
		return pcs.get(seat).bet;
	}
	
	public int chipsToCall(int seat){
		return highestBid - pcs.get(seat).bet;		
	}
	
	public void addPlayer(int seat, int amount){
		pcs.put(seat, new PlayerChips(amount));
	}
	
	public void removePlayer(int seat){
		pcs.remove(seat);
	}
	
	private class PlayerChips {
		
		public int bet = 0;
		public int chips;
		
		public PlayerChips(int chips) {
			this.chips = chips;
		}
	}
	
	private class Pot {
		public int amount;
		public int[] players;
	}
}
