package hr.fer.zemris.java.tecaj.hw3.prob1;


/**
 * Exception used in misdeeds occuring in {@link Lexer}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * ID of exception.
	 */
	private static final 
			long serialVersionUID = -3418417678755472299L;


	/**
	 * Creates an empty exception.
	 */
	public LexerException(){
	}
	
	/**
	 * Creates an exception with the given string as a message.
	 * 
	 * @param string message for the exception
	 */
	public LexerException(String string) {
		super(string);
	}
	
	
}
