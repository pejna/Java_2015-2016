package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.lexer.EscapeManager;

/**
 * A node representing non-tag elements of a document.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class TextNode extends Node {

	/**
	 * Text contained in the node.
	 */
	private final String text_;

	
	/**
	 * Creates a nove representing the given text.
	 * 
	 * @param text text we want represented in the node
	 */
	public TextNode(String text){
		text_ = text;
	}
	
	
	/**
	 * @return the text in the node
	 */
	public String getText_() {
		return text_;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#toString()
	 */
	@Override
	public String toString() {
		String string = text_;
		
		for(char c : EscapeManager.ESCAPABLE_TEXT){
			string = string.replace(
					"" + c, 
					"" + EscapeManager.ESCAPE_CHARACTER + c);
		}
		
		return string;
	}
	
	
}
