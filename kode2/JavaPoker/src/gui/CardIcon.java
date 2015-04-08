package gui;

import game.essentials.Card;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CardIcon extends JLabel {
	private static final String LOCATION = "images/";

	public void setCard(Card c) {
		String path = null;
		if (c == null)
			path = LOCATION + "back.png";
		else
			path = LOCATION + c.toString() + ".png";
		
		try {
			setIcon(new ImageIcon(ImageIO.read(new File(path))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}

}
