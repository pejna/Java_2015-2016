package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Interface for usage when an object represents a lexical token.
 * {@link TokenType} is to be also implemented in the implementing
 * concrete class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface Token {
	
	
	/**
	 * Type of the token. Must be implemented in the implementing
	 * class.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	public static interface TokenType{
		
	}
	
	/**
	 * Gets the type of the token.
	 * 
	 * @return returns the type of the token
	 */
	public TokenType getType();
	
	
	/**
	 * Gets the value of the token.
	 * 
	 * @return returns the value of the token
	 */
	public Object getValue();
}
