package poker;

import java.util.HashSet;

import event.GameObserver;

public class PokerGame {
	private GameController control;
	private PlayerControl[] players;
	private HashSet<GameObserver> obs = new HashSet<GameObserver>();
	
	public PlayerControl[] getPlayerControllers() {
		return players;
	}
	
	public void add(GameObserver o) {
		obs.add(o);
	}
	
	public void remove(GameObserver o) {
		obs.remove(o);
	}
	
	public void start() {
		
	}
	
	public void restart() {
		
	}
	
	public enum Action {
		FOLD, CALL, BET, DEAL;
	}
}
