package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken.DatabaseTokenType;

public class DatabaseOperatorFactory{

	public static String[] operators = {
			"=",
			"<=",
			">=",
			"!=",
			"<",
			">",
			"LIKE"
	};
	
	public static String EQUALS = operators[0];
	
	public static char EQUALS_CHAR = operators[0].charAt(0);
	
	public static String LESS_OR_EQUALS = operators[1];
	
	public static String MORE_OR_EQUALS = operators[2];
	
	public static String NOT_EQUALS = operators[3];
	
	public static String LESS = operators[4];
	
	public static String MORE = operators[5];
	
	public static String LIKE = operators[6];
	
	public static IComparisonOperator produce(DatabaseToken token){
		if(token.getType() != DatabaseTokenType.OPERATOR){
			throw new IllegalArgumentException("Warning - "
					+ "Expected OPERATOR token "
					+ "but recieved" + token.getType() + " token!");
		}
		
		String value =(String)token.getValue();
		
		if(value.equals(EQUALS)){
			return new EqualsComparisonOperator();
		}
		if(value.equals(LESS_OR_EQUALS)){
			return new LessOrEqualsComparisonOperator();
		}
		if(value.equals(MORE_OR_EQUALS)){
			return new MoreOrEqualsComparisonOperator();
		}
		if(value.equals(NOT_EQUALS)){
			return new NotEqualsComparisonOperator();
		}
		if(value.equals(LESS)){
			return new LessComparisonOperator();
		}
		if(value.equals(MORE)){
			return new MoreComparisonOperator();
		}
		if(value.equals(LIKE)){
			return new LikeComparisonOperator();
		}
		
		throw new IllegalArgumentException("Warning - "
				+ "Token value does not match any know type!");
	}
	
	public static DatabaseToken produceToken(String string){
		for(String op : operators){
			if(op.equals(string)){
				return new DatabaseToken(
						string, DatabaseTokenType.OPERATOR);
			}
		}
		
		return null;
	}
	
	
	public static boolean isOperator(char character){
		for(String string : operators){
			if(character == string.charAt(0)){
				return true;
			}
		}
		
		return false;
	}
}
