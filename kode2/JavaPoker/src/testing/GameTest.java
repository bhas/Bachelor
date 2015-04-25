package testing;

import game.GameStateManager;

public class GameTest {

	public static void main(String[] args) {
		GameStateManager cm = new GameStateManager();
		String[] players = new String[] { "Michael", "Poker-John", null, "Flash Bob", null, null };
		cm.newGame(players);
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
}
