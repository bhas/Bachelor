package event;

import poker.PlayerControl;
import dealer.Card;


public interface GameObserver {
	public void actionPerformed(ActionEvent e);
	
	public void cardDealt(String s, Card[] cards);
	
	public void roundFinished(PlayerControl winner, Card[] cards, int winPrice);
	
	public void roundStarted(int p1chips, int p2chips, int p1blind, int p2blind);
	
	}

