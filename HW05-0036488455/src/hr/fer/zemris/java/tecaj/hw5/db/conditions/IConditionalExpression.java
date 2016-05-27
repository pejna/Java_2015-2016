package hr.fer.zemris.java.tecaj.hw5.db.conditions;


/**
 * Interface used by all condition objects for comparation.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface IConditionalExpression<T> {
	
	/**
	 * Checks whether the condition of the object is fulfilled for
	 * the given object. Throws {@link IllegalArgumentException} if
	 * given null.
	 * 
	 * @param object object on which we check the condition
	 * @return retuns true if condition is fulfilled, otherwise
	 * false
	 */
	public boolean fulfilled(T object);
}
