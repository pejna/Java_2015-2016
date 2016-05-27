package hr.fer.zemris.java.gui.calc.operations;

import java.util.Objects;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;

/**
 * <p>
 * A button that updates the memory with the provided string.
 * </p>
 * <P>
 * The given string should be either a single digit or a dot. If something else
 * is provided the {@link CalculatorMemory} has no means to handle that.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorButton
 * @see CalculatorMemory
 */
public class InputButton extends CalculatorButton {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 292661344636064127L;

	/**
	 * Input for the memory.
	 */
	private String input;


	/**
	 * Creates the button with the provided text and input.
	 * 
	 * @param text
	 *            text of the button
	 * @param input
	 *            input for the memory, should me either a single digit or a dot
	 */
	public InputButton(String text, String input) {
		super(text);
		Objects.requireNonNull(input);

		this.input = input;
	}


	public void onClick(CalculatorMemory memory) {
		Objects.requireNonNull(memory);

		memory.updateCurrent(input);
	}

}
