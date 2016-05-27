package hr.fer.zemris.java.custom.collections;


/**
 * Exception used to represent occurence of reading from empty stack.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class EmptyStackException extends RuntimeException {

	
	/**
	 * Creates an exception with the given message.
	 * 
	 * @param s message for the exception
	 */
	public EmptyStackException(String s){
		super(s);
	}
	
}
