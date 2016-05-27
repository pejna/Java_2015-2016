package hr.fer.zemris.java.tecaj.hw6.observer1;


/**
 * Class demonstrates the use of {@link IntegerStorage} and 
 * {@link IntegerStorageObserver}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * Initial value of the storage.
	 */
	private static final int INITIAL_VALUE = 20;
	
	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(INITIAL_VALUE);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
 		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
	}

}
