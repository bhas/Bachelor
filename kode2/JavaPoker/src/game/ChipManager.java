package game;

import java.util.HashMap;

public class ChipManager {
	
	private HashMap<Integer, PlayerChips> pcs;
	
	public ChipManager() {
		pcs = new HashMap<Integer, PlayerChips>();
	}
	
	public int chips(int seat) {
		return pcs.get(seat).chips;
	}
	
	public int bet(int seat) {
		return pcs.get(seat).bet;
	}
	
	private class PlayerChips {
		
		public int bet;
		public int chips;
		
		public PlayerChips() {
			
		}
	}
}
