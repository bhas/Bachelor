package event;

import poker.PlayerControl;

public class ActionEvent {
	public final PlayerControl player;
	public final int value;
	public final Action action;

	public ActionEvent(PlayerControl p, Action action) {
		player = p;
		this.action = action;
		value = 0;
	}

	public ActionEvent(PlayerControl p, Action action, int value) {
		player = p;
		this.action = action;
		this.value = value;
	}

	public String toString() {
		if (action == Action.BET || action == Action.CALL)
			return action.toString() + " (" + value + ") - "
					+ player.toString();

		return action.toString() + " - " + player.toString();
	}

	public enum Action {
		FOLD, CALL, BET, DEAL;
	}
}
