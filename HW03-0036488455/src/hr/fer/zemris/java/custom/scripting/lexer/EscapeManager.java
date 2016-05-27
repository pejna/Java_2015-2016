package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Manages escape characters found in files {@link Lexer} 
 * can tokenize.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class EscapeManager {


	/**
	 * Character used for escaping usual meanings of characters.
	 */
	public static final char ESCAPE_CHARACTER = '\\';
	
	/**
	 * Characters that can be escaped in text reading mode.
	 */
	public static final char[] ESCAPABLE_TEXT = {
			'\\',
			'{'
	};
	
	/**
	 * Charaters that can be escaed in strings.
	 */
	public static final char[] ESCAPABLE_STRING = {
			'\\',
			'\"',
			'n',
			'r',
			't'
	};
	
	/**
	 * Characters into which characters from ESCAPABLE_STRING are
	 * escaped to. Should maintain indexes accordingly.
	 */
	public static final char[] ESCAPED_TO_STRING = {
			'\\',
			'\"',
			'\n',
			'\r',
			'\t'
	};
	
	/**
	 * Number of position of linestart letter in ESCAPABLE_STRING
	 */
	public static final char LETTER_LINESTART = ESCAPABLE_STRING[2];
	
	/**
	 * Number of position of newline letter in ESCAPABLE_STRING
	 */
	public static final int LETTER_NEWLINE = ESCAPABLE_STRING[3];

	/**
	 * Number of position of tab letter in ESCAPABLE_STRING
	 */
	public static final int LETTER_TAB = ESCAPABLE_STRING[4];
	
	
	/**
	 * Creates a new value from given character. Supports \ and { as 
	 * escapable characters. Throws {@link LexerException} for all 
	 * others.
	 * 
	 * @param character character we want escaped
	 * @return returns the escaped character
	 */
	public static char EscapeFromText(char character){
				
		for(char c : ESCAPABLE_TEXT){
			if(character == c){
				return character;
			}
		}
		
		throw new LexerException("Warning - "
				+ "Unsupported escapable character!");
	}
	
	
	/**
	 * Creates new value from given character. Supports \, ", n, r and
	 * t as escapable characters. Throws {@link LexerException} for
	 * all others.
	 * 
	 * @param character character we want escaped
	 * @return returns the escaped character
	 */
	public static char EscapeFromString(char character){
		boolean isEscapable = false;
		
		for(char c : ESCAPABLE_STRING){
			if(character == c){
				isEscapable = true;
				break;
			}
		}
		
		if(!isEscapable){
			throw new LexerException("Warning - "
					+ "Unsupported escape character!");
		}
		
		if(character == LETTER_LINESTART){
			return '\n';
			
		} else if(character == LETTER_NEWLINE){
			return '\r';

		} else if(character == LETTER_TAB){
			return '\t';
			
		} else {
			return character;
		}
	}
}
