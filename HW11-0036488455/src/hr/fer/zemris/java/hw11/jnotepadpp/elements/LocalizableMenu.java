package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.util.Objects;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * {@link JMenu} intended for use with {@link ILocalizationProvider}
 * implementing classes.
 * <p>
 * It is required to provide the name key in the constructor as the key for the
 * localization string. If no localization is needed it is better to use
 * {@link JMenu} as this class has no aditional benefits.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see JMenu
 * @see ILocalizationProvider
 * @see ILocalizationListener
 */
public class LocalizableMenu extends JMenu implements ILocalizationListener {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = -8094630724288454955L;

	/**
	 * Key of the name of the menu.
	 */
	private String nameKey;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider;


	/**
	 * Creates the menu. Menu shows the text at with the given key in the
	 * localization file.
	 * 
	 * @param nameKey
	 *            key of the name in the localization file
	 * @param provider
	 *            localization provider
	 */
	public LocalizableMenu(String nameKey, ILocalizationProvider provider) {
		Objects.requireNonNull(nameKey);
		Objects.requireNonNull(provider);
		this.nameKey = nameKey;
		this.provider = provider;
		provider.AddLocalizationListener(this);
	}


	@Override
	public void localizationChanged() {
		setText(provider.getTranslation(nameKey));
	}

}
