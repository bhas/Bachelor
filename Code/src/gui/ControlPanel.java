package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import poker.PlayerControl;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private JButton fold, call, bet;
	private JSlider betSlide;
	private JTextField betField;
	private JLabel name, chips;

	public ControlPanel() {
		setBackground(new Color(30, 30, 30));
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));

		initComponents();
		createLayout();
	}

	private void createLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.add(name);
		panel.add(chips);
		add(panel);
		add(Box.createRigidArea(new Dimension(10,50)));
		
		add(fold);
		add(Box.createRigidArea(new Dimension(10,50)));
		add(call);
		add(Box.createHorizontalStrut(10));
		add(bet);
		add(betField);
//		
//		add(betField);
//		add(betSlide);
//		add(bet);
	}

	private void initComponents() {
		fold = new JButton("Fold");
		call = new JButton("Check / Call");
		bet = new JButton("Bet / Raise");
		name = new JLabel("Player:  Kasper Støy");
		name.setFont(new Font("Calibri", Font.PLAIN, 16));
		name.setForeground(new Color(160,160,160));
		chips = new JLabel("Chips:  200$");
		chips.setFont(new Font("Calibri", Font.PLAIN, 16));
		chips.setForeground(new Color(160,160,160));
		betField = new JTextField("0");
		betField.setColumns(4);
		betField.setMargin(new Insets(3,3,3,3));
		betSlide = new JSlider();
		betSlide.setOpaque(false);
	}

	public void show(PlayerControl p) {

	}
}
