package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import poker.PlayerControl;
import dealer.Card;
import event.ActionEvent;
import event.GameObserver;

@SuppressWarnings("serial")
public class GUI extends JFrame implements GameObserver {
	private PlayerControl p1, p2;
	private Container pane = getContentPane();
	private PokerBoard board;
	private ControlPanel cp = new ControlPanel();

	public GUI() {
		super("Poker");
		board = new PokerBoard("Jens", "Bjørn", 1000, 50);
		pane.setLayout(new BorderLayout());
		pane.add(board, "Center");
		pane.add(cp, "South");

		setSize(540, 520);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.action) {
		case FOLD:
			if (e.player == p1)
				board.hideHand(0);
			else if (e.player == p2)
				board.hideHand(1);
			break;
		case CALL:
		case BET:
			if (e.player == p1) {
				board.setChips(0, p1.getMyChips());
				board.setBet(0, p1.getMyBet());
			} else {
				board.setChips(1, p2.getMyChips());
				board.setBet(1, p2.getMyBet());
			}
			break;
		}
	}

	@Override
	public void cardDealt(String s, Card[] cards) {
		switch (s) {
		case "Hand":
			board.setHand(0, cards[0], cards[1]);
			board.setHand(1, cards[2], cards[3]);
		case "Flop":
			board.setSharedCard(0, cards[0]);
			board.setSharedCard(1, cards[1]);
			board.setSharedCard(2, cards[2]);
		case "Turn":
			board.setSharedCard(3, cards[0]);
		case "River":
			board.setSharedCard(4, cards[0]);
		}
	}

	@Override
	public void roundFinished(PlayerControl winner, Card[] cards, int winPrice) {
		board.setHand(0, cards[0], cards[1]);
		board.setHand(1, cards[2], cards[3]);
	}

	@Override
	public void roundStarted(int p1chips, int p2chips, int p1blind, int p2blind) {
		board.setChips(0, p1chips);
		board.setChips(0, p1chips);
		board.setBet(0, p1blind);
		board.setBet(1, p2blind);
	}
}
