package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Enumeration of the {@link Lexer} states. 
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 *
 */
public enum LexerMode {
	
	/**
	 * Text-reading mode of the {@link Lexer}.
	 */
	TEXT,
	
	/**
	 * Tag-reading mode of the {@link Lexer}.
	 */
	TAG;
}
