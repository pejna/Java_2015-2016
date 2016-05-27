package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for {@link ILocalizationProvider} implementing classes providing
 * services of saving subscribing, unsubscribing and managing listeners.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationListener
 */
public abstract class AbstractLocalizationProvider
		implements ILocalizationProvider {

	/**
	 * List of listeners.
	 */
	List<ILocalizationListener> listeners;


	/**
	 * Creates the localization provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}


	@Override
	public void AddLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener);
		listeners.add(listener);
		listener.localizationChanged();
	}


	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener);
		listeners.remove(listener);

	}


	/**
	 * Notifies all listeners calling their localizationChanged() method.
	 */
	public void fire() {
		List<ILocalizationListener> tempList = new ArrayList<>(listeners);

		for (ILocalizationListener listener : tempList) {
			listener.localizationChanged();
		}
	}


	@Override
	public abstract String getTranslation(String string);

}
