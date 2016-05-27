package hr.fer.zemris.java.tecaj.hw6.observer1;


/**
 * {@link IntegerStorageObserver} that prints double the value's
 * amount to the standard output for first n times, n is given
 * via constructor. When finished deletes itself from the subject.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver{

	/**
	 * Number of times left to be called.
	 */
	private int callNumber;
	
	/**
	 * What is written out before double value.
	 */
	private static final String INTRO_MESSAGE = "Double value: ";
	
	/**
	 * Multiplier of the printed number.
	 */
	private static final int INCREMENTATION = 2;
	
	
	/**
	 * Constructs the {@link DoubleValue} that is active for the
	 * given amount of calls. Does not accept numbers lesser than 1.
	 * 
	 * @param number max number of times called
	 */
	public DoubleValue(int number) {
		if(number <= 0){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot call DoubleValue less than once!");
		}
		
		callNumber = number;
	}
	
	
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot react to null subject!");
		}
		System.out.println(INTRO_MESSAGE + 
				istorage.getValue()*INCREMENTATION);
		
		callNumber--;
		if(callNumber == 0){
			istorage.removeObserver(this);
		}
	}

}
