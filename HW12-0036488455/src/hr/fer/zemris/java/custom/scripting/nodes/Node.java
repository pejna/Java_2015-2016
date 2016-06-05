package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Represents a logical node in a document. Can contain other subnodes.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 * @see INodeVisitor
 */
public abstract class Node {

	/**
	 * Collection containing children of this node.
	 */
	private ArrayIndexedCollection children;


	/**
	 * Gives this node a child node.
	 * 
	 * @param child
	 *            new child node
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}


	/**
	 * Finds out the number of children of this node.
	 * 
	 * @return returns the number of children
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}

		return children.size();
	}


	/**
	 * Procures a child at the requested index. Throws
	 * {@link IndexOutOfBoundsException} if index is too big.
	 * 
	 * @param index
	 *            index of a child
	 * @return returns the child at requested index
	 */
	public Node getChild(int index) {
		if (index >= children.size()) {
			throw new IndexOutOfBoundsException("Warning - "
					+ "Unable to get child node from " + "requested index!");
		}

		return (Node) children.get(index);
	}


	/**
	 * Accepts the visitor and performs the action.
	 * 
	 * @param visitor
	 *            visitor to be accepted
	 */
	public abstract void accept(INodeVisitor visitor);
}
