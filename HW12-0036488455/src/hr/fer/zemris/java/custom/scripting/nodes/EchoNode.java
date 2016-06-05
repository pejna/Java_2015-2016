package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node representing a command which generates some textual output dynamically.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 * @see INodeVisitor
 */
public class EchoNode extends Node {

	/**
	 * Contains all elements of an echo node.
	 */
	private final Element[] elements;


	/**
	 * Creates an echo node with the given elements.
	 * 
	 * @param elements
	 *            array containing elements of this node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}


	/**
	 * Creates an echo node with the given element objects.
	 * 
	 * @param elements
	 *            array containing elements of this node
	 */
	public EchoNode(Object[] elements) {
		this.elements = new Element[elements.length];

		for (int i = 0; i < elements.length; i++) {
			this.elements[i] = (Element) elements[i];
		}
	}


	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}


	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

}
