package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dealer.Card;

@SuppressWarnings("serial")
public class CardIcon extends JLabel {

	public CardIcon(Card c) {
		setCard(c);
	}

	public void setCard(Card c) {
		BufferedImage img = null;
		
		try {
			if (c == null) 
				img = ImageIO.read(new File("images/cards/BACK.png"));
			else 
				img = ImageIO.read(new File("images/cards/" + c.toString() + ".png"));
		} catch (IOException e) { }
		
		setIcon(new ImageIcon(img));
	}
}
