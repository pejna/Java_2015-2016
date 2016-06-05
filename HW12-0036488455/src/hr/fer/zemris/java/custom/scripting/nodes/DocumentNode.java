package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing entire document.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 * @see INodeVisitor
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
}
