package game.essentials;

public enum PokerAction {
	FOLD("Fold"), CHECK("Check"), CALL("Call"), BET("Bet"), RAISE("Raise"), ALL_IN("All-in");  
	
	private String name;
	
	PokerAction(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
