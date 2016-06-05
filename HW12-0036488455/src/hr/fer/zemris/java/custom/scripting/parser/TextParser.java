package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;


/**
 * Parses a text nodes from the given lexer. Used in conjunction
 * with {@link SmartScriptParser} to read strings. Lexer should be 
 * given so that it already has read that it is a text node.
 * 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class TextParser {


	/**
	 * Parses a text node from the string.
	 * 
	 * @param lexer lexer that sends the text token 
	 * @param stack stack onto which we save the created node
	 */
	public static void parseText(Lexer lexer, ObjectStack stack) {
		Token token = lexer.getToken();
		Node parsed = new TextNode((String) token.getValue());
		
		NodeStackManager.addChildToStack(stack, parsed);
	}

}
