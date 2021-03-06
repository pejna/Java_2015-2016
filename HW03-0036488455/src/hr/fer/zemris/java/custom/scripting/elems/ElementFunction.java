package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents mathematical function names found in a document.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ElementFunction extends Element {
	
	/**
	 * Name of the function.
	 */
	private final String name_;
	
	
	/**
	 * Creates an element function with the given name.
	 * 
	 * @param name name of the function
	 */
	public ElementFunction(String name) {
		name_ = name;
	}

	
	@Override
	public String asText(){
		return "@" + name_;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name_;
	}
}
