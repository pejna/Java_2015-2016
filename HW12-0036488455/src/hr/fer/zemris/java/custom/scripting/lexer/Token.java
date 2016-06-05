package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * A token is a structure representing a lexeme that explicitly 
 * indicates its categorization for the purpose of parsing. 
 * Represents the type given.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 */
public class Token {
	
	/**
	 * Type of the token.
	 */
	private TokenType type_;
	
	/**
	 * Value of the token.
	 */
	private Object value_;
	
	
	/**
	 * Creates a token of the given type with the given value. If
	 * the type is EOF, give null as value.
	 * 
	 * @param type type of the token
	 * @param value value of the token, if eof token set to null
	 */
	public Token(TokenType type, Object value){
		type_ = type;
		value_ = value;
	}
	
	
	/**
	 * Gets the value of the token.
	 * 
	 * @return returns the value of the token
	 */
	public Object getValue(){
		return value_;
	}
	
	
	/**
	 * Gets the type of the token.
	 * 
	 * @return returns the type of the token
	 */
	public TokenType getType(){
		return type_;
	}
	
	
	
}
