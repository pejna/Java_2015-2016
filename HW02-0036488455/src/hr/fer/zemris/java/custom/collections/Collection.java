/**
 * Package contains custom collection, it's implementations for use 
 * with all object types and supoorting classes for use with these
 * collections.
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Collection of objects. Used for storing many objects for easier managment.
 * Can contain multiples of the same element. Does not contain null.
 * 
 * @author Juraj Pejnovic
 * @version 1.1
 *
 */
public class Collection {

	/**
	 * Default Collection constructor.
	 * 
	 */
	protected Collection() {

	}

	/**
	 * Checks whether the collection is empty.
	 * 
	 * @return returns true if empty, otherwise retuns false
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Checks the size of the collection.
	 * 
	 * @return returns the size of the collection, 0 if empty
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds a new element to the collection.
	 * 
	 * @param value
	 *            object we want to add to the collection
	 */
	public void add(Object value) {
	}

	/**
	 * Checks whether the collection contains the provided object.
	 * 
	 * @param value
	 *            object we want found, can be null
	 * @return returns true of collection contains that object, otherwise
	 *         returns false, if null returns true
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes one occurence of the given object form the collection. Null not
	 * present.
	 * 
	 * @param value
	 *            the object to be removed
	 * @return returns true if removed or false if no matching object has been
	 *         found
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Gets the contents of the collection on a form of an array.
	 * 
	 * @return returns an array of contained objects
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException(
				"Warning - " + "operation not implemented!");
	}

	/**
	 * Processes each element of the collection with the provided processor.
	 * 
	 * @param processor
	 *            processor to be used on elements
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds all members of the provided collection to this collection.
	 * 
	 * @param other
	 *            collection containing elements to be added
	 * @throws IllegalArgumentException
	 *             thrown if provided collection is null
	 */
	public void addAll(Collection other)
			throws IllegalArgumentException {
		if (other == null) {
			throw new IllegalArgumentException("Aborting - "
					+ "Provided collection must not be null.");
		}

		Processor processor = new Processor() {

			@Override
			public void process(Object value) {
				add(value);
			}
		};

		other.forEach(processor);
	}

	/**
	 * Empties the collection.
	 * 
	 */
	public void clear() {
	}

	/**
	 * Checks whether given index is beween given bounds.
	 * 
	 * @param index
	 *            index we wish checked
	 * @param bottom
	 *            bottom bound of the accepted area
	 * @param top
	 *            top bound of the accepted area
	 * @return returns true if the provided index is valid, otherwise returns
	 *         false
	 */
	protected boolean isValidIndex(int index, int bottom, int top) {
		return index >= bottom && index < top;
	}

}
