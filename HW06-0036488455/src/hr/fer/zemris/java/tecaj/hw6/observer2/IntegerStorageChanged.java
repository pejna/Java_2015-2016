package hr.fer.zemris.java.tecaj.hw6.observer2;


/**
 * Carries information of changes occured in the 
 * {@link IntegerStorage} objects.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class IntegerStorageChanged {
	
	/**
	 * Storage this notification belongs to.
	 */
	private IntegerStorage storage;
	
	/**
	 * Value before the change took effect.
	 */
	private int valueBeforeChange;
	
	/**
	 * Value after the change took effect.
	 */
	private int value;
	
	
	/**
	 * Creates the message with the given arguments.
	 * 
	 * @param storage storage this message belongs to
	 * @param valueBeforeChange value before change
	 * @param value value after change
	 */
	public IntegerStorageChanged(IntegerStorage storage,
			int valueBeforeChange, int value) {
		this.storage = storage;
		this.valueBeforeChange = valueBeforeChange;
		this.value = value;
	}

	
	/**
	 * @return the storage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	
	/**
	 * @return the valueBeforeChange
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}
	
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
