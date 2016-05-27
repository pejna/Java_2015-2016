package hr.fer.zemris.java.gui.calc.operations;

import java.util.Objects;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;

/**
 * <p>
 * Unary operation button for operations to be perfomed on
 * {@link CalculatorMemory}.
 * </P>
 * <p>
 * OnClick method uses getCurrent method to get current memory and setCurrent
 * method of {@link CalculatorMemory} to pass the output.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorButton
 * @see CalculatorMemory
 */
public class UnaryOperationButton extends CalculatorButton {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 8727300222982260497L;

	/**
	 * Function to be performed.
	 */
	Function<Double, Double> function;

	/**
	 * Inverse function to be perfomed if the inverse flag is true.
	 */
	Function<Double, Double> inverseFunction;


	/**
	 * Creates the button with the given text and functions.
	 * 
	 * @param text
	 *            text of the button
	 * @param function
	 *            function to be performed if the inverse flag is false
	 * @param inverseFunction
	 *            function to be performed if the inverse flag is true
	 */
	public UnaryOperationButton(String text, Function<Double, Double> function,
			Function<Double, Double> inverseFunction) {
		super(text);
		Objects.requireNonNull(function);
		Objects.requireNonNull(inverseFunction);
		this.function = function;
		this.inverseFunction = inverseFunction;
	}


	public void onClick(CalculatorMemory memory) {
		Function<Double, Double> f;
		if (memory.isInversed()) {
			f = inverseFunction;
		} else {
			f = function;
		}
		memory.setCurrent(f.apply(memory.getCurrent()));
	}
}
