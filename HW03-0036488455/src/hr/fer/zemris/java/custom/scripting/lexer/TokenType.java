package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of token types. Choose according to the desired token 
 * type.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 */
public enum TokenType {
	
	/**
	 * Type of the final token of a string.
	 */
	EOF,
	
	/**
	 * Type of token representing text.
	 */
	TEXT,
	
	/**
	 * Type of token representing a keyword for a tag.
	 */
	KEYWORD,
	
	/**
	 * Type of token representing transition to tag.
	 */
	TRANSITION_IN,
	
	/**
	 * Type of token representing transition from tag.
	 */
	TRANSITION_OUT,
	
	/**
	 * Type of token representing mathematical operator.
	 */
	OPERATOR,
	
	/**
	 * Type of token representing mathematical function.
	 */
	FUNCTION,
	
	/**
	 * Type of token representing string.
	 */
	STRING,
	
	/**
	 * Type of token representing a variable.
	 */
	VARIABLE,
	
	/**
	 * Type of token representing an integer.
	 */
	INTEGER,
	
	/**
	 * Type of token representing a double precision floating point 
	 * number.
	 */
	DOUBLE;
	
	
}
