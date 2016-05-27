package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * {@link IntegerStorageObserver} that prints the number of times it's 
 * subject had it's number changed after attaching itself to it.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Number of times called.
	 */
	int counter = 0;
	
	/**
	 * Accompanying message with the number.
	 */
	private static final String INTRO_MESSAGE = 
			"Number of value changes since tracking: ";
	
	
	@Override
	public void valueChanged(IntegerStorageChanged notification) {
		if(notification == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot react to null subject!");
		}
		
		System.out.println(INTRO_MESSAGE + ++counter);
	}

}
