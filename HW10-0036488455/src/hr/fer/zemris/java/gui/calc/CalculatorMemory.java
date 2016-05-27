package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiFunction;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.operations.CalculatorButton;

/**
 * <p>
 * Calculator memory used to store calculation data.
 * </p>
 * <p>
 * Should be used with {@link CalculatorButton} for commands, input and
 * querries. Use set current to reset the input and set the screen to the given
 * number. Use updateCurrent with string containing a single digit. No screens
 * are set by default and should be given with addScreens. SetOperation method
 * should be used to set the next operation to be performed on the stored
 * numbers. SwitchInverse() should be used before calculate() or setCurrent() if
 * inverse operation is desired. Reset resets the stack and all internally
 * stored operations and variables.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Calculator
 * @see CalculatorButton
 */
public class CalculatorMemory {

	/**
	 * Stack of the memory.
	 */
	Stack<Double> stack;

	/**
	 * Screens on which to show the contents.
	 */
	List<JLabel> screens;

	/**
	 * Number saved if binary operation is to be calculated, first number in an
	 * operation.
	 */
	double savedNumber;

	/**
	 * Used for concurrent binary operations, second number in an operation.
	 */
	double savedSecondNumber;

	/**
	 * Currently stored number.
	 */
	String current;

	/**
	 * Determines whether to use inverse or normal operation.
	 */
	boolean inversedOperation;

	/**
	 * Tracks if the dot is present.
	 */
	boolean dot;

	/**
	 * Track if the input updates current number or starts a new one.
	 */
	boolean acceptNewNumber;

	/**
	 * Tracks if the second number is saved for concurrent binary operations.
	 */
	boolean savedSecond;

	/**
	 * Function to be performed.
	 */
	private BiFunction<Double, Double, Double> function;

	/**
	 * Inverse of the function to be performed.
	 */
	private BiFunction<Double, Double, Double> inverseFunction;

	/**
	 * Character representing the floating point.
	 */
	private static final char DOT = '.';

	/**
	 * Default screen shown in the calculator.
	 */
	private static final String DEFAULT_SCREEN = "0";


	/**
	 * Initializes the memory.
	 */
	public CalculatorMemory() {
		screens = new ArrayList<>();
		stack = new Stack<>();
		inversedOperation = false;
		reset();
	}


	/**
	 * Resets the memory state to the default state resetting the stack and all
	 * stored functions and variables.
	 */
	public void reset() {
		current = DEFAULT_SCREEN;
		savedNumber = 0;
		stack.clear();
		function = (u, a) -> Double.parseDouble(current);
		inverseFunction = function;
		acceptNewNumber = true;
		updateScreens();
	}

	/*
	 * ******** Content methods ************************************************
	 */


	/**
	 * Gets the currently displayed number.
	 * 
	 * @return returns the currently displayed number
	 */
	public double getCurrent() {
		return Double.parseDouble(current);
	}


	/**
	 * Sets the screen to show the given nummber.
	 * 
	 * @param newCurrent
	 *            new number to be displayed
	 */
	public void setCurrent(double newCurrent) {
		current = "" + newCurrent;
		if (current.indexOf(DOT) != -1) {
			dot = true;
		} else {
			dot = false;
		}
		acceptNewNumber = true;
		updateScreens();
	}


	/**
	 * Updates the current state with the given input. Accepts a string
	 * containing a number or a dot for ease of use.
	 * 
	 * @param update
	 *            string containing either a double number or a dot
	 */
	public void updateCurrent(String update) {
		if (update.equals(DOT + "")) {
			setDot();
			return;
		}
		double number = Double.parseDouble(update);

		if (acceptNewNumber) {
			current = "" + (int) number;
			acceptNewNumber = false;
		} else {
			current += (int) number;
		}

		savedSecond = false;
		updateScreens();
	}


	/**
	 * Adds the dot to the number if not already present.
	 */
	private void setDot() {
		if (acceptNewNumber) {
			current = DEFAULT_SCREEN;
			acceptNewNumber = false;
		}

		if (!dot) {
			current += DOT;
		}

		dot = true;
		savedSecond = false;
		updateScreens();
	}


	/**
	 * Clears the screen and sets it to the default screen.
	 */
	public void clear() {
		current = DEFAULT_SCREEN;
		acceptNewNumber = true;
		dot = false;
		savedSecond = false;
		updateScreens();
	}


	/*
	 * ******** Calculation methods ********************************************
	 */

	/**
	 * Sets the next operation to be perfomed and it's inverse operation. If no
	 * inverse operation exists the same operation should be given. Inverse
	 * operation will be performed if inverse flag is set by switchInverse.
	 * Inverse can be checked by isInversed method. After giving operations, a
	 * new input should be given. First operand is the input before giving the
	 * operations and second operand is given after givin operation.
	 * 
	 * @param function
	 *            function to be performed
	 * @param inverseFunction
	 *            inverse of the function if exists, function otherwise
	 */
	public void setOperations(BiFunction<Double, Double, Double> function,
			BiFunction<Double, Double, Double> inverseFunction) {
		Objects.requireNonNull(function);
		Objects.requireNonNull(inverseFunction);
		
		this.function = function;
		this.inverseFunction = inverseFunction;
		savedNumber = Double.parseDouble(current);
		acceptNewNumber = true;
	}


	/**
	 * Calculates the saved operation given by the setOperations method. Retains
	 * the operation and second operand afterwards.
	 */
	public void calculate() {
		BiFunction<Double, Double, Double> f;
		if (inversedOperation) {
			f = inverseFunction;
		} else {
			f = function;
		}

		if (!savedSecond) {
			savedSecondNumber = Double.parseDouble(current);
			savedSecond = true;
		}
		savedNumber = f.apply(savedNumber, savedSecondNumber);
		current = "" + savedNumber;
		acceptNewNumber = true;
		updateScreens();
	}


	/*
	 * ******** Inversing methods **********************************************
	 */

	/**
	 * Switches the inverse flag.
	 */
	public void switchInverse() {
		inversedOperation = !inversedOperation;
	}


	/**
	 * Checks the inverse flag.
	 * @return returns the state of invere flas
	 */
	public boolean isInversed() {
		return inversedOperation;
	}


	/*
	 * ******** Stack methods **************************************************
	 */

	/**
	 * Pushes the number to the stack.
	 */
	public void push() {
		stack.push(Double.parseDouble(current));
	}


	/**
	 * Pops the number from the stack and sets it as the current number.
	 */
	public void pop() {
		if (stack.isEmpty()) {
			return;
		}
		current = "" + stack.pop();
		updateScreens();
	}


	/*
	 * ******** Screen methods *************************************************
	 */

	/**
	 * Updates all screens with currenly stored number or message.
	 */
	public void updateScreens() {
		for (JLabel screen : screens) {
			screen.setText("<html><h2>"+current+"</h2></html>");
		}
	}

	/**
	 * Adds a screen to the memory.
	 * @param screen screen to show memory contents
	 */
	public void addScreen(JLabel screen) {
		Objects.requireNonNull(screen);
		screens.add(screen);
		updateScreens();
	}


	/**
	 * Removes a screen from the memory screens.
	 * @param screen screen to be removed
	 */
	public void removeScreen(JLabel screen) {
		screens.remove(screen);
	}


	/**
	 * Removes all used screens.
	 */
	public void removeAllScreens() {
		screens.clear();
	}
}
