package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing a constant double number.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/**
	 * Represents the value of the number.
	 */
	private final double value;


	/**
	 * Creates an element with the given value.
	 * 
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}


	@Override
	public String asText() {
		return "" + value;
	}


	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
}
