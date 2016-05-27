package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.lexer.EscapeManager;

/**
 * Element containing a string.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ElementString extends Element {

	/**
	 * Represents the string contained in the element.
	 */
	private final String value_;

	
	/**
	 * Creates an element with the given string.
	 * 
	 * @param value value of the string
	 */
	public ElementString(String value) {
		value_ = value;
	}

	
	/**
	 * Returns the string as document text.
	 */
	@Override
	public String asText(){
		String string = value_;
		
		for(int i = 0; 
				i < EscapeManager.ESCAPABLE_STRING.length;
				i++){
			string = string.replace(
					"" + EscapeManager.ESCAPED_TO_STRING[i],
					
					"" + EscapeManager.ESCAPE_CHARACTER + 
					EscapeManager.ESCAPABLE_STRING[i]);
		}

		string = "\"" + string + "\"";
		return string;
	}

	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value_;
	}
}
