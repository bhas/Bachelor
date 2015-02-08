package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dealer.Card;
import dealer.Dealer;


@SuppressWarnings("serial")
public class GUI extends JFrame {
	private Container pane = getContentPane();
	private Dealer dealer = new Dealer(1337);
	private PokerBoard board = new PokerBoard();
	private ControlPanel cp = new ControlPanel();
	
	public GUI() {
		super("Poker");
		pane.setLayout(new BorderLayout());
		pane.add(board, "Center");
		pane.add(cp, "South");
		
		
		setSize(540, 520);
//		dealer.deal();
//		pane.setBackground(Color.green);
//		pane.setLayout(new BorderLayout());
//		pane.add(new PokerBoard(dealer), "North");
//		pane.add(createButtonPanel(), "Center");
		
		setVisible(true);
	}
}
