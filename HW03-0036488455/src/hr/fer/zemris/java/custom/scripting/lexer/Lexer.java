package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Performs lexical analysis on a string. Can recognize text and
 * tags in between {$ and $} characters. Recognizes strings, numbers,
 * keywords, functions and operators.
 * 
 * @author Juraj Pejnovic
 * @version 2.1
 */
public class Lexer {

	/**
	 * Array containing the given string.
	 */
	private char[] data_;
	
	/**
	 * Token containing the current found token in the string.
	 */
	private Token token_;
	
	/**
	 * Index of the currently analyzed character in data_.
	 */
	private int currentIndex_;
	
	/**
	 * State of the lexer, can be BASIC or EXTENDED
	 */
	private LexerMode mode_;
	
	/**
	 * Marks the beginning of a string.
	 */
	private static final char STRING_MARKER = '\"';
	
	/**
	 * Marks the beginning of a function.
	 */
	private static final char FUNCTION_SIGN = '@';
	
	/**
	 * Marks the beginning of a tag.
	 */
	private static final String TAG_START = "{$";
	
	/**
	 * Marks the ending of a tag.
	 */
	private static final String TAG_END = "$}";
	
	/**
	 * Used as a announcer of a negative number.
	 */
	private static final char MINUS = '-';
	
	/**
	 * Character used for portrayal of dot in double numbers.
	 */
	private static final char DOT = '.';
	
	/**
	 * Character portraying an underscore used in words.
	 */
	private static final char UNDERSCORE = '_';
	
	/**
	 * Mathematical perators supported in the lexer.
	 */
	private static final char[] OPERATORS = {
			'+',
			'-',
			'*',
			'/',
			'^'
	};
	
	
	
