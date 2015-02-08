package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dealer.Card;

@SuppressWarnings("serial")
public class PokerBoard extends JPanel {
	private PlayerPanel p1, p2;
	private SharedPanel sp;
	private final Font font = new Font("Calibri", Font.PLAIN, 18);

	public PokerBoard() {
		setLayout(null);
		setBackground(new Color(0, 150, 0));
		p1 = new PlayerPanel("Poker Bot", 2500);
		p1.setHand(Card.C_A, Card.S_A);
		p1.setBounds(10, 180, 170, 220);
		add(p1);
		p2 = new PlayerPanel("Kasper Støy", 5000);
		p2.setHand(Card.D_Q, Card.D_K);
		p2.setBounds(340, 180, 170, 220);
		add(p2);
		sp = new SharedPanel(50);
		sp.setBounds(60, 10, 400, 160);
		add(sp);
	}

	private class SharedPanel extends JPanel {
		private CardIcon ci1, ci2, ci3, ci4, ci5;
		private JLabel pot, blind;

		public SharedPanel(int blind) {
			setOpaque(false);
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.darkGray));
			initComponents(blind);
			createLayout();
		}

		private void initComponents(int blind) {
			ci1 = new CardIcon(null);
			ci2 = new CardIcon(null);
			ci3 = new CardIcon(null);
			ci4 = new CardIcon(null);
			ci5 = new CardIcon(null);
			pot = new JLabel("Pot:  0$");
			pot.setFont(font);
			pot.setAlignmentX(0.5f);
			this.blind = new JLabel("Blind:  " + blind + "$");
			this.blind.setFont(font);
			this.blind.setAlignmentX(0.5f);
		}

		private void createLayout() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(blind);
			
			JPanel cards = new JPanel();
			cards.add(ci1);
			cards.add(ci2);
			cards.add(ci3);
			cards.add(ci4);
			cards.add(ci5);
			cards.setOpaque(false);
			add(cards);
			add(pot);
		}

		public void setFlop(Card c1, Card c2, Card c3) {
			ci1.setCard(c1);
			ci2.setCard(c2);
			ci3.setCard(c3);
			ci1.setVisible(true);
			ci2.setVisible(true);
			ci3.setVisible(true);
		}

		public void setTurn(Card c) {
			ci4.setCard(c);
			ci4.setVisible(true);
		}

		public void setRiver(Card c) {
			ci5.setCard(c);
			ci5.setVisible(true);
		}

		public void clear() {
			ci1.setVisible(false);
			ci2.setVisible(false);
			ci3.setVisible(false);
			ci4.setVisible(false);
			ci5.setVisible(false);
		}
	}

	private class PlayerPanel extends JPanel {
		private boolean showHand;
		private Card c1, c2;
		private CardIcon ci1, ci2;
		private JLabel chips, bet, name;
		private JCheckBox box;

		public PlayerPanel(String name, int chips) {
			setOpaque(false);
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.darkGray));
			initComponents(name, chips);
			createLayout();

		}

		private void createLayout() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(name);
			add(box);

			JPanel hand = new JPanel();
			hand.add(ci1);
			hand.add(ci2);
			hand.setAlignmentX(0);
			hand.setOpaque(false);
			add(hand);

			add(chips);
			add(bet);
		}

		private void initComponents(String name, int chips) {
			ci1 = new CardIcon(null);
			ci2 = new CardIcon(null);
			this.chips = new JLabel("chips:  " + chips + "$");
			this.chips.setFont(font);
			bet = new JLabel("bet:  0$");
			bet.setFont(font);
			this.name = new JLabel(name);
			this.name.setFont(font.deriveFont(24f));
			box = new JCheckBox("Show hand");
			box.setOpaque(false);
			box.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					showHand = e.getStateChange() == ItemEvent.SELECTED ? true
							: false;
					setHand(c1, c2);
				}
			});
			box.setFont(font);
		}

		public void setHand(Card c1, Card c2) {
			this.c1 = c1;
			this.c2 = c2;
			if (showHand) {
				ci1.setCard(c1);
				ci2.setCard(c2);
			} else {
				ci1.setCard(null);
				ci2.setCard(null);
			}
		}

		public void setBet(int amount) {
			bet.setText("Bet:  " + amount + "$");
		}

		public void setChips(int amount) {
			chips.setText("Chips:  " + amount + "$");
		}

		public void setPlayerName(String s) {
			name.setText(s);
		}
	}
}
