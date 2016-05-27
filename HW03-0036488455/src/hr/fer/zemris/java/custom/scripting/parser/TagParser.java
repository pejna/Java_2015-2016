package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;


/**
 * Parses The read tag, used in conjunction with 
 *  {@link SmartScriptParser} to parse documents. Given lexer should
 *  already have read tag opening brackets and now TagParser can read
 *  the name of the tag.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class TagParser {
	
	/**
	 * Parses the tag. Throws {@link SmartScriptParserException} if
	 * type is unknown.
	 * 
	 * @param lexer lexer that sends the tokens
	 * @param stack stack onto which we save the node
	 * @return returns a node from the parsed tag
	 */
	public static void parseTag(Lexer lexer, ObjectStack stack){
		Token name = lexer.nextToken();
		
		if(name.getType() == TokenType.TRANSITION_OUT){
			return;
		}
		
		if(!KeywordManager.isKeywordToken(name)){
			throw new SmartScriptParserException("Warning - "
					+ "Unsupported tag!");
		} else {
			determineTag(lexer, stack, name.getValue());
		}
	}
	

	
	/**
	 * Determines the type of keyword and creates a matching node.
	 * 
	 * @param lexer lexer that sends the tokens
	 * @param stack stack onto which we save the node
	 * @param value keyword to be determined
	 */
	private static void determineTag(Lexer lexer, 
			ObjectStack stack,
			Object value) {
		String keyword = (String) value;
		
		if(keyword.toUpperCase().
				equals(KeywordManager.KEYWORD_FOR.toUpperCase())){
			parseFor(lexer, stack);
			
		} else if(keyword.toUpperCase().
				equals(KeywordManager.KEYWORD_END.toUpperCase())){
			parseEnd(lexer, stack);
			
		} else if(keyword.toUpperCase().
				equals(KeywordManager.KEYWORD_ECHO.toUpperCase())){
			parseEcho(lexer, stack);
		}
	}

	
	/**
	 * Parses an echo node from the string.
	 * 
	 * @param lexer lexer that sends the tokens
	 * @param stack stack onto which we save the node
	 */
	private static void parseEcho(Lexer lexer, ObjectStack stack) {
		Collection collection = new ArrayIndexedCollection();
		Token token = lexer.nextToken();
		while(token.getType() != TokenType.EOF){
			if(token.getType() == TokenType.TRANSITION_OUT){
				EchoNode parsed = new EchoNode(collection.toArray());
				
				NodeStackManager.addChildToStack(stack, parsed);
				
				return;
				
			} else {
				Element element = createEchoElement(token);
				collection.add(element);
				token = lexer.nextToken();
			}
		}
		
		throw new SmartScriptParserException("Warning - "
				+ "Echo node ended without closing!");
	}


	/**
	 * Creates an element from a token found in echo tag. Throws 
	 * {@link SmartScriptParserException} if unallowed.
	 * 
	 * @param token token to be transformed
	 * @return returns element from token
	 */
	private static Element createEchoElement(Token token) {
		switch(token.getType()){
			case OPERATOR :
				return new ElementOperator((String) token.getValue());
				
			case FUNCTION :
				return new ElementFunction((String) token.getValue());
				
			case STRING :
				return new ElementString((String) token.getValue());

			case VARIABLE :
				return new ElementVariable((String) token.getValue());
			
			case INTEGER :
				return new ElementConstantInteger(
						(int)token.getValue());
			
			case DOUBLE :
				return new ElementConstantDouble(
						(double) token.getValue());
			
			default :
				throw new SmartScriptParserException("Warning - "
						+ "Unallowed token in echo!");
		}
	}


	/**
	 * Parses an end tag from the string.
	 * 
	 * @param lexer lexer that sends the tokens
	 * @param stack stack onto which we save the node
	 * 
	 */
	private static void parseEnd(Lexer lexer, ObjectStack stack) {
		Token token = lexer.nextToken();
		
		if(token.getType() == TokenType.TRANSITION_OUT){
			try{
			NodeStackManager.popFromStack(stack);
			} catch ( RuntimeException exception){
				throw new SmartScriptParserException("Warning - "
						+ "Too many end tags!");
			}
		} else {
			throw new SmartScriptParserException("Warning - "
					+ "Incorrect end tag!");
		}
	}

	
	/**
	 * Parses and creates a for loop node. Throw 
	 * {@link SmartScriptParserException} if unable to.
	 * 
	 * @param lexer lexer that sends the tokens
	 * @param stack stack onto which we save the node
	 * @return returns parsed node
	 */
	private static void parseFor(Lexer lexer, ObjectStack stack){
		Token variableToken = lexer.nextToken();
		
		if(variableToken.getType() != TokenType.VARIABLE){
			throw new SmartScriptParserException("Warning - "
					+ "Cannot have for loop without variable!");
		}
		ElementVariable variable = 
				new ElementVariable((String)variableToken.getValue());
		
		Token startExpressionToken = lexer.nextToken();
		
		if(!isForExpression(startExpressionToken)){
			throw new SmartScriptParserException("Warning - "
					+ "Unrecognized expression!");
		}
		Element startExpression = 
				createForExpression(startExpressionToken);
		
		Token endExpressionToken = lexer.nextToken();
		
		if(!isForExpression(endExpressionToken)){
			throw new SmartScriptParserException("Warning - "
					+ "Unrecognized expression!");
		}
		Element endExpression = 
				createForExpression(endExpressionToken);
		
		Token stepExpressionToken = lexer.nextToken();
		if(!isForExpression(stepExpressionToken)){
			
			if(stepExpressionToken.getType() == 
					TokenType.TRANSITION_OUT){
				ForLoopNode parsed =  new ForLoopNode(variable, 
						startExpression, 
						endExpression, 
						null);
				
				NodeStackManager.pushToStack(stack, parsed);
				
			} else {
				throw new SmartScriptParserException("Warning - "
						+ "Unrecognized for loop form!");
			}
		}
		Element stepExpression =
				createForExpression(stepExpressionToken);
		
		Token endTag = lexer.nextToken();
		if(endTag.getType() != TokenType.TRANSITION_OUT){
			throw new SmartScriptParserException("Warning - "
					+ "Too long for loop!");
		}
		
		ForLoopNode parsed = new ForLoopNode(variable, 
				startExpression, 
				endExpression, 
				stepExpression);
		
		NodeStackManager.pushToStack(stack, parsed);
	}
	
	
	/**
	 * Creates an expression for a for loop element.
	 * 
	 * @param token token to be transformed into expression
	 * @return returns returns expression element
	 */
	private static Element createForExpression(Token token) {
		switch (token.getType()){
			case STRING :
				return new 
						ElementString((String)token.getValue());
				
			case INTEGER :
				return new 
						ElementConstantInteger((int)token.getValue());
			case DOUBLE :
				return new 
						ElementConstantDouble(
								(double)token.getValue());
			case VARIABLE :
				return new
						ElementVariable((String)token.getValue());
			default :
				throw new SmartScriptParserException("Warning - "
						+ "Cannot recognize for expression type!");
		}
	}


	/**
	 * Checks if token is a valid expression for a FOR tag.
	 * 
	 * @param token token that is analyzed
	 * @return return true if it is valid, otherwise returns false
	 */
	private static boolean isForExpression(Token token){
		switch(token.getType()){
			case STRING :
				return true;
				
			case DOUBLE :
				return true;
				
			case INTEGER :
				return true;
				
			case VARIABLE :
				return true;
				
			default :
				return false;
		}
	}
	

}
