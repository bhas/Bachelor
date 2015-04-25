package testing;

import game.essentials.Card;
import game.essentials.PokerAction;
import gui.PokerWindow;

import java.util.ArrayList;
import java.util.List;

public class GuiTest {
	public static void main(String[] args) {
		PokerWindow pk = new PokerWindow();

		ArrayList<String> names = new ArrayList<String>();
		names.add(0, null);
		names.add(1, "Michael");
		names.add(2, null);
		names.add(3, "Flash Bob");
		names.add(4, "Mr Pop");
		names.add(5, null);

		try {
			simulate(names, pk);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void simulate(List<String> names, PokerWindow pk)
			throws InterruptedException {
		pk.gameStarted(names, 500);
		Thread.sleep(2000);
		pk.roundStarted(1);
		pk.playerUpdated(1, null, 0, 500);
		pk.playerUpdated(3, null, 10, 490);
		pk.playerUpdated(4, null, 20, 480);
		pk.actorChanged(1);
		Thread.sleep(2000);
		pk.playerUpdated(1, null, 20, 480);
		pk.playerActed(1, PokerAction.CALL);
		Thread.sleep(1000);
		pk.actorChanged(3);
		Thread.sleep(2000);
		pk.playerUpdated(3, null, 20, 480);
		pk.playerActed(3, PokerAction.CALL);
		Thread.sleep(1000);
		pk.actorChanged(4);
		Thread.sleep(2000);
		pk.playerActed(4, PokerAction.CHECK);
		pk.boardUpdated(Card.createMultiple("Jd Kd 4s"), 60);
		pk.actorChanged(3);
	}
}
