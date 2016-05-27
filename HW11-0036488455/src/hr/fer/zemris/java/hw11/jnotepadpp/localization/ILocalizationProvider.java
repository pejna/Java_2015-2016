package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Enables the implementing classes to act as providers for localization to
 * classes that require localization. Used with {@link ILocalizationListener}
 * for desired effects.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationListener
 */
public interface ILocalizationProvider {

	/**
	 * Adds a listener to this object.
	 * 
	 * @param listener
	 *            listener to be added
	 */
	void AddLocalizationListener(ILocalizationListener listener);


	/**
	 * Removes the given listener from this object if present.
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener listener);


	/**
	 * Gets translation for the given key.
	 * 
	 * @param string
	 *            key of the desired string
	 * @return returns the found key or throws exception
	 */
	String getTranslation(String string);
}
