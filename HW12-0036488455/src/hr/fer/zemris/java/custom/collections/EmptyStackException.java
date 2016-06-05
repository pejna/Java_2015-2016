package hr.fer.zemris.java.custom.collections;


/**
 * Exception used to represent occurence of reading from empty stack.
 * 
 * @author Juraj Pejnovic
 * @version 1.1
 *
 */
public class EmptyStackException extends RuntimeException {

	
	/**
	 * ID of this exception.
	 */
	private static final long serialVersionUID = -2097566153666133661L;

	
	/**
	 * Creates an exception with no message.
	 */
	public EmptyStackException(){
	}
	
	
	/**
	 * Creates an exception with the given message.
	 * 
	 * @param s message for the exception
	 */
	public EmptyStackException(String s){
		super(s);
	}
	
}
