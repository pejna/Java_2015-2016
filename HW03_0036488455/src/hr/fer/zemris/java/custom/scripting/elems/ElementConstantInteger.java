package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representing a constant integer.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * Represents the value of the integer.
	 */
	private final int value_;
	
	
	/**
	 * Creates the element with the given value.
	 * 
	 * @param value value of the element
	 */
	public ElementConstantInteger(int value) {
		value_ = value;
	}

	@Override
	public String asText(){
		return "" + value_;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value_;
	}
}
