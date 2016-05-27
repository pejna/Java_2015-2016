package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;


/**
 * Allows the implementing object to act as a container for data
 * allowing for ordered and fast retrieval of data.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * 
 * @param <K> keys for the values inside the container, each value
 * should have a unique key
 * @param <V> values inside the container
 */
public interface Container<K, V> extends Iterable<V>{
	
	/**
	 * Used for ordered retrieval of data. Data is retrieved
	 * in the same order as it was put in.
	 * 
	 * @return
	 */
	public List<V> getItemList();
	
	
	/**
	 * Used for fast retrieval of data. Search for the data
	 * containing the given key. 
	 * 
	 * @param key key with which to search the data by
	 * @return returns the value with the requested key
	 */
	public V getItem(K key);
	
	/**
	 * Places the object in the container.
	 * 
	 * @param item object to be places
	 */
	public void addItem(Object item);
}
