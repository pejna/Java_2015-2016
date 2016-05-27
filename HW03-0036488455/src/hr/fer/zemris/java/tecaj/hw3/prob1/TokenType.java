package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enumeration of token types. Choose according to the desired token 
 * type.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public enum TokenType {
	
	/**
	 * Type of the final token of a string.
	 */
	EOF,
	
	/**
	 * Type of a token containing a word.
	 */
	WORD,
	
	/**
	 * Type of a token containing a number.
	 */
	NUMBER,
	
	/**
	 * Type of a token containing a number.
	 */
	SYMBOL;
}
