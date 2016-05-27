package hr.fer.zemris.java.custom.collections;


/**
 * Collection that stores it's elements into a linked list. Provides
 * all services collections offer. Can contain multiples of an 
 * element. Does not contain null elements. 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	
	/**
	 * Node in a list. Used for storing data given to the collection.
	 * 
	 * @author Juraj Pejnovic
	 *
	 */
	private class ListNode{
		Object value;
		ListNode previous;
		ListNode next;
		
		
		/**
		 * Creates an empty node.
		 */
		ListNode(){
			value = null;
			previous = null;
			next = null;
		}
		
		
		/**
		 * Creates an unlinked node containing given information.
		 * 
		 * @param value value we want the node to contain
		 */
		ListNode(Object value){
			this.value = value;
			previous = null;
			next = null;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	
	/**
	 * Creates an empty collection.
	 * 
	 */
	public LinkedListIndexedCollection(){
		first = null;
		last = null;
		size = 0;
	}
	
	
	/**
	 * Creates a collection containing elements from the given
	 * collection.
	 * 
	 * @param oldCollection collection containing elements for new
	 * collection
	 * @throws IllegalArgumentException thrown if given null
	 */
	public LinkedListIndexedCollection(Collection oldCollection)
			throws IllegalArgumentException{
		if(oldCollection == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create collection from "
					+ "null collection.");
		}
		size = 0;
		addAll(oldCollection);
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return first == null;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	@Override
	public int size() {
		return size;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#add(java.lang.Object)
	 */
	@Override
	public void add(Object value) throws IllegalArgumentException{
		if(value == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot add null object to the collection!");
		}
		
		ListNode newNode = new ListNode(value);
		if(size == 0){
			first = newNode;
			last = newNode;
		} else {
			newNode.previous = last;
			last.next = newNode;
		}
		
		last = newNode;
		size++;
	}
	
	
	/**
	 * Acquires the element at the requested position. Valid indexes
	 * are between 0 and size of the collection.
	 * 
	 * @param index position of the element
	 * @return returns the lement at given index
	 * @throws IndexOutOfBoundsException thrown if given index is out
	 * of bounds
	 */
	public Object get(int index)throws IndexOutOfBoundsException{
		if(!isValidIndex(index, 0, size)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Cannot reach the element on the given index!");
		}
		
		ListNode current;
		
		if(index < size/2){
			int position = 0;
			current = first;

			while(position != index){
				current = current.next;
				position++;
			}
		} else {
			int position = size-1;
			current = last;
			
			while(position != index){
				current = current.previous;
				position--;
			}
		}
		
		return current.value;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object value) {
		ListNode current = first;
		while(current != null){
			if (current.value == value){
				return true;
			}
			current = current.next;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		ListNode current = first;
		boolean found = false;
		
		while(current != null){
			if(current.value == value){
				found = true;
				break;
			}
			current = current.next;
		}
		
		if(found){
			current.previous.next = current.next;
			current.next.previous = current.previous;
			size--;
		}
		
		return found;
	}
	
	
	/**
	 * Removes the element on the provided index. Valid index is 
	 * between 0 and the size of the collection.
	 * 
	 * @param index index of the element we wish removed
	 * @throws IndexOutOfBoundsException thrown if provided index
	 * is out of bounds
	 */
	public void remove(int index) throws IndexOutOfBoundsException{
		if(!isValidIndex(index, 0, size)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Cannot remove element on given position!");
		}
		
		int position = 0;
		ListNode current = first;
		
		for(position = 0; position < index; position++){
			current = current.next;
		}
		
		current.previous.next = current.next;
		current.next.previous = current.previous;
		
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		if(size == 0){
			return null;
		}
		
		Object[] newArray = new Object[size];
		ListNode current = first;
		
		for(int i = 0; i < size; i++){
			newArray [i] = current.value;
			current = current.next;
		}
		
		return newArray;
	}


	/**
	 * Processes each element of the collection with the provided
	 * processor.
	 * 
	 * @param processor processor to be used on elements
	 * @throws IllegalArgumentException thrown if given null processor
	 */
	@Override
	public void forEach(Processor processor) 
			throws IllegalArgumentException{
		if(processor == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot use null processor!");
		}
		
		ListNode current = first;
		while(current != null){
			processor.process(current.value);
			current = current.next;
		}
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	
	/**
	 * Inserts the element at requested position. Does not overwrite
	 * previous element if it was present. Instead shifts the previous
	 * element and all others by one place.
	 * 
	 * @param value value we want inserted
	 * @param position position we want value inserted to, 
	 * valid positions are between 0 and size including size
	 * @throws IndexOutOfBoundsException thrown if requested
	 * position is out of bounds
	 * @throws IllegalArgumentException thrown if given null value
	 */
	public void insert(Object value, int position)
			throws IndexOutOfBoundsException, 
			IllegalArgumentException{
		if(!isValidIndex(position, 0, size)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Cannot reach the given position!");
		}
		
		if(value == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot insert null into collection!");
		}
		
		ListNode newNode = new ListNode(value);
		ListNode current;
		if(position < size/2){
			int place = 0;
			current = first;
			
			for(place = 0; place < position; place++){
				current = current.next;
			}
		} else {
			int place = size - 1;
			current = last;
			
			for(place = size - 1; place> position; place--){
				current = current.previous;
			}
		}
		
		current.previous.next = newNode;
		newNode.previous = current.previous;
		
		current.previous = newNode;
		newNode.next = current;
		
		size++;
	}

	
	/**
	 * Finds the index of the given element if present.
	 * 
	 * @param value value of the lements we want found
	 * @return returns the index of requested element if present,
	 * otherwise returns -1
	 */
	public int indexOf(Object value){
		int position = 0;
		ListNode current = first;
		while(current != null){
			if(current.value.equals(value)){
				return position;
			}
		}
		
		return -1;
	}
}
