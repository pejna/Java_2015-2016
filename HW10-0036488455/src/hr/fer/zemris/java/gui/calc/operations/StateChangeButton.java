package hr.fer.zemris.java.gui.calc.operations;

import java.util.Objects;
import java.util.function.Consumer;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;

/**
 * <p>
 * A universal {@link CalculatorButton} that i able to change the calculator
 * state.
 * </p>
 * <p>
 * The button can use all methods of the of the {@link CalculatorMemory} but
 * should be used only for those methods not accesable by {@link InputButton},
 * {@link BinaryOperationButton} or {@link UnaryOperationButton}.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorButton
 */
public class StateChangeButton extends CalculatorButton {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 7411486782657443162L;

	/**
	 * Function to be performed.
	 */
	private Consumer<CalculatorMemory> function;


	/**
	 * Creates the button with the given text and function. Function should
	 * contain calls to {@link CalculatorMemory} methods.
	 * 
	 * @param text
	 *            text of the button
	 * @param function
	 *            functio to be performed on {@link CalculatorMemory}
	 */
	public StateChangeButton(String text, Consumer<CalculatorMemory> function) {
		super(text);
		Objects.requireNonNull(function);
		this.function = function;
	}


	public void onClick(CalculatorMemory memory) {
		function.accept(memory);
	}
}
