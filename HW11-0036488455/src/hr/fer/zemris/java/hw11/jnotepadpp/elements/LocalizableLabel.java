package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.util.Objects;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * {@link JLabel} intended for use with {@link ILocalizationListener}.
 * <p>
 * In the construction it is required to give the localization key for the text
 * of the label. For other information look to {@link JLabel} documentation.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see JLabel
 * @see ILocalizationProvider
 */
public class LocalizableLabel extends JLabel implements ILocalizationListener {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 6238512840930248875L;

	/**
	 * Localization key of the text.
	 */
	private String textKey;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;


	/**
	 * Creates the label. Label shows the text at with the given key in the
	 * localization file.
	 * 
	 * @param textKey
	 *            localization key of the text
	 * @param provider
	 *            localization provider
	 */
	public LocalizableLabel(String textKey, ILocalizationProvider provider) {
		Objects.requireNonNull(textKey);
		Objects.requireNonNull(provider);
		this.textKey = textKey;
		this.provider = provider;
		provider.AddLocalizationListener(this);
	}


	@Override
	public void localizationChanged() {
		setText(provider.getTranslation(textKey));
	}

}
