package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Int wraper class that is able to have observers.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class IntegerStorage {

	/**
	 * Value wrapped.
	 */
	private int value;
	
	/**
	 * List of observers.
	 */
	private List<IntegerStorageObserver> observers;
	
	
	/**
	 * Creates the storage with the given value stored.
	 * @param initialValue value to be stored
	 */
	public IntegerStorage(int initialValue) {
		value = initialValue;
	}
	
	
	/**
	 * Adds the given observer onto the storage. Does not accept null.
	 * 
	 * @param observer new observer to be added
	 */
	public void addObserver(IntegerStorageObserver observer){
		if(observer == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null as an observer!");
		}
		
		if(observers == null){
			observers = new CopyOnWriteArrayList<>();
		}
		
		observers.add(observer);
	}
	
	
	/**
	 * Removes the given observer if present.
	 * @param observer observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer){
		if(observers != null){
			observers.remove(observer);
		}
	}
	
	
	/**
	 * Removes all observers from the storage.
	 */
	public void clearObservers(){
		if(observers != null){
			observers.clear();
		}
	}
	
	
	/**
	 * Gets the value stored.
	 * 
	 * @return returns the value stored
	 */
	public int getValue(){
		return value;
	}
	
	
	/**
	 * Sets the value and updates the observers.
	 * 
	 * @param value new value to be set
	 */
	public void setValue(int value){
		if(this.value != value){
			IntegerStorageChanged notification= 
					new IntegerStorageChanged(
							this, this.value, value);
			
			this.value = value;
			if(observers != null){
				
				for(IntegerStorageObserver observer : observers){
					observer.valueChanged(notification);
				}
			}
		}
	}
	
	
}
