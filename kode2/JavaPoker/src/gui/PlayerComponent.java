package gui;

import game.essentials.Card;

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
	private JLabel betLabel, chipsLabel, nameLabel;
	private CardIcon ci1, ci2;

	public PlayerComponent(String name, int x, int y) {
		initComponents();
		setMoney(300, 200);
		setHand(Card.CA, Card.HA);
		nameLabel.setText(name);
		createLayout();
		setBounds(x, y, 155, 173);
	}

	public void setMoney(int chips, int bet) {
		chipsLabel.setText(chips + " $");
		betLabel.setText(bet + " $");
	}

	public void setHand(Card c1, Card c2) {
		ci1.setCard(c1);
		ci2.setCard(c2);
		ci1.setVisible(true);
		ci2.setVisible(true);
	}

	public void clearHand() {
		ci1.setVisible(false);
		ci2.setVisible(false);
	}

	private void initComponents() {
		Color c = Color.white;
		betLabel = new CustomLabel(c);
		chipsLabel = new CustomLabel(c);
		nameLabel = new CustomLabel(Color.orange);
		ci1 = new CardIcon();
		ci2 = new CardIcon();
	}

	private void createLayout() {
		// panel with 2 cards
		JPanel cardPanel = new JPanel();
		cardPanel.setOpaque(false);
		cardPanel.add(ci1);
		cardPanel.add(ci2);

		// panel with name, bet and chips
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(0, 0, 0, 150));
		infoPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.ORANGE));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(nameLabel);
		infoPanel.add(chipsLabel);
		infoPanel.add(betLabel);

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
