package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;


/**
 * Contains multiple stacks accesible by string keywords. Each
 * stack acts as  a {@link Stack}. Uses {@link ValueWrapper} as
 * container for items on stack.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ObjectMultistack {

	/**
	 * Map containing stacks mapped to their keywords.
	 */
	private Map<String, MultistackEntry> stacks;
	
	
	/**
	 * Creates a normal multistack.
	 * 
	 */
	public ObjectMultistack() {
		stacks = new HashMap<>();
	}
	
	
	/**
	 * Pushes the given {@link ValueWrapper} on to the stack 
	 * associated with the given string.
	 * 
	 * @param name key associated with desired stack
	 * @param valueWrapper container to be pushed
	 */
	public void push(String name, ValueWrapper valueWrapper){
		Objects.requireNonNull(name);
		
		if(!stacks.containsKey(name)){
			stacks.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		MultistackEntry entry = 
				new MultistackEntry(valueWrapper, stacks.get(name));
		stacks.put(name, entry);
	}
	
	
	/**
	 * Pops an element from the requested stack.
	 * 
	 * @param name keyword associated with the stack to be popped
	 * @return returns the popped container
	 * @throws NoSuchElementException if no stakc associated to the 
	 * name exists
	 * @throws EmptyStackException if the desired stack is empty
	 */
	public ValueWrapper pop(String name){
		if(!stacks.containsKey(name)){
			throw new NoSuchElementException("Warning - "
					+ "Multistack does not contain "
					+ "stack named: " + name);
		}
		
		MultistackEntry entry = stacks.get(name);
		
		if(entry == null){
			throw new EmptyStackException();
		}
		
		stacks.put(name, entry.getNext());
		return entry.getValue();
	}
	
	
	/**
	 * Peeks the top element of the stack associated with the 
	 * given string.
	 * 
	 * @param name string associated with the desired stack
	 * @return returns the top container of the stack
	 * @throws NoSuchElementException thrown if no stack
	 * is associated with the given string
	 * @throws EmptyStackException thrown if associated stack is empty
	 */
	public ValueWrapper peek(String name){
		if(!stacks.containsKey(name)){
			throw new NoSuchElementException("Warning - "
					+ "Multistack does not contain a "
					+ "stack named: " + name);
		}
		
		if(stacks.get(name)== null){
			throw new EmptyStackException();
		}
		
		return stacks.get(name).getValue();
	}
	
	
	/**
	 * Checks if the stack associated with the given string is
	 * empty.
	 * 
	 * @param name string associated with examined stack
	 * @return returns true if empty, otherwise false
	 */
	public boolean isEmpty(String name){
		if(!stacks.containsKey(name)){
			return true;
		}
		
		if(stacks.get(name) == null){
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * Entry containing values of the stack in the form of a linked
	 * list node.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		/**
		 * Container of value in this node.
		 */
		private ValueWrapper value;
		
		/**
		 * Lower entry of the stack the entry is a part of.
		 */
		private MultistackEntry next;

		/**
		 * Creates a normal entry with the given parameters.
		 * 
		 * @param value container of the value to be in the node
		 * @param next bottom element of the stack
		 */
		public MultistackEntry(ValueWrapper value,
				MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * @return the value
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * @return the next
		 */
		public MultistackEntry getNext() {
			return next;
		}
	}
}
