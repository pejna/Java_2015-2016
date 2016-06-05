package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A visitor interface for {@link Node} extending classes' visitors.
 * <p>
 * Supports visiting all known {@link Node} children. Implementing classes
 * should take care of tree traversal as accept() function in {@link Node}
 * classes don't have implemented the traversal.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Node
 */
public interface INodeVisitor {

	/**
	 * Visits the text node and processes it.
	 * 
	 * @param node
	 *            node to be processed
	 */
	public void visitTextNode(TextNode node);


	/**
	 * Visits the for loop node and processes it. Also invokes visitation of all
	 * for loop node children.
	 * 
	 * @param node
	 *            node to be visited
	 */
	public void visitForLoopNode(ForLoopNode node);


	/**
	 * Visits the echo node and processes it.
	 * 
	 * @param node
	 *            node to be visited
	 */
	public void visitEchoNode(EchoNode node);


	/**
	 * Visits the document node and processes it. Also invokes visitation of all
	 * document node children.
	 * 
	 * @param node
	 *            node to be visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
