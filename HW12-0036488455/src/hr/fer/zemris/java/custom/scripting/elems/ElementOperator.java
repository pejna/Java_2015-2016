package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of a mathematical symbol.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ElementOperator extends Element {

	/**
	 * Represents a mathematical symbol.
	 */
	private final String symbol;
	

	/**
	 * Creates an element representing given symbol.
	 * 
	 * @param symbol symbol represented by the element
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	
	@Override
	public String asText(){
		return symbol;
	}

	
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
}
