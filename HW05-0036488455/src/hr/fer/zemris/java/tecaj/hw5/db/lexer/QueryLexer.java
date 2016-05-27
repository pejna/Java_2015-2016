package hr.fer.zemris.java.tecaj.hw5.db.lexer;

import java.util.Iterator;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.tecaj.hw5.db.conditions.DatabaseConditionFactory;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken.DatabaseTokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.DatabaseOperatorFactory;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeComparisonOperator;

/**
 * Performs lexical analysis on a database querry. Recognizes 
 * string literals, operators and variable names.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */

public class QueryLexer extends Lexer 
			implements Iterable<DatabaseToken>{

	/**
	 * Array containing the given string.
	 */
	private char[] data;
	
	/**
	 * Token containing the current found token in the string.
	 */
	private DatabaseToken currentToken;
	
	/**
	 * Index of the currently analyzed character in data_.
	 */
	private int currentIndex;
	
	/**
	 * Marks the beginning of a string.
	 */
	private static final char STRING_MARKER = '\"';
	
	/**
	 * Represents the eqals character used in operators.
	 */
	private static final char EQUALS = 
			DatabaseOperatorFactory.EQUALS_CHAR;
	
	
	/**
	 * Creates a lexer that analyzes the given string.
	 * 
	 * @param data data returned
	 */
	public QueryLexer(String data) {
		if(data == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot analyze empty string!");
		}
		
		this.data = data.toCharArray();
		currentIndex = 0;
	}

	
	/*
     * ******** Lexer methods ****************************************
	 */
	
	
	@Override
	public DatabaseToken nextToken() {
		if(currentIndex > data.length){
			throw new LexerException("Warning - "
					+ "Already reached end of file! "
					+ "No more tokens to read!");
		}
		
		while(currentIndex < data.length){
			if(Character.isWhitespace(data[currentIndex])){
				currentIndex++;
				continue;
			}
			
			if(Character.isLetter(data[currentIndex])){
				return readWord();
			}
			
			if(data[currentIndex] == STRING_MARKER){
				currentIndex++;
				return readString();
			}
			
			if(Character.isDigit(data[currentIndex])){
				throw new LexerException("Warning - "
						+ "Cannot read numberical characters!");
			}
			
			if(DatabaseOperatorFactory.
					isOperator(data[currentIndex])){
				return readOperator();
			}
			
			throw new LexerException("Warning - "
					+ "Unrecognized character " 
					+ data[currentIndex] + "!");
		}
		
		currentIndex++;
		currentToken = new DatabaseToken(null, DatabaseTokenType.EOF);
		return currentToken;
	}

	
	@Override
	public DatabaseToken getToken() {
		return currentToken;
	}

	
	/*
	 * ******** Reader methods ***************************************
	 */
	
	
	private DatabaseToken readWord(){
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length){
			if(Character.isLetter(data[currentIndex])){
				sb.append(data[currentIndex++]);
				continue;
			} else {
				break;
			}
		}
		String word = sb.toString();
		
		DatabaseToken token = 
				DatabaseConditionFactory.produceToken(word);
		
		if(token != null){
			currentToken = token;
			return currentToken;
		}
		
		token = DatabaseOperatorFactory.produceToken(word);
		
		if(token != null){
			currentToken = token;
			return currentToken;
		}
		
		throw new LexerException("Warning encountered unsupported "
				+ "keyword: " + word + "!");
	}
	
	
	private DatabaseToken readOperator(){
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);
		if(data[currentIndex] == EQUALS){
			sb.append(data[currentIndex++]);
		}
		
		DatabaseToken token = 
					DatabaseOperatorFactory.
						produceToken(sb.toString());
			
		if(token == null){
			throw new LexerException("Warning - "
					+ "Unsupported token: " + sb.toString());
		}
		
		currentToken = token;
		return currentToken;
	}
	
	
	private DatabaseToken readString(){
		StringBuilder sb = new StringBuilder();
		boolean haveWildcard = false;
		
		while(currentIndex < data.length){
			if(data[currentIndex] == '\"'){
				currentIndex++;
				String string = sb.toString();
				return new DatabaseToken(
						string, DatabaseTokenType.STRING);
				
			}
			
			if(data[currentIndex] == LikeComparisonOperator.WILDCARD){
				if(haveWildcard){
					throw new LexerException("Warning - "
							+ "String cannot "
							+ "contain more wildcard characters!");
				} else {
					haveWildcard = true;
				}
			}
			
			sb.append(data[currentIndex++]);
		}
		
		throw new LexerException("Warning - "
				+ "Unfinished string found: " + sb.toString());
	}
	

	/*
	 * ******** Iterator *********************************************
	 */
	
	
	/**
	 * Allows iteration over the data inside the lexer.
	 */
	@Override
	public Iterator<DatabaseToken> iterator() {
		return new QueryIterator();
	}

	
	/**
	 * Iterator for iterating across the lexer data.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private class QueryIterator implements Iterator<DatabaseToken>{

		/**
		 * Local index of the iterator inside the data array.
		 */
		int iteratorIndex;
		
		/**
		 * Local current token of the iterator
		 */
		DatabaseToken iteratorToken;
		
		
		/**
		 * Creates an iterator that starts at the beginning of the 
		 * data array.
		 */
		public QueryIterator() {
			iteratorIndex = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(iteratorToken == null){
				return true;
			}
			
			if(iteratorToken.getType() == DatabaseTokenType.EOF){
				return false;
			}
			
			return true;
		}

		@Override
		public DatabaseToken next() {
			if(!hasNext()){
				throw new NoSuchElementException(
						"Warning - Reached the end of lexer iteration!");
			}
			int tempIndex = currentIndex;
			DatabaseToken tempToken = currentToken;
			currentIndex = iteratorIndex;
			
			iteratorToken = nextToken();
			
			iteratorIndex = currentIndex;
			currentToken = tempToken;
			currentIndex = tempIndex;
			
			return iteratorToken;
		}
		
	}
}