	/**
	 * Creates a lexer that analyzes the given string. If given null
	 * throws {@link IllegalArgumentException}. Default state is 
	 * BASIC.
	 * 
	 * @param text string we want analyzed
	 */
	public Lexer(String text){
		if(text == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot analyze null!");
		}
		
		data_ = text.toCharArray();
		currentIndex_ = 0;
		token_ = null;
		
		setMode(LexerMode.TEXT);
	}
	
	
	/**
	 * Sets the mode of work of the lexer. If TEXT, procures text 
	 * tokens. If TAG procures FOR or ECHO tokens.
	 * 
	 * 
	 * @param state wished state of the lexer
	 */
	public void setMode(LexerMode mode){
		if(mode == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot set lexer to null state!");
		}
		
		mode_ = mode;
	}
	
	
	/**
	 * Switches the state between TAG and TEXT.
	 * 
	 */
	private void switchMode(){
		if(mode_ == LexerMode.TAG){
			setMode(LexerMode.TEXT);
		} else {
			setMode(LexerMode.TAG);
		}
	}
	
	
	/**
	 * Gets the next token in the string.
	 * 
	 * @return
	 */
	public Token nextToken(){
		if(mode_ == LexerMode.TEXT){
			token_ = readText();
		} else {
			token_ = readTag();
		}
		
		return getToken();
	}
	
	
	/**
	 * Gets the current token found in the string.
	 * 
	 * @return returns the current token
	 */
	public Token getToken(){
		return token_;
	}
	
	
	/**
	 * Reads a token in the text reading mode. If it finds transition 
	 * tags, it will trasfer to tag reading mode mode.
	 * 
	 * @return returns the entire text block token
	 */
	private Token readText(){
		if(currentIndex_ > data_.length){
			throw new LexerException("Warning - "
					+ "Already reached end of file! "
					+ "No more tokens to read!");
		}
		
		String text = "";
		
		while(currentIndex_ < data_.length){
			
			if(isTransitionToTag()){
				switchMode();
				if(text.length() == 0){
					return readTag();
					
				} else {
					return new Token(TokenType.TEXT, text);
					
				}
			} else if(data_[currentIndex_] == 
					EscapeManager.ESCAPE_CHARACTER){
				currentIndex_++;
				
				if(currentIndex_ >= data_.length){
					throw new LexerException("Warning - "
							+ "Cannot escape EOF!");
				}
				
				text += EscapeManager.
						EscapeFromText(data_[currentIndex_]);
				currentIndex_++;
				
			} else {
				text += data_[currentIndex_];
				currentIndex_++;
			}
		}
		
		if(text.length() == 0){
			currentIndex_++;
			return new Token(TokenType.EOF, null);
		}
		
		return new Token(TokenType.TEXT, text);
	}
	
	
	/**
	 * Reads a token in tag reading mode. If it finds transition tags
	 * it will transfer to text reading mode.
	 * 
	 * @return returns a read token
	 */
	private Token readTag(){
		if(currentIndex_ > data_.length){
			throw new LexerException("Warning - "
					+ "Already reached end of file! "
					+ "No more tokens to read!");
		}
		
		while(currentIndex_ < data_.length){
			if(Character.isWhitespace(data_[currentIndex_])){
				currentIndex_++;
				
			} else if(data_[currentIndex_] == STRING_MARKER){
				currentIndex_++;
				return readString();
				
			} else if(isTransitionFromTag()){
				currentIndex_ += 2;
				switchMode();
				return new Token(TokenType.TRANSITION_OUT, TAG_END);
				
			} else if(isTransitionToTag()){
				currentIndex_ += 2;
				return new Token(TokenType.TRANSITION_IN, TAG_START);
				
			} else if(Character.isDigit(data_[currentIndex_]) ||
					data_[currentIndex_] == MINUS){
				return readNumber();
				
			} else if(data_[currentIndex_] == FUNCTION_SIGN){
				return readFunction();
				
			} else if(Character.isLetter(data_[currentIndex_])){
				return readWord();
				
			} else if(data_[currentIndex_] == 
					KeywordManager.KEYWORD_ECHO_CHAR){
				currentIndex_++;
				return new Token(TokenType.KEYWORD, 
						KeywordManager.KEYWORD_ECHO);
				
			} else {
				try {
					return readOperator();
					
				} catch(LexerException exception) {
					throw new LexerException("Warning - "
							+ "Unsupported character sequence "
							+ "found!");
				}
			}
		}
		
		currentIndex_++;
		return new Token(TokenType.EOF, null);
	}
	
	
	/**
	 * Reads the next word and creates either a variable or a keyword
	 * token. Throws {@link LexerException} if word is not supported.
	 * 
	 * @return returns either a VARIABLE or a KEYWORD token
	 */
	private Token readWord() {
		String value = "";
		
		while(currentIndex_ < data_.length){
			if(Character.isLetter(data_[currentIndex_])){
				value += data_[currentIndex_++] ;
			} else if(Character.isWhitespace(data_[currentIndex_])){
				currentIndex_++;
				break;
			} else if(Character.isDigit(data_[currentIndex_])){
				value += data_[currentIndex_++];
			} else if(data_[currentIndex_] == UNDERSCORE){
				value += data_[currentIndex_++];
			} else {
				
				break;
			}
		}
		
		if(KeywordManager.isKeyword(value)){
			return new Token(TokenType.KEYWORD, value);
		} else {
			return new Token(TokenType.VARIABLE, value);
		}
	}

	
	/**
	 * Reads and creates a token representing a function. Supported
	 * function names start with a letter and have as many letters,
	 * numbers or _ as desired.
	 * 
	 * @return returns created token
	 */
	private Token readFunction() {
		currentIndex_++;
		if(currentIndex_ >= data_.length){
			throw new LexerException("Warning - "
					+ "Cannot create function from end of file!");
		}
		
		if(!Character.isLetter(data_[currentIndex_])){
			throw new LexerException("Warning - "
					+ "Unsupported function name!");
		}
		
		String value = "";
		
		while(currentIndex_ < data_.length){
			if(Character.isLetter(data_[currentIndex_])
					|| Character.isDigit(data_[currentIndex_])
					|| data_[currentIndex_] == UNDERSCORE){
				value += data_[currentIndex_++];
			} else {
				currentIndex_++;
				return new Token(TokenType.FUNCTION, value);
			}
		}
		
		return new Token(TokenType.FUNCTION, value);
	}


