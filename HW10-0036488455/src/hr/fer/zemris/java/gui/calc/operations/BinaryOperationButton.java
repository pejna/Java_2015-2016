package hr.fer.zemris.java.gui.calc.operations;

import java.util.Objects;
import java.util.function.BiFunction;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;

/**
 * <p>
 * A binary operation button that installs in the memory an operation that uses
 * two operands gotten from memory to calculate a new number and store it in the
 * memory.
 * </p>
 * <p>
 * The class uses setOperations method of the {@link CalculatorMemory}.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorButton
 * @see CalculatorMemory
 */
public class BinaryOperationButton extends CalculatorButton {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 1661545190134436471L;

	/**
	 * Function to be perfomed.
	 */
	private BiFunction<Double, Double, Double> function;

	/**
	 * Inverse of the function to be perfomed.
	 */
	private BiFunction<Double, Double, Double> inverseFunction;


	/**
	 * Creates the button that installs the given functions.
	 * 
	 * @param text
	 *            text of the button
	 * @param function
	 *            function to be performed if inverse flag is false
	 * @param inverseFunction
	 *            function to be performed if the inverse flag is true
	 */
	public BinaryOperationButton(String text,
			BiFunction<Double, Double, Double> function,
			BiFunction<Double, Double, Double> inverseFunction) {
		super(text);

		Objects.requireNonNull(function);
		Objects.requireNonNull(inverseFunction);

		this.function = function;
		this.inverseFunction = inverseFunction;
	}


	public void onClick(CalculatorMemory memory) {
		memory.setOperations(function, inverseFunction);
	}
}
