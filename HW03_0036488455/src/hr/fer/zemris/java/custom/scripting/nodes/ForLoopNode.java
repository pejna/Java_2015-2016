package hr.fer.zemris.java.custom.scripting.nodes;


import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.KeywordManager;


/**
 * Node representing for loops found in a document.
 * 
 * @author Juraj Pejnovic
 * @version 0.9
 */
public class ForLoopNode extends Node{

	/**
	 * Variable in the for loop.
	 */
	private final ElementVariable variable_;
	
	/**
	 * Starting expression of the loop.
	 */
	private final Element startExpression_;
	
	/**
	 * Ending expression of the loop.
	 */
	private final Element endExpression_;
	
	/**
	 * Step expression of the loop.
	 */
	private final Element stepExpression_;
	
	
	
	/**
	 * Creates for loop node from given arguments. Only step
	 * expression allowed to be null, for all others throws 
	 * {@link IllegalArgumentException} if null.
	 * 
	 * @param variable variable that will change in the loop
	 * @param startExpression starting expression of the variable
	 * @param endExpression ending expression of the variable
	 * @param stepExpression step expression of the variable
	 */
	public ForLoopNode(ElementVariable variable, 
			Element startExpression, 
			Element endExpression,
			Element stepExpression){
		if(variable == null){
			throw new IllegalArgumentException("Warning - "
					+ "Assigned variable cannot be null!");
		}
		if(startExpression == null){
			throw new IllegalArgumentException("Warning - "
					+ "Assigned start expression cannot be null!");
		}
		if(endExpression == null){
			throw new IllegalArgumentException("Warning - "
					+ "Assigned end expression cannot be null!");
		}
		
		variable_ = variable;
		startExpression_ = startExpression;
		endExpression_ = endExpression;
		stepExpression_ = stepExpression;
	}

	
	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable_;
	}

	
	/**
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression_;
	}

	
	/**
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression_;
	}

	
	/**
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression_;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#toString()
	 */
	@Override
	public String toString() {
		String string = "{$ " + KeywordManager.KEYWORD_FOR + " ";
		
		string += variable_.asText() + " ";
		string += startExpression_.asText() + " ";
		string += endExpression_.asText() + " ";
		if(stepExpression_ != null){
			string += stepExpression_.asText() + " ";
		}
		string += "$}";
		
		string += super.toString();
		
		string +="{$ "+ KeywordManager.KEYWORD_END +" $}";
		
		return string;
	}
}
