package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Enables the implementing classes to act as listeners for
 * {@link ILocalizationProvider} implementing classes. To subscribe to a
 * provider a listener should use addLocalizationListener() method with
 * reference to it passes as a reference. Same for unsubbing.
 * 
 * @author Peda
 *
 */
public interface ILocalizationListener {

	/**
	 * Called to revalidate the localization recieved.
	 */
	void localizationChanged();
}
