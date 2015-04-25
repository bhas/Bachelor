package game.observer;

import game.essentials.Card;
import game.essentials.PokerAction;

import java.util.List;

public interface IObserver {
	public void gameStarted(List<String> names, int chips);

	public void gameFinished(int winner);

	public void boardUpdated(List<Card> cc, int pot);

	public void playerUpdated(int seat, List<Card> cc, int bet, int chips);

	public void actorChanged(int seat);

	public void roundStarted(int dealer);
	
	public void playerActed(int seat, PokerAction pa);
}
