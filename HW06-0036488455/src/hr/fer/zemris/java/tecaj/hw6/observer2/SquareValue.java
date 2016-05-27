package hr.fer.zemris.java.tecaj.hw6.observer2;


/**
 * {@link IntegerStorageObserver} that prints the value and it's 
 * square to the standard output with accompanying messages. 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Power of the value written out.
	 */
	private static final int POWER = 2;
	
	/**
	 * First part of the accompanying message.
	 */
	private static final String INTRO_MESSAGE = 
			"Provided new value: ";
	
	/**
	 * Message written before writing out the square.
	 */
	private static final String INTER_MESSAGE = ", Square is ";
	
	
	@Override
	public void valueChanged(IntegerStorageChanged notification) {
		if(notification == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot react to null subject!");
		}
		
		System.out.println(INTRO_MESSAGE + notification.getValue() 
				+ INTER_MESSAGE 
				+ (int)Math.pow(notification.getValue(), POWER));
	}

}
