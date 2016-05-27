package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;


/**
 * Parses the given string an creates a document tree. Can read
 * tags in {$ $} braces with FOR, END and = as names of them
 * 
 * @author Juraj Pejnovic
 * @version 1.5
 */
public class SmartScriptParser {

	/**
	 * Creates tokens from the given text for parser to interpret.
	 */
	private Lexer lexer_;
	
	/**
	 * Document node of the parsed string.
	 */
	private DocumentNode document_;
	
	/**
	 * Internal stack used for easier node managment.
	 */
	private ObjectStack stack_;
	

	/**
	 * Creates a parser that parses the given string and creates a 
	 * document tree from it.
	 * 
	 * @param text string we wish parsed
	 */
	public SmartScriptParser(String text){
		
		try{
			smartlyParse(text);
			
		} catch (RuntimeException exception){
			throw new SmartScriptParserException(
					exception.getMessage());
			
		}
	}
	
	
	/**
	 * Smartly parses the entire string creating many nodes.
	 */
	private void smartlyParse(String text){
		lexer_ = new Lexer(text);
		
		stack_ = new ObjectStack();
		
		document_ = new DocumentNode();
		
		NodeStackManager.pushToStack(stack_, document_);
		
		Token token = lexer_.nextToken();
		
		while(token.getType() != TokenType.EOF){
			switch (token.getType()){
				case TEXT :
					TextParser.parseText(lexer_, stack_);
					break;
					
				case TRANSITION_IN :
					TagParser.parseTag(lexer_, stack_);
					break;
					
				default :
					throw new SmartScriptParserException("Warning - "
							+ "Something unallowed happened!"
							+ "Read impossible token type!");
			}
			
			token = lexer_.nextToken();
		}
		
	}
	
	
	/**
	 * Procures a node containing the entire document tree.
	 * 
	 * @return returns the document node
	 */
	public DocumentNode getDocumentNode() {
		return document_;
	}
}
