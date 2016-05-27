package hr.fer.zemirs.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates the {@link PrimListModel} class and it's capabilities. Creates a
 * window with 2 empty list and a next button. Pressing the button lists the
 * next prime number in the lists.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see PrimListModel
 */
public class PrimDemo extends JFrame {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = -2523894687551466678L;

	/**
	 * Title of the window.
	 */
	private static final String TITLE = "PrimDemo";


	/**
	 * Creates the demo.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(TITLE);
		setSize(400, 500);
		initGUI();
	}


	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		PrimListModel model;
		model = new PrimListModel();

		JButton nextButton = new JButton("next");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.next();
			}
		});

		mainPanel.add(nextButton, BorderLayout.SOUTH);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 2));

		JList<Integer> listA = new JList<>(model);
		JList<Integer> listB = new JList<>(model);
		listPanel.add(new JScrollPane(listA));
		listPanel.add(new JScrollPane(listB));

		mainPanel.add(listPanel, BorderLayout.CENTER);

		setContentPane(mainPanel);

	}


	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
