package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Objects;

/**
 * Localization provider used for safe and efficient memory managment.
 * <p>
 * If using a component that is going to be used very briefly, and no listener
 * lists are desired inside, than this can be used instead. Just subscribe all
 * components to this one, and call connect() to connect this bridge to the main
 * localization provider. When finished just call disconnect() and led the
 * garbage collector take care of it.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationListener
 * @see ILocalizationProvider
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider
		implements ILocalizationListener {

	/**
	 * Main provider.
	 */
	private ILocalizationProvider provider;


	/**
	 * Creates the bridge connecting listeners to the given provider.
	 * 
	 * @param provider
	 *            provider to bridge to
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		Objects.requireNonNull(provider);
		this.provider = provider;
	}


	@Override
	public String getTranslation(String string) {
		return provider.getTranslation(string);
	}


	@Override
	public void localizationChanged() {
		fire();
	}


	/**
	 * Connect the bridge to the provider.
	 */
	public void connect() {
		provider.AddLocalizationListener(this);
	}


	/**
	 * Disconnect the bridge from the provider.
	 */
	public void disconnect() {
		provider.removeLocalizationListener(this);
	}
}
