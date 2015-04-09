package testing;

import game.essentials.Card;
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
			pk.gameStarted(names, 500);
			Thread.sleep(500);
			pk.playerUpdated(1, null, 0, 500, true);
			pk.playerUpdated(3, null, 10, 490, false);
			pk.playerUpdated(4, null, 20, 480, false);

			pk.boardUpdated(Card.createMultiple("2s 2h Jd Qd As"), 200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void simulate(List<String> names, PokerWindow pk)
			throws InterruptedException {
		pk.gameStarted(names, 500);
		Thread.sleep(1000);
		pk.playerUpdated(1, null, 0, 500, true);
		pk.playerUpdated(3, null, 10, 490, false);
		pk.playerUpdated(4, null, 20, 480, false);
		Thread.sleep(1000);
		
		pk.boardUpdated(Card.createMultiple("2s 2h Jd Qd As"), 200);
	}
}
