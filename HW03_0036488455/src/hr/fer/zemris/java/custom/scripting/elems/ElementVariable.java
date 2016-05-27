package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An element representing a variable.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ElementVariable extends Element {
	
	/**
	 * Represents the name of the variable.
	 */
	private final String name_;

	
	/**
	 * Creates a element variable with the given string as a name.
	 * @param name name of the element variable
	 */
	public ElementVariable(String name) {
		name_ = name;
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return name_;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name_;
	}
	
	
}
