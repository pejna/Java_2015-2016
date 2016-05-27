package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.Node;


/**
 * Manages an {@link ObjectStack} as a strack of {@link Node} 
 * objects.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class NodeStackManager {

	
	/**
	 * Pushes the given node to given stack.
	 * 
	 * @param stack stack onto which to push the given node
	 * @param node node to be pushed
	 */
	public static void pushToStack(ObjectStack stack,Node node){
		if(stack.size() == 0){
			stack.push(node);

		} else {
			addChildToStack(stack, node);
			stack.push(node);
		}
	}
	
	
	/**
	 * Pops a node from stack.
	 * 
	 * @param stack stack from which to pop a node
	 * @return returns the popped node
	 */
	public static Node popFromStack(ObjectStack stack){
		return (Node) stack.pop();
		
	}

	
	/**
	 * Adds a child node to the current top node of the stack.
	 * 
	 * @param stack stack with nodes
	 * @param child child node to be added
	 */
	public static void addChildToStack(ObjectStack stack, 
			Node child){
		((Node) stack.peek()).addChildNode(child);
	}
}
