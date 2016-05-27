package hr.fer.zemris.java.tecaj.hw6.observer2;


/**
 * Observer class interface used for adding new possibilities to the 
 * observed class(subject). Requires the call of valueChanger from 
 * subject when needed to take effect.
 * 
 * @author Juraj Pejnović
 * @version 1.0
 */
public interface IntegerStorageObserver {
	
	/**
	 * Called when the subject updates it's state. Called from subject
	 * and requires subject as the argument.
	 * @param notification message containing value
	 */
	public void valueChanged(IntegerStorageChanged notification);
}
