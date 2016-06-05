package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing non-tag elements of a document.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 * @see INodeVisitor
 */
public class TextNode extends Node {

	/**
	 * Text contained in the node.
	 */
	private final String text;


	/**
	 * Creates a nove representing the given text.
	 * 
	 * @param text
	 *            text we want represented in the node
	 */
	public TextNode(String text) {
		this.text = text;
	}


	/**
	 * @return the text in the node
	 */
	public String getText() {
		return text;
	}


	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

}
