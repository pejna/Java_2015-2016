package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.lexer.KeywordManager;

/**
 * Node representing a command which generates some textual output 
 * dynamically.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class EchoNode extends Node{
	
	/**
	 * Contains all elements of an echo node.
	 */
	private final Element[] elements_;

	
	/**
	 * Creates an echo node with the given elements.
	 * 
	 * @param elements array containing elements of this node
	 */
	public EchoNode(Element[] elements) {
		elements_ = elements;
	}

	
	/**
	 * Creates an echo node with the given element objects.
	 * 
	 * @param elements array containing elements of this node
	 */
	public EchoNode(Object[] elements) {
		elements_ = new Element[elements.length];
		
		for(int i = 0; i < elements.length ;i++){
			elements_[i] = (Element) elements[i];
		}
	}

	
	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements_;
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#toString()
	 */
	@Override
	public String toString() {
		String string = "{$ " + KeywordManager.KEYWORD_ECHO + " ";
		for(Element element : elements_){
			string += element.asText() + " ";
		}
		string += "$}";
		return string;
	}
	
	
}
