package hr.fer.zemris.java.custom.collections;


/**
 * Stack of objects. Can push, pop and peek in O(1) time.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ObjectStack {
	
	ArrayIndexedCollection container;
	
	
	/**
	 * Creates an empty stack.
	 * 
	 */
	public ObjectStack(){
		container = new ArrayIndexedCollection();
	}
	
	
	/**
	 * Checks whether the stack is empty.
	 * 
	 * @return returns true if stack is empty, otherwise returns false
	 */
	public boolean isEmpty(){
		return container.isEmpty();
	}
	
	
	/**
	 * Finds out the stack's size.
	 * 
	 * @return returns the size of the stack
	 */
	public int size(){
		return container.size();
	}
	
	
	/**
	 * Pushes given object onto the stack. Does not accept null.
	 * 
	 * @param value object to be pushed onto stack
	 * @throws IllegalArgumentException thrown if given null to push
	 */
	public void push(Object value) throws IllegalArgumentException{
		if(value == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot add null to the stack!");
		}
		
		container.add(value);
	}
	
	
	/**
	 * Pops an object form the top of the stack if the stack is not 
	 * empty.
	 * 
	 * @return returns popped object
	 * @throws EmptyStackException thrown if the stack is empty
	 */
	public Object pop() throws EmptyStackException {
		if(container.size() == 0){
			throw new EmptyStackException("Warning - "
					+ "Cannot pop from empty stack!");
		}
		
		Object value = container.get(container.size() - 1);
		container.remove(container.size() - 1);
		
		return value;
	}
	
	
	/**
	 * Gets the top object from the stack without taking it off if the
	 * stack is not empty.
	 * 
	 * @return returns the top object from the stack
	 * @throws EmptyStackException thrown if the stack is empty
	 */
	public Object peek() throws EmptyStackException {
		if(container.size() == 0){
			throw new EmptyStackException("warning - "
					+ "Cannot peek into empty stack!");
		}
		
		return container.get(container.size() - 1);
	}
	
	
	/**
	 * Empties the stack.
	 * 
	 */
	public void clear(){
		container.clear();
	}
}
