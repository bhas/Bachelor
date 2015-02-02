package gui;

import game.Dealer;
import game.Deck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI extends JFrame {
	private Container pane = getContentPane();
	private Deck d = new Deck();
	private Dealer dealer = new Dealer(1337);
	
	public GUI() {
		super("Poker");
		setSize(800, 500);
		dealer.deal();
		pane.setBackground(Color.green);
		pane.setLayout(new BorderLayout());
		pane.add(new PokerBoard(dealer), "North");
		pane.add(createButtonPanel(), "Center");
		
		setVisible(true);
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		
		JButton button = new JButton("Deal");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dealer.deal();
				repaint();
			}
		});
		panel.add(button);
		
		return panel;
	}
}
