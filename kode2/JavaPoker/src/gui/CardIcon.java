package gui;

import game.essentials.Card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CardIcon extends JLabel {
	private static final String LOCATION = "images/";
	private BufferedImage cardImg = null;

	public CardIcon() {
		setPreferredSize(new Dimension(72, 96));
	}

	public void setCard(Card c) {
		String path = null;
		if (c == null)
			path = LOCATION + "back.png";
		else
			path = LOCATION + c.toString() + ".png";

		try {
			cardImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (cardImg != null) {
			g.drawImage(cardImg, 0, 0, null);
		} else {
			g.setColor(new Color(0,0,0,80));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public void clear() {
		cardImg = null;
	}
}
