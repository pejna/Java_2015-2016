package hr.fer.zemris.java.tecaj.hw5.db.filter;


/**
 * Interface used by filters.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface IFilter<T> {
	
	/**
	 * Checks if the given item is accepted.
	 * 
	 * @param item item to be checked
	 * @return returns true if accepted
	 */
	boolean accepts(T item);
}
