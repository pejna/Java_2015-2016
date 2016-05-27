package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Hashtable implementation that uses TableEntry as it's building
 * block. Stores lists of {@link TableEntry} in an array.
 * 
 * @author Juraj Pejnovic
 *
 * @param <K> key of the given item
 * @param <V> value of the given item
 */
public class SimpleHashtable<K, V> 
		implements Hashtable<K, V>, 
		Iterable<SimpleHashtable.TableEntry<K, V>>{

	
	/**
	 * Used as a building block to represent key-value pairs in the
	 * table.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 * @param <K> key of the pair
	 * @param <V> value of the pair
	 */
	public static class TableEntry<K, V>{
		
		/**
		 * Key of the pair.
		 */
		private K key;
		
		/**
		 * Value of the pair
		 */
		private V value;
		
		/**
		 * Next 
		 */
		public TableEntry<K, V> next;
		
		
		/**
		 * Creates a table entry with the given key, value and 
		 * reference to the next entry if present.
		 * 
		 * @param key key of the pari
		 * @param value value of the pair
		 * @param next next {@link TableEntry}
		 */
		public TableEntry(K key, V value, 
				TableEntry<K, V> next){	
			if(key == null){
				throw new IllegalArgumentException("Warning - "
						+ "Entry cannot have null key!");
			}
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * @return the key
		 */
		public K getKey(){
			return this.key;
		}
		
		
		/**
		 * @return the value
		 */
		public V getValue(){
			return value;
		}
		
		
		/**
		 * Sets the value. Cannot be null.
		 * @param value value to be set
		 */
		public void setValue(V value){
			if(value == null){
				throw new IllegalArgumentException("Warning - "
						+ "Cannot place null into table!");
			}
			this.value = value;
		}
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object other) {
			if(!other.getClass().equals(this.getClass())){
				return false;
			}
			return this.key.equals(
					((TableEntry<K, V>)other).key);
		}
		
		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}
	
	
	/*
	 * ******** Attributes *******************************************
	 */
	
	
	/**
	 * Contains table entry lists
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Number of entries present.
	 */
	private int size;
	
	/**
	 * Counts the amount of modification as safeguard for iteration.
	 */
	private int modificationCount;
	
	
	/*
	 * ******** Constrants *******************************************
	 */
	
	
	/**
	 * Default capacity of the table.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Minimal capacity of the table.
	 */
	private static final int MINIMAL_CAPACITY = 2;
	
	/**
	 * Factor of enlargement if need for resizing appears.
	 */
	private static final int ENLARGEMENT_FACTOR = 2;
	
	/**
	 * Opens to string output.
	 */
	private static final String TO_STRING_OPENER = "[";
	
	/**
	 * Closes to string output.
	 */
	private static final String TO_STRING_CLOSER = "]";
	
	/**
	 * Places bewteen entries in to string output.
	 */
	private static final String TO_STRING_DELIMITER = ", ";
	
	
	/*
	 * ******** Constructor methods **********************************
	 */
	
	
	/**
	 * Creates a default table.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a table with given number as capacity. Does not 
	 * accept numbers lesser than 1.
	 * 
	 * @param capacity capacity of the table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if(capacity < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create SimpleHashTable with "
					+ "capacity of " + capacity + "!");
		}
		
		int realCapacity = MINIMAL_CAPACITY;
		while(realCapacity < capacity){
			realCapacity *=2;
		}
		
		table = (TableEntry<K, V>[])
				new TableEntry[realCapacity];
		modificationCount = 0;
		size = 0;
	}


	/*
	 * ******** Hashtable methods ************************************
	 */
	
	
	@Override
	public void put(K key, V value) {
		if(key == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot place null key in table!");
		}
		
		int index = keyToHash(key);
		TableEntry<K, V> current = table[index];
		if(current == null){
			table[index] = new TableEntry<K, V>
								(key, value, null);
			size++;
			modificationCount++;
			return;
		}
		
		while(current.next != null){
			if(current.getKey().equals(key)){
				current.setValue(value);
				return;
			}
			current = current.next;
		}
		
		if(current.getKey().equals(key)){
			current.setValue(value);
			return;
		}
		
		current.next = new TableEntry<K, V>(key, value, null);
		modificationCount++;
		size++;
		checkSize();
	}

	
	@Override
	public V get(Object key) {
		if(key == null){
			return null;
		}

		int index = keyToHash(key);
		TableEntry<K, V> current = table[index];
		while(current != null){
			if(current.key.equals(key)){
				return current.getValue();
			}
			
			current = current.next;
		}
		
		return null;
	}


	@Override
	public int size() {
		return size;
	}


	@Override
	public boolean containsKey(Object key) {
		if(key == null){
			return false;
		}
		int index = keyToHash(key);
		TableEntry<K, V> entry = table[index];
		while(entry != null){
			if( entry.getKey().equals(key)){
				return true;
			}
			entry = entry.next;
		}
		
		return false;
	}


	@Override
	public boolean containsValue(Object value) {
		for(TableEntry<K, V> entry : this){
			if(entry.getValue() == null && value == null){
				return true;
			}
			if(entry.getValue().equals(value)){
				return true;
			}
		}
		return false;
	}


	@Override
	public void remove(Object key) {
		int index = keyToHash(key);
		TableEntry<K, V> current = table[index];
		if(current == null){
			return;
		}
		
		if(current.key.equals(key)){
			table[index] = current.next;
			size--;
			modificationCount++;
			return;
		}
		
		while(current.next != null){
			if(current.next.key.equals(key)){
				current.next = current.next.next;
				size--;
				modificationCount++;
				return;
			} 
			current = current.next;
		}
		
		return;
	}


	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	@Override
	public void clear() {
		for(int i = 0; i < table.length; i++){
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/**
	 * Transform the given key to hashcode usable by the table.
	 * 
	 * @param key key to be hashcodizied
	 * @return returns the hashcode of the key
	 */
	private int keyToHash(Object key){
		return Math.abs(key.hashCode())%table.length;
	}
	
	/**
	 * Checks if the size of the table is 75% or greater than 
	 * capacity.
	 */
	private void checkSize(){
		if(size >= table.length * 0.75){
			restructure();
		}
	}
	
	
	/**
	 * Restructures the table to twice the capacity.
	 */
	@SuppressWarnings("unchecked")
	private void restructure(){
		SimpleHashtable<K, V> newTable = 
				new SimpleHashtable<>();
		
		newTable.table = table;
		newTable.size = size;
		
		table = (TableEntry<K, V>[])
				new TableEntry[table.length * ENLARGEMENT_FACTOR];
		size = 0;
		
		for(TableEntry<K, V> entry : newTable){
			this.put(entry.getKey(), entry.getValue());
		}
		
		modificationCount++;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TO_STRING_OPENER);
		
		for(TableEntry<K, V> entry : this){
			sb.append(entry.toString()).append(TO_STRING_DELIMITER);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append(TO_STRING_CLOSER);
		return sb.toString();
	}
	
	
	/*
	 * ******** Table iterator class *********************************
	 */
	
	/**
	 * Iterator over the table.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private class IteratorImpl 
			implements Iterator<TableEntry<K, V>> {
		
		/**
		 * Current entry the iterator points to.
		 */
		private TableEntry<K, V> current;
		
		/**
		 * Index in the table of the current iterator.
		 */
		private int index;
		
		/**
		 * If it is the first iteration.
		 */
		private boolean isFirst;
		
		/**
		 * Used for safe iterating. Must be same as table modification
		 * count to iterate.
		 */
		private int currentModificationCount;
		
		/**
		 * Checks if the current entry is removed.
		 */
		private boolean isRemoved;
		
		
		/**
		 * Creates an iterator.
		 */
		public IteratorImpl(){
			currentModificationCount = modificationCount;
			
			index = 0;
			while(table[index] == null){
				index++;
				if(index == table.length){
					throw new NoSuchElementException("Warning - "
							+ "Cannot iterate empty table!");
				}
			}
			
			current = table[index];
			
			isFirst = true;
			isRemoved = false;
		}
		
		
		@Override
		public boolean hasNext() {
			checkChangedTable();
			
			if(isFirst){
				return true;
			}
			
			if(current != null && current.next != null){
				return true;
			}
			
			int tempIndex = index + 1;
			while(tempIndex < table.length){
				
				if(table[tempIndex] != null){
					return true; 
				}
				tempIndex++;
			} 
			
			return false;
		}

		
		@Override
		public TableEntry<K, V> next() {	
			checkChangedTable();
			
			if(!hasNext()){
				throw new NoSuchElementException("Warning - "
						+ "No more elements to iterate!");
			}
			
			isRemoved = false;
			if(isFirst){
				isFirst = false;
				return current;
			}
			
			if(current.next != null){
				current = current.next;
				return current;
			}
			
			index++;
			while(index < table.length){
				if(table[index] != null){
					current = table[index];
					return current;
				}
				
				index++;
			}
			
			return null;
		}
	

		@Override
		public void remove() {
			checkChangedTable();
			if(isRemoved){
				throw new IllegalStateException("Warning - "
						+ "Cannot remove same element more times!");
			}
			if(isFirst){
				throw new IllegalStateException("Warning - "
						+ "Cannot remove from uninitialized "
						+ "iterator!");
			}
			
			isRemoved = true;
			SimpleHashtable.this.remove(current.getKey());
			currentModificationCount++;
			
		}
		
		/**
		 * If iterated table has been changed by someone else
		 * other than iterator throws 
		 * {@link ConcurrentModificationException}.
		 */
		private void checkChangedTable(){
			if(currentModificationCount != modificationCount){
				throw new ConcurrentModificationException("Warning - "
						+ "Table was modified and is no longer "
						+ "iterable with this iterator!"); 
			}
		}
	}
	
	
}
