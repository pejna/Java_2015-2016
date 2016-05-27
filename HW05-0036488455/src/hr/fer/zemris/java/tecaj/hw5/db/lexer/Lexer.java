package hr.fer.zemris.java.tecaj.hw5.db.lexer;


/**
 * Interface enables all implementing classes to behave like
 * a lexicographical analyzer for strings.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public abstract class Lexer {

	 /**
	 * Exception used in misdeeds occuring in {@link Lexer}.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 *
	 */
	public static class LexerException extends RuntimeException {

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
	
	/**
	 * Gets the next token in the string.
	 * 
	 * @return returns the next token found in string
	 */
	public abstract Token nextToken();	
	
	/**
	 * Gets the current token found in the string.
	 * 
	 * @return returns the current token
	 */
	public abstract Token getToken();
}
