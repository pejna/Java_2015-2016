package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing a constant double number.
 * 
 * @author Peda
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Represents the value of the number.
	 */
	private final double value_;
	
	/**
	 * Creates an element with the given value.
	 * 
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		value_ = value;
	}

	@Override
	public String asText(){
		return "" + value_;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value_;
	}
}