	/**
	 * Reads a number and creates either an integer or double token.
	 * Throws {@link LexerException} if the found number is not 
	 * readable.
	 * 
	 * @return returns a toker representing either a double or an 
	 * 		integer
	 */
	private Token readNumber() {
		if(data_[currentIndex_] == MINUS){
			if(currentIndex_ + 1 != data_.length){
				if(!Character.isDigit(data_[currentIndex_ + 1])){
					return readOperator();
				}
			}
		}
		
		String value = "" + data_[currentIndex_++];
		
		while(currentIndex_ < data_.length){
			if(Character.isDigit(data_[currentIndex_])){
				value += data_[currentIndex_++];
			} else if( data_[currentIndex_] == DOT){
				value += data_[currentIndex_++];
			} else {
				break;
			}
		}
		
		try {
			int numberInt = Integer.parseInt(value);
			return new Token(TokenType.INTEGER, numberInt);
			
			
		} catch (NumberFormatException exception1){
			try {
			double numberDouble = Double.parseDouble(value);
			return new Token(TokenType.DOUBLE, numberDouble);
			
			} catch (NumberFormatException exception2){
				throw new LexerException("Warning - "
						+ "Unable to read number!");
			}
		}
	}


	/**
	 * Returns a token representing one of the supported operators.
	 * If operator is not supported throws {@link LexerException}.
	 * 
	 * @return an operator token
	 */
	private Token readOperator() {
		boolean isOperator = false;
		for(char operator : OPERATORS){
			if(data_[currentIndex_] == operator){
				isOperator = true;
				break;
			}
		}
		
		if(!isOperator){
			throw new LexerException("Warning - "
					+ "Unsupported operator!");
		}
		
		return new Token(TokenType.OPERATOR, 
				"" + data_[currentIndex_++]);
	}


	/**
	 * Reads and creates a string token between two " characters.
	 * 
	 * @return returns token representation of the read string
	 */
	private Token readString() {
		if(currentIndex_ >= data_.length){
			throw new LexerException("Warning - "
					+ "Unfinished string found!");
		}
		
		String value = "";
		
		while(currentIndex_ < data_.length){
			if(data_[currentIndex_] == STRING_MARKER){
				currentIndex_++;
				return new Token(TokenType.STRING, value);
				
			} else if(data_[currentIndex_] == 
					EscapeManager.ESCAPE_CHARACTER){
				
				currentIndex_++;
				if(currentIndex_ >= data_.length){
					throw new LexerException("Warning - "
							+ "Cannot escape EOF in string!");
				}
				
				value += EscapeManager.
						EscapeFromString(data_[currentIndex_]);
				currentIndex_++;
				
			} else {
				value += data_[currentIndex_++];
			}
		}
		
		throw new LexerException("Warning - "
				+ "Unfinished string found!");
	}


	/**
	 * Checks whether the trasition between modes is valid. Throws 
	 * {@link LexerException} if no supported transitions of that 
	 * kind exist.
	 * 
	 * @return returns true if valid transition, false if first 
	 * character is not a part of transition
	 */
	private boolean isTransitionToTag(){
		char firstCharacter = data_[currentIndex_];
		int index = currentIndex_;
		
		if(firstCharacter == '{'){
			index++;
			if(data_[index] == '$'){
				return true;
			} else {
				return false;
			}
		}
 		
		return false;
	}
	
	
	/**
	 * Checks if the character at current index is actually a 
	 * transition tag. Throws if unsupported trasition.
	 * 
	 * @return returns true if is a transition tag, otherwise false
	 */
	private boolean isTransitionFromTag(){
		char firstCharacter = data_[currentIndex_];
		int index = currentIndex_;
		
		if(firstCharacter == '$'){
			index++;
			if(data_[index] == '}'){
				return true;
			} else {
				throw new LexerException("Warning - "
						+ "Unsupported tag entry sign!");
			}
		}
 		
		return false;
	}
	
}
