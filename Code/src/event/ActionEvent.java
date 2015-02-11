package event;

import poker.PlayerControl;

public class ActionEvent {
	public final PlayerControl player;
	public final Action action;

	public ActionEvent(PlayerControl p, Action action) {
		player = p;
		this.action = action;
	}

	public String toString() {
		return player.toString() + ":  " + action.toString();
	}

	public enum Action {
		FOLD, CALL, BET;
	}
}
