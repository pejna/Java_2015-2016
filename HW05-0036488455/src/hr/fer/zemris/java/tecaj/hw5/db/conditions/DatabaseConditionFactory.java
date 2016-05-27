package hr.fer.zemris.java.tecaj.hw5.db.conditions;

import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken.DatabaseTokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.DatabaseOperatorFactory;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexer;


/**
 * Factory class for producing {@link DatabaseCondition} and
 * {@link DatabaseToken} objects.
 * Also contains all conditions used for lexical analysis conditions.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class DatabaseConditionFactory{


	/**
	 * Contains all possible database condition query type keyword.
	 * 
	 */
	public static final String[] conditions = {
		//Order should be matched with other constants
			"firstName",
			"lastName",
			"jmbag",
			"AND"
	};
	
	/**
	 * Keyword for {@link FirstNameCondition}.
	 */
	public static final String FIRST_NAME = conditions[0];
	
	/**
	 * Keyword for {@link LastNameCondition}.
	 */
	public static final String LAST_NAME = conditions[1];
	
	/**
	 * Keyword for {@link JmbagCondition}.
	 */
	public static final String JMBAG = conditions[2];
	
	/**
	 * Keyword for {@link AndCondition}.
	 */
	public static final String AND = conditions[3];
	
	
	/**
	 * Produces a token from the given string. If given unrecognized
	 * string returns null.
	 * 
	 * @param string string from which to create a token
	 * @return returns
	 */
	public static DatabaseToken produceToken(String string){
		if(string.equals(FIRST_NAME)){
			return new DatabaseToken(
					string, DatabaseTokenType.FIRST_NAME);
		}
		
		if(string.equals(LAST_NAME)){
			return new DatabaseToken(
					string, DatabaseTokenType.LAST_NAME);
		}
		
		if(string.equals(JMBAG)){
			return new DatabaseToken(
					string, DatabaseTokenType.JMBAG);
		}
		
		if(string.toUpperCase().equals(AND)){
			return new DatabaseToken(
					string, DatabaseTokenType.AND);
		}
		
		return null;
	}
	
	
	/**
	 * Produces a {@link DatabaseCondition} from the given string.
	 * String unit should be made from a keyword, followed by an 
	 * operator found in {@link DatabaseOperatorFactory} followed
	 * by a literal value denoted by " signs. String units can
	 * be aggregated by the AND keyword. Throws 
	 * {@link IllegalArgumentException} if given null as argument.
	 * 
	 * @param conditions string containing the conditions
	 * @return returns the produced condition
	 */
	public static DatabaseCondition
			produce(String conditions) {
		if(conditions == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot produce condition from null string!");
		}
		
		QueryLexer lexer = new QueryLexer(conditions);
		
		AggregateCondition aggregation = new AggregateCondition();
		boolean endOfQuery = false;
		
		while(!endOfQuery){
			aggregation.addCondition(parseCondition(lexer));
			DatabaseToken nextToken = lexer.nextToken();
			
			switch(nextToken.getType()){
				
				case EOF :
					endOfQuery = true;
					//fallthrough
					
				case AND :
					break;

				default :
					throw new Lexer.LexerException("Warning - "
							+ "Incorrect ordering of word in query!");
			}
		}
		
		return aggregation;
	}
	
	
	/**
	 * Parses a condition from the given lexer.
	 * 
	 * @param lexer lexer to parse the condition from
	 * @return returns the condition
	 */
	private static DatabaseCondition 
			parseCondition(QueryLexer lexer) {
		
		DatabaseCondition condition;
		DatabaseToken conditionToken = lexer.nextToken();
		
		DatabaseToken operatorToken = lexer.nextToken();
		
		if(operatorToken.getType() != DatabaseTokenType.OPERATOR){
			throw new Lexer.LexerException("Warning - "
					+ "Incorrect ordering of words in query!");
		}
		IComparisonOperator operator = 
				DatabaseOperatorFactory.produce(operatorToken);
		
		DatabaseToken comparedToToken = lexer.nextToken();
		
		if(comparedToToken.getType() != DatabaseTokenType.STRING){
			throw new Lexer.LexerException("Warning - "
					+ "Incorrect ordering of words in query!");
		}
		String comparedTo = (String)comparedToToken.getValue();
		
		
		switch(conditionToken.getType()){
		
			case FIRST_NAME :
				condition = produceFirstNameCondition(operator, 
						comparedTo);
				
				return condition;
				
			case LAST_NAME :
				condition = produceLastNameCondition(operator, 
						comparedTo);
				
				return condition;
				
			case JMBAG :
				condition = produceJmbagCondition(operator, 
						comparedTo);
				
				return condition;
				
			default :
				throw new Lexer.LexerException("Warning - "
						+ "Incorrect ordering or words!");
		}
	}


	/**
	 * Produces an AndCondition with the given operator.
	 * 
	 * @param operator operator to be fulfilled for the condition
	 * @return returns the produced condition
	 */
	public static AggregateCondition produceAggregateCondition(){
		return new AggregateCondition();
	}
	
	
	/**
	 * Produces a FirstNameCondition with the given operator.
	 * 
	 * @param operator operator to be fulfilled for the condition
	 * @return returns the produced condition
	 */
	public static OperatorCondition produceFirstNameCondition(
			IComparisonOperator operator, String comparedTo){
		if(conditions == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot produce condition with null operator!");
		}
		if(comparedTo == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot compare to null!");
		}
		
		return new OperatorCondition(new FirstNameGetter(), 
				comparedTo, operator);
	}
	
	
	/**
	 * Produces a LastNameCondition with the given operator.
	 * 
	 * @param operator operator to be fulfilled for the condition
	 * @return returns the produced condition
	 */
	public static OperatorCondition produceLastNameCondition(
			IComparisonOperator operator, String comparedTo){
		if(conditions == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot produce condition with null operator!");
		}
		if(comparedTo == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot compare to null!");
		}
		
		return new OperatorCondition(new LastNameGetter(),
				comparedTo, operator);
	}
	
	
	/**
	 * Produces a JmbagCondition with the given operator.
	 * 
	 * @param operator operator to be fulfilled for the condition
	 * @return returns the produced condition
	 */
	public static OperatorCondition produceJmbagCondition(
			IComparisonOperator operator, String comparedTo){
		if(conditions == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot produce condition with null operator!");
		}
		if(comparedTo == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot compare to null!");
		}
		
		return new OperatorCondition(new JmbagGetter(), 
				comparedTo, operator);
	}
}
