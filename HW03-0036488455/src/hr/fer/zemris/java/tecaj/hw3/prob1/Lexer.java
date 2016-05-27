/**
 * Package contains all clases for 1st assignment of the 3rd homework
 * of Introduction to Java programming language in FER, 2015/2016
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Lexical analyzer. Analizes given string and procures tokens from 
 * it. Procures word, long number, symbol or eof tokens. If it 
 * finds \ in front of a number or a \ it considers it a letter.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
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
	private LexerState state_;
	
	/**
	 * Character if read switches the state.
	 */
	private static final char STATE_SWITCH = '#';
	
	/**
	 * Character if read treats the next number or \ as a letter.
	 */
	private static final char WORD_INCLUDER = '\\';
	
	
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
		
		setState(LexerState.BASIC);
	}
	
	
	/**
	 * Gets the next token in the string.
	 * 
	 * @return
	 */
	public Token nextToken(){
		if(state_ == LexerState.BASIC){
			token_ = readBasic();
		} else {
			token_ = readExtended();
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
	 * Sets the state of work of the lexer. If BASIC procures word,
	 * number or symbol tokens. If EXTENDED procures only word tokens.
	 * Activated by # in the string implicitly, switches to the other 
	 * state.
	 * 
	 * @param state wished state of the lexer
	 */
	public void setState(LexerState state){
		if(state == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot set lexer to null state!");
		}
		
		state_ = state;
	}
	
	
	/**
	 * Switches the state between BASIC and EXTENDED.
	 * 
	 */
	private void switchState(){
		if(state_ == LexerState.BASIC){
			setState(LexerState.EXTENDED);
		} else {
			setState(LexerState.BASIC);
		}
	}
	
	
	/**
	 * Called if in basic state. Reads the next token from the string.
	 * Throws {@link LexerException} if already reached the end file.
	 * 
	 * @return returns the read Token
	 */
	private Token readBasic(){
		if(currentIndex_ > data_.length){
			throw new LexerException("Warning - "
					+ "Already reached end of file! "
					+ "No more tokens to read!");
		}
		
		while(currentIndex_ < data_.length){
			if(Character.isLetter(data_[currentIndex_]) 
					|| data_[currentIndex_] == WORD_INCLUDER){
				String tokenEntry = readWord();
				
				return new Token(TokenType.WORD, tokenEntry);
				
			} else if(Character.isDigit(data_[currentIndex_])){
				long tokenEntry = readNumber();
				
				return new Token(TokenType.NUMBER
						, tokenEntry);
				
			} else if(Character.isWhitespace(data_[currentIndex_])){
				currentIndex_++;
				
			} else {
				return new Token(TokenType.SYMBOL, 
						data_[currentIndex_++]);
			}
		}
		currentIndex_++;
		return new Token(TokenType.EOF, null);
	}


	/**
	 * Reads the number from the given string. Throws 
	 * {@link LexerException} if read number is too long for string.
	 * 
	 * @return returns the read number
	 */
	private long readNumber() {
		String numberString = "";
		
		while(currentIndex_ != data_.length){
			if(Character.isDigit(data_[currentIndex_])){
				numberString += data_[currentIndex_];
				currentIndex_++;
			} else {
				break;
			}
		}
		try{
			long number = Long.parseLong(numberString);
			return number;
		} catch (NumberFormatException e){
			throw new LexerException("Warning - "
					+ "Cannot parse a number that long!");
		}
	}


	/**
	 * Reads a word from the given string. Throws 
	 * {@link LexerException} if it reads \ in front of a wrong 
	 * character.
	 * 
	 * @return returns the read word
	 */
	private String readWord() {
		String word = "";
		
		while(currentIndex_ < data_.length){
			if(Character.isLetter(data_[currentIndex_])){
				word += data_[currentIndex_];
				currentIndex_++;
				
			} else if( data_[currentIndex_] == WORD_INCLUDER){
				currentIndex_++;
				
				if(currentIndex_ == data_.length){
					throw new LexerException("Warning - "
							+ "Reached the end of the input "
							+ "before finishing analysis!");
				}
				
				if(Character.isDigit(data_[currentIndex_]) || 
						data_[currentIndex_] == '\\'){
					word += data_[currentIndex_];
					currentIndex_++;
					
				} else {
					throw new LexerException("Warning - "
							+ "Unnaceptable character placement!");
				}
			} else {
				break;
			}
		}
		
		return word;
	}
	
	
	/**
	 * Called if in EXTENDED state. Reads the next token from the
	 * string. Throws {@link LexerException} if already reached the 
	 * end file.
	 * 
	 * @return returns the read token
	 */
	private Token readExtended(){
		if(currentIndex_ > data_.length){
			throw new LexerException("Warning - "
					+ "Already reached end of file! "
					+ "No more tokens to read!");
		}
		
		String value = "";
		while(currentIndex_ < data_.length){
			if(data_[currentIndex_] == STATE_SWITCH){
				
				switchState();
				return new Token(TokenType.SYMBOL, 
						data_[currentIndex_++]);
				
			} else if(Character.isWhitespace(data_[currentIndex_])){
				currentIndex_++;
				
			} else {
				value = readWordExtended();
				return new Token(TokenType.WORD, value);
			}
		}
		currentIndex_++;
		return new Token(TokenType.EOF, null);
		
	}
	
	
	/**
	 * Reads the word from the string in the exended state.
	 * 
	 * @return returns the read word
	 */
	private String readWordExtended(){
		String word = "";
		
		while(currentIndex_ < data_.length){
			if( data_[currentIndex_] == STATE_SWITCH){
				return word;
				
			} else if(Character.isWhitespace(data_[currentIndex_])){
				currentIndex_++;
				return word;
				
			} else {
				word += data_[currentIndex_];
				currentIndex_++;
			}
		}
		
		return word;
	}
}
