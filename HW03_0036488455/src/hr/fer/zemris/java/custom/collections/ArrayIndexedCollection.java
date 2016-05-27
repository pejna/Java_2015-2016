package hr.fer.zemris.java.custom.collections;


/**
 * Collection that stores it's elements into an array. Provides
 * all services collections offer.
 * 
 * @author Juraj Pejnovic
 * @version 1.1
 */
public class ArrayIndexedCollection extends Collection{

	/**
	 * Number of contained elements.
	 */
	private int size;
	
	/**
	 * Maximum number of elements.
	 */
	private int capacity;
	
	/**
	 * Array containing the elements.
	 */
	private Object[] elements;
	
	/**
	 * Default max number of elements.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Default minimal capacity of elements.
	 */
	private static final int MINIMAL_CAPACITY = 1;
	
	/**
	 * When collection is full, multiplier when expanding capacity.
	 */
	private static final int CAPACITY_INCREMENT = 2;
	
	/**
	 * Creates an empty collection.
	 * 
	 */
	public ArrayIndexedCollection(){
		capacity = DEFAULT_CAPACITY;
		elements = new Object[capacity];
		size = 0;	
	}
	
	
	/**
	 * Creates a collection with the provided capacity.
	 * 
	 * @param initialCapacity initial capacity of the collection
	 * @throws IllegalArgumentException thrown if provided number
	 * is smaller than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) 
			throws IllegalArgumentException{
		if( initialCapacity < MINIMAL_CAPACITY){
			throw new IllegalArgumentException(
					"Warning - Unable to allocate the array of the"
					+ "given size");
		}
		
		capacity = initialCapacity;
		elements = new Object[capacity];
		size = 0;
	}
	
	
	/**
	 * Creates a collection from the provided collection.
	 * 
	 * @param oldCollection collection form which to create the 
	 * collection
	 * @throws IllegalArgumentException thrown if provided 
	 * collection is null
	 */
	public ArrayIndexedCollection(Collection oldCollection) 
			throws IllegalArgumentException{
		this();
		addAll(oldCollection);
	}
	
	
	/**
	 * Creates a collection from the given collection. Created 
	 * collection size has size of either provided size or the size 
	 * of the given collection, whichever is greater.
	 * 
	 * @param oldCollection collection form which to create the 
	 * collection
	 * @param initialCapacity desired capacity of the new collection
	 * @throws IllegalArgumentException thrown if unable to set the
	 * new collection to the provided capacity or if oldCollection is 
	 * null
	 */
	public ArrayIndexedCollection(
			Collection oldCollection, int initialCapacity) 
					throws IllegalArgumentException{
		this(initialCapacity);
		addAll(oldCollection);	
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
					+ "cannot add null to collection!");
		}
		
		if(size == capacity){
			Object[] temp = new Object[CAPACITY_INCREMENT * capacity];
			for( int i = 0 ; i < size; i++){
				temp[i] = elements[i];
			}
			temp[size] = value;
			
			elements = temp;
			size++;
			capacity *= CAPACITY_INCREMENT;
			
		} else {
			elements[size] = value;
			size++;
		}
	}
	
	/**
	 * Gets the object on the given index if possible.
	 * 
	 * @param index index of the desiredf element
	 * @return returns the element on the given index
	 * @throws IndexOutOfBoundsException thrown if index is not valid
	 */
	public Object get(int index) throws IndexOutOfBoundsException{
		if(!isValidIndex(index, 0, size)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Cannot reach given index!");
		}
		
		return elements[index];
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i< size; i++){
			if( elements[i].equals(value)){
				return true;
			}
		}
		
		return false;
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		int position = 0;
		boolean found = false;
		
		for(position = 0; position < size; position++){
			if( elements[position].equals(value)){
				found = true;
				break;
			}
		}
		
		if(found){
			for(int i = position; i < size-1; i++){
				elements[i] = elements[i+1];
			}
			size--;
			elements[size] = null;
		}
		
		return found;
	}

	
	/**
	 * Removes the element at the given position. Valid positions
	 * are between 0 and size of collection.
	 * 
	 * @param position position of the element we wish removed
	 * @throws IndexOutOfBoundsException thrown if position is not 
	 * valid
	 */
	public void remove(int position) 
			throws IndexOutOfBoundsException{
		if(!isValidIndex(position, 0, size)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "Cannot remove element from the given"
					+ "position!");
		}
		
		for(int i = position; i < size; i++){
			elements[i] = elements[i+1];
		}
		
		size--;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[size];

		for(int i = 0; i< size; i++){
			newArray[i] = elements[i];
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
			throws IllegalArgumentException {
		if(processor == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot use null processor!");
		}
		
		for(int i = 0; i < size; i++){
			processor.process(elements[i]); 
		}
	}
	

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		elements = new Object[capacity];
		size = 0;
	}
	
	
	/**
	 * Insters the given object to the requested position. Position
	 * should be between 0 and size of the collection.
	 * 
	 * @param value the object to be inserted
	 * @param position position to which we insert
	 * @throws IndexOutOfBoundsException thrown if requested position
	 * is out of bounds
	 * @throws IllegalArgumentException thrown of provided value is
	 * null
	 */
	public void insert(Object value, int position) 
			throws IndexOutOfBoundsException, 
			IllegalArgumentException{
		if(!isValidIndex(position, 0, size + 1)){
			throw new IndexOutOfBoundsException("Warning - "
					+ "cannot insert element into given position!");
		}
		
		if(value == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot insert null into the collection!");
		}
		
		if(size == capacity){
			Object[] temp = new Object[CAPACITY_INCREMENT * capacity];
			for( int i = 0 ; i < position; i++){
				temp[i] = elements[i];
			}
			
			temp[position] = value;
			
			for(int i = position; i< size; i++ ){
				temp[i + 1] = elements[i];
			} 
			
			size++;
			capacity *= CAPACITY_INCREMENT;
			elements = temp;
		} else {
			
			for( int i = size; i >  position; i--){
				elements[i] = elements[i-1];
			}
			
			elements[position] = value;
			size++;
		}
	}
	
	
	/**
	 * Returns the index of the given element if possible. Does not
	 * contain null.
	 * 
	 * @param value object we want found
	 * @return returns the index of the provided object, or -1 if
	 * the provided object is not present
	 */
	public int indexOf(Object value){
		int position = 0;
		for(position = 0; position < size; position++){
			if(elements[position].equals(value)){
				return position;
			}
		}
		return -1;
	}
	
	
}
