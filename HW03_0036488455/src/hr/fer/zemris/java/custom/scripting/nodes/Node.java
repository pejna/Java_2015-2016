package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Represents a logical node in a document. Can contain other 
 * subnodes.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Node {
	
	/**
	 * Collection containing children of this node.
	 */
	private ArrayIndexedCollection children_;
	
	
	/**
	 * Gives this node a child node.
	 * 
	 * @param child new child node
	 */
	public void addChildNode(Node child){
		if(children_ == null){
			children_ = new ArrayIndexedCollection();
		}
		
		children_.add(child);
	}
	
	
	/**
	 * Finds out the number of children of this node.
	 * 
	 * @return returns the number of children
	 */
	public int numberOfChildren(){
		if(children_ == null){
			return 0;
		}
		
		return children_.size();
	}
	
	
	/**
	 * Procures a child at the requested index. Throws 
	 * {@link IndexOutOfBoundsException} if index is too big.
	 * 
	 * @param index index of a child
	 * @return returns the child at requested index
	 */
	public Node getChild(int index){
		if(index >= children_.size()){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Unable to get child node from "
					+ "requested index!");
		}
		
		return (Node) children_.get(index);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String string = "";
		
		if(children_ == null){
			return string;
		}
		
		for(Object child : children_.toArray()){
			string +=child.toString();
		}
		
		return string;
	}
}
