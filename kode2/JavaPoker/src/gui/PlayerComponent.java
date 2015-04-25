package gui;

import game.essentials.Card;
import game.essentials.PokerAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerComponent extends JComponent {
	private JLabel betLabel, chipsLabel, nameLabel, actionLabel;
	private CardIcon ci1, ci2;
	private JPanel infoPanel;

	public PlayerComponent(String name, int x, int y) {
		initComponents();
		setMoney(300, 200);
		setHand(Card.CA, Card.HA);
		setActor(false);
		nameLabel.setText(name);
		createLayout();
		setBounds(x, y, 155, 195);
	}

	public void setAction(PokerAction action) {
		if (action == null)
			actionLabel.setText("-");
		else
			actionLabel.setText(action.toString());
	}
	
	public void setMoney(int chips, int bet) {
		chipsLabel.setText(chips + " $");
		betLabel.setText(bet + " $");
	}

	public void setActor(boolean isActor) {
		if (isActor)
			infoPanel.setBackground(new Color(130, 130, 70));
		else
			infoPanel.setBackground(new Color(50, 50, 50));
	}

	public void setHand(Card c1, Card c2) {
		ci1.setCard(c1);
		ci2.setCard(c2);
		ci1.setVisible(true);
		ci2.setVisible(true);
	}

	public void setDealer(boolean d) {
		if (d) {

		}
	}

	public void clearHand() {
		ci1.setVisible(false);
		ci2.setVisible(false);
	}

	private void initComponents() {
		Color c = Color.white;
		betLabel = new CustomLabel(c);
		chipsLabel = new CustomLabel(c);
		actionLabel = new CustomLabel(new Color(255,60,50));
		nameLabel = new CustomLabel(Color.orange);
		ci1 = new CardIcon();
		ci2 = new CardIcon();
		infoPanel = new JPanel();
	}

	private void createLayout() {
		// panel with 2 cards
		JPanel cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		cardPanel.add(ci1);
		cardPanel.add(ci2);

		// panel with name, bet and chips
		infoPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.ORANGE));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(nameLabel);
		infoPanel.add(chipsLabel);
		infoPanel.add(betLabel);
		infoPanel.add(actionLabel);

		// panel with all player info
		setLayout(new BorderLayout());
		setOpaque(false);
		add(cardPanel, "North");
		add(infoPanel, "Center");
	}

	private class CustomLabel extends JLabel {
		public CustomLabel(Color c) {
			setFont(new Font("Ariel", Font.BOLD, 15));
			setForeground(c);
			setAlignmentX(CENTER_ALIGNMENT);
		}
	}
}
