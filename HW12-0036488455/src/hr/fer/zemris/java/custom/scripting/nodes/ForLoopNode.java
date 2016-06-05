package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing for loops found in a document.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 * @see INodeVisitor
 */
public class ForLoopNode extends Node {

	/**
	 * Variable in the for loop.
	 */
	private final ElementVariable variable;

	/**
	 * Starting expression of the loop.
	 */
	private final Element startExpression;

	/**
	 * Ending expression of the loop.
	 */
	private final Element endExpression;

	/**
	 * Step expression of the loop.
	 */
	private final Element stepExpression;


	/**
	 * Creates for loop node from given arguments. Only step expression allowed
	 * to be null, for all others throws {@link IllegalArgumentException} if
	 * null.
	 * 
	 * @param variable
	 *            variable that will change in the loop
	 * @param startExpression
	 *            starting expression of the variable
	 * @param endExpression
	 *            ending expression of the variable
	 * @param stepExpression
	 *            step expression of the variable
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		if (variable == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Assigned variable cannot be null!");
		}
		if (startExpression == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Assigned start expression cannot be null!");
		}
		if (endExpression == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Assigned end expression cannot be null!");
		}

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}


	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}


	/**
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}


	/**
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}


	/**
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}


	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
