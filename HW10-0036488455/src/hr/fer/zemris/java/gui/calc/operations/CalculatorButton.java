package hr.fer.zemris.java.gui.calc.operations;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;

/**
 * <p>
 * Button used intended to be used with {@link CalculatorMemory} classes.
 * </p>
 * <p>
 * Provides methods to perform querries over the memory with the onClick method
 * which must be overriden by the children classes. Memory used must be set by
 * the installOnClick method first. Children classes should tell the operation
 * performed in their class description.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorMemory
 */
public abstract class CalculatorButton extends JButton {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = -5965524737072671462L;

	/**
	 * Default color of the button.
	 */
	private static final Color DEFAULT_COLOR = new Color(114, 159, 207);


	/**
	 * Creates the button.
	 * 
	 * @param text
	 *            text of the button
	 */
	public CalculatorButton(String text) {
		super(text);

		setBackground(DEFAULT_COLOR);
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		setText("<html><h2>"+text+"</h2></html>");
	}


	/**
	 * Performs the operation on the given memory.
	 * 
	 * @param memory
	 *            memory for the operation to be performed on
	 */
	public abstract void onClick(CalculatorMemory memory);


	/**
	 * Installs the onClick method in this class. Enables it to be called when
	 * the mutton is clicked in the GUI.
	 * 
	 * @param memory
	 *            memory to be used
	 * @return returns a reference to this for ease of use
	 */
	public CalculatorButton installOnClick(CalculatorMemory memory) {
		CalculatorButton button = this;

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.onClick(memory);
			}

		});

		return button;
	}
}
