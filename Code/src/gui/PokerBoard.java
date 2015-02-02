package gui;

import game.Card;
import game.Dealer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class PokerBoard extends JComponent {
	
	private Dealer dealer;
	
	public PokerBoard(Dealer d) {
		dealer = d;
		setBackground(new Color(0,200,0));
		setPreferredSize(new Dimension(500, 300));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// draw cards
		drawCards(20, 20, dealer.getHand(1), g);
		drawCards(getWidth()-150, 20, dealer.getHand(2), g);
		drawCards(getWidth() / 2 - 100, 20, dealer.getCommunityCards(), g);
	}
	
	private void drawCards(int x, int y, ArrayList<Card> cards, Graphics g) {
		int i = 0;
		while (i < cards.size()) {
			drawSingleCard(x + i * 50, y, cards.get(i), g);
			i++;
		}
	}
	
	private void drawSingleCard(int x, int y, Card c, Graphics g) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File("images/cards/" + c.toString() + ".png"));
		} catch (IOException e) {
			
		}
		
		g.drawImage(img, x,  y, null);
	}
}
