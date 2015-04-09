package gui;

import game.GameSettings;
import game.essentials.Card;
import game.observer.IObserver;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PokerWindow extends JFrame implements IObserver {
	private Container pane = getContentPane();
	private Point[] playerPos = setPlayerPos();
	private HashMap<Integer, PlayerComponent> pcs;
	private BoardComponent board = new BoardComponent(275, 250);

	public PokerWindow() {
		pane.setBackground(new Color(0, 120, 0));
		pane.setLayout(null);
	}

	private Point[] setPlayerPos() {
		int m = 20;
		int dx = 250;
		int dy = 200;
		Point[] pos = new Point[GameSettings.TABLE_SEATS];
		pos[0] = new Point(m + dx, m);
		pos[1] = new Point(m + 2 * dx, m);
		pos[2] = new Point(m + 3 * dx, m + dy);
		pos[3] = new Point(m + 2 * dx, m + dy * 2);
		pos[4] = new Point(m + dx, m + dy * 2);
		pos[5] = new Point(m, m + dy);

		return pos;
	}

	@Override
	public void update(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.update(g2);
	}

	@Override
	public void gameStarted(List<String> names, int chips) {
		pcs = new HashMap<Integer, PlayerComponent>();

		// adds players to the table view
		for (int i = 0; i < GameSettings.TABLE_SEATS; i++) {
			String s = names.get(i);
			if (s != null) {
				Point pos = playerPos[i];
				PlayerComponent pc = new PlayerComponent(s, pos.x, pos.y);
				pc.setMoney(chips, 0);
				pcs.put(i, pc);
				add(pcs.get(i));
			}
		}
		add(board);
		setSize(1000, 650);
		setVisible(true);
	}

	@Override
	public void gameFinished(int winner) {
		
	}

	@Override
	public void boardUpdated(List<Card> cc, int pot) {
		board.setCards(cc);
		board.setPot(pot);
	}

	@Override
	public void playerUpdated(int seat, List<Card> hand, int bet, int chips,
			boolean dealer) {
		PlayerComponent pc = pcs.get(seat);
		pc.setMoney(chips, bet);
		if (hand == null)
			pc.setHand(null, null);
		else
			pc.setHand(hand.get(0), hand.get(0));
	}

	private class BoardComponent extends JPanel {
		private CardIcon[] cards;
		private JLabel potLabel;

		public BoardComponent(int x, int y) {
			potLabel = new JLabel("0 $");
			potLabel.setFont(new Font("Ariel", Font.BOLD, 15));
			potLabel.setForeground(Color.orange);
			potLabel.setAlignmentX(CENTER_ALIGNMENT);

			cards = new CardIcon[5];
			for (int i = 0; i < cards.length; i++) {
				cards[i] = new CardIcon();
			}
			createLayout();
			clear();
			setOpaque(false);
			setBounds(x, y, 400, 125);
		}

		private void createLayout() {
			JPanel cardPanel = new JPanel();
			cardPanel.setOpaque(false);
			cardPanel.setAlignmentX(CENTER_ALIGNMENT);
			for (int i = 0; i < cards.length; i++) {
				cardPanel.add(cards[i]);
			}

			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(cardPanel);
			add(potLabel);
		}

		public void clear() {
			potLabel.setText("0 $");
			for (CardIcon ci : cards) {
				ci.clear();
			}
		}

		public void setCards(List<Card> cc) {
			for (int i = 0; i < cc.size(); i++) {
				Card c = cc.get(i);
				if (c == null)
					break;
				else
					cards[i].setCard(c);
			}
		}

		public void setPot(int i) {
			potLabel.setText(i + " $");
		}
	}
}
