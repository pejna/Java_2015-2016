package hr.fer.zemris.java.tecaj.hw5.db.lexer;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;

/**
 * Token implementation for tokens found in {@link StudentDatabase}
 * search querries.
 *
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class DatabaseToken implements Token{
	
	/**
	 * Represents the type of the token.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	public static enum DatabaseTokenType implements Token.TokenType{
		
		/**
		 * Represents the found jmbag keyword.
		 */
		JMBAG,
		
		/**
		 * Represents the found firstName keyword.
		 */
		FIRST_NAME,
		
		/**
		 * Represents the found lastName keyword.
		 */
		LAST_NAME,
		
		/**
		 * Represents the found string literal.
		 */
		STRING,
		
		/**
		 * Represents the found comparation operator.
		 */
		OPERATOR,
		
		/**
		 * Represents the found database command.
		 */
		COMMAND,
		
		/**
		 * Represents the found AND conditional operator.
		 */
		AND,
		
		/**
		 * Represents the end of the line.
		 */
		EOF;
	}

	
	/*
	 * ******** Private variables ************************************
	 */
	
	
	/**
	 * Value inside the token.
	 */
	private Object value;
	
	/**
	 * Type of the token.
	 */
	private DatabaseTokenType type;
	
	
	/*
	 * ******** Constructor methods **********************************
	 */
	
	
	/**
	 * Creates a databse token from the given object and type.
	 */
	public DatabaseToken(Object value, DatabaseTokenType type) {
		this.value = value;
		this.type = type;
	}

	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	@Override
	public DatabaseTokenType getType() {
		return type;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toString(){
		return "{" + 
				getType().toString() + 
				", " +
				"}";
	}
}
