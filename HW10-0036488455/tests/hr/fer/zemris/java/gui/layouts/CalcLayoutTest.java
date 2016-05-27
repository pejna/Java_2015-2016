package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

public class CalcLayoutTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test frame");
		frame.setSize(500, 200);

		JPanel cp = new JPanel();
		cp.setLayout(new CalcLayout(10));
		cp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
				Color.RED, Color.BLACK));
		JTextArea labela = new JTextArea("Ovo je tekst!");

		cp.add(labela, new RCPosition(1, 1));
		JButton button = new JButton("button");
		button.setMinimumSize(new Dimension(40, 40));
		cp.add(button, "2, 3");
		cp.add(new JButton("button"), "2, 1");
		cp.add(new JButton("button"), "2, 2");
		cp.add(new JButton("button"), "2, 4");
		cp.add(new JButton("button"), "2, 5");
		cp.add(new JButton("button"), "2, 6");
		cp.add(new JButton("button"), "1, 7");
		cp.add(new JButton("button"), "2, 7");
		cp.add(new JButton("button"), "3, 7");
		cp.add(new JButton("button"), "4, 7");
		cp.add(new JButton("button"), "5, 7");

		frame.setContentPane(cp);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}
