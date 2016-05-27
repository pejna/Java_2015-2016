package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Interface that requires implementation in object 
 * for comparison of 2 strings.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface IComparisonOperator {
	
	
	/**
	 * Checks if the given strings satisfy the comparison condition.
	 * Throws {@link IllegalArgumentException} if any of the arguments
	 * are null.
	 * 
	 * @param value1 first operand of the comparation
	 * @param value2 second operand of the comparation
	 * @return returns true if the condition is satisfied, otherwise
	 * false
	 */
	public boolean satisfied(String value1, String value2);
}
