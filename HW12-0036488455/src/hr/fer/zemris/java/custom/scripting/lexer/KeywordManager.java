package hr.fer.zemris.java.custom.scripting.lexer;

import javax.swing.text.html.parser.Parser;

/**
 * Manages keywords of tags parsable by {@link Parser}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class KeywordManager {
	
	/**
	 * Keyword names of supported tags, used for creation of tag 
	 * tokens.
	 */
	public static final String[] KEYWORDS = {
			"END",
			"FOR",
			"="
	};
	
	/**
	 * Keyword of end tag.
	 */
	public static final String KEYWORD_END = KEYWORDS[0];
	
	/**
	 * Keyword of for tag.
	 */
	public static final String KEYWORD_FOR = KEYWORDS[1];
	
	/**
	 * Keyword of echo tag.
	 */
	public static final String KEYWORD_ECHO = KEYWORDS[2];
	
	/**
	 * Keyword of echo tag as a character.
	 */
	public static final char KEYWORD_ECHO_CHAR = 
			KEYWORD_ECHO.toCharArray()[0];
	
	/**
	 * Checks whether the given string is a keyword.
	 * 
	 * @param value string we wish checked
	 * @return returns true if it is a keyword, false otherwise
	 */
	public static boolean isKeyword(String value) {
		for(String string : KEYWORDS){
			if(string.toUpperCase().equals(value.toUpperCase())){
				return true;
			}
		}
		
		return false;
	}


	/**
	 * Determines whether the given token is a keyword token.
	 * 
	 * @param token token to be identified
	 * @return returns true if keyword, otherwise false
	 */
	public static boolean isKeywordToken(Token token) {
		return token.getType() == TokenType.KEYWORD;
	}

}
