package hr.fer.zemris.java.tecaj.hw5.collections;

/**
 * Requires the implementing class to act as a hashtable.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * 
 * @param <K> key type for the entries in the table
 * @param <V> value type of the entries in the table
 */
public interface Hashtable<K, V> {

	/**
	 * Places the given value by the given key inside the table.
	 * 
	 * @param key key for the value to be known by
	 * @param value value to be placed inside
	 */
	public void put(K key, V value);
	
	
	/**
	 * Gets the value that goes by the given key.
	 * 
	 * @param key key to find the value by
	 * @return returns the value found
	 */
	public V get(Object key);
	
	
	/**
	 * Gets the size of the table.
	 * @return returns the amount of values stored
	 */
	public int size();
	
	
	/**
	 * Checks whether the given key is inside the table.
	 * 
	 * @param key key to be found
	 * @return returns true if found, otherwise false
	 */
	public boolean containsKey(Object key);
	
	
	/**
	 * Checks whether the given value is inside the table.
	 * 
	 * @param value value to be found
	 * @return returns true if found, otherwise false
	 */
	public boolean containsValue(Object value);
	
	
	/**
	 * Removes the requested object from table if possible.
	 * 
	 * @param key key of the object we want removed
	 */
	public void remove(Object key);
	
	
	/**
	 * Checks if the table is empty.
	 * 
	 * @return returns true if empty, otherwise false
	 */
	public boolean isEmpty();
	
	
	/**
	 * Empties the table.
	 */
	public void clear();
}
