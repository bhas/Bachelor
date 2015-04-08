package gui;

import game.GameSettings;
import game.essentials.Card;
import game.observer.IObserver;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PokerWindow extends JFrame implements IObserver {
	private Container pane = getContentPane();
	private Point[] playerPos = setPlayerPos();
	private HashMap<Integer, PlayerComponent> pcs; 

	public PokerWindow() {
		pane.setBackground(new Color(0, 120, 0));
		pane.setLayout(null);
	}
	
	private Point[] setPlayerPos() {
		int m = 20;
		int dx = 220;
		int dy = 170;
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.update(g2);
	}

	public static void main(String[] args) {
		PokerWindow pk = new PokerWindow();
		
		ArrayList<String> names = new ArrayList<String>();
		names.add(0, null);
		names.add(1, "Michael");
		names.add(2, null);
		names.add(3, "Flash Bob");
		names.add(4, null);
		names.add(5, "PrO H4xXor");
		
		pk.gameStarted(names, 500);
	}

	@Override
	public void gameStarted(List<String> names, int chips) {
		pcs = new HashMap<Integer, PlayerComponent>();
		
		// adds players to the table view
		for(int i = 0; i<GameSettings.TABLE_SEATS; i++) {
			String s = names.get(i);
			if (s != null) {
				Point pos = playerPos[i];
				PlayerComponent pc = new PlayerComponent(s, pos.x, pos.y);
				pc.setMoney(chips, 0);
				pcs.put(i, pc);
				add(pcs.get(i));
			}
		}
		
		setSize(1000, 600);
		setVisible(true);
	}

	@Override
	public void gameFinished(int winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boardUpdated(List<Card> cc, int pot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerUpdated(int seat, List<Card> cc, int bet, int chips,
			boolean dealer) {
		// TODO Auto-generated method stub
		
	}
}
