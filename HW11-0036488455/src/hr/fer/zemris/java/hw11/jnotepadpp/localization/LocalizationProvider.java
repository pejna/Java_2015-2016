package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Localization provider providing localization for it's listeners.
 * <p>
 * It is a singleton to secure only one localization at one time is used in the
 * program.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Singleton
	 */
	private static LocalizationProvider thisOne;

	/**
	 * Locale used.
	 */
	Locale locale;

	/**
	 * Bundle of resources used.
	 */
	ResourceBundle bundle;

	/**
	 * Path to the localizations.
	 */
	private String path;


	/**
	 * Creates the provider with localization at given path.
	 * 
	 * @param path
	 *            path of the localization files
	 */
	private LocalizationProvider(String path) {
		Objects.requireNonNull(path);
		this.path = path;
	}


	/**
	 * Gets the localization provider for the given path and given string part
	 * of name.
	 * 
	 * @param path
	 *            path to the localization files
	 * @param language
	 *            part of localization file names, should be set to "language"
	 * @return returns the provider
	 */
	public static ILocalizationProvider getProvider(String path,
			String language) {
		if (thisOne == null) {
			thisOne = new LocalizationProvider(path);
			thisOne.setLanguage(language);
		}
		return thisOne;
	}


	@Override
	public String getTranslation(String string) {
		Objects.requireNonNull(string);
		return bundle.getString(string);
	}


	public void setLanguage(String language) {
		Objects.requireNonNull(language);
		locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(path, locale);
		fire();
	}
}
